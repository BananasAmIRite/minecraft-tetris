package org.bananasamirite.minecrafttetris;

import org.bananasamirite.minecrafttetris.blockdata.TetrisBlock;
import org.bananasamirite.minecrafttetris.blockdata.TetrisBlockGroup;
import org.bananasamirite.minecrafttetris.controls.PlayerTetrisItemListener;
import org.bananasamirite.minecrafttetris.game.Block;
import org.bananasamirite.minecrafttetris.game.GameBlockGroup;
import org.bananasamirite.minecrafttetris.game.Rotation;
import org.bananasamirite.minecrafttetris.utils.Coordinate;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TetrisGame extends GameThread {
    private final TetrisConfig config;
    private GameBlockGroup current;
    private final TetrisRenderer renderer;
    private final MinecraftTetris plugin;
    private final TetrisGameManager manager;
    private final List<List<Block>> map = new ArrayList<>();
    private final Player player;
    private final PlayerTetrisItemListener controlListener;
    private int score = 0;

    private final int width;
    private final int height;
    public TetrisGame(TetrisGameManager manager, TetrisConfig cfg, Player p) {
        super(cfg.getTimeBetweenChange());
        this.plugin = manager.getPlugin();
        this.manager = manager;
        this.config = cfg;
        this.width = config.getWidth();
        this.height = config.getHeight();
        this.player = p;
        this.controlListener = new PlayerTetrisItemListener(this);
        this.renderer = new TetrisRenderer(this, cfg, p);
//        loadGroups(this.config.getBlockData());
        this.populateMap(cfg.getWidth(), cfg.getHeight());
        this.newCurrent();
        this.plugin.getServer().getPluginManager().registerEvents(this.controlListener, this.plugin);
    }

    private void populateMap(int w, int h) {
        for (int y = 0; y < h; y++) {
            List<Block> l = new ArrayList<>();
            for (int x = 0; x < w; x++) {
                l.add(new Block());
            }
            this.map.add(l);
        }
    }

    private void newCurrent() {
        TetrisBlockGroup next = this.plugin.getGroups().randBlockGroup();
        Rotation initialRotation = Rotation.getEnum((int) Math.floor(Math.random() * Rotation.values().length) );
        current = new GameBlockGroup(this, next, new Coordinate(config.getWidth() / 2, -next.getRotation(initialRotation).getHeightFromAnchorToBottom()), initialRotation);
    }

    @Override
    public void runGameLoop() {
        if (current == null) this.newCurrent();

        // removing full rows
        Optional<List<Block>> firstNonEmptyElem = this.map.stream().filter(e -> e.stream().filter(Block::isLit).toList().size() != 0).findFirst();
        if (firstNonEmptyElem.isPresent()) {
            int firstNonEmptyRow = this.map.indexOf(firstNonEmptyElem.get());
            int removed = 0;
            for (int i = map.size() - 1; i >= firstNonEmptyRow; i--) {
                List<Block> row = map.get(i);
                int filteredSize = row.stream().filter(Block::isLit).toList().size();
                if (filteredSize != row.size() && filteredSize != 0) continue;
                // every block is filled
                map.remove(i);
                if (filteredSize == row.size()) removed++;
            }
            for (int i = 0; i < removed; i++) {
                List<Block> r = new ArrayList<>();
                for (int j = 0; j < width; j++) r.add(new Block());
                this.map.add(0, r);
            }

            addScore(removed);
        }


        // lower the current block
        this.current.setAnchorPosition(new Coordinate(this.current.getAnchorPosition().getX(), this.current.getAnchorPosition().getY() + 1));

        // check collision
        boolean isCollided = false;
        for (TetrisBlock block : this.current.getListWithAnchorPos()) {
            if (
                    block.getCoordinate().getY() >= 0 && (
                            this.getBlockAt(block.getCoordinate().getX(), block.getCoordinate().getY() + 1) == null ||
                                    this.getBlockAt(block.getCoordinate().getX(), block.getCoordinate().getY() + 1).isLit()
                    )
            ) {
                isCollided = true;
                break;
            }
        }

        if (isCollided) {
            for (TetrisBlock b : this.current.getListWithAnchorPos()) {
                Block block = this.getBlockAt(b.getCoordinate().getX(), b.getCoordinate().getY());
                if (block != null) block.light();
            }
            this.current = null;
        }

        this.renderer.render();

        if (this.map.get(0).stream().filter(Block::isLit).toList().size() != 0) gameEnd();
    }

    public void gameEnd() {
        this.stopGame();
        this.renderer.clear();
        this.controlListener.restoreInventory();
        HandlerList.unregisterAll(this.controlListener);
        this.manager.removeGame(this);
        this.player.sendMessage("Game Over. Final Score: " + this.score);
    }

    private void addScore(int linesCleared) {
        score += linesCleared * 10;
    }

    public GameBlockGroup getCurrent() {
        return current;
    }

    public List<List<Block>> getMap() {
        return map;
    }

    public MinecraftTetris getPlugin() {
        return plugin;
    }

    public Material getLitMaterial() {
        return config.getBitBlock();
    }

    public Material getBackgroundMaterial() {
        return config.getBackgroundBlock();
    }

    public Material getProjectedMaterial() {
        return config.getProjectedMaterial();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Block getBlockAt(int x, int y) {
        try {
            return this.map.get(y).get(x);
        } catch (IndexOutOfBoundsException err) {
            return null;
        }
    }

    public void setSpeeding(boolean speeding) {
        this.setTimeBetweenTicks(speeding ? (long) (this.config.getTimeBetweenChange() / this.config.getSpeedUpRate()) : this.config.getTimeBetweenChange());
    }

    public Player getPlayer() {
        return player;
    }
}

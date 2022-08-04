package org.bananasamirite.minecrafttetris;

import org.bananasamirite.minecrafttetris.blockdata.TetrisBlock;
import org.bananasamirite.minecrafttetris.game.Block;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TetrisRenderer {
    private final Location renderStartLocation;
    private final TetrisGame game;

    // rotation data for the screen
    private int xInc; // either xInc or zInc is going to be 0, the other is going to be 1
    private int zInc;
    public TetrisRenderer(TetrisGame game, TetrisConfig config, Player p) {
        this.game = game;
        this.renderStartLocation = calcRenderStartLocation(config, p);
    }

    private Location calcRenderStartLocation(TetrisConfig cfg, Player p) {
        int x = (int) p.getLocation().getX() + p.getFacing().getModX() * cfg.getDistFromScreen() + ( p.getFacing().getModZ()) * cfg.getWidth() / 2; // TODO: uh
        int z = (int) p.getLocation().getZ() + p.getFacing().getModZ() * cfg.getDistFromScreen() + ( - p.getFacing().getModX()) * cfg.getWidth() / 2;

        xInc = -p.getFacing().getModZ();
        zInc = p.getFacing().getModX();

        return new Location(p.getWorld(), x, (int) (p.getLocation().getY() + cfg.getHeight() / 2), z);
    }

    public void render() {
        new BukkitRunnable() {
            @Override
            public void run() {
                // map
                for (int y = 0; y < game.getMap().size(); y++) {
                    for (int x = 0; x < game.getMap().get(y).size(); x++) {
                        Block b = game.getMap().get(y).get(x);
                        Location l = renderStartLocation.clone().add(xInc * x, -y, zInc * x);

                        l.getBlock().setType(b.isLit() ? game.getLitMaterial() : game.getBackgroundMaterial());
                    }
                }

//                Location loc = renderStartLocation.clone().add(xInc * 25, -99, zInc * 25);
//                loc.getBlock().setType(Material.GOLD_BLOCK);
//
//                game.getCurrent().getProjectedAnchorList();
//                // projected block
                if (game.getCurrent() == null) return;
                for (TetrisBlock block : game.getCurrent().getProjectedAnchorList()) {
                    if (block.getCoordinate().getX() < 0 || block.getCoordinate().getX() > game.getWidth() || block.getCoordinate().getY() < 0 || block.getCoordinate().getY() > game.getHeight()) continue;
                    Location l = renderStartLocation.clone().add(xInc * block.getCoordinate().getX(), -block.getCoordinate().getY(), zInc * block.getCoordinate().getX());
                    l.getBlock().setType(game.getProjectedMaterial());
                }

                // current block
                for (TetrisBlock block : game.getCurrent().getListWithAnchorPos()) {
                    if (block.getCoordinate().getX() < 0 || block.getCoordinate().getX() > game.getWidth() || block.getCoordinate().getY() < 0 || block.getCoordinate().getY() > game.getHeight()) continue;
                    Location l = renderStartLocation.clone().add(xInc * block.getCoordinate().getX(), -block.getCoordinate().getY(), zInc * block.getCoordinate().getX());
                    l.getBlock().setType(game.getLitMaterial());
                }



            }
        }.runTask(game.getPlugin());
    }

    public void clear() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (int y = 0; y < game.getMap().size(); y++) {
                    for (int x = 0; x < game.getMap().get(y).size(); x++) {
                        Location l = renderStartLocation.clone().add(xInc * x, -y, zInc * x);

                        l.getBlock().setType(Material.AIR);
                    }
                }
            }
        }.runTaskLater(game.getPlugin(), 20 * 2);
    }
}

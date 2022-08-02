package org.bananasamirite.minecrafttetris;

import org.bananasamirite.minecrafttetris.exceptions.AnchorNotFoundException;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TetrisGameManager {
    private final List<TetrisGame> games;
    private final MinecraftTetris plugin;

    public TetrisGameManager(MinecraftTetris plugin) {
        this.plugin = plugin;
        this.games = new ArrayList<>();
    }

    public TetrisGame createGame(Player p) {
        TetrisGame g = new TetrisGame(plugin, plugin.getTetrisConfig(), p);
        this.games.add(g);
        g.start();
        return g;
    }

    public void removeGame(TetrisGame game) {
        games.remove(game);
    }

    public void stopAllGames() {
        for (TetrisGame game : games) {
            game.gameEnd();
        }
        games.clear();
    }
}

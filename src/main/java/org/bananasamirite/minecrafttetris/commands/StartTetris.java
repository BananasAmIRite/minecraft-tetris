package org.bananasamirite.minecrafttetris.commands;

import org.bananasamirite.minecrafttetris.MinecraftTetris;
import org.bananasamirite.minecrafttetris.TetrisGame;
import org.bananasamirite.minecrafttetris.exceptions.AnchorNotFoundException;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartTetris implements CommandExecutor {
    private MinecraftTetris plugin;
    public StartTetris(MinecraftTetris pl) {
        this.plugin = pl;
        pl.getServer().getPluginCommand("starttetris").setExecutor(this);
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player plr)) {
            sender.sendMessage("Must be a player");
            return true;
        }

        BlockFace face = plr.getFacing();

        if (face != BlockFace.NORTH && face != BlockFace.EAST && face != BlockFace.SOUTH && face != BlockFace.WEST) {
            sender.sendMessage("Must face cardinal direction");
            return true;
        }

//        try {
//            TetrisGame game = new TetrisGame(plugin, plugin.getTetrisConfig(), plr);
//            game.start();
        this.plugin.getGameManager().createGame(plr);
//        } catch (AnchorNotFoundException e) {
//            plugin.getLogger().warning("Error while parsing anchor: " + e.getMessage());
//            sender.sendMessage("Error while creating Tetris game");
//        }

        return true;
    }
}

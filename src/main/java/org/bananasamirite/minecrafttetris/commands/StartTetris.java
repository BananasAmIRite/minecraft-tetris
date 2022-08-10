package org.bananasamirite.minecrafttetris.commands;

import org.bananasamirite.minecrafttetris.MinecraftTetris;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class StartTetris implements CommandExecutor {
    private final MinecraftTetris plugin;
    public StartTetris(MinecraftTetris pl) {
        this.plugin = pl;
        pl.getServer().getPluginCommand("starttetris").setExecutor(this);
        pl.getServer().getPluginCommand("starttetris").setTabCompleter((sender, command, alias, args) -> new ArrayList<>());
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

        if (this.plugin.getGameManager().isPlayerPlaying(plr)) {
            sender.sendMessage("Please finish your current game of tetris. ");
            return true;
        }

        this.plugin.getGameManager().createGame(plr);

        return true;
    }
}

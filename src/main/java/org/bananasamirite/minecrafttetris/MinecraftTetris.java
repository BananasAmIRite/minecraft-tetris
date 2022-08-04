package org.bananasamirite.minecrafttetris;

import org.bananasamirite.minecrafttetris.blockdata.TetrisBlockGroupManager;
import org.bananasamirite.minecrafttetris.commands.StartTetris;
import org.bananasamirite.minecrafttetris.exceptions.AnchorNotFoundException;
import org.bananasamirite.minecrafttetris.utils.Utils;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;

public final class MinecraftTetris extends JavaPlugin {
    private TetrisConfig config;
    private TetrisGameManager gameManager;
    private TetrisBlockGroupManager groups;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Starting plugin...");

        this.gameManager = new TetrisGameManager(this);
        this.groups = new TetrisBlockGroupManager();

        checkFiles();
        try {
            this.config = loadConfig();
            this.groups.registerBlockDatas(this.config.getBlockData());
        } catch (FileNotFoundException | AnchorNotFoundException e) {
            getLogger().severe("Could not parse config or tetris data: " + e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
        }

        new StartTetris(this);
    }

    private void checkFiles() {
//        if (!new File(getDataFolder() + File.separator + "config.yml").exists()) {
            saveResource("config.yml", false);
            saveResource("tetris_data.txt", false);
//        }
    }

    private TetrisConfig loadConfig() throws FileNotFoundException {
        return new TetrisConfig(
                getConfig().getInt("width"),
                getConfig().getInt("height"),
                Material.getMaterial(getConfig().getString("backgroundBlock")),
                Material.getMaterial(getConfig().getString("bitBlock")),
                Material.getMaterial(getConfig().getString("projectedBlock")),
                Arrays.asList(Utils.getStringFile(getDataFolder() + File.separator + getConfig().getString("blockDataPath")).split(":")),
                getConfig().getLong("timeBetweenChange"),
                getConfig().getDouble("speedUpRate"),
                getConfig().getInt("distFromScreen")
        );
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.gameManager.stopAllGames();
    }

    public TetrisGameManager getGameManager() {
        return gameManager;
    }

    public TetrisConfig getTetrisConfig() {
        return config;
    }

    public TetrisBlockGroupManager getGroups() {
        return groups;
    }
}

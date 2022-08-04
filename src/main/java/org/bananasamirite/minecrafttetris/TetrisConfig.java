package org.bananasamirite.minecrafttetris;

import org.bukkit.Material;

import java.util.List;

public class TetrisConfig {
    private final int width;
    private final int height;
    private final Material backgroundBlock;
    private final Material bitBlock;
    private final Material projectedMaterial;
    private final List<String> blockData;
    private final long timeBetweenChange;
    private final double speedUpRate;
    private final int distFromScreen;

    public TetrisConfig(int width, int height, Material bgBlock, Material bitBlock, Material projectedBlock, List<String> blockData, long timeBetweenChange, double speedUpRate, int distFromScreen) {
        this.width = width;
        this.height = height;
        this.backgroundBlock = bgBlock;
        this.bitBlock = bitBlock;
        this.projectedMaterial = projectedBlock;
        this.blockData = blockData;
        this.timeBetweenChange = timeBetweenChange;
        this.speedUpRate = speedUpRate;
        this.distFromScreen = distFromScreen;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Material getBackgroundBlock() {
        return backgroundBlock;
    }

    public Material getBitBlock() {
        return bitBlock;
    }

    public List<String> getBlockData() {
        return blockData;
    }

    public long getTimeBetweenChange() {
        return timeBetweenChange;
    }

    public double getSpeedUpRate() {
        return speedUpRate;
    }

    public int getDistFromScreen() {
        return distFromScreen;
    }

    public Material getProjectedMaterial() {
        return projectedMaterial;
    }
}

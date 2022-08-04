package org.bananasamirite.minecrafttetris.blockdata;

import org.bananasamirite.minecrafttetris.exceptions.AnchorNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class TetrisBlockGroupRotation {
    private List<TetrisBlock> blocks;
    private TetrisBlock anchor;
    private final TetrisBlockGroupRotationConfig config;

    public TetrisBlockGroupRotation() throws AnchorNotFoundException {
        this(new ArrayList<>(), new TetrisBlockGroupRotationConfig());
    }

    public TetrisBlockGroupRotation(List<TetrisBlock> blocks, TetrisBlockGroupRotationConfig cfg) throws AnchorNotFoundException {
        this.blocks = blocks;
        this.config = cfg;
        setupAnchor();
    }

    private void setupAnchor() throws AnchorNotFoundException {
        // check for anchor
        for (TetrisBlock block : this.blocks) {
            if (block.isAnchor()) {
                anchor = block;
                break;
            }
        }
        if (anchor == null) throw new AnchorNotFoundException("No anchor found. ");
    }

    public static TetrisBlockGroupRotation fromString(String s) throws AnchorNotFoundException {
        String[] data = s.split(System.lineSeparator());
        List<TetrisBlock> blocks = new ArrayList<>();
        TetrisBlockGroupRotationConfig config = new TetrisBlockGroupRotationConfig();
        for (String datum : data) {
            if (datum.startsWith("CFG")) {
                // config data
                config = TetrisBlockGroupRotationConfig.fromString(datum);
                continue;
            }
            TetrisBlock b = TetrisBlock.fromString(datum);
            if (b != null) blocks.add(b);
        }

        return new TetrisBlockGroupRotation(blocks, config);
    }

    public List<TetrisBlock> getBlocks() {
        return blocks;
    }

    public TetrisBlockGroupRotationConfig getConfig() {
        return config;
    }

    public void setBlocks(List<TetrisBlock> blocks) {
        this.blocks = blocks;
    }

    public int getHeightFromAnchorToBottom() {
        int lowest = this.anchor.getCoordinate().getY();
        double highest = Double.NEGATIVE_INFINITY;
        for (TetrisBlock b : blocks) {
            highest = Math.max(b.getCoordinate().getY(), highest);
        }

        return (int) Math.abs(highest - lowest);
    }

    public TetrisBlock getAnchor() {
        return anchor;
    }
}

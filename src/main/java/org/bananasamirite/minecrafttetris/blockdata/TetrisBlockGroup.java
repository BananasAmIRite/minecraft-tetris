package org.bananasamirite.minecrafttetris.blockdata;

import org.bananasamirite.minecrafttetris.utils.Coordinate;
import org.bananasamirite.minecrafttetris.exceptions.AnchorNotFoundException;
import org.bananasamirite.minecrafttetris.game.Rotation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TetrisBlockGroup {
    private TetrisBlockGroupRotation blocks;
    private TetrisBlockGroupRotation rotCC; // one rotation to the left; counter-clockwise
    private TetrisBlockGroupRotation rotC; // one rotation to the right; clockwise
    private TetrisBlockGroupRotation rot2; // two rotations in either direction

    public TetrisBlockGroup() throws AnchorNotFoundException {
        this(new TetrisBlockGroupRotation(), null, null, null);
    }

    public TetrisBlockGroup(String s) throws AnchorNotFoundException {
        this(TetrisBlockGroupRotation.fromString(s), null, null, null);
    }


    public TetrisBlockGroup(TetrisBlockGroupRotation b) throws AnchorNotFoundException {
        this(b, null, null, null);
    }

    public TetrisBlockGroup(TetrisBlockGroupRotation b,
                            TetrisBlockGroupRotation rotC,
                            TetrisBlockGroupRotation rotCC,
                            TetrisBlockGroupRotation rot2) throws AnchorNotFoundException {
        this.blocks = b;
        this.rotC = rotC;
        this.rotCC = rotCC;
        this.rot2 = rot2;
        generateDirections();
        normalizeAllRotations();
    }

    public List<TetrisBlock> getBlocks() {
        return blocks.getBlocks();
    }

    private void generateDirections() throws AnchorNotFoundException {
        final int anchorX = this.blocks.getAnchor().getCoordinate().getX();
        final int anchorY = this.blocks.getAnchor().getCoordinate().getY();

        if (rotC == null) {
            List<TetrisBlock> b = new ArrayList<>();
            // rotate everything left
            for (TetrisBlock block : blocks.getBlocks()) {
                TetrisBlock newBlock = new TetrisBlock(block);
                newBlock.setCoordinate(new Coordinate(
                                anchorX - (block.getCoordinate().getY() - anchorY),
                                anchorY + (block.getCoordinate().getX() - anchorX)
                        )
                );
                b.add(newBlock);
            }
            rotC = new TetrisBlockGroupRotation(b, new TetrisBlockGroupRotationConfig(Rotation.COUNTERCLOCKWISE));
        }

        if (rotCC == null) {
            List<TetrisBlock> b = new ArrayList<>();
            // rotate everything right
            for (TetrisBlock block : blocks.getBlocks()) {
                TetrisBlock newBlock = new TetrisBlock(block);
                newBlock.setCoordinate(new Coordinate(
                                anchorX + (block.getCoordinate().getY() - anchorY),
                                anchorY - (block.getCoordinate().getX() - anchorX)
                        )
                );
                b.add(newBlock);
            }
            rotCC = new TetrisBlockGroupRotation(b, new TetrisBlockGroupRotationConfig(Rotation.CLOCKWISE));
        }

        if (rot2 == null) {
            List<TetrisBlock> b = new ArrayList<>();
            // rotate everything twice
            for (TetrisBlock block : blocks.getBlocks()) {
                TetrisBlock newBlock = new TetrisBlock(block);
                newBlock.setCoordinate(new Coordinate(
                                anchorX - (block.getCoordinate().getX() - anchorX),
                                anchorY - (block.getCoordinate().getY() - anchorY)
                        )
                );
                b.add(newBlock);
            }
            rot2 = new TetrisBlockGroupRotation(b, new TetrisBlockGroupRotationConfig(Rotation.DOUBLE));
        }
    }

    private void normalizeAllRotations() throws AnchorNotFoundException {
        if (blocks.getConfig().isAllowedNormalize()) blocks.setBlocks(normalize(blocks.getBlocks()));
        if (rotC.getConfig().isAllowedNormalize()) rotC.setBlocks(normalize(rotC.getBlocks()));
        if (rotCC.getConfig().isAllowedNormalize()) rotCC.setBlocks(normalize(rotCC.getBlocks()));
        if (rot2.getConfig().isAllowedNormalize()) rot2.setBlocks(normalize(rot2.getBlocks()));
    }

    public List<TetrisBlock> getRot2() {
        return rot2.getBlocks();
    }

    public List<TetrisBlock> getRotCC() {
        return rotCC.getBlocks();
    }

    public List<TetrisBlock> getRotC() {
        return rotC.getBlocks();
    }

    public TetrisBlockGroupRotation getRotation(Rotation rot) {
        return rot == Rotation.NORMAL ? blocks :
                rot == Rotation.CLOCKWISE ? rotC :
                        rot == Rotation.COUNTERCLOCKWISE ? rotCC :
                                rot == Rotation.DOUBLE ? rot2 : null;
    }

    public static TetrisBlockGroup fromString(String data) throws AnchorNotFoundException {
        String[] groups = data.split(";");

        TetrisBlockGroupRotation normal = null;
        TetrisBlockGroupRotation rotC = null;
        TetrisBlockGroupRotation rotCC = null;
        TetrisBlockGroupRotation rot2 = null;

        for (String group : groups) {
            // System.out.println("group: " + group);

            try {
                TetrisBlockGroupRotation r = TetrisBlockGroupRotation.fromString(group);
                switch (r.getConfig().getRotation()) {
                    case CLOCKWISE -> rotC = r;
                    case COUNTERCLOCKWISE -> rotCC = r;
                    case DOUBLE -> rot2 = r;
                    default -> normal = r;
                }
            } catch (Exception ignored) {}
        }

        if (normal == null) return null;

        return new TetrisBlockGroup(normal, rotC, rotCC, rot2);
    }

    // normalizes all values so that the middle is the anchor block
    private List<TetrisBlock> normalize(List<TetrisBlock> blocks) throws AnchorNotFoundException {
        java.util.Optional<TetrisBlock> o = blocks.stream().filter(TetrisBlock::isAnchor).findFirst();

        if (!o.isPresent()) throw new AnchorNotFoundException();
        TetrisBlock anchor = o.get();


        int offsetX = anchor.getCoordinate().getX();
        int offsetY = anchor.getCoordinate().getY();

        // offset all the blocks by the lowest x and y so that the lowest x and y are 0, 0 respectively
        if (offsetX == 0 && offsetY == 0) return blocks;

        return TetrisBlockGroup.offsetList(blocks, -offsetX, -offsetY);
    }

    public static List<TetrisBlock> offsetList(List<TetrisBlock> blocks, int offsetX, int offsetY) {
        return blocks.stream().map(e -> {
            TetrisBlock b = new TetrisBlock(e);
            b.setCoordinate(new Coordinate(e.getCoordinate().getX() + offsetX, e.getCoordinate().getY() + offsetY));
            return b;
        }).collect(Collectors.toList());
    }
}

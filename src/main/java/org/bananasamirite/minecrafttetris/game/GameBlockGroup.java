package org.bananasamirite.minecrafttetris.game;


import org.bananasamirite.minecrafttetris.utils.Coordinate;
import org.bananasamirite.minecrafttetris.TetrisGame;
import org.bananasamirite.minecrafttetris.blockdata.TetrisBlock;
import org.bananasamirite.minecrafttetris.blockdata.TetrisBlockGroup;

import java.util.List;

// represents a block group INSIDE the game. TetrisBlockGroup is used for storing a block group in memory OUTSIDE the game.
public class GameBlockGroup {
    private final TetrisBlockGroup blockGroup;
    private Coordinate anchorPosition;
    private Rotation rotation;
    private final TetrisGame game;
    public GameBlockGroup(TetrisGame g, TetrisBlockGroup b, Coordinate anchorPos, Rotation initRotation) {
        this.game = g;
        this.blockGroup = b;
        this.anchorPosition = anchorPos;
        this.rotation = initRotation;
    }

    public Coordinate getAnchorPosition() {
        return anchorPosition;
    }

    public void translate(Coordinate coord) {
        this.setAnchorPosition(new Coordinate(this.getAnchorPosition().getX() + coord.getX(), this.getAnchorPosition().getY() + coord.getY()));
    }

    public boolean setAnchorPosition(Coordinate anchorPosition) {
        // when setting anchor position, check if the game allows this

        List<TetrisBlock> blocks = TetrisBlockGroup.offsetList(getRotationList(), anchorPosition.getX(), anchorPosition.getY());

        for (TetrisBlock block : blocks) {

            Block igBlockAt = game.getBlockAt(block.getCoordinate().getX(), block.getCoordinate().getY());
            if (block.getCoordinate().getY() >= 0 && (igBlockAt == null || igBlockAt.isLit())) return false;
        }

        this.anchorPosition = anchorPosition;
        return true;
    }

    public Coordinate getProjectedAnchorPosition() {
        List<TetrisBlock> blocks = TetrisBlockGroup.offsetList(getRotationList(), anchorPosition.getX(), anchorPosition.getY());

        int lowestY = game.getHeight() - getLowestBlock(blocks).getCoordinate().getY();

        for (TetrisBlock block : blocks) {
            for (int i = block.getCoordinate().getY(); i < game.getHeight(); i++) {
                int dist = i - block.getCoordinate().getY();
                if ((game.getBlockAt(block.getCoordinate().getX(), i) == null || game.getBlockAt(block.getCoordinate().getX(), i).isLit()) && dist < lowestY) {
                    lowestY = dist;
                    break;
                }
            }
        }

        return new Coordinate(anchorPosition.getX(), lowestY + anchorPosition.getY() - 1);
    }

    private TetrisBlock getLowestBlock(List<TetrisBlock> blocks) {
        int highestY = (int) Double.NEGATIVE_INFINITY;
        TetrisBlock lowestBlock = null;
        for (TetrisBlock block : blocks) {
            if (block.getCoordinate().getY() > highestY) {
                highestY = block.getCoordinate().getY();
                lowestBlock = block;
            }
        }
        return lowestBlock;
    }

    public List<TetrisBlock> getProjectedAnchorList() {
        Coordinate projectedAnchorCoordinate = getProjectedAnchorPosition();
        return TetrisBlockGroup.offsetList(getRotationList(), projectedAnchorCoordinate.getX(), projectedAnchorCoordinate.getY());
    }

    public TetrisBlockGroup getBlockGroup() {
        return blockGroup;
    }

    public void setRotation(Rotation rotation) {
        Rotation old = this.rotation;
        this.rotation = rotation;
        if (!setAnchorPosition(this.anchorPosition)) this.rotation = old;
    }

    public List<TetrisBlock> getRotationList() {
        List<TetrisBlock> e = null;
        switch (this.rotation) {
            case NORMAL -> e = blockGroup.getBlocks();
            case CLOCKWISE -> e = blockGroup.getRotC();
            case COUNTERCLOCKWISE -> e = blockGroup.getRotCC();
            case DOUBLE -> e = blockGroup.getRot2();
        }
        return e;
    }

    public List<TetrisBlock> getListWithAnchorPos() {
        return TetrisBlockGroup.offsetList(getRotationList(), anchorPosition.getX(), anchorPosition.getY());
    }

    public Rotation getRotation() {
        return rotation;
    }
}

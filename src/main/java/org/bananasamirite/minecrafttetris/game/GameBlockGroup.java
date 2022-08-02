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
//            System.out.println(game.getBlockAt(block.getCoordinate().getX(), block.getCoordinate().getY()) != null);
//            System.out.println(block.getCoordinate().getY());
//            System.out.println(!game.getBlockAt(block.getCoordinate().getX(), block.getCoordinate().getY()).isLit());

            Block igBlockAt = game.getBlockAt(block.getCoordinate().getX(), block.getCoordinate().getY());

            if (block.getCoordinate().getY() >= 0) {
                if (igBlockAt == null || igBlockAt.isLit()) return false;
            }
            // TODO: replace this line
//            if (game.getOutput().getBlockAt(block.getCoordinate().getX(), 0) == null) return; // we do NOT care about the y coordinate because let it be negative idc
        }

        this.anchorPosition = anchorPosition;
        return true;
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

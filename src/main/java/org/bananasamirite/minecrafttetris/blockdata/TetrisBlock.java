package org.bananasamirite.minecrafttetris.blockdata;

import org.bananasamirite.minecrafttetris.utils.Coordinate;

import java.util.Arrays;
import java.util.List;

public class TetrisBlock {
    private Coordinate coordinate;
    private boolean isAnchor = false;

    public TetrisBlock(Coordinate c, String[] args) {
        this(c, Arrays.asList(args));
    }

    public TetrisBlock(Coordinate c, List<String> args) {
        this.coordinate = c;
        checkArgs(args);
    }

    public TetrisBlock(TetrisBlock b) {
        this.coordinate = new Coordinate(b.getCoordinate());
        this.isAnchor = b.isAnchor;
    }

    private void checkArgs(List<String> args) {
        if (args.contains("ANCHOR")) isAnchor = true;
    }

    public boolean isAnchor() {
        return isAnchor;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate c) {
        this.coordinate = c;
    }

    public static TetrisBlock fromString(String s) {
        // c1,c2 ANCHOR ANOTHER_VAR
        List<String> args = Arrays.asList(s.split(" "));

        String[] cordBit = args.get(0).split(",");

        Coordinate cord;

        try {
            cord = new Coordinate(Integer.parseInt(cordBit[0]), Integer.parseInt(cordBit[1]));
        } catch (Exception err) {
            return null;
        }

        return new TetrisBlock(cord, args);
    }

    @Override
    public String toString() {
        return "TetrisBlock{" +
                "coordinate=" + coordinate +
                ", isAnchor=" + isAnchor +
                '}';
    }
}

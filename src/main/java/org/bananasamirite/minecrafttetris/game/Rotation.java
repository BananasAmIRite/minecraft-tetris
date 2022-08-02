package org.bananasamirite.minecrafttetris.game;

public enum Rotation {
    NORMAL(0),
    CLOCKWISE(1),
    DOUBLE(2),
    COUNTERCLOCKWISE(3);

    static {
        NORMAL.left = COUNTERCLOCKWISE;
        NORMAL.right = CLOCKWISE;

        CLOCKWISE.left = NORMAL;
        CLOCKWISE.right = DOUBLE;

        COUNTERCLOCKWISE.left = DOUBLE;
        COUNTERCLOCKWISE.right = NORMAL;

        DOUBLE.left = CLOCKWISE;
        DOUBLE.right = COUNTERCLOCKWISE;
    }

    private final int n;
    private Rotation left;
    private Rotation right;

    Rotation(int n) {
        this.n = n;
    }

    public int getN() {
        return n;
    }

    public Rotation getLeft() {
        return left;
    }

    public Rotation getRight() {
        return right;
    }

    public static Rotation getEnum(int n) {
        for (Rotation value : Rotation.values()) {
            if (value.getN() == n) return value;
        }
        return null;
    }
}

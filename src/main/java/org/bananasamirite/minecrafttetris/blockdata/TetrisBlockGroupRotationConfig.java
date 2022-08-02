package org.bananasamirite.minecrafttetris.blockdata;

import org.bananasamirite.minecrafttetris.game.Rotation;

public class TetrisBlockGroupRotationConfig {
    private final Rotation rotation;
    private final boolean allowedNormalize;

    public TetrisBlockGroupRotationConfig() {
        this(Rotation.NORMAL, true);
    }

    public TetrisBlockGroupRotationConfig(Rotation r) {
        this(r, true);
    }

    public TetrisBlockGroupRotationConfig(Rotation r, boolean allowedNormalize) {
        this.rotation = r;
        this.allowedNormalize = allowedNormalize;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public boolean isAllowedNormalize() {
        return allowedNormalize;
    }

    public static TetrisBlockGroupRotationConfig fromString(String s) {
        // parse config
        // config will look like:
        // CFG 0/1/2/3 corresponding to Rotation

        String[] params = s.split(" ");

        Rotation r = Rotation.getEnum(Integer.parseInt(params[1]));

        return new TetrisBlockGroupRotationConfig(r, !s.contains("NONORMALIZE"));
    }

    @Override
    public String toString() {
        return "TetrisBlockGroupRotationConfig(" +
                "rotation=" + rotation +
                ", allowedNormalize=" + allowedNormalize +
                ')';
    }
}

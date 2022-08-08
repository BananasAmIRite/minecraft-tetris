package org.bananasamirite.minecrafttetris.game;

// SINGLE block entity
public class Block {
    private boolean isLit;

    public Block() {
        isLit = false;
    }

    public boolean isLit() {
        return isLit;
    }

    public void light() {
        this.isLit = true;
    }

    public void unlight() {
        isLit = false;
    }
}

package org.bananasamirite.minecrafttetris.game;

import org.bananasamirite.minecrafttetris.TetrisConfig;
import org.bukkit.Material;

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

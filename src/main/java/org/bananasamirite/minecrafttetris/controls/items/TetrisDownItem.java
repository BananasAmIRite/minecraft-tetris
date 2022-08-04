package org.bananasamirite.minecrafttetris.controls.items;

import org.bananasamirite.minecrafttetris.TetrisGame;
import org.bananasamirite.minecrafttetris.controls.ToggleableItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class TetrisDownItem extends ToggleableItem {
    private final TetrisGame game;
    public TetrisDownItem(TetrisGame game, ItemStack item, Material off, Material on) {
        super(game.getPlugin(), item, off, on, false);
        this.game = game;
    }

    @Override
    public void onToggle(boolean toggledValue, ItemStack item) {
        this.game.setSpeeding(toggledValue);
    }
}

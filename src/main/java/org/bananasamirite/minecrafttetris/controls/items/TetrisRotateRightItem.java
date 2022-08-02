package org.bananasamirite.minecrafttetris.controls.items;

import org.bananasamirite.minecrafttetris.TetrisGame;
import org.bananasamirite.minecrafttetris.controls.ClickableItem;
import org.bananasamirite.minecrafttetris.utils.Coordinate;
import org.bukkit.inventory.ItemStack;

public class TetrisRotateRightItem extends ClickableItem {
    private final TetrisGame game;
    public TetrisRotateRightItem(TetrisGame game, ItemStack item) {
        super(game.getPlugin(), item);
        this.game = game;
    }

    @Override
    public void onClick(ItemStack item) {
        if (this.game.getCurrent() != null) this.game.getCurrent().setRotation(this.game.getCurrent().getRotation().getRight());
    }
}

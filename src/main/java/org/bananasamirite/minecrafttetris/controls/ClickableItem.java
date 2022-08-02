package org.bananasamirite.minecrafttetris.controls;

import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class ClickableItem extends InteractiveItem {
    public ClickableItem(Plugin pl, ItemStack item) {
        super(pl, item);
    }

    @Override
    public void onItemInteract(ItemStack item) {
        onClick(item);
    }

    public abstract void onClick(ItemStack item);
}

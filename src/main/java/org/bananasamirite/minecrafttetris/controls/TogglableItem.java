package org.bananasamirite.minecrafttetris.controls;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class TogglableItem extends InteractiveItem {
    private boolean toggleValue;
    private final Material off;
    private final Material on;
    public TogglableItem(Plugin pl, ItemStack item, Material off, Material on, boolean initialValue) {
        super(pl, item);
        this.off = off;
        this.on = on;
        this.setToggleValue(initialValue);
    }

    public void toggle() {
        this.setToggleValue(!toggleValue);
    }

    private void setToggleValue(boolean val) {
        this.toggleValue = val;
        getCurrentItem().setType(this.toggleValue ? on : off);
    }

    @Override
    public void onItemInteract(ItemStack item) {
        toggle();
        onToggle(toggleValue, item);
    }

    public abstract void onToggle(boolean toggledValue, ItemStack item);
}

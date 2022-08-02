package org.bananasamirite.minecrafttetris.controls;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemController {
    private List<InteractiveItem> items;

    public ItemController() {
        this.items = new ArrayList<>();
    }

    public void addItem(InteractiveItem item) {
        this.items.add(item);
    }

    public void removeItem(InteractiveItem item) {
        this.items.remove(item);
    }

    public void clearItems() {
        this.items.clear();
    }

    public void triggerItem(ItemStack item) {
        for (InteractiveItem interactiveItem : items) {
            if (interactiveItem.isItem(item)) {
                interactiveItem.triggerInteract(item);
                return;
            }
        }
    }
}

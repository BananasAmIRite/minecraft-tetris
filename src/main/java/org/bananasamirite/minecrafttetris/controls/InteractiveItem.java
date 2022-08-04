package org.bananasamirite.minecrafttetris.controls;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.UUID;

public abstract class InteractiveItem {
    private static final String KEY = "INTERACTIVE_ITEM";

    private final Plugin plugin;
    private final String value;
    private ItemStack item;

    public InteractiveItem(Plugin pl, ItemStack item) {
        this.plugin = pl;
        this.value = setupItem(item);
        this.item = item;
    }

    private String setupItem(ItemStack item) {
        String value = String.valueOf(UUID.randomUUID());
        ItemMeta m = item.getItemMeta();
        m.getPersistentDataContainer().set(new NamespacedKey(plugin, KEY), PersistentDataType.STRING, value);
        item.setItemMeta(m);
        return value;
    }

    public final boolean isItem(ItemStack item) {
        return item.getItemMeta() != null &&
                Objects.equals(item.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, KEY), PersistentDataType.STRING), this.value);
    }

    public ItemStack getItem() {
        return item;
    }

    public final void triggerInteract(ItemStack item) {
        this.item = item;
        onItemInteract(item);
    }

    protected ItemStack getCurrentItem() {
        return this.item;
    }

    protected abstract void onItemInteract(ItemStack item);
}

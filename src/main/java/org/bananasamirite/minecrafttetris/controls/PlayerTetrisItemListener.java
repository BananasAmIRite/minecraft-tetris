package org.bananasamirite.minecrafttetris.controls;

import net.kyori.adventure.text.Component;
import org.bananasamirite.minecrafttetris.MinecraftTetris;
import org.bananasamirite.minecrafttetris.TetrisGame;
import org.bananasamirite.minecrafttetris.controls.items.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerTetrisItemListener extends ItemController implements Listener {
    private final Player player;
    private ItemStack[] storedInventory;
    private final TetrisGame game;
    public PlayerTetrisItemListener(TetrisGame game) {
        this.game = game;
        this.player = game.getPlayer();
        setupInventory();
    }

    private void setupInventory() {
        storedInventory = this.player.getInventory().getContents();
        this.player.getInventory().setContents(new ItemStack[] {});
        setItemInInventory(2, new TetrisLeftItem(this.game, new ItemStack(Material.ARROW)), Component.text("Left"));
        setItemInInventory(3, new TetrisRotateLeftItem(this.game, new ItemStack(Material.ARROW)), Component.text("Rotate Left"));
        setItemInInventory(4, new TetrisDownItem(this.game, new ItemStack(Material.RED_CONCRETE), Material.RED_CONCRETE, Material.GREEN_CONCRETE), Component.text("Speed Up/Slow Down"));
        setItemInInventory(5, new TetrisRotateRightItem(this.game, new ItemStack(Material.ARROW)), Component.text("Rotate Right"));
        setItemInInventory(6, new TetrisRightItem(this.game, new ItemStack(Material.ARROW)), Component.text("Right"));
    }

    private void setItemInInventory(int slot, InteractiveItem item, Component name) {
        ItemMeta m = item.getItem().getItemMeta();
        m.displayName(name);
        item.getItem().setItemMeta(m);
        this.addItem(item);
        this.player.getInventory().setItem(slot, item.getItem());
    }

    public void restoreInventory() {
        this.player.getInventory().setContents(storedInventory);
        this.clearItems();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getPlayer() != player || e.getAction() != Action.LEFT_CLICK_AIR) return;
        triggerItem(e.getItem());
    }
}

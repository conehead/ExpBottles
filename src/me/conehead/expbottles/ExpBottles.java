package me.conehead.expbottles;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ExpBottles extends JavaPlugin implements Listener {

    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getInventory() instanceof EnchantingInventory) || !(event.getWhoClicked() instanceof Player)) return;

        EnchantingInventory inv = (EnchantingInventory) event.getInventory();
        Player player = (Player) event.getWhoClicked();

        ItemStack itemToConvert = null;

        if (event.isShiftClick()) {
            if (inv.getItem() == null && event.getCurrentItem().getType() == Material.GLASS_BOTTLE) {
                itemToConvert = event.getCurrentItem();
            }
        } else {
            if (inv.getItem() == null && event.getCursor().getType() == Material.GLASS_BOTTLE) {
                if (event.getSlotType() == InventoryType.SlotType.CRAFTING) {
                    itemToConvert = event.getCursor();
                }
            }
        }

        if (itemToConvert != null && player.getTotalExperience() >= 11) {
            ExpEngine.removeExpFromPlayer(player, 11);

            if (itemToConvert.getAmount() > 1) {
                itemToConvert.setAmount(itemToConvert.getAmount() - 1);
            } else {
                if (event.isShiftClick()) {
                    event.setCurrentItem(null);
                } else {
                    event.setCursor(null);
                }
            }

            inv.setItem(new ItemStack(Material.EXP_BOTTLE));
            event.setCancelled(true);
        }
    }
}

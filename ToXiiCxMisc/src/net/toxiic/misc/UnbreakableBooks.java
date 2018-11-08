package net.toxiic.misc;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UnbreakableBooks implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			if (e.getView().getTopInventory().getType().equals(InventoryType.ANVIL)) {
				if (e.getClickedInventory().getType().equals(InventoryType.ANVIL)) {
					AnvilInventory anvilInv = (AnvilInventory) e.getInventory();
					ItemStack[] itemsInAnvil = anvilInv.getContents();
					if (itemsInAnvil[0] != null && itemsInAnvil[1] != null) {
						if (itemsInAnvil[1].getType().equals(Material.ENCHANTED_BOOK)
								&& itemsInAnvil[1].getItemMeta().isUnbreakable()) {
							ItemStack result = itemsInAnvil[0].clone();
							ItemMeta meta = result.getItemMeta();
							meta.setUnbreakable(true);
							result.setItemMeta(meta);
							if (e.getRawSlot() == 2) {
								if (e.getCurrentItem().equals(null)) {
									e.setCurrentItem(result);
								} else {
									e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
									e.setCurrentItem(result);
								}
							} else {
								anvilInv.setItem(2, result);
							}
						}
					}

				}
			}
		}
	}
}

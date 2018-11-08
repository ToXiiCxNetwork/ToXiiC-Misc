package net.toxiic.misc;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.itemnbtapi.NBTItem;

public class BetterShears implements CommandExecutor, Listener {

	List<ItemStack[]> recipes = new ArrayList<ItemStack[]>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				player.getInventory().addItem(this.getShears(3));
				return true;
			} else {
				player.getInventory().addItem(this.getShears(Integer.parseInt(args[0])));
				return true;
			}

		}

		return false;
	}

	public ItemStack getShears(int level) {
		ItemStack output = new ItemStack(Material.SHEARS);
		ItemMeta meta = output.getItemMeta();
		meta.setDisplayName(ChatColor.GRAY + "Reinforced Shears");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "These shears are much sharper than normal shears");
		lore.add(ChatColor.GRAY + "It looks like they could give more wool from sheep.");
		lore.add(ChatColor.GRAY + "Level: " + String.valueOf(level));
		meta.setLore(lore);
		output.setItemMeta(meta);
		NBTItem nbti = new NBTItem(output);
		nbti.setInteger("shearingBonus", level);
		return nbti.getItem();
	}

	@EventHandler(priority = EventPriority.LOW)
	public void craftingHandler(PrepareItemCraftEvent e) {
		CraftingInventory inv = e.getInventory();
		for (ItemStack[] recipe : recipes) {
			try {
				ItemStack[] matrix = inv.getMatrix();
				int items = 0;
				for (int i = 0; i < recipe.length; i++) {
					if (recipe[i].getType() == matrix[i].getType()) {
						items++;
					}
				}
				if (items == 9) {
					NBTItem nbti = new NBTItem(matrix[4]);
					if (nbti.hasKey("shearingBonus")) {
						int level = nbti.getInteger("shearingBonus");
						inv.setResult(this.getShears(level + 1));

					}
				}
			} catch (Exception err) {

			}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void shearSheep(PlayerShearEntityEvent e) {
		if (!e.isCancelled()) {
			ItemStack held = e.getPlayer().getItemInHand();
			if (held.getType() == Material.SHEARS) {
				NBTItem nbti = new NBTItem(held);
				if (nbti.hasKey("shearingBonus")) {
					int bonus = nbti.getInteger("shearingBonus");
					held.setDurability((short) (held.getDurability() - bonus));
					e.setCancelled(true);
					Sheep sheep = (Sheep) e.getEntity();
					sheep.setSheared(true);
					ItemStack wool = null;
					switch (sheep.getColor()) {
					case BLACK:
						wool = new ItemStack(Material.BLACK_WOOL);
						break;
					case BLUE:
						wool = new ItemStack(Material.BLUE_WOOL);
						break;
					case BROWN:
						wool = new ItemStack(Material.BROWN_WOOL);
						break;
					case CYAN:
						wool = new ItemStack(Material.CYAN_WOOL);
						break;
					case GRAY:
						wool = new ItemStack(Material.GRAY_WOOL);
						break;
					case GREEN:
						wool = new ItemStack(Material.GREEN_WOOL);
						break;
					case LIGHT_BLUE:
						wool = new ItemStack(Material.LIGHT_BLUE_WOOL);
						break;
					case LIGHT_GRAY:
						wool = new ItemStack(Material.LIGHT_GRAY_WOOL);
						break;
					case LIME:
						wool = new ItemStack(Material.LIME_WOOL);
						break;
					case MAGENTA:
						wool = new ItemStack(Material.MAGENTA_WOOL);
						break;
					case ORANGE:
						wool = new ItemStack(Material.ORANGE_WOOL);
						break;
					case PINK:
						wool = new ItemStack(Material.PINK_WOOL);
						break;
					case PURPLE:
						wool = new ItemStack(Material.PURPLE_WOOL);
						break;
					case RED:
						wool = new ItemStack(Material.RED_WOOL);
						break;
					case WHITE:
						wool = new ItemStack(Material.WHITE_WOOL);
						break;
					case YELLOW:
						wool = new ItemStack(Material.YELLOW_WOOL);
						break;
					}
					for (int i = 0; i < bonus + 1; i++) {
						sheep.getLocation().getWorld().dropItem(sheep.getLocation(), wool);
					}
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			if (e.getView().getTopInventory().getType().equals(InventoryType.ANVIL)) {
				AnvilInventory anvilInv = (AnvilInventory) e.getInventory();
				ItemStack[] itemsInAnvil = anvilInv.getContents();
				if (itemsInAnvil[0] != null && itemsInAnvil[1] != null) {
					if (itemsInAnvil[0].getType().equals(Material.SHEARS)
							&& itemsInAnvil[1].getType().equals(Material.SHEARS)) {
						NBTItem slot1 = new NBTItem(itemsInAnvil[0]);
						NBTItem slot2 = new NBTItem(itemsInAnvil[1]);
						if ((slot1.hasKey("shearingBonus")) == true && (slot2.hasKey("shearingBonus")) == true) {
							if (String.valueOf(slot1.getInteger("shearingBonus"))
									.equalsIgnoreCase(String.valueOf(slot2.getInteger("shearingBonus")))) {
								ItemStack result = this.getShears(slot1.getInteger("shearingBonus") + 1);
								if (e.getRawSlot() == 2) {
									e.setCurrentItem(result);
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
}

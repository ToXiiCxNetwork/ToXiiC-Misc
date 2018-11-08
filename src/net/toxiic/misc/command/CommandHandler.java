package net.toxiic.misc.command;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CommandHandler implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("use this item to spawn a spinning armor stand");
		lore.add("whatever is in your offhand will appear in the head slot");

		ArrayList<String> help = new ArrayList<String>();
		help.add("/tmisc help - Show this menu");
		help.add("/tmisc fi - Gives hologram spawn egg");
		help.add("/tmisc letter - gives letters");

		if (!(sender instanceof Player)) { // So console can't access command.
			sender.sendMessage("You must be a player to do this command!");
			return true;
		} else {
			Player p = (Player) sender;
			if (args.length == 0) {
				p.sendMessage("Try Using /tmisc help");
				return true;
			} else {
				switch (args[0]) {
				case "h":
				case "help":
					p.sendMessage("ToXiiC-Misc help");
					for(String msg : help) {
						p.sendMessage(msg);
					}
					break;
				case "floatingitem":
				case "fi":
					if (p.hasPermission("toxiicmisc.floatingitem")) {
						ItemStack item = new ItemStack(Material.BAT_SPAWN_EGG);
						ItemMeta meta = item.getItemMeta();
						meta.setDisplayName("hologram");
						meta.setLore(lore);
						item.setItemMeta(meta);
						p.getInventory().addItem(item);
						return true;
					}
				case "letter":
					if (p.hasPermission("toxiicmisc.letters")) {
						Inventory inv = Bukkit.createInventory(p, 27, "Letters");
						ItemStack letter = new ItemStack(Material.DIAMOND_HOE);
						ItemMeta meta = letter.getItemMeta();
						meta.setUnbreakable(true);
						meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
						letter.setItemMeta(meta);
						letter.setAmount(64);
						for (int i = 6; i < 32; i++) {
							letter.setDurability((short) i);
							inv.addItem(letter);
						}
						p.openInventory(inv);
						return true;
					}
				}

				return true;
			}
		}
	}
}

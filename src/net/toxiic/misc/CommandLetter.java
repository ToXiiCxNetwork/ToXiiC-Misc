package net.toxiic.misc;

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

public class CommandLetter implements CommandExecutor {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
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
		return true;
	}
}

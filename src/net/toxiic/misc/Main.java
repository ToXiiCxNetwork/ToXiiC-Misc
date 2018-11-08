package net.toxiic.misc;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import net.toxiic.misc.command.CommandHandler;

public class Main extends JavaPlugin implements Listener {
	private static final Logger log = Logger.getLogger("Minecraft");
	List<ArmorStand> stands = new ArrayList<ArmorStand>();

	public void onEnable() {
		if (getServer().getPluginManager().getPlugin("ItemNBTAPI") == null) {
			log.severe(String.format("[%s] - Disabled: ItemNBTAPI Missing", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		} else {
			Bukkit.getLogger().info("Hello ItemNBTAPI");
		}

		registerCommand();
		registerEvents();
		new BukkitRunnable() {
			double angle = 0;

			public void run() {
				for (ArmorStand as : stands) {
					as.setHeadPose(new EulerAngle(0, angle, 0));
				}
				angle = angle + 0.05;
			}
		}.runTaskTimer(this, 1L, 1L);
	}

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");

		logger.info(pdfFile.getName() + " has been disable!");
	}

	@EventHandler
	public void onPlaceEgg(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (e.getItem().getType().equals(Material.BAT_SPAWN_EGG)
					&& e.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("hologram")) {
				Player p = e.getPlayer();
				Location bloc = e.getClickedBlock().getLocation();
				Location loc = new Location(bloc.getWorld(), bloc.getX(), bloc.getY() + 1, bloc.getZ());
				ItemStack item = p.getInventory().getItemInOffHand();
				ArmorStand as = (ArmorStand) loc.getWorld().spawn(loc, ArmorStand.class);
				as.setHelmet(item);
				as.setGravity(true);
				stands.add(as);
				e.setCancelled(true);
			}
		}
	}

	public void registerCommand() {
		Bukkit.getPluginCommand("toxiicmisc").setExecutor(new CommandHandler());
	}

	public void registerEvents() {
		Bukkit.getPluginManager().registerEvents(new EventHandlers(), this);
		Bukkit.getPluginManager().registerEvents(new BetterShears(), this);
		Bukkit.getPluginManager().registerEvents(new UnbreakableBooks(), this);
		Bukkit.getPluginManager().registerEvents(this, this);
	}
}

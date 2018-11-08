package net.toxiic.misc.util;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Util {
	public static HashMap<String, Object> idLink;

	public static String color(String msg) { //convert color codes
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static void msg(Player p, String msg) { //send a player a colored message
		p.sendMessage(color(msg));
	}

	public static void loadAll() {
		idLink = new HashMap<String, Object>();
	}

	public static void inform(String text) {
		Logger logger = Logger.getLogger("Minecraft");
		logger.info("[" + "ToXiiCxMisc" + "] " + text);
	}

	public static void warn(String text) {
		Logger logger = Logger.getLogger("Minecraft");
		logger.warning("[" + "ToXiiCxMisc" + "] " + text);
	}

	public static void severe(String text) {
		Logger logger = Logger.getLogger("Minecraft");
		logger.severe("[" + "ToXiiCxMisc" + "] " + text);
	}

}

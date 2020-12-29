package io.dpteam.UniversalStaffWatch.Commands;

import io.dpteam.UniversalStaffWatch.Main;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandUnwatch implements CommandExecutor {
	Main plugin;

	public CommandUnwatch(Main plugin) {
		super();
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		boolean watching = Main.watchConfig.getBoolean("watching." + sender.getName());
		Server server = player.getServer();
		if (sender instanceof Player) {
			if (sender.hasPermission("rswatch.unwatch")) {
				if (watching) {
					int returnPosX = Main.watchConfig.getInt(sender.getName() + ".returnLocation.X");
					int returnPosY = Main.watchConfig.getInt(sender.getName() + ".returnLocation.Y");
					int returnPosZ = Main.watchConfig.getInt(sender.getName() + ".returnLocation.Z");
					World returnWorld = server.getWorld(Main.watchConfig.getString(sender.getName() + ".returnLocation.world"));
					player.setCanPickupItems(true);
					player.setFlying(false);
					player.setAllowFlight(false);
					this.plugin.disableVanish(player);
					Location returnPos = new Location(returnWorld, (double)returnPosX, (double)returnPosY, (double)returnPosZ);
					returnPos.setWorld(returnWorld);
					player.teleport(returnPos);
					int gameMode = Main.watchConfig.getInt(sender.getName() + ".returnGameMode");
					player.setGameMode(GameMode.getByValue(gameMode));
					Main.watchConfig.set("watching." + sender.getName(), false);
					this.plugin.saveConfig();
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "[DPT.MC]" + ChatColor.BLUE + " You aren't watching anyone!");
					return true;
				}
			} else {
				return true;
			}
		} else {
			sender.sendMessage("[DPT.MC] Must be a player!");
			return true;
		}
	}
}

package io.dpteam.UniversalStaffWatch;

import io.dpteam.UniversalStaffWatch.Commands.CommandUnwatch;
import io.dpteam.UniversalStaffWatch.Commands.CommandWatch;
import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public String Version = "0.1";
	public static File pluginFolder;
	public static File configFile;
	public static FileConfiguration watchConfig;

	public Main() {
		super();
	}

	public void onEnable() {
		this.getCommand("watch").setExecutor(new CommandWatch(this));
		this.getCommand("unwatch").setExecutor(new CommandUnwatch(this));
		this.getServer().getPluginManager().addPermission(new Permission("rswatch.unwatch"));
		this.getServer().getPluginManager().addPermission(new Permission("rswatch.watch"));
		this.getServer().getPluginManager().registerEvents(new Events(), this);
		pluginFolder = this.getDataFolder();
		configFile = new File(pluginFolder, "config.yml");
		watchConfig = new YamlConfiguration();
		if (!pluginFolder.exists()) {
			try {
				pluginFolder.mkdir();
			} catch (Exception var4) {
			}
		}

		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (Exception var3) {
			}
		}

		try {
			watchConfig.load(configFile);
		} catch (Exception var2) {
		}
		this.getServer().getLogger().info("[UniversalStaffWatch] Plugin loaded and enabled");
	}

	public void onDisable() {
		this.saveConfig();
		this.getServer().getLogger().info("[UniversalStaffWatch] Plugin loaded and enabled");
	}

	public void saveConfig() {
		try {
			watchConfig.save(configFile);
		} catch (IOException var2) {
			var2.printStackTrace();
		}
	}

	public void enableVanish(Player player) {
		Player[] var5;
		int var4 = (var5 = (Player[])this.getServer().getOnlinePlayers().toArray(new Player[0])).length;

		for(int var3 = 0; var3 < var4; ++var3) {
			Player other = var5[var3];
			other.hidePlayer(player);
		}
	}

	public void disableVanish(Player player) {
		Player[] var5;
		int var4 = (var5 = (Player[])this.getServer().getOnlinePlayers().toArray(new Player[0])).length;

		for(int var3 = 0; var3 < var4; ++var3) {
			Player other = var5[var3];
			other.showPlayer(player);
		}
	}
}

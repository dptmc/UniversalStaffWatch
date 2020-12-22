package io.dpteam.UniversalStaffWatch.Commands;

import io.dpteam.UniversalStaffWatch.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWatch implements CommandExecutor {
   UniversalStaffWatch plugin;

   public CommandWatch(UniversalStaffWatch plugin) {
      super();
      this.plugin = plugin;
   }

   public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      if (sender instanceof Player) {
         if (sender.hasPermission("rswatch.watch")) {
            UniversalStaffWatch.watchConfig.set("watching." + sender.getName(), false);
            Player player = (Player)sender;
            boolean watching = UniversalStaffWatch.watchConfig.getBoolean("watching." + sender.getName());
            if (!watching) {
               if (args.length == 1) {
                  Player target = Bukkit.getPlayer(args[0]);
                  if (target != null) {
                     Location targetLoc = target.getLocation();
                     Location returnPos = ((Player)sender).getLocation();
                     GameMode returnGm = ((Player)sender).getGameMode();
                     int returnPosX = returnPos.getBlockX();
                     int returnPosY = returnPos.getBlockY();
                     int returnPosZ = returnPos.getBlockZ();
                     String returnWorld = returnPos.getWorld().getName();
                     UniversalStaffWatch.watchConfig.set(sender.getName() + ".watching", target.getName());
                     UniversalStaffWatch.watchConfig.set(sender.getName() + ".returnLocation.X", returnPosX);
                     UniversalStaffWatch.watchConfig.set(sender.getName() + ".returnLocation.Y", returnPosY);
                     UniversalStaffWatch.watchConfig.set(sender.getName() + ".returnLocation.Z", returnPosZ);
                     UniversalStaffWatch.watchConfig.set(sender.getName() + ".returnLocation.world", returnWorld);
                     UniversalStaffWatch.watchConfig.set(sender.getName() + ".returnGameMode", returnGm.getValue());
                     this.plugin.saveConfig();
                     if (player.isInsideVehicle()) {
                        player.leaveVehicle();
                     }

                     UniversalStaffWatch.watchConfig.set("watching." + sender.getName(), true);
                     UniversalStaffWatch.watchConfig.set(target.getName() + ".watched", true);
                     player.setCanPickupItems(false);
                     player.setAllowFlight(true);
                     player.setFlying(true);
                     this.plugin.enableVanish(player);
                     player.teleport(targetLoc);
                     this.plugin.saveConfig();
                     return true;
                  } else {
                     sender.sendMessage(ChatColor.RED + "[DPT.MC] " + ChatColor.BLUE + "That player isn't online!");
                     return true;
                  }
               } else {
                  return false;
               }
            } else {
               sender.sendMessage(ChatColor.RED + "[DPT.MC]" + ChatColor.BLUE + " You are already watching someone!");
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

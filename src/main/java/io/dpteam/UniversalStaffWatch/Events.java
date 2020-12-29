package io.dpteam.UniversalStaffWatch;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class Events implements Listener {
   public Events() {
      super();
   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void onBlockPlace(BlockPlaceEvent event) {
      Player player = event.getPlayer();
      if (Main.watchConfig.getBoolean("watching." + player.getName())) {
         event.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void onBlockBreak(BlockBreakEvent event) {
      Player player = event.getPlayer();
      if (Main.watchConfig.getBoolean("watching." + player.getName())) {
         event.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void onDropItem(PlayerDropItemEvent event) {
      Player player = event.getPlayer();
      if (Main.watchConfig.getBoolean("watching." + player.getName())) {
         event.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void onPlayerDamage(EntityDamageEvent event) {
      if (event.getEntity() instanceof Player) {
         Player player = (Player)event.getEntity();
         if (Main.watchConfig.getBoolean("watching." + player.getName())) {
            event.setCancelled(true);
         }
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void onEntityTarget(EntityTargetEvent event) {
      Player player = (Player)event.getTarget();
      if (Main.watchConfig.getBoolean("watching." + player.getName())) {
         event.setCancelled(true);
      }

   }

   @EventHandler(
      priority = EventPriority.NORMAL
   )
   public void EntityDamageByEntityEvent(EntityDamageByEntityEvent event) {
      if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
         Player damager = (Player)event.getDamager();
         Player player = (Player)event.getEntity();
         if (Main.watchConfig.getString(damager.getName() + ".watching") == player.getName()) {
            event.setCancelled(true);
         }
      }

   }
}

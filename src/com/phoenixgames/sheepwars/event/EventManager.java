/**
 * SheepWars plugin for PaperMC 1.14.4
 * Copyright (c) 2020-present, Killax-D.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.phoenixgames.sheepwars.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class EventManager implements Listener {

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent in) {
		PlayerCommandPreprocess.event(in);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent in) {
		PlayerJoin.event(in);
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent in) {
		PlayerLeave.event(in);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent in) {
		PlayerInteract.event(in);
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent in) {
		PlayerMove.event(in);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent in) {
		PlayerDropItem.event(in);
	}
	
	@EventHandler
	public void onEntityRegainHealth(EntityRegainHealthEvent in) {
		EntityRegainHealth.event(in);
	}
	
	@EventHandler
	public void onPlayerItemDamage(PlayerItemDamageEvent in) {
		PlayerItemDamage.event(in);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent in) {
		InventoryClick.event(in);
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent in) {
		PlayerDeath.event(in);
	}

	@EventHandler
	public void onVehicleExit(VehicleExitEvent in){
		VehicleExit.event(in);
	}
	
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent in) {
		FoodLevelChange.event(in);
	}

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent in) {
		EntityDamageByEntity.event(in);
	}
	
	@EventHandler
	public void onEntityExplode(EntityExplodeEvent in) {
		EntityExplode.event(in);
	}
	
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent in) {
		EntitySpawn.event(in);
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent in) {
		EntityDeath.event(in);
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent in) {
		ProjectileHit.event(in);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent in){
		BlockPlace.event(in);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent in){
		BlockBreak.event(in);
	}
	
}

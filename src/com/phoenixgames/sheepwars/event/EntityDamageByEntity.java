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

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftSheep;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.phoenixgames.sheepwars.entity.sheep.EntitySheepWars;
import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.player.PlayerData.Fields;

public class EntityDamageByEntity {

	public static void event(EntityDamageByEntityEvent event) {
		if (event.getCause() == DamageCause.ENTITY_EXPLOSION && event.getEntity() instanceof Player) {
			event.setDamage(event.getDamage()/3);
		}
		
		if (event.getDamager() instanceof CraftSheep) {
			EntitySheepWars damager = (EntitySheepWars) ((CraftSheep) event.getDamager()).getHandle();
			if(event.getEntity() instanceof CraftPlayer) {
				if(((Player) event.getEntity()).getHealth() <= event.getFinalDamage()){
					if(damager.getSummoner() instanceof Player) {
						if (damager.getSummoner().getUniqueId() != event.getEntity().getUniqueId()) {
							PlayerData.getData(damager.getSummoner().getUniqueId()).addValue(Fields.KILLS, 1);

							for(Player p : Bukkit.getOnlinePlayers()) {
								if(p != null)
									p.sendMessage(String.format(EnumTranslate.KILL_PLAYER.getTranslation(p.getLocale()), ((CraftPlayer) damager.getSummoner()).getDisplayName(), ((CraftPlayer) event.getEntity()).getDisplayName()));
							}
						}
					}
				}
			}
			
		} else {
			if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
				PlayerData dataVictim = PlayerData.getData(event.getEntity().getUniqueId());
				PlayerData dataKiller = PlayerData.getData(event.getDamager().getUniqueId());
				if(dataVictim.getTeam() == dataKiller.getTeam())
				event.setCancelled(true);
			}
			if (event.getEntity() instanceof Player && event.getDamager() instanceof Arrow) {
				PlayerData dataVictim = PlayerData.getData(event.getEntity().getUniqueId());
				Arrow arrow = (Arrow) event.getDamager();
				PlayerData dataKiller = PlayerData.getData(((Entity) arrow.getShooter()).getUniqueId());
				if(dataVictim.getTeam() == dataKiller.getTeam())
					event.setCancelled(true);
			}
		}
	}
	
}

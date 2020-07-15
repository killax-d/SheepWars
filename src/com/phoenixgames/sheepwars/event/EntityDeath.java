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

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftSheep;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.player.PlayerData.Fields;

public class EntityDeath {
	
	public static void event(EntityDeathEvent event) {
		event.setDroppedExp(0);
		if(event.getDrops() != null)
			event.getDrops().clear();
		
		if(event.getEntity() == null)
			return;
		
		Player killer = event.getEntity().getKiller();
		if(killer != null && killer instanceof Player) {
			PlayerData data = PlayerData.getData(killer.getUniqueId());
			if(event.getEntity().getClass() == CraftSheep.class) {
				data.addValue(Fields.SHEEP_KILLED, 1);

				String type = event.getEntity().getMetadata("type").get(0).asString();
				World world = event.getEntity().getLocation().getWorld();
				
				if(type == EnumSheepWars.BLIND.getName())
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.BLIND.getWool());
				else if(type == EnumSheepWars.DISTORSION.getName())
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.DISTORSION.getWool());
				else if(type == EnumSheepWars.EARTHQUAKE.getName())
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.EARTHQUAKE.getWool());
				else if(type == EnumSheepWars.FIRE.getName())
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.FIRE.getWool());
				else if(type == EnumSheepWars.FRAG.getName())
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.FRAG.getWool());
				else if(type == EnumSheepWars.FREEZE.getName())
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.FREEZE.getWool());
				else if(type == EnumSheepWars.FUSE.getName())
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.FUSE.getWool());
				else if(type == EnumSheepWars.GALACTIC.getName()) {
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.GALACTIC.getWool());
					world.setTime(6000);
				}
				else if(type == EnumSheepWars.SEEKER.getName())
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.SEEKER.getWool());
				else if(type == EnumSheepWars.THUNDER.getName()) {
					world.dropItem(event.getEntity().getLocation(), EnumSheepWars.THUNDER.getWool());
					world.setThundering(false);
				}
			}
		}
		
	}
}

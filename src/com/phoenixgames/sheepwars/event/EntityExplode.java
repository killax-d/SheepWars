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

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.util.Vector;

public class EntityExplode{
	
	public static void event(EntityExplodeEvent event) {
		List<Block> blocks = event.blockList();
		event.setCancelled(true);
		Random random = new Random();
		
		for (Block b : blocks) {
			if (b != null) {
				Material mat = b.getType();
				b.setType(Material.AIR);
				if(random.nextInt(100) < 25) {
					FallingBlock fb = event.getLocation().getWorld().spawnFallingBlock(b.getLocation(), mat.createBlockData());
					
			        float x = (float) -1 + (float) (Math.random() * ((0.5 - -0.5) + 1));
			        float y = (float) (Math.random() * 1.5);
			        float z = (float) -1 + (float) (Math.random() * ((0.5 - -0.5) + 1));
			 
			        fb.setVelocity(new Vector(x, y, z));
			        
					fb.setDropItem(false);
				}
			}
		}
		
		Collection<Entity> entities = event.getLocation().getNearbyEntities(3, 3, 3);
		for (Entity e : entities) {
			if(e != null && e.getType() != EntityType.FALLING_BLOCK) {
		        float x = (float) -0.5 + (float) (Math.random() * ((0.5 - -0.5) + 1));
		        float y = (float) (Math.random() * 1.5);
		        float z = (float) -0.5 + (float) (Math.random() * ((0.5 - -0.5) + 1));
		        
		        e.setVelocity(new Vector(x, y, z));
			}
	        
		}
		
	}
}
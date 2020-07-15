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
package com.phoenixgames.sheepwars.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class LocatorUtils {

	private static BlockFace[] faces = new BlockFace[] {
			BlockFace.UP,
			BlockFace.NORTH,
			BlockFace.DOWN,
			BlockFace.SOUTH,
			BlockFace.EAST,
			BlockFace.WEST
	};
	
	public static Set<Block> getNearbyBlocks(Location location, int around){
		location = location.getBlock().getLocation();// correction for entity postion
		Set<Block> blocks = new HashSet<Block>();
		Block block = location.getBlock();
		
		if(block != null) {

			blocks.add(block);
			
			for(int i = 0; i < around; i++) {
				List<Block> tmp = new ArrayList<Block>();
				for(Block b : blocks) {
					if(b != null) {
						for(int j = 0; j < faces.length; j++) {
							Block blockScanned = b.getRelative(faces[j]);
							if(!tmp.contains(blockScanned))
								tmp.add(blockScanned);
						}
					}
				}
				blocks.addAll(tmp);
			}
		}
		return blocks;
	}
	
	public static Set<Block> getSquaredAreaBlocks(Location location, int around){
		location = location.getBlock().getLocation();// correction for entity postion
		Set<Block> blocks = new HashSet<Block>();
		for(int x = -around; x <= around; x++) {
			for(int y = -around; y <= around; y++) {
				for(int z = -around; z <= around; z++) {
					blocks.add(location.add(x, y, z).getBlock());
					location.subtract(x, y, z);
				}
			}
		}
		return blocks;
	}
	
	public static Set<Block> getSquaredAreaGround(Location location, int around){
		Set<Block> blocks = getSquaredAreaBlocks(location, around);
		Set<Block> ground = new HashSet<Block>();
		for (Block b : blocks) {
			if(b != null && b.getType() == Material.AIR && b.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
				ground.add(b);
			}
		}

		return ground;
	}
	
	public static Set<Location> getSquaredAreaGroundLocation(Location location, int around){
		Set<Block> blocks = getSquaredAreaGround(location, around);
		Set<Location> ground = new HashSet<Location>();
		for (Block b : blocks) {
			if(b != null) {
				ground.add(b.getLocation());
			}
		}

		return ground;
	}
	
	public static Set<Location> getSquaredArea(Location location, int around){
		Set<Block> blocks = getSquaredAreaBlocks(location, around);
		Set<Location> locations = new HashSet<Location>();
		
		for(Block b : blocks) {
			locations.add(b.getLocation());
		}
		
		return locations;
	}
	
	public static Set<Location> getNearbyLocation(Location location, int around){
		Set<Block> blocks = getNearbyBlocks(location, around);
		Set<Location> locations = new HashSet<Location>();
		
		for(Block b : blocks) {
			locations.add(b.getLocation());
		}
		
		return locations;
	}
	
	public static Set<Location> getNearbyFlatLocation(Location location, int around, double y){
		Set<Block> blocks = getNearbyBlocks(location, around);
		Set<Location> locations = new HashSet<Location>();
		
		for(Block b : blocks) {
			if(b.getLocation().getY() == y) {
				locations.add(b.getLocation());
			}
		}
		
		return locations;
	}
}

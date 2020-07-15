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
package com.phoenixgames.sheepwars.entity.sheep;

import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.utils.LocatorUtils;

import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.Items;
import net.minecraft.server.v1_14_R1.World;

public class EntitySheepFreeze extends EntitySheepWars {
	
	public EntitySheepFreeze(EntityTypes<? extends EntitySheep> entitytypes, World world) {
		super(entitytypes, world);
	}

	@Override
	public void onSpawn() {
		
	}
	
	@Override
	public void onEndLife() {
		
	}

	@Override
	public String getSheepName() {
		return "&dFREEZE";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.FREEZE;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.LIGHT_BLUE;
	}

	@Override
	public Item getBoostItem() {
		return Items.WATER_BUCKET;
	}

	@Override
	public float getExplosionForce() {
		return 8.0F;
	}

	@Override
	public int getEffectSpeed() {
		return 40;
	}

	@Override
	public int getAreaSize() {
		return 6;
	}

	@Override
	public void playParticle() {
		if(ticksLived % getEffectSpeed()/4 == 0) {
			Random random = new Random();
			Set<Location> locations = LocatorUtils.getSquaredArea(new Location(world.getWorld(), locX, locY, locZ), getAreaSize());
			
			for(Location l : locations) {
				if(random.nextInt(100) < 5)
					world.getWorld().spawnParticle(Particle.FALLING_DUST,
							l.getX() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
							l.getY() + (double) (this.random.nextFloat() * this.getHeight() * 1.5F),
							l.getZ() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
							1, 0.0D, 0.0D, 0.0D, 0.0D, Material.SNOW_BLOCK.createBlockData());
			}
		}
	}

	@Override
	public boolean isSupport() {
		return false;
	}

	@Override
	public void playAreaEffect() {
		if(dead)
			return;
		if(ticksLived % getEffectSpeed() == 0) {
			for(Player p : getNearbyEnemies(getAreaSize())) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, getEffectSpeed(), 3));
			}
			
			Location location = new Location(world.getWorld(), locX, locY, locZ);
			Set<Block> blocks = LocatorUtils.getSquaredAreaBlocks(location, getAreaSize());
			
			if(!blocks.isEmpty()) {
				for(Block b : blocks) {
					if((b.getType() == Material.AIR 
							|| b.getType() == Material.GRASS
							|| b.getType() == Material.TALL_GRASS)
							&& b.getRelative(BlockFace.DOWN).getType() != Material.AIR 
							&& b.getRelative(BlockFace.DOWN).getType() != Material.SNOW
							&& b.getRelative(BlockFace.DOWN).getType() != Material.ICE) {
						b.setType(Material.SNOW);
					}
					else if(b.getType() == Material.SNOW) {
						Snow s = (Snow) b.getBlockData().clone();
						int layer = s.getLayers();
						if(layer < 7) {
							s.setLayers(++layer);
							b.setBlockData(s);
						}
						else
							b.setType(Material.SNOW_BLOCK);
					}
					else if(b.getType() == Material.WATER)
						b.setType(Material.ICE);
				}
			}
		}
	}
	
	@Override
	public boolean willExplode() {
		return false;
	}
	
	@Override
	public int getTickDuration() {
		return 20 * 8;
	}
}

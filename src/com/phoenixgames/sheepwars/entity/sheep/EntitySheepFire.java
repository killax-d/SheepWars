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
import org.bukkit.block.BlockFace;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.utils.LocatorUtils;

import net.minecraft.server.v1_14_R1.DamageSource;
import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.Items;
import net.minecraft.server.v1_14_R1.PathType;
import net.minecraft.server.v1_14_R1.World;

public class EntitySheepFire extends EntitySheepWars {

	public EntitySheepFire(EntityTypes<? extends EntitySheep> entitytypes, World world) {
		super(entitytypes, world);
		this.a(PathType.LAVA);
	}

	@Override
	public void onSpawn() {
		
	}
	
	@Override
	public void onEndLife() {
		
	}

	@Override
	public String getSheepName() {
		return "&6FIRE";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.FIRE;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.ORANGE;
	}

	@Override
	public Item getBoostItem() {
		return Items.BLAZE_POWDER;
	}

	@Override
	public float getExplosionForce() {
		return 6.0F;
	}

	@Override
	public int getEffectSpeed() {
		return 10;
	}

	@Override
	public int getAreaSize() {
		return 5;
	}
	
	@Override
	public void playParticle() {
		if(ticksLived % getEffectSpeed() == 0) {
			Random random = new Random();
			Set<Location> locations = LocatorUtils.getNearbyLocation(new Location(world.getWorld(), locX, locY, locZ), getAreaSize());
			
			for(Location l : locations) {
				if(random.nextInt(100) < 35)
					world.getWorld().spawnParticle(Particle.FLAME,
							l.getX() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
							l.getY() + (double) (this.random.nextFloat() * this.getHeight() * 1.5F),
							l.getZ() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
							3, 0.0D, 0.0D, 0.0D, 0.0D, null);
			}
		}
	}

	@Override
	public boolean isSupport() {
		return false;
	}

	@Override
	public void playAreaEffect() {
		if(ticksLived % getEffectSpeed() == 0) {
			Random random = new Random();
			Set<Location> locations = LocatorUtils.getNearbyLocation(new Location(world.getWorld(), locX, locY, locZ), getAreaSize());
			for(Location l : locations) {
				if(l.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
					if(random.nextInt(100) < 5) {
						if(l.getBlock().getType() == Material.AIR)
							l.getBlock().setType(Material.FIRE);
					}
				}
			}
		}
	}

	@Override
	public boolean willExplode() {
		return true;
	}
	
	@Override
	public int getTickDuration() {
		return 20 * 8;
	}

	@Override
	public boolean damageEntity(DamageSource damagesource, float f) {
		if (damagesource.equals(DamageSource.FIRE) || damagesource.equals(DamageSource.BURN) || damagesource.equals(DamageSource.LAVA)) {
			return false;
		}
		return super.damageEntity(damagesource, f);
	}

}

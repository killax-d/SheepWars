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

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.utils.LocatorUtils;

import net.minecraft.server.v1_14_R1.DamageSource;
import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.Items;
import net.minecraft.server.v1_14_R1.World;

public class EntitySheepThunder extends EntitySheepWars {

	public EntitySheepThunder(EntityTypes<? extends EntitySheep> entitytypes, World world) {
		super(entitytypes, world);
	}

	@Override
	public void onSpawn() {
		world.getWorld().setStorm(true);
	}
	
	@Override
	public void onEndLife() {
		world.getWorld().setStorm(false);
	}

	@Override
	public String getSheepName() {
		return "&eTHUNDER";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.THUNDER;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.YELLOW;
	}

	@Override
	public Item getBoostItem() {
		return Items.IRON_INGOT;
	}

	@Override
	public float getExplosionForce() {
		return 8.0F;
	}
	
	@Override
	public int getEffectSpeed() {
		return 20;
	}

	@Override
	public int getAreaSize() {
		return 5;
	}
	
	@Override
	public void playParticle() {
		if(!world.getWorld().hasStorm() && isAlive())
			world.getWorld().setStorm(true);
		
		if(ticksLived % getEffectSpeed()/2 == 0) {
			Random random = new Random();
			Set<Location> locations = LocatorUtils.getSquaredAreaGroundLocation(new Location(world.getWorld(), locX, locY, locZ), getAreaSize());
			
			for(Location l : locations) {
				if(random.nextInt(100) < 35)
					world.getWorld().spawnParticle(Particle.TOTEM,
						l.getX() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
						l.getY() + (double) (this.random.nextFloat() * this.getHeight() * 1.5F),
						l.getZ() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
						2, 0.0D, 0.0D, 0.0D, 0.0D, null);
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
			List<Player> players = getNearbyPlayers(getAreaSize());
			if(!players.isEmpty()) {
				for (Player p : players) {
					if(p != null) {
						if(random.nextInt(100) < 25) {
							world.getWorld().strikeLightning(p.getLocation());
						}
					}
				}
			}
			
			Set<Block> ground = LocatorUtils.getSquaredAreaGround(new Location(world.getWorld(), locX, locY, locZ), getAreaSize());
			
			for (Block b : ground) {
				if(random.nextInt(100) == 0) {
					world.getWorld().strikeLightning(b.getLocation().add(0.0D, 1.0D, 0.0D));
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
	
	@Override
	public boolean damageEntity(DamageSource damagesource, float f) {
		if (damagesource.equals(DamageSource.LIGHTNING) || damagesource.equals(DamageSource.BURN) || damagesource.equals(DamageSource.FIRE)) {
			return false;
		}
		return super.damageEntity(damagesource, f);
	}

}

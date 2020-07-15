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

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.utils.LocatorUtils;

import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.Items;
import net.minecraft.server.v1_14_R1.World;

public class EntitySheepEarthquake extends EntitySheepWars {

	public EntitySheepEarthquake(EntityTypes<? extends EntitySheep> entitytypes, World world) {
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
		return "&eEARTHQUAKE";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.EARTHQUAKE;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.BROWN;
	}

	@Override
	public Item getBoostItem() {
		return Items.POTATO;
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
		if(ticksLived % getEffectSpeed()/2 == 0) {
			Set<Location> locations = LocatorUtils.getSquaredAreaGroundLocation(new Location(world.getWorld(), locX, locY, locZ), getAreaSize());
			
			for(Location l : locations) {
				world.getWorld().spawnParticle(Particle.BLOCK_CRACK,
					l.getX(),
					l.getY(),
					l.getZ(),
					1, 0.0D, 0.0D, 0.0D, 0.0D, Material.DIRT.createBlockData());
			}
		}
	}

	@Override
	public boolean isSupport() {
		return false;
	}

	@Override
	public void playAreaEffect() {
		if(ticksLived % getEffectSpeed() == 0)
			for(Player p : getNearbyPlayers(getAreaSize()))
				if(p != null) {
					p.setKiller((Player) getSummoner());
					p.setVelocity((new Vector(0.0D, 3.7D, 0.0D)).add(p.getLocation().getDirection()).multiply(0.35D));
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

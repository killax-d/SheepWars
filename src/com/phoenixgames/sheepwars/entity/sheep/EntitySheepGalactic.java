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

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Fireball;
import org.bukkit.util.Vector;

import com.phoenixgames.sheepwars.entity.custom.EntityMeteor;
import com.phoenixgames.sheepwars.enums.EnumCustomEntity;
import com.phoenixgames.sheepwars.enums.EnumSheepWars;

import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.Items;
import net.minecraft.server.v1_14_R1.World;

public class EntitySheepGalactic extends EntitySheepWars {

	public EntitySheepGalactic(EntityTypes<? extends EntitySheep> entitytypes, World world) {
		super(entitytypes, world);
	}

	@Override
	public void onSpawn() {
		
	}
	
	@Override
	public void onEndLife() {
		world.getWorld().setTime(6000);
	}
	
	@Override
	public String getSheepName() {
		return "&1GALACTIC";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.GALACTIC;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.BLUE;
	}

	@Override
	public Item getBoostItem() {
		return Items.LAPIS_LAZULI;
	}

	@Override
	public float getExplosionForce() {
		return 8.0F;
	}

	@Override
	public int getEffectSpeed() {
		return 20 * 3;
	}

	@Override
	public int getAreaSize() {
		return 10;
	}
	
	@Override
	public void playParticle() {
		world.getWorld().spawnParticle(Particle.VILLAGER_ANGRY,
				this.locX + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
				this.locY + (double) (this.random.nextFloat() * this.getHeight() * 1.5F),
				this.locZ + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
				2, 0.0D, 0.0D, 0.0D, 0.0D, null);
	}

	@Override
	public boolean isSupport() {
		return false;
	}

	@Override
	public void playAreaEffect() {
		if(world.getWorld().getTime() != 18000)
			world.getWorld().setTime(18000);
		
		if (ticksLived % getEffectSpeed() == 0) {
			Random random = new Random();
			
			EntityMeteor meteor = EntityMeteor.summonMeteor(this, EnumCustomEntity.METEOR, new Location(world.getWorld(), locX, locY, locZ));
			Fireball fb = (Fireball) meteor.getBukkitEntity();
			fb.setBounce(false);
			fb.setIsIncendiary(true);
			
			Location fbloc = fb.getLocation();

			double x = locX + (random.nextInt(getAreaSize()) * Math.random() > 0.5 ? -1 : 1);
			double z = locZ + (random.nextInt(getAreaSize()) * Math.random() > 0.5 ? -1 : 1);
			
			Vector dir = new Vector(x - fbloc.getX(), locY - fbloc.getY(), z - fbloc.getZ());
			fb.setDirection(dir);
			
		}
	}

	@Override
	public boolean willExplode() {
		return true;
	}

	@Override
	public int getTickDuration() {
		return 20 * 3;
	}
}

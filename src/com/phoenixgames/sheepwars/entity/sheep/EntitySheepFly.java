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

import org.bukkit.Particle;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;

import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.Items;
import net.minecraft.server.v1_14_R1.World;

public class EntitySheepFly extends EntitySheepWars {

	public EntitySheepFly(EntityTypes<? extends EntitySheep> entitytypes, World world) {
		super(entitytypes, world);
	}

	@Override
	public void onSpawn() {
		getBukkitEntity().setPassenger(getSummoner());
	}
	
	@Override
	public void onEndLife() {
		
	}

	@Override
	public String getSheepName() {
		return "&eFLY";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.FLY;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.WHITE;
	}

	@Override
	public Item getBoostItem() {
		return Items.FEATHER;
	}

	@Override
	public float getExplosionForce() {
		return 8.0F;
	}

	@Override
	public int getEffectSpeed() {
		return 5;
	}

	@Override
	public int getAreaSize() {
		return 2;
	}
	
	@Override
	public void playParticle() {
		if(ticksLived % getEffectSpeed() == 0) {
			world.getWorld().spawnParticle(Particle.CLOUD,
					locX + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
					locY - 0.15D,
					locZ + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
					50, 0.0D, 0.0D, 0.0D, 0.0D, null);
		}
	}

	@Override
	public boolean isSupport() {
		return false;
	}

	@Override
	public void playAreaEffect() {
		if(ticksLived > 20 && onGround) {
			die();
		}
	}

	@Override
	public boolean willExplode() {
		return false;
	}
	
	@Override
	public int getTickDuration() {
		return -1;
	}

}

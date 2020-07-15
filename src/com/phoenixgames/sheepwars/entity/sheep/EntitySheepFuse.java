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

public class EntitySheepFuse extends EntitySheepWars {

	public EntitySheepFuse(EntityTypes<? extends EntitySheep> entitytypes, World world) {
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
		return "&4FUSE";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.FUSE;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.RED;
	}

	@Override
	public Item getBoostItem() {
		return Items.REDSTONE;
	}

	@Override
	public float getExplosionForce() {
		return isBaby() ? 6.0F : 12.0F;
	}
	
	@Override
	public int getEffectSpeed() {
		return 60;
	}

	@Override
	public int getAreaSize() {
		return 0;
	}
	
	@Override
	public void playParticle() {
		world.getWorld().spawnParticle(Particle.CRIT,
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
		if(isBaby() && onGround && ticksLived > 5) {
			explode();
		}
	}
	
	@Override
	public boolean willExplode() {
		return true;
	}
	
	@Override
	public int getTickDuration() {
		return isBaby() ? -1 : 20 * 6;
	}

}

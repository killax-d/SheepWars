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
import org.bukkit.util.Vector;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;

import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.Items;
import net.minecraft.server.v1_14_R1.World;

public class EntitySheepFrag extends EntitySheepWars {

	public EntitySheepFrag(EntityTypes<? extends EntitySheep> entitytypes, World world) {
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
		return "&7FRAG";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.FRAG;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.GRAY;
	}

	@Override
	public Item getBoostItem() {
		return Items.GUNPOWDER;
	}

	@Override
	public float getExplosionForce() {
		return 8.0F;
	}

	@Override
	public int getEffectSpeed() {
		return 20 * 7;
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
		if(ticksLived == getEffectSpeed()) {
			EntitySheepWars[] entities = new EntitySheepWars[] {
					EntitySheepWars.summonSheep(getBukkitEntity(), EnumSheepWars.FUSE),
					EntitySheepWars.summonSheep(getBukkitEntity(), EnumSheepWars.FUSE),
					EntitySheepWars.summonSheep(getBukkitEntity(), EnumSheepWars.FUSE)
				};
			
			for(int i = 0; i < entities.length; i++) {
				entities[i].setAge(-1);
				entities[i].ageLocked = true;
				org.bukkit.entity.Entity e = entities[i].getBukkitEntity();
				
				float pitch = (float) (Math.random() * (Math.random() < 0.5 ? 1 : -1));
				float yaw = (float) (Math.random() * (Math.random() < 0.5 ? 1 : -1));
				
				e.setVelocity((new Vector(pitch, 2.5D, yaw)).add(new Vector(0, pitch, yaw)).multiply(0.35D));
			}
			
			die();
		}
	}
	
	@Override
	public boolean willExplode() {
		return true;
	}
	
	@Override
	public int getTickDuration() {
		return 20 * 5;
	}
}

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

import org.bukkit.Location;
import org.bukkit.Particle;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.player.PlayerData;

import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.GenericAttributes;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.Items;
import net.minecraft.server.v1_14_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_14_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_14_R1.World;

public class EntitySheepSeeker extends EntitySheepWars {

	public EntitySheepSeeker(EntityTypes<? extends EntitySheep> entitytypes, World world) {
		super(entitytypes, world);
	}

	@Override
	public void onSpawn() {
		this.getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE);
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(0D);
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30590000554919242D);
		targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 10, true, true, p -> PlayerData.getData(p.getUniqueID()).getTeam() != getTeam()));
		goalSelector.a(0, new PathfinderGoalMeleeAttack(this, 1.0D, false));
	}
	
	@Override
	public void onEndLife() {
		
	}

	@Override
	public String getSheepName() {
		return "&2SEEKER";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.SEEKER;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.GREEN;
	}

	@Override
	public Item getBoostItem() {
		return Items.SLIME_BALL;
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
		return 8;
	}
	
	@Override
	public void playParticle() {
		if(ticksLived % getEffectSpeed() == 0) {
			world.getWorld().spawnParticle(Particle.SLIME,
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
		if(getGoalTarget() != null) {
			Location locTarget = new Location(world.getWorld(), getGoalTarget().locX, getGoalTarget().locY, getGoalTarget().locZ);
			Location loc = new Location(world.getWorld(), locX, locY, locZ);
			if(ticksLived > 20 * 2 && loc.distance(locTarget) < 1.5D) {
				explode();
			}
		}
	}

	@Override
	public boolean willExplode() {
		return true;
	}
	
	@Override
	public int getTickDuration() {
		return 20 * 10;
	}

}

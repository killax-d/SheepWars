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
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.utils.LocatorUtils;

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

public class EntitySheepHeal extends EntitySheepWars {
	
	public EntitySheepHeal(EntityTypes<? extends EntitySheep> entitytypes, World world) {
		super(entitytypes, world);
	}

	@Override
	public void onSpawn() {
		this.getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE);
		this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(0D);
		this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30590000554919242D);
		targetSelector.a(0, new PathfinderGoalNearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, 10, true, true, p -> p.getUniqueID() == getSummoner().getUniqueId()));
		goalSelector.a(0, new PathfinderGoalMeleeAttack(this, 1.0D, false));
	}
	
	@Override
	public void onEndLife() {
		
	}

	@Override
	public String getSheepName() {
		return "&dHEAL";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.HEAL;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.PINK;
	}

	@Override
	public Item getBoostItem() {
		return Items.APPLE;
	}

	@Override
	public int getEffectSpeed() {
		return 40;
	}

	@Override
	public int getAreaSize() {
		return 8;
	}

	@Override
	public float getExplosionForce() {
		return 8.0F;
	}

	@Override
	public void playParticle() {
		if(ticksLived % getEffectSpeed()/4 == 0) {
			Random random = new Random();
			Set<Location> locations = LocatorUtils.getSquaredArea(new Location(world.getWorld(), locX, locY, locZ), getAreaSize());
			
			for(Location l : locations) {
				if(random.nextInt(100) < 5)
					world.getWorld().spawnParticle(Particle.HEART,
							l.getX() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
							l.getY() + (double) (this.random.nextFloat() * this.getHeight() * 1.5F),
							l.getZ() + (double) (this.random.nextFloat() * this.getWidth() * 2.0F) - (double) this.getWidth(),
							1, 0.0D, 0.0D, 0.0D, 0.0D, null);
			}
		}
	}

	@Override
	public boolean isSupport() {
		return true;
	}

	@Override
	public void playAreaEffect() {
		if(ticksLived % getEffectSpeed() == 0) {
			for(Player p : getNearbyPlayers(getAreaSize())) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, getEffectSpeed(), 2));
			}
		}
	}
	
	@Override
	public void setTarget() {
		EntityPlayer target = ((CraftPlayer) getSummoner()).getHandle();
		setGoalTarget(target, TargetReason.CUSTOM, true);
	}

	@Override
	public boolean willExplode() {
		return false;
	}
	
	@Override
	public int getTickDuration() {
		return 20 * 6;
	}
}

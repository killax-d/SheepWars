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
package com.phoenixgames.sheepwars.entity.custom;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.phoenixgames.sheepwars.enums.EnumCustomEntity;

import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityFireball;
import net.minecraft.server.v1_14_R1.EntityLargeFireball;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.MovingObjectPosition;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.World;

public class EntityMeteor extends EntityLargeFireball {

	private static Random random = new Random();
	
	public EntityMeteor(EntityTypes<? extends EntityFireball> entitytypes, World world) {
		super(EntityTypes.FIREBALL, world);
	}

	@Override
	public void a(MovingObjectPosition movingobjectposition) {
		setMot(getMot().getX() * 1.10F, getMot().getY() * 1.10F, getMot().getZ() * 1.10F);
		world.getWorld().createExplosion(this.locX, this.locY-2.0D, this.locZ, 4.0F, true, true);
		die();
	}
	
	public static EntityMeteor summonMeteor(Entity summoner, EnumCustomEntity entityType, Location location) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("id", "minecraft:" + entityType.getName());
		CraftWorld nmsWorld = (CraftWorld) summoner.world.getWorld();
		EntityMeteor meteor = new EntityMeteor(null, nmsWorld.getHandle());
		
		nmsWorld.getHandle().addEntity(meteor, SpawnReason.NATURAL);

		double x = location.getX() + (random.nextInt(25) + 5) * (Math.random() < 0.5 ? 1 : -1);
		double y = location.getY() + (random.nextInt(40) + 50);
		double z = location.getZ() + (random.nextInt(25) + 5) * (Math.random() < 0.5 ? 1 : -1);
		
		meteor.setPosition(x, y, z);
		
		return meteor;
	}
	
	@Override
	public void tick() {
		for (double i = 0; i <= Math.PI; i += Math.PI / 80) {
			  double radius = Math.sin(i);
			  double x = Math.cos(i) * radius;
			  double y = Math.cos(i);
			  double z = Math.sin(i) * radius;
			  
			  world.getWorld().spawnParticle(Particle.SMOKE_NORMAL, locX + x, locY + y, locZ + z, 1, 0.0D, 0.0D, 0.0D, 0.0D, null);
			  world.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, locX + x, locY + y, locZ + z, 1, -1.0D, -1.0D, -1.0D, 0.0D, null);
		}
		super.tick();
	}
}

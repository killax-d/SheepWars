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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.utils.LocatorUtils;

import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.Items;
import net.minecraft.server.v1_14_R1.World;

public class EntitySheepDistorsion extends EntitySheepWars {

	public EntitySheepDistorsion(EntityTypes<? extends EntitySheep> entitytypes, World world) {
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
		return "&5DISTORSION";
	}
	
	@Override
	public EnumSheepWars getType() {
		return EnumSheepWars.DISTORSION;
	}
	
	@Override
	public EnumColor getColor() {
		return EnumColor.PURPLE;
	}

	@Override
	public Item getBoostItem() {
		return Items.ENDER_PEARL;
	}

	@Override
	public float getExplosionForce() {
		return 8.0F;
	}

	@Override
	public void playParticle() {
		Bukkit.getWorld(this.world.getWorldData().getName()).spawnParticle(Particle.ENCHANTMENT_TABLE,
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
	public int getEffectSpeed() {
		return 15;
	}

	@Override
	public int getAreaSize() {
		return 5;
	}

	@Override
	public void playAreaEffect() {
		if(ticksLived % getEffectSpeed() == 0) {
			CraftWorld nmsWorld = world.getWorld();
			Location location = new Location(nmsWorld, locX, locY, locZ);
			
			Set<Block> blocks = LocatorUtils.getNearbyBlocks(location, getAreaSize());
			
			if(!blocks.isEmpty()) {
				nmsWorld.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 0.5F);
				for(Block b : blocks) {
					if (b != null) {
			    		if (b.getType() != Material.AIR && b.getType() != Material.BARRIER) {
			    			summonFallingBlock(b, nmsWorld, 0.1F, 0.3F, 0.1F);
			    		}
			    		b.setType(Material.AIR);
			    	}
			    }
			}
			
		}
	}
	
	private void summonFallingBlock(Block block, CraftWorld nmsWorld, float speedX, float speedY, float speedZ) {
		FallingBlock fb = nmsWorld.spawnFallingBlock(block.getLocation(), block.getBlockData());
	    float x = -speedX + (float)(Math.random() * (speedX - -speedX + 1.0F));
	    float y = -speedY + (float)(Math.random() * (speedY - -speedY + 1.0F));
	    float z = -speedZ + (float)(Math.random() * (speedZ - -speedZ + 1.0F));
	    fb.setVelocity(new Vector(x, y, z));
	    fb.setDropItem(false);
	}

	@Override
	public boolean willExplode() {
		return false;
	}
	
	@Override
	public int getTickDuration() {
		return 20 * 3;
	}

}

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_14_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.metadata.FixedMetadataValue;

import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.utils.TextUtils;

import net.minecraft.server.v1_14_R1.BlockPosition;
import net.minecraft.server.v1_14_R1.DamageSource;
import net.minecraft.server.v1_14_R1.DifficultyDamageScaler;
import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityHuman;
import net.minecraft.server.v1_14_R1.EntityInsentient;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.EntitySheep;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumColor;
import net.minecraft.server.v1_14_R1.EnumMobSpawn;
import net.minecraft.server.v1_14_R1.Explosion.Effect;
import net.minecraft.server.v1_14_R1.GeneratorAccess;
import net.minecraft.server.v1_14_R1.GenericAttributes;
import net.minecraft.server.v1_14_R1.GroupDataEntity;
import net.minecraft.server.v1_14_R1.Item;
import net.minecraft.server.v1_14_R1.ItemStack;
import net.minecraft.server.v1_14_R1.MinecraftKey;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_14_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_14_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_14_R1.Vec3D;
import net.minecraft.server.v1_14_R1.World;
import net.minecraft.server.v1_14_R1.WorldServer;

public abstract class EntitySheepWars extends EntitySheep {

	private org.bukkit.entity.Entity summoner;
	private EnumTeam team;

	public EntitySheepWars(EntityTypes<? extends EntitySheep> entitytypes, World world) {
		super(EntityTypes.SHEEP, world);
		goalSelector = new PathfinderGoalSelector(
				world != null && world.getMethodProfiler() != null ? world.getMethodProfiler() : null);
		targetSelector = new PathfinderGoalSelector(
				world != null && world.getMethodProfiler() != null ? world.getMethodProfiler() : null);
		goalSelector.a(1, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
		goalSelector.a(2, new PathfinderGoalRandomLookaround(this));
	}
	
	public void setSummoner(org.bukkit.entity.Entity summoner) {
		this.summoner = summoner;
	}

	public org.bukkit.entity.Entity getSummoner() {
		return summoner;
	}
	
	public EnumTeam getTeam() {
		return team;
	}
	
	private void setTeam(EnumTeam team) {
		this.team = team;
	}

	@Override
	public GroupDataEntity prepare(GeneratorAccess generatoraccess, DifficultyDamageScaler difficultydamagescaler,
			EnumMobSpawn enummobspawn, @Nullable GroupDataEntity groupdataentity,
			@Nullable NBTTagCompound nbttagcompound) {
		GroupDataEntity gde = super.prepare(generatoraccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
		EntitySheep sheep = ((EntitySheep) this.getBukkitEntity().getHandle());
		sheep.setColor(getColor());
		sheep.setCustomName(TextUtils.toChatComponent((isSupport() ? "&a[\u2764] " : "&c[\u27B8] ") + getSheepName()));
		return gde;
	}
	
	/** 
	 * Return collection of nearby entities at 'x' blocks around,
	 * without sheep instance
	 **/
	
	public List<Player> getNearbyPlayers(int around){
		Location loc = new Location(this.world.getWorld(), this.locX, this.locY, this.locZ);
		Collection<org.bukkit.entity.Entity> nearbyEntities = loc.getWorld().getNearbyEntities(loc, around, around, around);
		
		List<Player> nearbyPlayers = new ArrayList<Player>();
		
		for(org.bukkit.entity.Entity e : nearbyEntities) {
			if (e instanceof Player) {
				nearbyPlayers.add((Player) e);
			}
		}
		return nearbyPlayers;
	}
	
	public List<Player> getNearbyEnemies(int around){
		List<Player> nearbyPlayers = getNearbyPlayers(around);
		
		List<Player> nearbyEnemies = new ArrayList<Player>();
		
		for(Player p : nearbyPlayers) {
			if(PlayerData.getData(p.getUniqueId()).getTeam() != getTeam()) {
				nearbyEnemies.add(p);
			}
		}
		return nearbyEnemies;
	}
	
	public static org.bukkit.inventory.ItemStack getRandomSheepItem() {
		Random random = new Random();
		int i = random.nextInt(100);
		if (i < 10)
			return EnumSheepWars.getEntityByWool(Material.BLACK_WOOL).getWool();
		else if (i < 12)
			return EnumSheepWars.getEntityByWool(Material.BLUE_WOOL).getWool();
		else if (i < 20)
			return EnumSheepWars.getEntityByWool(Material.BROWN_WOOL).getWool();
		else if (i < 35)
			return EnumSheepWars.getEntityByWool(Material.GRAY_WOOL).getWool();
		else if (i < 37)
			return EnumSheepWars.getEntityByWool(Material.GREEN_WOOL).getWool();
		else if (i < 50)
			return EnumSheepWars.getEntityByWool(Material.LIGHT_BLUE_WOOL).getWool();
		else if (i < 65)
			return EnumSheepWars.getEntityByWool(Material.ORANGE_WOOL).getWool();
		else if (i < 80)
			return EnumSheepWars.getEntityByWool(Material.PINK_WOOL).getWool();
		else if (i < 88)
			return EnumSheepWars.getEntityByWool(Material.PURPLE_WOOL).getWool();
		else if (i < 95)
			return EnumSheepWars.getEntityByWool(Material.RED_WOOL).getWool();
		else if (i < 97)
			return EnumSheepWars.getEntityByWool(Material.WHITE_WOOL).getWool();
		else
			return EnumSheepWars.getEntityByWool(Material.YELLOW_WOOL).getWool();
	}
	
	public static EntitySheepWars summonSheep(org.bukkit.entity.Entity summoner, EnumSheepWars sheepType) {
		Location location = summoner.getLocation();
		WorldServer nmsWorld = ((CraftWorld) summoner.getWorld()).getHandle();

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("id", "minecraft:" + sheepType.getName());
		Vec3D vec3d = new Vec3D(location.getX(), location.getY(), location.getZ());
		
		Entity entity = EntityTypes.a(nbt, nmsWorld, entity1 -> {
			entity1.setPositionRotation(vec3d.x, vec3d.y, vec3d.z, entity1.yaw, entity1.pitch);
			return !nmsWorld.addEntitySerialized(entity1) ? null : entity1;
		});
		
		
		((EntityInsentient) entity).prepare(nmsWorld, nmsWorld.getDamageScaler(new BlockPosition(vec3d)), EnumMobSpawn.COMMAND, (GroupDataEntity) null, (NBTTagCompound) nbt);
		entity.setInvulnerable(true);
		EntitySheepWars sheep = (EntitySheepWars) entity;
		sheep.setSummoner(summoner);
		if (summoner instanceof Player)
			sheep.setTeam(PlayerData.getData(summoner.getUniqueId()).getTeam());
		else
			sheep.setTeam(EnumTeam.UNKNOW);
		sheep.onSpawn();
		sheep.setInvulnerable(false);
		double health = 10.0D;
		if(summoner instanceof Player) {
			health = health * (PlayerData.getData(summoner.getUniqueId()).getKit().getStats().getSheepHealth());
		}
		sheep.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(health);
		sheep.setHealth((float) health);
		sheep.getBukkitEntity().setMetadata("type", new FixedMetadataValue(SheepWars.getInstance(), sheepType.getName()));
		return sheep;
	}

	public abstract String getSheepName();
	
	public abstract EnumSheepWars getType();
	
	public abstract EnumColor getColor();
	
	public abstract float getExplosionForce();
	
	public abstract Item getBoostItem();

	public abstract void playParticle();
	
	public abstract void playAreaEffect();

	public abstract int getEffectSpeed();
	
	public abstract int getAreaSize();
	
	public abstract int getTickDuration();
	
	public abstract boolean isSupport();

	public abstract void onSpawn();
	
	public abstract boolean willExplode();
	
	public abstract void onEndLife();
	
	@Override
	public int getExpValue(EntityHuman entityhuman) {
		return 0;
	}
	
	@Override
	public MinecraftKey getDefaultLootTable() {
        return null;
    }
	
	@Override
	public boolean i(ItemStack itemstack) {
		return itemstack.getItem() == getBoostItem();
	}
	
	@Override
	public void tick() {
		if(locY < 0) {
			die();
			return;
		}
		super.tick();
		
		int percent = (int) ((float)ticksLived/(float)getTickDuration()*100);
		if(percent > 50) {
			double result = Math.cos((percent-50)*(float)ticksLived/100);
			if(result > 0)
				setColor(EnumColor.WHITE);
			else if(result < 0)
				setColor(getColor());
		}
		
		
		if(ticksLived % 20 == 0) {
			setTarget();
		}
		
		playParticle();
		playAreaEffect();
		
		if (percent == 75 && willExplode())
			world.getWorld().playSound(new Location(world.getWorld(), locX, locY, locZ), Sound.ENTITY_TNT_PRIMED, 1.0F, 0.5F);
		if (getTickDuration() != -1 && ticksLived >= getTickDuration()) {
			explode();
		}
	}
	
	protected void explode() {
		if(willExplode()) {
			getWorld().createExplosion(this, locX, locY + (double) (this.getHeight() / 16.0F), locZ, getExplosionForce(), false, Effect.DESTROY);
		}
		die();
	}
	
	public void setTarget() {
		List<Player> nearbyPlayers = getNearbyEnemies(8);
		if(!nearbyPlayers.isEmpty()) {
			EntityPlayer target = ((CraftPlayer) nearbyPlayers.get(0)).getHandle();
			setGoalTarget(target, TargetReason.CUSTOM, true);
		}
	}
	
	@Override
	public boolean damageEntity(DamageSource damagesource, float f) {
		if (damagesource.equals(DamageSource.FALL))
			return false;
		return super.damageEntity(damagesource, f);
	}
	
	@Override
	public void die() {
		onEndLife();
		super.die();
	}
}

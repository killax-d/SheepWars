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
package com.phoenixgames.sheepwars.bonus;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepWars;
import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.settings.SheepWarsSpawnPoint;
import com.phoenixgames.sheepwars.utils.TextUtils;

public class Bonus {

	private static Bonus currentBonus;
	private Location spawn;
	
	public Bonus() {
		if(currentBonus != null)
			currentBonus.die();
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!PlayerData.getData(player.getUniqueId()).isDead())
				player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
		}
		Random random = new Random();
		List<SheepWarsSpawnPoint> spawns = SheepWars.getInstance().getSettings().getBonusSpawn();
		
		spawn = spawns.get(random.nextInt(spawns.size())).getLocation();
		currentBonus = this;
		summon();
	}
	
	private void summon() {
		Bukkit.getWorld("world").getBlockAt(spawn).setMetadata("bonus", new FixedMetadataValue(SheepWars.getInstance(), true));
		Bukkit.getWorld("world").getBlockAt(spawn).setType(getRandomMaterial());
	}
	
	private void die() {
		currentBonus = null;
		Bukkit.getWorld("world").getBlockAt(spawn).removeMetadata("bonus", SheepWars.getInstance());;
		Bukkit.getWorld("world").getBlockAt(spawn).setType(Material.AIR);
	}
	
	public static Material getRandomMaterial() {
		Random random = new Random();
		switch (random.nextInt(25)) {
			case 0:
				return Material.BLACK_WOOL;
			case 1:
				return Material.BLUE_WOOL;
			case 2:
				return Material.BROWN_WOOL;
			case 3:
				return Material.CYAN_WOOL;
			case 4:
				return Material.GRAY_WOOL;
			case 5:
				return Material.GREEN_WOOL;
			case 6:
				return Material.LIGHT_BLUE_WOOL;
			case 7:
				return Material.LIGHT_GRAY_WOOL;
			case 8:
				return Material.LIME_WOOL;
			case 9:
				return Material.MAGENTA_WOOL;
			case 10:
				return Material.ORANGE_WOOL;
			case 11:
				return Material.PINK_WOOL;
			case 12:
				return Material.PURPLE_WOOL;
			case 13:
				return Material.RED_WOOL;
			case 14:
				return Material.YELLOW_WOOL;
			default:
				return Material.WHITE_WOOL;
		}
	}
	
	public static void onProjectileHit(ProjectileHitEvent event) {
		if(event.getHitBlock() != null && event.getHitBlock().hasMetadata("bonus")) {
			applyEffectFromMaterial(event.getHitBlock(), (Player) event.getEntity().getShooter());
			currentBonus.die();
		}
		event.getEntity().remove();
	}
	
	public static void applyEffectFromMaterial(Block block, Player player) {
		PlayerData data = PlayerData.getData(player.getUniqueId());
		EnumTeam team = data.getTeam();
		
		EnumTranslate effect;
		int duration = 0;
		
		switch (block.getType()) {
			case BLACK_WOOL:
				effect = EnumTranslate.BLIND;
				duration = 10;
				for(Player e : team.getEnemies()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * duration, 1));
				}
				break;
			case BLUE_WOOL:
				effect = EnumTranslate.SWIFT;
				duration = 15;
				for(Player e : team.getAlivePlayers()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 2));
				}
				break;
			case BROWN_WOOL:
				effect = EnumTranslate.LEVITATION;
				duration = 8;
				for(Player e : team.getEnemies()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 20 * duration, 2));
				}
				break;
			case CYAN_WOOL:
				effect = EnumTranslate.PERCANT;
				duration = 20;
				for(Player e : team.getEnemies()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * duration, 2));
				}
				break;
			case GRAY_WOOL:
				effect = EnumTranslate.ROBUSTE;
				duration = 20;
				for(Player e : team.getAlivePlayers()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * duration, 1));
				}
				break;
			case GREEN_WOOL:
				effect = EnumTranslate.IVY;
				duration = 3;
				for(Player e : team.getEnemies()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * duration, 1));
				}
				break;
			case LIGHT_BLUE_WOOL:
				effect = EnumTranslate.FREEZE;
				duration = 15;
				for(Player e : team.getEnemies()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * duration, 2));
				}
				break;
			case LIGHT_GRAY_WOOL:
				effect = EnumTranslate.BOUNCY;
				duration = 0;
				for(Player e : team.getEnemies()) {
					if(e != null)
						e.setVelocity((new Vector(0.0D, 3.7D, 0.0D)).add(e.getLocation().getDirection()).multiply(0.35D));
				}
				break;
			case LIME_WOOL:
				effect = EnumTranslate.RABBIT;
				duration = 10;
				for(Player e : team.getAlivePlayers()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * duration, 1));
				}
				break;
			case MAGENTA_WOOL:
				effect = EnumTranslate.POUTCH;
				duration = 15;
				for(Player e : team.getEnemies()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 20 * duration, 2));
				}
				break;
			case ORANGE_WOOL:
				effect = EnumTranslate.FIRE;
				duration = 20;
				for(Player e : team.getAlivePlayers()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 20 * duration, 1));
				}
				break;
			case PINK_WOOL:
				effect = EnumTranslate.DOCTOR;
				duration = 10;
				for(Player e : team.getAlivePlayers()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * duration, 1));
				}
				break;
			case PURPLE_WOOL:
				effect = EnumTranslate.CONFUSION;
				duration = 10;
				for(Player e : team.getEnemies()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * duration, 1));
				}
				break;
			case RED_WOOL:
				effect = EnumTranslate.FURY;
				duration = 15;
				for(Player e : team.getAlivePlayers()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * duration, 1));
				}
				break;
			case YELLOW_WOOL:
				effect = EnumTranslate.GOLDEN;
				duration = 10;
				for(Player e : team.getAlivePlayers()) {
					if(e != null)
						e.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20 * duration, 2));
				}
				break;
			default:
				effect = EnumTranslate.MORE_SHEEP;
				duration = 0;
				for(Player p : team.getAlivePlayers()) {
					if(p != null) {
						String bonus = "";
						if(Math.random() < PlayerData.getData(p.getUniqueId()).getKit().getStats().getSheepCount()) {
							p.getInventory().addItem(EntitySheepWars.getRandomSheepItem());
							bonus = " &e(+1 bonus)";
						}
						p.getInventory().addItem(EntitySheepWars.getRandomSheepItem());
						p.sendMessage(TextUtils.colorize(EnumTranslate.SHEEP_GIVE.getTranslation(p.getLocale()) + bonus));
					}
				}
				break;
		}
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			if(duration > 0)
				p.sendMessage(String.format(EnumTranslate.BONUS.getTranslation(p.getLocale()), player.getName(), effect.getTranslation(p.getLocale()), duration));
			else
				p.sendMessage(String.format(EnumTranslate.BONUS_WITHOUT_DURATION.getTranslation(p.getLocale()), player.getName(), effect.getTranslation(p.getLocale())));
		}
	}
	
	public static void updateBonus() {
		if (currentBonus != null) {
			Bukkit.getWorld("world").getBlockAt(currentBonus.spawn).setType(getRandomMaterial());
			
			for (double i = 0; i <= Math.PI; i += Math.PI / 10) {
				double radius = Math.sin(i);
				double y = Math.cos(i);
				for (double a = 0; a < Math.PI * 2; a+= Math.PI / 10) {
					double x = Math.cos(a) * radius;
					double z = Math.sin(a) * radius;
					
					Bukkit.getWorld("world").spawnParticle(Particle.TOTEM, currentBonus.spawn.getX() + x, currentBonus.spawn.getY() + y, currentBonus.spawn.getZ() + z, 2, 0.0D, 0.0D, 0.0D, 0.0D, null);
				}
			}
		}
	}
	
	public static void reset() {
		if (currentBonus != null) {
			currentBonus.die();
		}
	}
	
}

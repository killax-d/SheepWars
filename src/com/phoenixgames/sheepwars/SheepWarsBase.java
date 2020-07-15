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
package com.phoenixgames.sheepwars;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.Type;
import com.phoenixgames.sheepwars.bonus.Bonus;
import com.phoenixgames.sheepwars.clock.FastClock;
import com.phoenixgames.sheepwars.clock.UpdateUI;
import com.phoenixgames.sheepwars.entity.custom.EntityMeteor;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepBlind;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepDistorsion;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepEarthquake;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepFire;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepFly;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepFrag;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepFreeze;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepFuse;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepGalactic;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepHeal;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepSeeker;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepThunder;
import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.event.EventManager;
import com.phoenixgames.sheepwars.kit.KitSwordsMan;
import com.phoenixgames.sheepwars.kit.KitTeam;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.scoreboard.RightScoreBoard;

import net.minecraft.server.v1_14_R1.DataConverterRegistry;
import net.minecraft.server.v1_14_R1.DataConverterTypes;
import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityTypes;
import net.minecraft.server.v1_14_R1.EnumCreatureType;
import net.minecraft.server.v1_14_R1.IRegistry;
import net.minecraft.server.v1_14_R1.SharedConstants;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SheepWarsBase extends JavaPlugin {

	private Map<String, EntityTypes<Entity>> customEntities = new HashMap<>();

	@Override
	public void onLoad() {
		this.registerCustomEntity("fusesheep", "fusesheep", EntitySheepFuse::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("healsheep", "healsheep", EntitySheepHeal::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("galacticsheep", "galacticsheep", EntitySheepGalactic::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("firesheep", "firesheep", EntitySheepFire::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("distorsionsheep", "distorsionsheep", EntitySheepDistorsion::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("earthquakesheep", "earthquakesheep", EntitySheepEarthquake::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("freezesheep", "freezesheep", EntitySheepFreeze::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("flysheep", "flysheep", EntitySheepFly::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("fragsheep", "fragsheep", EntitySheepFrag::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("blindsheep", "blindsheep", EntitySheepBlind::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("thundersheep", "thundersheep", EntitySheepThunder::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("seekersheep", "seekersheep", EntitySheepSeeker::new, EnumCreatureType.CREATURE);
		this.registerCustomEntity("meteorcustom", "meteorcustom", EntityMeteor::new, EnumCreatureType.MISC);
	}

	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new EventManager(), this);
		initPlayer();
		initGame();
	}

	@Override
	public void onDisable() {
		if(SheepWars.getInstance().getSettings() != null)
			Bonus.reset();
	}

	public void initPlayer() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.teleport(SheepWars.getInstance().getSettings().getSpawnPoint().getLocation());
			PlayerData data = PlayerData.getData(player.getUniqueId());
			RightScoreBoard.addToPlayer(player);
			player.setInvulnerable(false);

			data.setTeam(EnumTeam.UNKNOW);
			data.setKit(new KitTeam(player, 0));
			player.getInventory().clear();
			data.getKit().equipHotbar(player);
			data.setKit(new KitSwordsMan(player, data.getKits().get(KitSwordsMan.class)));
			
			UpdateUI.updateScoreboard();
		}
	}

	public void initGame() {
		new FastClock().runTaskTimer(this, 1L, 1L);
	}

	public Map<String, EntityTypes<Entity>> getCustomEntities() {
		return customEntities;
	}

	protected void registerCustomEntity(String entityName, String nmsName, EntityTypes.b entity,
			EnumCreatureType creatureType) {
		Map<String, Type<?>> types = (Map<String, Type<?>>) DataConverterRegistry.a()
				.getSchema(DataFixUtils.makeKey(SharedConstants.a().getWorldVersion()))
				.findChoiceType(DataConverterTypes.ENTITY).types();
		types.put("minecraft:" + entityName, types.get("minecraft:" + nmsName));
		EntityTypes.a<Entity> a = EntityTypes.a.a(entity, creatureType);
		EntityTypes entityType = IRegistry.a(IRegistry.ENTITY_TYPE, entityName, a.a(entityName));
		customEntities.put(entityName, entityType);
	}
}

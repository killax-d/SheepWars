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
package com.phoenixgames.sheepwars.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.kit.KitArcher;
import com.phoenixgames.sheepwars.kit.KitBonusSheep;
import com.phoenixgames.sheepwars.kit.KitHardnessSheep;
import com.phoenixgames.sheepwars.kit.KitMobility;
import com.phoenixgames.sheepwars.kit.KitMoreHealth;
import com.phoenixgames.sheepwars.kit.KitPyroman;
import com.phoenixgames.sheepwars.kit.KitSheepWars;
import com.phoenixgames.sheepwars.kit.KitSpectator;
import com.phoenixgames.sheepwars.kit.KitSupport;
import com.phoenixgames.sheepwars.kit.KitSwordsMan;

public class PlayerData {

	private static HashMap<UUID, PlayerData> datas = new HashMap<UUID, PlayerData>();
	
	public static enum Fields{
		KILLS, SHEEP_THROWED, SHEEP_KILLED;
	}

	private UUID uuid;
	private boolean dead;
	private EnumTeam team;
	private KitSheepWars kitSelected;
	private Map<Class<? extends KitSheepWars>, Integer> kitLevel;
	
	private int kills;
	private int sheepThrowed;
	private int sheepKilled;
	
	public static PlayerData getData(UUID uuid) {
		if(datas.containsKey(uuid))
			return datas.get(uuid);
		return Bukkit.getPlayer(uuid) != null ? new PlayerData(uuid) : null;
	}
	
	public static PlayerData getData(Player player) {
		return getData(player.getUniqueId());
	}
	
	
	public static void destroyData(UUID uuid) {
		if(datas.containsKey(uuid))
			datas.remove(uuid);
	}
	
	public static void destroyData(Player player) {
		destroyData(player.getUniqueId());
	}
	
	public PlayerData(UUID uuid) {
		Random random = new Random();
		this.uuid = uuid;
		kitLevel = new HashMap<Class<? extends KitSheepWars>, Integer>();
		kitLevel.put(KitArcher.class, random.nextInt(5)+1);
		kitLevel.put(KitBonusSheep.class, random.nextInt(5)+1);
		kitLevel.put(KitHardnessSheep.class, random.nextInt(5)+1);
		kitLevel.put(KitMobility.class, random.nextInt(5)+1);
		kitLevel.put(KitMoreHealth.class, random.nextInt(5)+1);
		kitLevel.put(KitPyroman.class, random.nextInt(5)+1);
		kitLevel.put(KitSupport.class, random.nextInt(5)+1);
		kitLevel.put(KitSwordsMan.class, random.nextInt(5)+1);
		
		kills = 0;
		sheepThrowed = 0;
		sheepKilled = 0;
		dead = false;
		
		datas.put(uuid, this);
	}

	public PlayerData(Player player) {
		this(player.getUniqueId());
	}
	
	public KitSheepWars getKit() {
		return kitSelected;
	}
	
	public void setKit(KitSheepWars kit) {
		this.kitSelected = kit;
	}
	
	public void setTeam(EnumTeam team) {
		team.addPlayer(Bukkit.getPlayer(uuid));
		if(this.kitSelected != null)
			this.kitSelected.setTeam(team);
		this.team = team;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public void die() {
		setTeam(EnumTeam.SPECTATOR);
		this.dead = true;
		Player player = Bukkit.getPlayer(uuid);
		player.getInventory().clear();
		setKit(new KitSpectator(player, 0));
		getKit().equipHotbar(player);
		player.setGameMode(GameMode.SPECTATOR);
	}
	
	
	public EnumTeam getTeam() {
		return team;
	}
	
	public Map<Class<? extends KitSheepWars>, Integer> getKits(){
		return kitLevel;
	}
	
	public void addValue(Fields field, int value) {
		switch(field) {
			case KILLS:
				kills += value;
				break;
			case SHEEP_KILLED:
				sheepKilled += value;
				break;
			case SHEEP_THROWED:
				sheepThrowed += value;
				break;
			default:
				System.err.println("[ERROR] PlayerData FieldNotFound");
				break;
		}
	}
	
	public void setFields(Fields field, Object value) {
		switch(field) {
			case KILLS:
				kills = (int) value;
				break;
			case SHEEP_KILLED:
				sheepKilled = (int) value;
				break;
			case SHEEP_THROWED:
				sheepThrowed = (int) value;
				break;
			default:
				System.err.println("[ERROR] PlayerData FieldNotFound");
				break;
		}
	}
	
	public Object getFields(Fields field) {
		switch(field) {
			case KILLS:
				return kills;
			case SHEEP_KILLED:
				return sheepKilled;
			case SHEEP_THROWED:
				return sheepThrowed;
			default:
				System.err.println("[ERROR] PlayerData FieldNotFound");
				return null;
		}
	}

	@Override
	public String toString() {
		return "PlayerData [kills=" + kills + ", sheepThrowed=" + sheepThrowed + ", sheepKilled=" + sheepKilled + "]";
	}
	
}

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
package com.phoenixgames.sheepwars.enums;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.scoreboard.RightScoreBoard;
import com.phoenixgames.sheepwars.utils.TextUtils;

public enum EnumTeam {

	UNKNOW(EnumTranslate.UNKNOW_TEAM, "404", "&7"), RED(EnumTranslate.RED_TEAM, "Red", "&c"), BLUE(EnumTranslate.BLUE_TEAM, "Blue", "&9/**\r\n" + 
			" * SheepWars plugin for PaperMC 1.14.4\r\n" + 
			" * Copyright (c) 2020-present, Killax-D.\r\n" + 
			" *\r\n" + 
			" * This program is free software: you can redistribute it and/or modify\r\n" + 
			" * it under the terms of the GNU General Public License as published by\r\n" + 
			" * the Free Software Foundation, either version 3 of the License, or\r\n" + 
			" * (at your option) any later version.\r\n" + 
			"\r\n" + 
			" * This program is distributed in the hope that it will be useful,\r\n" + 
			" * but WITHOUT ANY WARRANTY; without even the implied warranty of\r\n" + 
			" * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\r\n" + 
			" * GNU General Public License for more details.\r\n" + 
			"\r\n" + 
			" * You should have received a copy of the GNU General Public License\r\n" + 
			" * along with this program.  If not, see <https://www.gnu.org/licenses/>.\r\n" + 
			" */\r\n" + 
			""), SPECTATOR(EnumTranslate.SPECTATOR_TEAM, "Spectateur", "&7");
	
	private String teamName;
	private EnumTranslate name;
	private int maxPlayer;
	private List<UUID> players;
	private List<Location> spawnPoints;
	private String prefix;
	
	private static final class Fields{
		private static final Set<EnumTeam> TEAMS = new HashSet<EnumTeam>();
	}
	
	private static void removePlayerFromAll(Player player) {
		for(EnumTeam team : Fields.TEAMS)
			if(team != null && team.hasPlayer(player))
				team.removePlayer(player);
	}
	
	EnumTeam(EnumTranslate name, String teamName, String prefix){
		this.name = name;
		this.teamName = teamName;
		this.prefix = TextUtils.colorize(prefix);
		players = new ArrayList<UUID>();
		Fields.TEAMS.add(this);
	}
	
	public static EnumTeam getTeamByName(String name) {
		for(EnumTeam team : EnumTeam.values()) {
			if(team.getTeamName().equals(name)) {
				return team;
			}
		}
		return UNKNOW;
	}
	
	public String getTeamName() {
		return this.teamName;
	}
	
	public String getPrefix() {
		return prefix;
	}

	public void addPlayer(Player player) {
		if(this == EnumTeam.UNKNOW || this == EnumTeam.SPECTATOR) {
			removePlayerFromAll(player);
			players.add(player.getUniqueId());
			return;
		}
		if(SheepWars.state == EnumGameState.RUNNING)
			player.sendMessage(String.format(EnumTranslate.RUNNING_EXCEPTION.getTranslation(player.getLocale()), getName(player.getLocale())));
		if(players.size() < maxPlayer) {
			if(!hasPlayer(player)) {
				removePlayerFromAll(player);
				players.add(player.getUniqueId());
				player.sendMessage(String.format(EnumTranslate.JOIN_TEAM.getTranslation(player.getLocale()), getName(player.getLocale())));
				PlayerData.getData(player.getUniqueId()).setTeam(this);
				RightScoreBoard.addToPlayer(player);
			}
		}
		else {
			player.sendMessage(String.format(EnumTranslate.FULL_TEAM.getTranslation(player.getLocale()), getName(player.getLocale())));
		}
	}
	
	public Set<Player> getAlivePlayers() {
		Set<Player> playersAlive = new HashSet<Player>();
		for (UUID id : players) {
			if(!PlayerData.getData(id).isDead())
				playersAlive.add(Bukkit.getPlayer(id));
		}
		return playersAlive;
	}
	
	public Set<Player> getEnemies() {
		Set<Player> enemies = new HashSet<Player>();
		for(EnumTeam team : EnumTeam.values()) {
			if(!team.getTeamName().equals(teamName)) {
				for(Player player : team.getAlivePlayers()) {
					enemies.add(player);
				}
			}
		}
		return enemies;
	}
	
	public void removePlayer(Player player) {
		if(players.contains(player.getUniqueId()))
			players.remove(player.getUniqueId());
	}
	
	public boolean hasPlayer(Player player) {
		return players.contains(player.getUniqueId());
	}
	
	public void setMaxPlayer(int max) {
		maxPlayer = max;
	}
	
	public int getMaxPlayer() {
		return maxPlayer;
	}
	
	public String getName(String language) {
		return name.getTranslation(language);
	}
	
	public Location getRandomSpawn() {
		Random random = new Random();
		int i = random.nextInt(spawnPoints.size());
		Location location = spawnPoints.get(i);
		while(location == null) {
			i = random.nextInt(spawnPoints.size());
			location = spawnPoints.get(i);
		}
		spawnPoints.set(i, null);
		return location;
	}
	
	public Player getRandomPlayer() {
		Random random = new Random();
		int i = random.nextInt(players.size());
		Player player = Bukkit.getPlayer(players.get(i));
		while(player == null) {
			i = random.nextInt(players.size());
			player = Bukkit.getPlayer(players.get(i));
		}
		return player;
	}
	
	public void setSpawnPoints(List<Location> spawnPoints) {
		this.spawnPoints = spawnPoints;
	}
	
	public Set<Player> getPlayers(){
		Set<Player> players = new HashSet<Player>();
		for (UUID uuid : this.players) {
			if(uuid != null)
				players.add(Bukkit.getPlayer(uuid));
		}
		return players;
	}
}

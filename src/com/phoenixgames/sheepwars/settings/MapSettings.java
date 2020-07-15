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
package com.phoenixgames.sheepwars.settings;

import java.util.Arrays;
import java.util.List;

public class MapSettings {

	private String name;
	private int nbPlayers;
	private int minPlayers;
	private int gameDuration;
	private int bonusCooldown;
	private int sheepGive;
	private List<String> mapAuthors;
	private SheepWarsSpawnPoint spawn;
	private List<SheepWarsSpawnPoint> bonusSpawn;
	private List<SheepWarsTeam> teams;

	public MapSettings() {
		SheepWarsSpawnPoint s1 = new SheepWarsSpawnPoint(176.5F, 56.0F, 78.5F, 0.0F, 0.0F);
		SheepWarsSpawnPoint s2 = new SheepWarsSpawnPoint(152.5F, 56.0F, 86.5F, 0.0F, 0.0F);
		SheepWarsSpawnPoint s3 = new SheepWarsSpawnPoint(120.5F, 56.0F, 94.5F, 0.0F, 0.0F);
		SheepWarsSpawnPoint s4 = new SheepWarsSpawnPoint(140.5F, 56.0F, 92.5F, 0.0F, 0.0F);
		bonusSpawn = Arrays.asList(s1, s2, s3, s4);
	}
	
	public String getName() {
		return name;
	}

	public int getNbPlayers() {
		return nbPlayers;
	}
	
	public int getMinPlayers() {
		return minPlayers;
	}

	public int getGameDuration() {
		return gameDuration;
	}
	
	public int getGameDurationInTick() {
		return gameDuration * 60 * 20;
	}
	
	public int getBonusCooldown() {
		return bonusCooldown;
	}
	
	public int getSheepGive() {
		return sheepGive;
	}
	
	public List<String> getAuthors() {
		return mapAuthors;
	}

	public List<SheepWarsSpawnPoint> getBonusSpawn() {
		return bonusSpawn;
	}

	public SheepWarsSpawnPoint getSpawnPoint() {
		return spawn;
	}

	public List<SheepWarsTeam> getTeams() {
		return teams;
	}

}

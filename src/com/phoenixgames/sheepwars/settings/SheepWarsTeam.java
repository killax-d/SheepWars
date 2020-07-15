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

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class SheepWarsTeam {

	private String name;
	private int nbPlayers;
	private List<SheepWarsSpawnPoint> spawnPoints;

	public String getName() {
		return name;
	}
	
	public int getNbPlayers() {
		return nbPlayers;
	}

	public List<SheepWarsSpawnPoint> getSpawnPoints() {
		return spawnPoints;
	}
	
	public List<Location> getSpawnLocations() {
		List<Location> locations = new ArrayList<>();
		for(SheepWarsSpawnPoint swsp : spawnPoints) {
			locations.add(swsp.getLocation());
		}
		return locations;
	}

}

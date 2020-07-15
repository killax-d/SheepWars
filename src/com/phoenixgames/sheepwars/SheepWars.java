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

import java.io.FileReader;
import java.io.IOException;

import org.bukkit.Bukkit;

import com.google.gson.Gson;
import com.phoenixgames.sheepwars.enums.EnumGameState;
import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.settings.MapSettings;
import com.phoenixgames.sheepwars.settings.SheepWarsTeam;

public class SheepWars extends SheepWarsBase {

	private static SheepWars instance;
	private static MapSettings settings;
	public static EnumGameState state;
	
	@Override
	public void onLoad() {
		instance = this;
		
		// LOAD config file from world folder
        Gson gson = new Gson();
        
		try (FileReader reader = new FileReader("world/mapsettings.json")) {
			settings = gson.fromJson(reader, MapSettings.class);
		} catch (IOException e) {
	    	System.err.println("No mapsettings.json in world folder, shutting down plugin...");
	    	Bukkit.getPluginManager().disablePlugin(this);
	    }
		
		if(settings != null) {
			state = EnumGameState.WAIT;
			
			for(SheepWarsTeam sheepTeam : settings.getTeams()) {
				EnumTeam team = EnumTeam.getTeamByName(sheepTeam.getName());
				if(team != EnumTeam.UNKNOW) {
					team.setMaxPlayer(sheepTeam.getNbPlayers());
					team.setSpawnPoints(sheepTeam.getSpawnLocations());
				}
			}
			
			super.onLoad();
		}
	}
	
	public static SheepWars getInstance() {
		return instance;
	}
	
	public MapSettings getSettings() {
		return settings;
	}
	
	
}

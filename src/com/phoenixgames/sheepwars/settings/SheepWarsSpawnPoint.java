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

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SheepWarsSpawnPoint {

	private float x;
	private float y;
	private float z;
	private float yaw;
	private float pitch;
	
	public SheepWarsSpawnPoint(float x, float y, float z, float yaw, float pitch) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}
	
	public Location getLocation() {
		Location location = new Location(Bukkit.getWorld("world"), x, y, z);
		location.setPitch(pitch);
		location.setYaw(yaw);
		return location;
	}


}

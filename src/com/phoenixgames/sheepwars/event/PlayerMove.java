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
package com.phoenixgames.sheepwars.event;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.clock.FastClock;
import com.phoenixgames.sheepwars.player.PlayerData;

public class PlayerMove {

	public static void event(PlayerMoveEvent event) {
		switch(SheepWars.state) {
			case WAIT:
				if(event.getTo().getY() < 0.0)
					event.getPlayer().teleport(SheepWars.getInstance().getSettings().getSpawnPoint().getLocation());
				break;
			case RUNNING:
				if(event.getTo().getY() < 0.0) {
					event.getPlayer().damage(event.getPlayer().getHealth());
					PlayerData.getData(event.getPlayer().getUniqueId()).die();
				}
				if(FastClock.playing)
					return;
				Location from = event.getFrom();
				Location to = event.getTo();
				if(from.getY() != to.getY())
					event.setCancelled(true);
				break;
			case END:
				if(event.getTo().getY() < 0.0)
					event.getPlayer().teleport(SheepWars.getInstance().getSettings().getSpawnPoint().getLocation());
				break;
		}
	}
	
}

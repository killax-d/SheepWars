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

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.player.PlayerData.Fields;

public class PlayerDeath {

	public static void event(PlayerDeathEvent event) {
		switch(SheepWars.state) {
			case WAIT:
				event.setCancelled(true);
				event.getEntity().teleport(SheepWars.getInstance().getSettings().getSpawnPoint().getLocation());
				break;
			case RUNNING:
				Player killer = event.getEntity().getKiller();
				
				PlayerData data = PlayerData.getData(event.getEntity().getUniqueId());
				data.die();

				event.setDeathMessage(null);
				
				if(killer != null) {
					PlayerData dataKiller = PlayerData.getData(killer.getUniqueId());
					dataKiller.addValue(Fields.KILLS, 1);
	
					for(Player p : Bukkit.getOnlinePlayers()) {
						if(p != null)
							p.sendMessage(String.format(EnumTranslate.KILL_PLAYER.getTranslation(p.getLocale()), killer.getDisplayName(), event.getEntity().getDisplayName()));
					}
				}
				break;
			case END:
				event.setCancelled(true);
				event.getEntity().teleport(SheepWars.getInstance().getSettings().getSpawnPoint().getLocation());
				break;
		}
	}
}

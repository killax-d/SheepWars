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

import org.bukkit.event.block.BlockBreakEvent;

import com.phoenixgames.sheepwars.SheepWars;

public class BlockBreak {

	public static void event(BlockBreakEvent event) {
		switch(SheepWars.state) {
			case WAIT:
				event.setCancelled(true);
				break;
			case RUNNING:
				break;
			case END:
				event.setCancelled(true);
				break;
		}
		event.setDropItems(false);
	}
}

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

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.phoenixgames.sheepwars.enums.EnumInventory;

public class InventoryClick {

	public static void event(InventoryClickEvent event) {
		if (!(event.getInventory().getHolder() instanceof Player)) {
			return;
		}
		Player player = (Player) event.getWhoClicked();
		for (EnumInventory inv : EnumInventory.values()) {
			if (inv.isInside(player)) {
				if (event.getCurrentItem() != null) {
					inv.getInventory(player).onClick(event);
				}
			}
		}
	}
}

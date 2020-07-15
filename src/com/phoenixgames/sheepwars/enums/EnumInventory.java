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

import org.bukkit.entity.Player;

import com.phoenixgames.sheepwars.inventory.AbstractInventory;
import com.phoenixgames.sheepwars.inventory.KitMenu;

public enum EnumInventory {

	KIT(KitMenu.class, EnumTranslate.KIT_MENU);

	private Class<? extends AbstractInventory> clazz;
	private EnumTranslate invTitle;

	EnumInventory(Class<? extends AbstractInventory> clazz, EnumTranslate invTitle){
		this.clazz = clazz;
		this.invTitle = invTitle;
	}

	public AbstractInventory getInventory(Player player) {
		try {
			return clazz.getConstructor(EnumTranslate.class, Player.class).newInstance(invTitle, player);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void openFor(Player player) {
			getInventory(player).openFor(player);
	}

	public boolean isInside(Player player) {
		return player.getOpenInventory().getTitle().equals(invTitle.getTranslation(player.getLocale()));
	}

}

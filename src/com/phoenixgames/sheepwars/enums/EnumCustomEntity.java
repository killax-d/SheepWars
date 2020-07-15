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

import java.util.Map;

import com.phoenixgames.sheepwars.SheepWars;

import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityTypes;

public enum EnumCustomEntity {

	METEOR("meteor"), SIT("sit");

	private String name;

	EnumCustomEntity(String name) {
		this.name = name + "custom";
	}

	public EntityTypes<? extends Entity> getEntityType() {
		Map<String, EntityTypes<Entity>> registeredEntities = SheepWars.getPlugin(SheepWars.class).getCustomEntities();
		if (registeredEntities.containsKey(name)) {
			return registeredEntities.get(name);
		}
		return EntityTypes.TNT;
	}
	
	public String getName() {
		return name;
	}
	
}

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
package com.phoenixgames.sheepwars.kit;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.utils.TextUtils;

public class KitTeam extends KitSheepWars {

	public KitTeam(){
		super();
	}
	
	public KitTeam(Player player, int level){
		super(player, 0);
	}
	
	@Override
	public String getName() {
		return EnumTranslate.STARTER.getTranslation(getLang());
	}

	@Override
	public List<String> getDescription() {
		return TextUtils.parseLine(EnumTranslate.DESC_STARTER.getTranslation(getLang()));
	}
	
	@Override
	public Material getIcon() {
		return Material.NAME_TAG;
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] {
			null,
			null,
			null,
			null
		};
	}

	@Override
	public ItemStack[] getHotbar() {
		return new ItemStack[] {
				fastItem(Material.NAME_TAG, EnumTranslate.KIT_SELECTOR.getTranslation(getLang()), null, null),
				null,//fastItem(Material.RED_DYE, String.format(EnumTranslate.TEAM_SELECTOR.getTranslation(getLang()), EnumTranslate.RED_TEAM.getTranslation(getLang())), null, null),
				null,//fastItem(Material.LAPIS_LAZULI, String.format(EnumTranslate.TEAM_SELECTOR.getTranslation(getLang()), EnumTranslate.BLUE_TEAM.getTranslation(getLang())), null, null),
				null,
				null,
				null,
				null,
				null,
				fastItem(Material.RED_BED, EnumTranslate.BACK_HUB.getTranslation(getLang()), null, null)
		};
	}

	@Override
	public Statistics getStats() {
		return new Statistics(10, 1.0F, 1.0F, 1.0F);
	}

}

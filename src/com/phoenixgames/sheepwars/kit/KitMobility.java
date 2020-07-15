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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.utils.TextUtils;

public class KitMobility extends KitSheepWars {
	
	private Map<Enchantment, Integer> enchantBoots;

	public KitMobility(){
		super();
	}
	
	public KitMobility(Player player, int level){
		super(player, level);

		enchantBoots = new HashMap<Enchantment, Integer>();
		
		switch(getLevel()) {
			case 1:
				enchantBoots.put(Enchantment.PROTECTION_FALL, 1);
				break;
			case 2:
				enchantBoots.put(Enchantment.PROTECTION_FALL, 2);
				break;
			case 3:
				enchantBoots.put(Enchantment.PROTECTION_FALL, 3);
				break;
			case 4:
				enchantBoots.put(Enchantment.PROTECTION_FALL, 4);
				break;
			case 5:
				enchantBoots.put(Enchantment.PROTECTION_FALL, 4);
				break;
		}
		
	}
	
	@Override
	public String getName() {
		return EnumTranslate.MOBILITY.getTranslation(getLang());
	}

	@Override
	public List<String> getDescription() {
		return TextUtils.parseLine(EnumTranslate.DESC_MOBILITY.getTranslation(getLang()));
	}
	
	@Override
	public Material getIcon() {
		return Material.LEATHER_BOOTS;
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] {
				fastArmorPiece(Material.LEATHER_HELMET, String.format("%s &e%s", getName(), getRomanLevel()), null, null),
				fastArmorPiece(Material.LEATHER_CHESTPLATE, String.format("%s &e%s", getName(), getRomanLevel()), null, null),
				fastArmorPiece(Material.LEATHER_LEGGINGS, String.format("%s &e%s", getName(), getRomanLevel()), null, null),
				fastArmorPiece(Material.LEATHER_BOOTS, String.format("%s &e%s", getName(), getRomanLevel()), null, enchantBoots)
		};
	}

	@Override
	public ItemStack[] getHotbar() {
		return new ItemStack[] {
				fastItem(Material.WOODEN_SWORD, String.format("%s &e%s", getName(), getRomanLevel()), null, null),
				fastItem(Material.BOW, String.format("%s &e%s", getName(), getRomanLevel()), null, null),
				null,
				null,
				null,
				null,
				null,
				null,
				null
		};
	}

	@Override
	public Statistics getStats() {
		return new Statistics(8 + (1*(getLevel()-1)), 1.0F + (0.075F * getLevel()), 1.0F, 0.0F);
	}

}

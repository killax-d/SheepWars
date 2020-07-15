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

public class KitArcher extends KitSheepWars {

	private Map<Enchantment, Integer> enchantBow;

	public KitArcher(){
		super();
	}
	
	public KitArcher(Player player, int level){
		super(player, level);
		
		enchantBow = new HashMap<Enchantment, Integer>();
		
		switch(getLevel()) {
			case 1:
				enchantBow.put(Enchantment.ARROW_KNOCKBACK, 1);
				break;
			case 2:
				enchantBow.put(Enchantment.ARROW_KNOCKBACK, 1);
				break;
			case 3:
				enchantBow.put(Enchantment.ARROW_DAMAGE, 1);
				enchantBow.put(Enchantment.ARROW_KNOCKBACK, 1);
				break;
			case 4:
				enchantBow.put(Enchantment.ARROW_DAMAGE, 1);
				enchantBow.put(Enchantment.ARROW_KNOCKBACK, 1);
				break;
			case 5:
				enchantBow.put(Enchantment.ARROW_DAMAGE, 1);
				enchantBow.put(Enchantment.ARROW_KNOCKBACK, 2);
				break;
		}
		
	}
	
	@Override
	public String getName() {
		return EnumTranslate.ARCHER.getTranslation(getLang());
	}

	@Override
	public List<String> getDescription() {
		return TextUtils.parseLine(EnumTranslate.DESC_ARCHER.getTranslation(getLang()));
	}
	
	@Override
	public Material getIcon() {
		return Material.ARROW;
	}

	@Override
	public ItemStack[] getArmor() {
		return new ItemStack[] {
				fastArmorPiece(Material.LEATHER_HELMET, String.format("%s &e%s", getName(), getRomanLevel()), null, null),
				fastArmorPiece(Material.LEATHER_CHESTPLATE, String.format("%s &e%s", getName(), getRomanLevel()), null, null),
				fastArmorPiece(Material.LEATHER_LEGGINGS, String.format("%s &e%s", getName(), getRomanLevel()), null, null),
				fastArmorPiece(Material.LEATHER_BOOTS, String.format("%s &e%s", getName(), getRomanLevel()), null, null)
		};
	}

	@Override
	public ItemStack[] getHotbar() {
		return new ItemStack[] {
				fastItem(Material.WOODEN_SWORD, String.format("%s &e%s", getName(), getRomanLevel()), null, null),
				fastItem(Material.BOW, String.format("%s &e%s", getName(), getRomanLevel()), null, enchantBow),
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
		return new Statistics(8 + (1*(getLevel()-1)), 1.0F + (0.025F * getLevel()), 1.0F, 0.0F);
	}

}

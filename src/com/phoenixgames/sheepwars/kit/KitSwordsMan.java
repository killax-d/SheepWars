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

public class KitSwordsMan extends KitSheepWars {

	private Map<Enchantment, Integer> enchantSword;
	private Material materialSword;

	public KitSwordsMan(){
		super();
	}
	
	public KitSwordsMan(Player player, int level){
		super(player, level);

		enchantSword = new HashMap<Enchantment, Integer>();
		
		switch(getLevel()) {
			case 1:
				materialSword = Material.WOODEN_SWORD;
				enchantSword.put(Enchantment.DAMAGE_ALL, 1);
				break;
			case 2:
				materialSword = Material.WOODEN_SWORD;
				enchantSword.put(Enchantment.DAMAGE_ALL, 1);
				enchantSword.put(Enchantment.KNOCKBACK, 1);
				break;
			case 3:
				materialSword = Material.WOODEN_SWORD;
				enchantSword.put(Enchantment.DAMAGE_ALL, 2);
				enchantSword.put(Enchantment.KNOCKBACK, 1);
				break;
			case 4:
				materialSword = Material.STONE_SWORD;
				enchantSword.put(Enchantment.KNOCKBACK, 1);
				break;
			case 5:
				materialSword = Material.STONE_SWORD;
				enchantSword.put(Enchantment.KNOCKBACK, 2);
				break;
		}
		
	}
	
	@Override
	public String getName() {
		return EnumTranslate.SWORDSMAN.getTranslation(getLang());
	}

	@Override
	public List<String> getDescription() {
		return TextUtils.parseLine(EnumTranslate.DESC_SWORDSMAN.getTranslation(getLang()));
	}
	
	@Override
	public Material getIcon() {
		return Material.WOODEN_SWORD;
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
				fastItem(materialSword, String.format("%s &e%s", getName(), getRomanLevel()), null, enchantSword),
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
		return new Statistics(10 + (1*(getLevel()-1)), 1.0F + (0.025F * getLevel()), 1.0F, 0.0F);
	}

}

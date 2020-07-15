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
package com.phoenixgames.sheepwars.inventory;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.utils.TextUtils;

public abstract class AbstractInventory {
	
	private static final ItemStack NOT_FOUND = fastItem(TextUtils.colorize("&7&l404 &r&cItem not found"), "&eError please contact an administrator", Material.BARRIER);

	protected static ItemStack			glass;
	protected EnumTranslate				title;
	protected Map<Integer, ItemStack>	items;
	protected int[][]					pattern;

	public AbstractInventory(EnumTranslate title, int[][] pattern) {
		this.title = title;
		this.pattern = pattern;
	}

	public abstract void onClick(InventoryClickEvent event);

	public abstract void openFor(Player player);

	public EnumTranslate getTitle() {
		return title;
	}

	protected static ItemStack fastItem(String title, String desc, Material mat) {
		ItemStack quick = new ItemStack(mat, 1);
		ItemMeta meta = quick.getItemMeta();
		meta.setDisplayName(TextUtils.colorize(title));
		if (desc != null) {
			meta.setLore(TextUtils.convert(desc, 25));
		}
		quick.setItemMeta(meta);
		return quick;
	}
	
	public boolean isMatching(ItemStack stack, ItemStack inItem) {
		if (stack == null || inItem == null || stack.getType() == Material.AIR) {
			return false;
		}
		return stack.getItemMeta().getDisplayName().equals(inItem.getItemMeta().getDisplayName());
	}
	
	public int getTotalSize() {
		int x = 0;
		for (int i = 0; i < pattern.length; i++)
			x += pattern[i].length;
		return x;
	}

	public void dispatchItems(Inventory inv) {
		for (int i = 0; i < pattern.length; i++) {
			for (int j = 0; j < pattern[i].length; j++) {
				if (pattern[i][j] != 0)
					inv.setItem((i*9) + j, items.containsKey(pattern[i][j]) 
							? items.get(pattern[i][j]) 
							: NOT_FOUND);
			}
		}
	}
	
}

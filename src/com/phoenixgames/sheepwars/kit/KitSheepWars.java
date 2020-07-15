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
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.utils.TextUtils;

public abstract class KitSheepWars {

	private int level;
	private EnumTeam team;
	private String lang;

	public KitSheepWars(Player player, int level){
		this.level = level;
		this.team = PlayerData.getData(player.getUniqueId()).getTeam();
		this.lang = player.getLocale();
	}
	
	public KitSheepWars(){
		
	}
	
	public abstract String getName();
	
	public abstract List<String> getDescription();
	
	public abstract Material getIcon();
	
	public abstract ItemStack[] getArmor();
	
	public abstract ItemStack[] getHotbar();
	
	public abstract Statistics getStats();
	
	public String getLang() {
		return lang;
	}
	
	public KitSheepWars setData(Player player) {
		this.lang = player.getLocale();
		this.level = PlayerData.getData(player.getUniqueId()).getKits().get(this.getClass());
		return this;
	}

	public void setTeam(EnumTeam team) {
		this.team = team;
	}
	
	public int getLevel() {
		return level;
	}

	public ItemStack buildIcon() {
		List<String> desc = getDescription();
		desc.addAll(getStats().toDescription(getLang()));
		return fastItem(getIcon(), TextUtils.colorize(getName() + " &e" + getRomanLevel()), desc, null);
	}
	
	public String getRomanLevel() {
		switch(getLevel()) {
			case 1:
				return "I";
			case 2:
				return "II";
			case 3:
				return "III";
			case 4:
				return "IV";
			case 5:
				return "V";
			default:
				return "O";
		}
	}
	
	public EnumTeam getTeam() {
		return team;
	}
	

	public Color getColor() {
		switch(team) {
			case BLUE:
				return Color.BLUE;
			case RED:
				return Color.RED;
			default:
				return Color.GRAY;
		}
	}
	
	public void equipArmor(Player player) {
		if(getArmor().length == 0)
			return;
		player.getInventory().setHelmet(getArmor()[0]);
		player.getInventory().setChestplate(getArmor()[1]);
		player.getInventory().setLeggings(getArmor()[2]);
		player.getInventory().setBoots(getArmor()[3]);
	}
	
	public void equipHotbar(Player player) {
		if(getHotbar().length == 0)
			return;
		for(int i = 0; i < 9; i++) {
			if(getHotbar()[i] != null)
				player.getInventory().setItem(i, getHotbar()[i]);
		}
	}
	
	public ItemStack fastItem(Material material, String name, List<String> descriptions, Map<Enchantment, Integer> enchants) {
		ItemStack item = new ItemStack(material);
		if(descriptions != null)
			item.setLore(descriptions);
		if(enchants != null)
			item.addEnchantments(enchants);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(TextUtils.colorize(name));
		item.setItemMeta(meta);
		return item;
	}
	
	public ItemStack fastArmorPiece(Material material, String name, List<String> descriptions, Map<Enchantment, Integer> enchants) {
		ItemStack item = fastItem(material, name, descriptions, enchants);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		meta.setColor(getColor());
		item.setItemMeta(meta);
		return item;
	}
}

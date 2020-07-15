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

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepWars;
import com.phoenixgames.sheepwars.utils.TextUtils;

import net.minecraft.server.v1_14_R1.Entity;
import net.minecraft.server.v1_14_R1.EntityTypes;

public enum EnumSheepWars {

	FUSE("fuse", fastWool("&cFUSE", Material.RED_WOOL)),
	HEAL("heal", fastWool("&dHEAL", Material.PINK_WOOL)),
	GALACTIC("galactic", fastWool("&1GALACTIC", Material.BLUE_WOOL)),
	FIRE("fire", fastWool("&6FIRE", Material.ORANGE_WOOL)),
	DISTORSION("distorsion", fastWool("&6DISTORSION", Material.PURPLE_WOOL)),
	EARTHQUAKE("earthquake", fastWool("&eEARTHQUAKE", Material.BROWN_WOOL)),
	FREEZE("freeze", fastWool("&bFREEZE", Material.LIGHT_BLUE_WOOL)),
	FLY("fly", fastWool("&fFLY", Material.WHITE_WOOL)),
	FRAG("frag", fastWool("&8FRAG", Material.GRAY_WOOL)),
	BLIND("blind", fastWool("&0BLIND", Material.BLACK_WOOL)),
	THUNDER("thunder", fastWool("&eTHUNDER", Material.YELLOW_WOOL)),
	SEEKER("seeker", fastWool("&2SEEKER", Material.GREEN_WOOL));

	private String name;
	private ItemStack wool;
	
	private static final class Fields{
		private static final Map<Material, EnumSheepWars> SHEEP_BY_WOOL = new HashMap<Material, EnumSheepWars>();
	}

	EnumSheepWars(String name, ItemStack wool) {
		this.name = name + "sheep";
		this.wool = wool;
		Fields.SHEEP_BY_WOOL.put(wool.getType(), this);
	}
	
	public static EnumSheepWars getEntityByWool(Material material) {
		return Fields.SHEEP_BY_WOOL.get(material);
	}

	public EntityTypes<? extends Entity> getEntityType() {
		Map<String, EntityTypes<Entity>> registeredSheeps = SheepWars.getPlugin(SheepWars.class).getCustomEntities();
		if (registeredSheeps.containsKey(name)) {
			return registeredSheeps.get(name);
		}
		return EntityTypes.SHEEP;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack getWool() {
		return wool;
	}
	
	public static EntitySheepWars summon(Material wool, Player summoner) {
		return EntitySheepWars.summonSheep(summoner, getEntityByWool(wool));
	}
	
	private static ItemStack fastWool(String title, Material material) {
		ItemStack quick = new ItemStack(material);
		ItemMeta meta = quick.getItemMeta();
		meta.setDisplayName(TextUtils.colorize(title));
		quick.setItemMeta(meta);
		return quick;
	}
	
	public static boolean isMatching(ItemStack itemIn, ItemStack current) {
		return itemIn.getItemMeta().getDisplayName() == current.getItemMeta().getDisplayName();
	}
}

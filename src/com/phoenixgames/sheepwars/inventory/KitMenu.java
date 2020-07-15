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

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.kit.KitArcher;
import com.phoenixgames.sheepwars.kit.KitBonusSheep;
import com.phoenixgames.sheepwars.kit.KitHardnessSheep;
import com.phoenixgames.sheepwars.kit.KitMobility;
import com.phoenixgames.sheepwars.kit.KitMoreHealth;
import com.phoenixgames.sheepwars.kit.KitPyroman;
import com.phoenixgames.sheepwars.kit.KitSupport;
import com.phoenixgames.sheepwars.kit.KitSwordsMan;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.scoreboard.RightScoreBoard;

public class KitMenu extends AbstractInventory {
	
	private static KitArcher ARCHER = new KitArcher();
	private static KitBonusSheep BONUS_SHEEP = new KitBonusSheep();
	private static KitHardnessSheep HARDNESS_SHEEP = new KitHardnessSheep();
	private static KitMobility MOBILITY = new KitMobility();
	private static KitMoreHealth MORE_HEALTH = new KitMoreHealth();
	private static KitPyroman PYROMAN = new KitPyroman();
	private static KitSupport SUPPORT = new KitSupport();
	private static KitSwordsMan SWORDS_MAN = new KitSwordsMan();

	private static int[][] pattern = new int[][] { 
		{ 0, 1, 2, 3, 4, 5, 6, 7, 0 }, 
		{ 0, 8, 9, 10, 0, 11, 12, 13, 0 },
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }
	};

	public KitMenu(EnumTranslate title, Player player) {
		super(title, pattern);

		ARCHER.setData(player);
		BONUS_SHEEP.setData(player);
		HARDNESS_SHEEP.setData(player);
		MOBILITY.setData(player);
		MORE_HEALTH.setData(player);
		PYROMAN.setData(player);
		SUPPORT.setData(player);
		SWORDS_MAN.setData(player);
	}

	@Override
	public void onClick(InventoryClickEvent event) {
		ItemStack current = event.getCurrentItem();
		Player player = (Player) event.getWhoClicked();
		PlayerData data = PlayerData.getData(player.getUniqueId());
		if (isMatching(current, ARCHER.buildIcon())) {
			data.setKit(ARCHER);
		} else if (isMatching(current, BONUS_SHEEP.buildIcon())) {
			data.setKit(BONUS_SHEEP);
		} else if (isMatching(current, HARDNESS_SHEEP.buildIcon())) {
			data.setKit(HARDNESS_SHEEP);
		} else if (isMatching(current, MOBILITY.buildIcon())) {
			data.setKit(MOBILITY);
		} else if (isMatching(current, MORE_HEALTH.buildIcon())) {
			data.setKit(MORE_HEALTH);
		} else if (isMatching(current, PYROMAN.buildIcon())) {
			data.setKit(PYROMAN);
		} else if (isMatching(current, SUPPORT.buildIcon())) {
			data.setKit(SUPPORT);
		} else if (isMatching(current, SWORDS_MAN.buildIcon())) {
			data.setKit(SWORDS_MAN);
		}
		RightScoreBoard.addToPlayer(player);
		event.setCancelled(true);
	}

	@SuppressWarnings("serial")
	@Override
	public void openFor(Player player) {
		items = new HashMap<Integer, ItemStack>() {
			{
				put(1, ARCHER.buildIcon());
				put(2, BONUS_SHEEP.buildIcon());
				put(3, HARDNESS_SHEEP.buildIcon());
				put(4, MOBILITY.buildIcon());
				put(5, MORE_HEALTH.buildIcon());
				put(6, PYROMAN.buildIcon());
				put(7, SUPPORT.buildIcon());
				put(8, SWORDS_MAN.buildIcon());
			}
		};
		
		
		Inventory inv = Bukkit.createInventory(player, getTotalSize(), title.getTranslation(player.getLocale()));
		dispatchItems(inv);
		player.openInventory(inv);
	}

}

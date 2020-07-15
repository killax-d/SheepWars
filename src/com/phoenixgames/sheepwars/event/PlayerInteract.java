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
package com.phoenixgames.sheepwars.event;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepWars;
import com.phoenixgames.sheepwars.enums.EnumInventory;
import com.phoenixgames.sheepwars.enums.EnumSheepWars;
import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.player.PlayerData.Fields;

import net.minecraft.server.v1_14_R1.Vec3D;

public class PlayerInteract {
	
	public static void event(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		switch(SheepWars.state) {
		case WAIT:
			if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				switch(event.getMaterial()) {
					case LAPIS_LAZULI:
						EnumTeam.BLUE.addPlayer(player);
						break;
					case RED_DYE:
						EnumTeam.RED.addPlayer(player);
						break;
					case NAME_TAG:
						EnumInventory.KIT.openFor(player);
						break;
					default:
						break;
					}
				}
			break;
		case RUNNING:
			if((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null && event.getItem().getType().toString().contains("_WOOL")) {
				Vector vec = player.getLocation().getDirection().multiply(6);
				
				vec.setY(vec.getY() > 2.5 ? 2.5 : vec.getY());
				
				EntitySheepWars entity = EnumSheepWars.summon(event.getMaterial(), player);
				
				if(entity != null) {
					event.setCancelled(true);
					ItemStack wool = player.getInventory().getItemInMainHand();
					wool.setAmount(wool.getAmount()-1);
					player.getActiveItem().setAmount(player.getActiveItem().getAmount()-1);
					if (!entity.isSupport())
						entity.setMot(new Vec3D(vec.getX(), vec.getY(), vec.getZ()));
					
					PlayerData data = PlayerData.getData(player.getUniqueId());
					data.addValue(Fields.SHEEP_THROWED, 1);
				}
			}
			break;
		case END:
			break;
		default:
			break;
		}
	}
	
}

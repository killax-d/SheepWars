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

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.enums.EnumGameState;
import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.kit.KitSwordsMan;
import com.phoenixgames.sheepwars.kit.KitTeam;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.scoreboard.RightScoreBoard;
import com.phoenixgames.sheepwars.utils.TextUtils;

public class PlayerJoin {

	public static void event(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerData data = PlayerData.getData(player.getUniqueId());
		data.setTeam(EnumTeam.UNKNOW);
		data.setKit(new KitTeam(player, 0));
		player.getInventory().clear();
		data.getKit().equipHotbar(player);
		data.setKit(new KitSwordsMan(player, data.getKits().get(KitSwordsMan.class)));
		player.setWalkSpeed(0.2F);
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(32);
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
		player.setHealth(20.0D);
		RightScoreBoard.addToPlayer(player);
		player.teleport(SheepWars.getInstance().getSettings().getSpawnPoint().getLocation());
		player.setInvulnerable(true);

		player.setPlayerListHeader(TextUtils.colorize("&6&lPhoenix-Games"));
		player.setPlayerListFooter(TextUtils.colorize("&6SheepWars &ecreated by &6&lKillax_D"));
		
		if(SheepWars.state == EnumGameState.RUNNING)
			player.setGameMode(GameMode.SPECTATOR);
		else
			player.setGameMode(GameMode.SURVIVAL);
	}
}

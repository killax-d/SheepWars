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
package com.phoenixgames.sheepwars.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.clock.FastClock;
import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.player.PlayerData.Fields;
import com.phoenixgames.sheepwars.utils.TextUtils;

public class RightScoreBoard {

	public static void addToPlayer(Player player){
		if (!player.isOnline()) {
			return;
		}
		Scoreboard board = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
		Objective right = board.registerNewObjective("SheepWarsRight", "dummy", TextUtils.colorize("&6SheepWars : " + Bukkit.getServer().getName()));
		right.setDisplaySlot(DisplaySlot.SIDEBAR);
		PlayerData data = PlayerData.getData(player.getUniqueId());
		switch (SheepWars.state) {
			case WAIT:
				Score online = right.getScore(String.format(EnumTranslate.PLAYERS.getTranslation(player.getLocale()), Bukkit.getOnlinePlayers().size()));
				online.setScore(0);
				Score time = right.getScore(String.format(EnumTranslate.START_IN.getTranslation(player.getLocale()), FastClock.countTimer));
				time.setScore(0);
				Score kit = right.getScore(String.format(EnumTranslate.KIT_SELECTED.getTranslation(player.getLocale()), data.getKit().getName()));
				kit.setScore(0);
				Score team = right.getScore(String.format(EnumTranslate.YOUR_TEAM.getTranslation(player.getLocale()), data.getTeam().getName(player.getLocale())));
				team.setScore(0);
				break;
			case RUNNING:
				Score timeLeft = right.getScore(String.format(EnumTranslate.TIME_LEFT.getTranslation(player.getLocale()), FastClock.tickToTime));
				timeLeft.setScore(0);
				Score red = right.getScore(TextUtils.colorize(EnumTranslate.RED_TEAM.getTranslation(player.getLocale()) + " &r&e: &r" + EnumTeam.RED.getAlivePlayers().size()));
				red.setScore(0);
				Score blue = right.getScore(TextUtils.colorize(EnumTranslate.BLUE_TEAM.getTranslation(player.getLocale()) + " &r&e: &r" + EnumTeam.BLUE.getAlivePlayers().size()));
				blue.setScore(0);
				Score kills = right.getScore(String.format(EnumTranslate.KILLS.getTranslation(player.getLocale()), data.getFields(Fields.KILLS)));
				kills.setScore(0);
				break;
			case END:
				break;
			default:
				break;
		}
		player.setScoreboard(board);
	}
}

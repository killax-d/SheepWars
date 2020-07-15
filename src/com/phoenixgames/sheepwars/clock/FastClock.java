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
package com.phoenixgames.sheepwars.clock;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import com.destroystokyo.paper.Title;
import com.phoenixgames.sheepwars.SheepWars;
import com.phoenixgames.sheepwars.bonus.Bonus;
import com.phoenixgames.sheepwars.entity.sheep.EntitySheepWars;
import com.phoenixgames.sheepwars.enums.EnumGameState;
import com.phoenixgames.sheepwars.enums.EnumTeam;
import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.kit.Statistics;
import com.phoenixgames.sheepwars.player.PlayerData;
import com.phoenixgames.sheepwars.utils.TextUtils;

public class FastClock extends BukkitRunnable {

	private int				startTimer		= 0;
	public static int		countTimer		= 15;
	private int				endTimer		= 0;
	private int				bonusCooldown	= 0;
	private int				sheepGive		= 0;
	private int				immobilisation	= 100;
	public static String 	tickToTime 		= "0:00";
	private boolean			end 			= false;
	public static boolean	playing 		= false;
	
	@Override
	public void run() {
		switch (SheepWars.state) {
		case WAIT:
			if (Bukkit.getOnlinePlayers().size() < SheepWars.getInstance().getSettings().getMinPlayers()) {
				countTimer = 15;
				return;
			}
			++startTimer;
			if (startTimer % 20 == 0 && startTimer != 0) {
				startTimer = 0;
				if (countTimer > 0) {
					--countTimer;
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
					}
					UpdateUI.updateScoreboard();
				} else {
					startGame();
					SheepWars.state = EnumGameState.RUNNING;
				}
			}
			break;
		case RUNNING:
			if(startTimer < immobilisation) {
				if(startTimer % 20 == 0) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
						int timeLeft = (immobilisation - startTimer) / 20;
						p.sendTitle(new Title(TextUtils.colorize((timeLeft > 3 ? "&6" : "&c") + timeLeft)));
					}
				}
				startTimer++;
				if(startTimer == immobilisation) {
					playing = true;
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.sendTitle(new Title(""));
					}
				}
				return;
			}
			if (endTimer == 20 * 3) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.setInvulnerable(false);
				}
			}
			checkTooLong();
			checkWin();
			if(endTimer % bonusCooldown == 0) {
				new Bonus();
			}
			if(endTimer % sheepGive == 0) {
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(!PlayerData.getData(p.getUniqueId()).getTeam().equals(EnumTeam.SPECTATOR)) {
						String bonus = "";
						if(Math.random() <= PlayerData.getData(p.getUniqueId()).getKit().getStats().getSheepCount()) {
							p.getInventory().addItem(EntitySheepWars.getRandomSheepItem());
							bonus = " &e(+1 bonus)";
						}
						p.getInventory().addItem(EntitySheepWars.getRandomSheepItem());
						p.sendMessage(TextUtils.colorize(EnumTranslate.SHEEP_GIVE.getTranslation(p.getLocale()) + bonus));
					}
				}
			}
			if(endTimer % 20 == 0) {
				tickToTime = TextUtils.tickToTime(SheepWars.getInstance().getSettings().getGameDurationInTick() - endTimer);
				UpdateUI.updateScoreboard();
				Bonus.updateBonus();
			}
			if(end) {
				countTimer = 15;
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.setGameMode(GameMode.SPECTATOR);
				}
			}
			break;
		case END:
			Bonus.reset();
			++startTimer;
			if (startTimer % 20 == 0 && startTimer != 0) {
				startTimer = 0;
				if (countTimer > 0) {
					--countTimer;
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.sendActionBar(String.format(EnumTranslate.SHUTDOWN_IN.getTranslation(p.getLocale()), countTimer));
					}
				} else {
					for(Player p : Bukkit.getOnlinePlayers()) {
						p.kickPlayer(TextUtils.colorize("&eGénération d'une nouvelle partie."));
					}
					Bukkit.shutdown();
					this.cancel();
				}
			} 
			break;
		}
	}

	private void startGame() {
		startTimer = 0;
		bonusCooldown = 20 * SheepWars.getInstance().getSettings().getBonusCooldown();
		sheepGive = 20 * SheepWars.getInstance().getSettings().getSheepGive();

		for (@SuppressWarnings("unused") Player p : Bukkit.getOnlinePlayers()) {
			Player player = EnumTeam.UNKNOW.getRandomPlayer();
			player.setGameMode(GameMode.SURVIVAL);
			if(EnumTeam.BLUE.getPlayers().size() >= EnumTeam.RED.getPlayers().size())
				PlayerData.getData(player.getUniqueId()).setTeam(EnumTeam.RED);
			else
				PlayerData.getData(player.getUniqueId()).setTeam(EnumTeam.BLUE);
		}
		
//		for (Player player : EnumTeam.UNKNOW.getPlayers()) {
//			if(EnumTeam.BLUE.getPlayers().size() >= EnumTeam.RED.getPlayers().size())
//				PlayerData.getData(player).setTeam(EnumTeam.RED);
//			else
//				PlayerData.getData(player).setTeam(EnumTeam.BLUE);
//		}
		
		for (Player player : Bukkit.getOnlinePlayers()) {
			if(player.getOpenInventory() != null)
				player.closeInventory(Reason.TELEPORT);
			player.setGameMode(GameMode.SURVIVAL);
			player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
			player.getInventory().clear();
			player.setWalkSpeed(0.2F);
			PlayerData data = PlayerData.getData(player.getUniqueId());
			data.getKit().setTeam(data.getTeam());
			Statistics stats = data.getKit().getStats();
			player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(stats.getHeart()*2);
			String name = TextUtils.colorize(data.getTeam().getPrefix() + player.getName() + "&r");
			player.setDisplayName(name);
			player.setPlayerListName(name);
			player.setCustomName(name);
			player.setCustomNameVisible(true);
			player.setWalkSpeed(player.getWalkSpeed() * stats.getSpeed());
			player.setHealth(stats.getHeart()*2);
			data.getKit().equipArmor(player);
			data.getKit().equipHotbar(player);
			if(player.getInventory().getItem(1).getType() == Material.BOW)
				player.getInventory().getItem(1).addEnchantment(Enchantment.ARROW_INFINITE, 1);
			player.getInventory().setItem(9, new ItemStack(Material.ARROW));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, immobilisation, 255));
		}
		
		for (Player redPlayer : EnumTeam.RED.getPlayers()) {
			if(redPlayer != null) {
				Location loc = EnumTeam.RED.getRandomSpawn();
				loc.setWorld(redPlayer.getWorld());
				redPlayer.teleport(loc, TeleportCause.PLUGIN);
			}
		}
		for (Player bluePlayer : EnumTeam.BLUE.getPlayers()) {
			if(bluePlayer != null) {
				Location loc = EnumTeam.BLUE.getRandomSpawn();
				loc.setWorld(bluePlayer.getWorld());
				bluePlayer.teleport(loc, TeleportCause.PLUGIN);
			}
		}
	}
	
	private void checkTooLong() {
		++endTimer;
		if (endTimer > SheepWars.getInstance().getSettings().getGameDurationInTick()) {
			UpdateUI.updateScoreboard();
			SheepWars.state = EnumGameState.END;
			for(Player p : Bukkit.getOnlinePlayers()) {
				p.sendMessage(EnumTranslate.GAME_ENDED.getTranslation(p.getLocale()));
			}
			end = true;
		}
	}
	
	private void checkWin() {
		EnumTeam[] winner = new EnumTeam[2];
		if(EnumTeam.RED.getAlivePlayers().isEmpty()) {
			winner[0] = EnumTeam.BLUE;
		}
		if(EnumTeam.BLUE.getAlivePlayers().isEmpty()) {
			winner[1] = EnumTeam.RED;
		}
		
		if(winner[0] != null && winner[1] != null) {
			UpdateUI.updateScoreboard();
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p != null) 
					p.sendMessage(EnumTranslate.MATCH_DRAW.getTranslation(p.getLocale()));
			}
			SheepWars.state = EnumGameState.END;
			end = true;
		}
		else if(winner[0] != null) {
			UpdateUI.updateScoreboard();
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p != null) 
					p.sendMessage(String.format(EnumTranslate.TEAM_WON.getTranslation(p.getLocale()), winner[0].getName(p.getLocale())));
			}
			SheepWars.state = EnumGameState.END;
			end = true;
		}
		else if(winner[1] != null) {
			UpdateUI.updateScoreboard();
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p != null) 
					p.sendMessage(String.format(EnumTranslate.TEAM_WON.getTranslation(p.getLocale()), winner[1].getName(p.getLocale())));
			}
			SheepWars.state = EnumGameState.END;
			end = true;
		}
	}

}

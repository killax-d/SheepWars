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

import com.phoenixgames.sheepwars.utils.TextUtils;

public enum EnumTranslate {

	BLUE_TEAM("&9&lBleue&r", "&9&lBlue&r"),
	RED_TEAM("&c&lRouge&r", "&c&lRed&r"),
	SPECTATOR_TEAM("&7&lSpectateur", "&7&lSpectator"),
	JOIN_TEAM("Vous avez rejoint l'équipe %s", "You have join the %s Team"),
	FULL_TEAM("L'équipe %s est complete", "The %s Team is full"),
	UNKNOW_TEAM("Aucune équipe", "No team"),
	SPECTATOR_MENU("&eMenu des Spéctateurs", "&eSpectator Menu"),
	KIT_MENU("&eMenu des Kits", "&eKits Menu"),
	BACK_HUB("&e< Retourner au HUB", "&e< Back to HUB"),
	RUNNING_EXCEPTION("&cVous ne pouvez pas effectuer ceci une fois la partie lancé.", "&cYou cannot do that when the game is running"),
	PLAYERS("&eJoueurs : &r%s", "&ePlayers : &r%s"),
	START_IN("&eLancement dans : &r%s", "&eStarting in : &r%s"),
	GAME_ENDED("&eLa partie est terminé", "&eGame is ended"),
	TIME_LEFT("&eTemps restant : &r%s", "&eTime left : &r%s"),
	KIT_SELECTED("&eVotre kit : &r%s", "&eYour kit : &r%s"),
	YOUR_TEAM("&eEquipe : &r%s", "&eTeam : &r%s"),
	BONUS("&6%s &ea activé le bonus &r%s &r&epour &r%s &esecondes", "&6&l%s &ehas actived &r%s &r&ebonus for &r%s &eseconds"),
	BONUS_WITHOUT_DURATION("&6%s &ea activé le bonus &r%s", "&6&l%s &ehas actived &r%s &r&ebonus"),
	SHEEP_GIVE("&eVous avez reçu &6&l+1 Mouton", "&eYou received &6&l+1 Sheep"),
	MATCH_DRAW("&eEgalité", "&eMatch draw"),
	TEAM_WON("&eL'équipe &r%s &eremporte la partie", "&eThe team &r%s &ewon the game"),
	KILLS("&eTué(s) : &r%s", "&eKill(s) : &r%s"),
	KILL_PLAYER("%s &ea tué &r%s&r.", "%s &ehas killed &r%s&r."),
	KIT_SELECTOR("&eSelectionner votre kit", "&eChoose your kit"),
	TEAM_SELECTOR("&eRejoindre l'équipe &r%s", "&eJoin the &r%s &eTeam"),
	SHUTDOWN_IN("&eDéconnection dans &r%s &esecondes", "&eDisconnect in &r%s &eseconds"),
	HEART("Coeur", "Heart"),
	SPEED("Vitesse de déplacement", "Movement Speed"),
	SHEEP_BONUS("De chance d'obtenir 1 moutons supplémentaire", "Of chance to get a bonus sheep"),
	SHEEP_HEALTH("De vie supplémentaire a vos moutons", "Of extra sheep Health"),

	// EFFECTS
	BLIND("&0&lOeil noir", "&0&lBlack eye"),
	SWIFT("&b&lPoids plume", "&b&lFeather weight"),
	LEVITATION("&e&lWingardium Leviosa", "&e&lWingardium Leviosa"),
	PERCANT("&3&lOeil perçant", "&3&lPiercing gaze"),
	ROBUSTE("&7&lRobustesse", "&7&lRobustness"),
	IVY("&a&lVent insidieu", "&a&lIvy wind"),
	FREEZE("&b&lMister freeze", "&b&lMister freeze"),
	BOUNCY("&7&lRebond", "&7&lBouncy"),
	RABBIT("&a&lRoger le lapin", "&a&lRoger the rabbit"),
	POUTCH("&6&lPoutch", "&6&lPoutch"),
	FIRE("&6&lCha brule", "&6&lFiery essence"),
	DOCTOR("&7&lQuoi de neuf Doc ?", "&7&lWhat's up Doc ?"),
	CONFUSION("&e&lFolie", "&e&lMadness"),
	FURY("&c&lFurie sanguinaire", "&c&lBloody fury"),
	GOLDEN("&e&lCoeur d'or", "&e&lGolden heart"),
	MORE_SHEEP("&f&l+1 Mouton", "&f&l+1 Sheep"),
	
	// KITS
	//	SPECTATOR KIT
	SPECTATOR("&6Spectateur", "&6Spectator"),
	DESC_SPECTATOR("&7Vous êtes mort", "&7You are dead"),
	//	STARTER KIT
	STARTER("", ""),
	DESC_STARTER("", ""),
	//	HARDNESS KIT
	HARDNESS_SHEEP("&6Mouton blindé", "&6Hardness Sheep"),
	DESC_HARDNESS_SHEEP("&7Vos moutons seront plus résistant", "&7Your sheep gonna be resistant"),
	//	ARCHER KIT
	ARCHER("&6Archer", "&6Archer"),
	DESC_ARCHER("&7Vos flèches dépasserons le mur du son", "&7Your arrow gotta go fast"),
	//	SWORDSMAN KIT
	SWORDSMAN("&6Epeiste", "&6Swordsman"),
	DESC_SWORDSMAN("&7Taper taper taper !", "&7Go in front !"),
	//	MOBILITY KIT
	MOBILITY("&6Mobilité", "&6Mobility"),
	DESC_MOBILITY("&7Vous vous déplacer plus vite", "&7Your are gotta go fast"),
	// SUPPORT KIT
	SUPPORT("&6Support", "&6Support"),
	DESC_SUPPORT("&7Aidez vos alliés, devenez un médecin de guerre", "&7Help your mate, become a veteran doctor"),
	// BONUS SHEEP KIT
	BONUS_SHEEP("&6Plus de mouton", "&6More Sheep"),
	DESC_BONUS_SHEEP("&7Vous avez une chance d'obtenir des moutons supplémentaire", "&7You have a chance to get more sheeps"),
	// MORE HEALTH KIT
	MORE_HEALTH("&6Plus de vie", "&6More Health"),
	DESC_MORE_HEALTH("&7Commencez la partie avec un boost de vitalité", "&7Start the game with a health boost"),
	// PYROMAN KIT
	PYROMAN("&6Pyroman", "&6Pyroman"),
	DESC_PYROMAN("&7Ne pas trop jouer avec le feu", "&7Don't play with the fire")
	;
	
	private String fr;
	private String en;
	
	EnumTranslate(String fr, String en) {
		this.fr = fr;
		this.en = en;
	}
	
	public String getTranslation(String language) {
		switch(language) {
			case "fr_fr":
				return TextUtils.colorize(fr);
			default:
				return TextUtils.colorize(en);
		}
	}
	
}

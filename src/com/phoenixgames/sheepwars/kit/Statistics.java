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

import com.phoenixgames.sheepwars.enums.EnumTranslate;
import com.phoenixgames.sheepwars.utils.TextUtils;

public class Statistics {

	private int heart;
	private float speed;
	private float sheepHealth;
	private float sheepCount;
	
	public Statistics(int heart, float speed, float sheepHealth, float sheepCount) {
		this.heart = heart;
		this.speed = speed;
		this.sheepHealth = sheepHealth;
		this.sheepCount = sheepCount;
	}

	public int getHeart() {
		return heart;
	}

	public float getSpeed() {
		return speed;
	}

	public float getSheepHealth() {
		return sheepHealth;
	}

	public float getSheepCount() {
		return sheepCount;
	}
	
	public List<String> toDescription(String lang) {
		return TextUtils.parseLine(TextUtils.colorize(
				"&b" + heart + "&c\u2764 &7" + EnumTranslate.HEART.getTranslation(lang) + "\n" +
				"&b+" + Math.round((speed -1.0F) * 100) + ".0% &7" + EnumTranslate.SPEED.getTranslation(lang) + "\n" +
				"&b+" + Math.round((sheepHealth -1.0F) * 100) + ".0% &7" + EnumTranslate.SHEEP_HEALTH.getTranslation(lang) + "\n" +
				"&b+" + sheepCount * 100 + "% &7" + EnumTranslate.SHEEP_BONUS.getTranslation(lang)));
	}
	
}

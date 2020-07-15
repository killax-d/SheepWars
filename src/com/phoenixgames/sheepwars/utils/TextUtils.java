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
package com.phoenixgames.sheepwars.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

import net.minecraft.server.v1_14_R1.ChatComponentText;

public class TextUtils {

	public static String colorize(String text) {
		return text.replaceAll("&", "\u00A7");
	}
	
	public static ChatComponentText toChatComponent(String text) {
		return new ChatComponentText(colorize(text));
	}
	
	public static String tickToTime(int tick) {
		tick /= 20;
        
        int minutes = tick / 60; 
        tick %= 60;

        int seconds = tick;
		return minutes + ":" + (seconds < 10 ? "0" + seconds : seconds);
	}
	
	public static List<String> parseLine(String text) {
		List<String> strings = new ArrayList<String>();
		for(String str : text.split("\n")) {
			if(str != null)
				strings.add(str);
		}
		return strings;
	}
	
	public static List<String> convert(String in, int lenght) {
		in = ChatColor.stripColor(in);
		String colour = "";
		if (in.startsWith("&")) {
			colour = in.substring(0, 2);
			in = in.substring(2, in.length());
		}
		StringBuilder sb = new StringBuilder(in);
		int i = 0;
		while (i + lenght < sb.length() && (i = sb.lastIndexOf(" ", i + lenght)) != -1) {
			sb.replace(i, i + 1, "#" + colour);
		}
		String[] returned = sb.toString().split("#");
		returned[0] = colour + returned[0];
		i = 0;
		for (String part : returned) {
			returned[i] = colorize(part);
			i++;
		}
		return Arrays.asList(returned);
	}
}

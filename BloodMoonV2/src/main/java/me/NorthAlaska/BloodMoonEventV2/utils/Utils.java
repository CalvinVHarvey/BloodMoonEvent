package me.NorthAlaska.BloodMoonEventV2.utils;

import org.bukkit.ChatColor;

public class Utils 
{
	public Utils()
	{
		
	}
	public static String chat(String s)
	{
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static int nextInt(int min, int max)
	{
		return (int)(Math.random() * ((max-min)+1)) + min;
	}
}

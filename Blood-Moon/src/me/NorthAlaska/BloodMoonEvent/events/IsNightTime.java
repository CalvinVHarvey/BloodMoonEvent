package me.NorthAlaska.BloodMoonEvent.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.NorthAlaska.BloodMoonEvent.Main;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;

public class IsNightTime extends Event
{
	private static final HandlerList handlers = new HandlerList();
	
	private long time;
	private Main plugin;
	private static Main plugin1;
	
	public IsNightTime(Main plugin,boolean bloodmoon)
	{
		//this.bloodmoon = bloodmoon;
		this.plugin = plugin;
	}
	public IsNightTime(Main plugin)
	{
		this.plugin = plugin;
		this.plugin1 = plugin;
	}
	
	public long getTime()
	{
		return time;
	}
	
	public boolean isNight()
	{
		ArrayList<String> worldnames = findOverWorld();
		time = plugin.getServer().getWorld(worldnames.get(0)).getTime();
		return time > 13000 && time < 23000;
	}
	
	public static ArrayList<String> findOverWorld()
	{
		ArrayList<String> overworlds = new ArrayList<String>();
		for (int i = 0; i < plugin1.getServer().getWorlds().size(); i++)
		{
			World cur = plugin1.getServer().getWorlds().get(i);
			if (cur.getEnvironment() == Environment.NORMAL)
			{
				overworlds.add(cur.getName());
			}
		}
		if (overworlds.size() > 0)
		{
			return overworlds;
		}
		else
		{
			return null;
		}
	}
	
	
	public HandlerList getHandlers() 
	{
		return handlers;
	}
	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	
}

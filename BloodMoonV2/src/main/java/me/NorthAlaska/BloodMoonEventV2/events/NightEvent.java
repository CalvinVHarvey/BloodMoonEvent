package me.NorthAlaska.BloodMoonEventV2.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;

public class NightEvent implements Listener
{
	private static int counter;
	private Main plugin;
	private static boolean bloodmoon; 
	
	public NightEvent(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void isNightTime(IsNightTime e)
	{
		
		
		if (e.isNight() && counter == 0)
		{
			counter = (23000 - ((int)e.getTime()))/20;
			bloodmoon = (int)(Math.random() * 100) <= plugin.getConfig().getInt("percentChanceForBloodMoon");
			if (bloodmoon)
			{
				Bukkit.broadcastMessage(Utils.chat("&4&lThe Blood Moon Rises"));
				BloodMoonEvent event = new BloodMoonEvent(plugin, BloodMoonEvent.Type.START);
				Bukkit.getPluginManager().callEvent(event);
			}
		}
		else if (e.isNight() == false)
		{
			counter = 0;
			if (bloodmoon)
			{
				Bukkit.broadcastMessage(Utils.chat("&6&lThe Blood Moon Fades"));
				BloodMoonEvent endBloodMoon = new BloodMoonEvent(plugin, BloodMoonEvent.Type.END);
				Bukkit.getPluginManager().callEvent(endBloodMoon);
			}
			bloodmoon = false;
		}
		else if(counter > 0)
		{
			counter--;
		}
		
	}
	
	public static void setBloodMoon(long time, boolean bloodmoonValue)
	{
		bloodmoon = bloodmoonValue;
		if (bloodmoon)
		{
			counter = (23000 - ((int)time))/20;
			Bukkit.broadcastMessage(Utils.chat("&4&lThe Blood Moon Rises"));
		}
		else
		{
			Bukkit.broadcastMessage(Utils.chat("&6&lThe Blood Moon Fades"));
			if (!(time > 13000) && !(time < 23000))	
				counter = 0;
		}
	}
	
	public static void setBloodmoon(boolean bloodmoonValue)
	{
		bloodmoon = bloodmoonValue;
	}
	
	public static boolean getBloodMoon()
	{
		return bloodmoon;
	}
}

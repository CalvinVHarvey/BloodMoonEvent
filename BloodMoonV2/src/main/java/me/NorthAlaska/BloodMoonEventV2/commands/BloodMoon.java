package me.NorthAlaska.BloodMoonEventV2.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.events.BloodMoonEvent;
import me.NorthAlaska.BloodMoonEventV2.events.IsNightTime;
import me.NorthAlaska.BloodMoonEventV2.events.NightEvent;
import me.NorthAlaska.BloodMoonEventV2.utils.ItemBuilder;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;

public class BloodMoon implements CommandExecutor
{
	private Main plugin;
	
	public BloodMoon(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("bloodmoon").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player p = (Player)sender;
		ArrayList<String> overworlds = IsNightTime.findOverWorld();
		if(args.length == 0 || args[0].toLowerCase().equals("help"))
		{
			p.sendMessage("/Bloodmoon start - Starts a Bloodmoon\n/Bloodmoon end - Ends a Bloodmoon\n/Bloodmoon reload - Reloads the looter.yml for drops");
			return  true;
		}
		else if (args[0].toLowerCase().equals("start"))
		{
			if (p.hasPermission("Bloodmoon.start") && isNight(plugin.getServer().getWorld(overworlds.get(0)).getTime()))
			{
				NightEvent.setBloodMoon(plugin.getServer().getWorld(overworlds.get(0)).getTime(), true);
				BloodMoonEvent blood = new BloodMoonEvent(plugin, BloodMoonEvent.Type.START);
				Bukkit.getPluginManager().callEvent(blood);
			}
			else if(!(p.hasPermission("Bloodmoon.start")))
				p.sendMessage(Utils.chat("&cInsufficient Permissions"));
			else if (!(isNight(plugin.getServer().getWorld(overworlds.get(0)).getTime())))
				p.sendMessage(Utils.chat("&cYou can only do that at Night Time!"));
			return true;
		}
		else if (args[0].toLowerCase().equals("end"))
		{
			if (p.hasPermission("Bloodmoon.end") && NightEvent.getBloodMoon())
			{
				NightEvent.setBloodMoon(plugin.getServer().getWorld(overworlds.get(0)).getTime(), false);
				BloodMoonEvent endBloodMoon = new BloodMoonEvent(plugin, BloodMoonEvent.Type.END);
				Bukkit.getPluginManager().callEvent(endBloodMoon);
			}
			else if(NightEvent.getBloodMoon() == false)
			{
				p.sendMessage(Utils.chat("&cThere is no BloodMoon"));
			}
			else if(!(p.hasPermission("Bloodmoon.end")))
			{
				p.sendMessage(Utils.chat("&cInsufficient Permissions"));
			}
			return true;
		}
		else if(args[0].equalsIgnoreCase("reload"))
		{
			if (p.hasPermission("Bloodmoon.reload"))
			{
				plugin.getItems().reload();
				plugin.setLoot(new ItemBuilder(plugin));
				plugin.reloadConfig();
				p.sendMessage(Utils.chat("&aSuccessfully Reloaded Configs"));
			}
			else
			{
				p.sendMessage(Utils.chat("&cInsufficient Permissions"));
			}
			return true;
		}
		return false;
	}
	
	
	public boolean isNight(long time)
	{
		return time > 13000 && time < 23000;
	}
	
}

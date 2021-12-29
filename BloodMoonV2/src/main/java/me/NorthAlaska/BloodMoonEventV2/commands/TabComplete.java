package me.NorthAlaska.BloodMoonEventV2.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import me.NorthAlaska.BloodMoonEventV2.Main;

public class TabComplete implements TabCompleter
{
	private Main plugin;
	public TabComplete(Main plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
	{
		List<String> list = new ArrayList<String>();
		if (command.getName().equals("bloodmoon"))
		{
			list.add("start");
			list.add("end");
			list.add("reload");
		}
		return list;
	}

}

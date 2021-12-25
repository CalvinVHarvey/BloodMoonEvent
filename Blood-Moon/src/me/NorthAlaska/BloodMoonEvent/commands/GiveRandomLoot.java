package me.NorthAlaska.BloodMoonEvent.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.NorthAlaska.BloodMoonEvent.Main;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;

public class GiveRandomLoot implements CommandExecutor
{
	private Main plugin;
	
	public GiveRandomLoot(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("giverandomloot").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player p = (Player) sender;
		if (sender instanceof Player)
		{
			if (p.hasPermission("Bloodmoon.give"))
			{
				ArrayList<ItemStack> item = plugin.getLoot().calcDrop();
				PlayerInventory inv = p.getInventory();
				for (int i = 0; i < item.size(); i++)
				{
					inv.addItem(item.get(i));
					p.sendMessage("Item: " +item.get(i).getType());
				}
			}
			else
			{
				p.sendMessage(Utils.chat("&cInsufficient Permissions"));
			}
		}
		else
		{
			Bukkit.getConsoleSender().sendMessage("You Must be Ingame to Execute This Command!");
		}
		
		return false;
	}

}

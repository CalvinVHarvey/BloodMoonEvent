package me.NorthAlaska.BloodMoonEventV2.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.events.IsNightTime;
import me.NorthAlaska.BloodMoonEventV2.mobs.*;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;

public class SummonBloodZombie implements CommandExecutor
{
	
	private Main plugin;
	public SummonBloodZombie(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("summonBloodZombie").setExecutor(this);
	}
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player p;
		World w;
		if (sender instanceof Player)	
		{
			p = (Player) sender;
			w = p.getWorld();
		}
		else 
		{
			Bukkit.getConsoleSender().sendMessage(Utils.chat("&cYou Must be a player to send this command!"));
			return false;
		}
		if (p.hasPermission("Bloodmoon.summon"))
		{
			Location here1 = p.getLocation();
			Zombie zombie = (Zombie) w.spawnEntity(here1, EntityType.ZOMBIE);
			new ZombieConverter(zombie, plugin);
			Skeleton skeleton = (Skeleton) w.spawnEntity(here1, EntityType.SKELETON);
			new SkeletonConverter(skeleton, plugin);
			p.sendMessage(Utils.chat("&4Summoning Blood Zombie"));
		}
		else
		{
			p.sendMessage(Utils.chat("&cInsufficient Permissions."));
		}
		return false;
	}
	
}

package me.NorthAlaska.BloodMoonEvent.commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import me.NorthAlaska.BloodMoonEvent.Main;
import me.NorthAlaska.BloodMoonEvent.events.IsNightTime;
import me.NorthAlaska.BloodMoonEvent.mobs.BloodSkeleton;
import me.NorthAlaska.BloodMoonEvent.mobs.BloodSpider;
import me.NorthAlaska.BloodMoonEvent.mobs.EvilGolem;
import me.NorthAlaska.BloodMoonEvent.mobs.StrongZombie;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EntityZombie;

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
		// TODO Auto-generated method stub
		Player p = (Player) sender;
		if (p.hasPermission("Bloodmoon.summon"))
		{
			Location here1 = p.getLocation();
			StrongZombie blood = new StrongZombie(here1, plugin);
			BloodSkeleton bloodSkel = new BloodSkeleton(p.getLocation(), plugin, false);
			BloodSpider spider = new BloodSpider(here1, plugin);
			EvilGolem job = new EvilGolem(here1, plugin);
			WorldServer world = ((CraftWorld) p.getWorld()).getHandle();
			world.addEntity(job);
			world.addEntity(blood);
			world.addEntity(bloodSkel);
			world.addEntity(spider);
			spider.addRider((Spider) spider.getBukkitEntity());
			
			p.sendMessage(Utils.chat("&4Summoning Blood Zombie"));
		}
		else
		{
			p.sendMessage(Utils.chat("&cInsufficient Permissions."));
		}
		return false;
	}
	
}

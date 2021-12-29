package me.NorthAlaska.BloodMoonEventV2.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.events.*;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;

public class DeathEventListener implements Listener
{
	private Main plugin;
	
	public DeathEventListener(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void deathListener(EntityDeathEvent e)
	{
		LivingEntity dead = e.getEntity();
		Location loc = dead.getLocation();
		if (dead.getKiller() instanceof Player)
		{
			if (dead.getCustomName() != null && dead.getCustomName().contains(Utils.chat("&4&lBlood")))
			{
				ArrayList<ItemStack> drops = plugin.getLoot().calcDrop(dead.getType());
				for (int i = 0; i < drops.size(); i++)
				{
					plugin.getServer().getWorld(IsNightTime.findOverWorld().get(0)).dropItem(loc, drops.get(i));
				}
			}
		}
		
		if (dead instanceof IronGolem)
		{
			if (dead.getCustomName() != null && dead.getCustomName().equals(Utils.chat("&4&lBlood Golem")))
			{
				SpawnEvent.getGolems().remove(SpawnEvent.find((IronGolem)dead));
			}
		}
	}
	
	
}

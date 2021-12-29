package me.NorthAlaska.BloodMoonEventV2.utils;

import java.util.List;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.events.NightEvent;

public class DelayedEntityRemove extends BukkitRunnable 
{
	private Main plugin;
	private List<LivingEntity> entity;
	int counter;
	public DelayedEntityRemove(Main plugin, List<LivingEntity> entity)
	{
		this.plugin = plugin;
		this.entity = entity;
		counter = entity.size() -1;
	}
	
	
	@Override
	public void run() 
	{
		if (counter >= 0)	
		{
			if (entity.get(counter).getCustomName() != null && entity.get(counter).getCustomName().contains(Utils.chat("&4&lBlood")))
			{
				entity.get(counter).setHealth(0);
			}
		}
		else if(NightEvent.getBloodMoon())
		{
			this.cancel();
		}
		else
		{
			this.cancel();
		}
		counter--;
	}

}

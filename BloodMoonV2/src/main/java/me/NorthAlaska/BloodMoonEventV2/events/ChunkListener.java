package me.NorthAlaska.BloodMoonEventV2.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;

public class ChunkListener implements Listener
{
	private Main plugin;
	
	public ChunkListener(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void chunkListener(ChunkLoadEvent e)
	{
		int counter = 0;
		List<LivingEntity> list = plugin.getServer().getWorld(IsNightTime.findOverWorld().get(0)).getLivingEntities();
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) instanceof IronGolem)
			{
				if (list.get(i).getCustomName() != null && list.get(i).getCustomName().equals(Utils.chat("&4&lBlood Golem")))
				{
					SpawnEvent.getGolems().add((IronGolem) list.get(i));
					counter++;
				}
			}
		}
		if (counter == 0)
		{
			plugin.getBlood().getConfig().set("count", 0);
		}
	}
}

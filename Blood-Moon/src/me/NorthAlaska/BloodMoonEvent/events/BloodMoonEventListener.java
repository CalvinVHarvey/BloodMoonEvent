package me.NorthAlaska.BloodMoonEvent.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;

import me.NorthAlaska.BloodMoonEvent.Main;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;
import me.NorthAlaska.BloodMoonEvent.utils.*;

public class BloodMoonEventListener implements Listener
{
	private Main plugin;
	
	public BloodMoonEventListener(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void BloodMoonEvents(BloodMoonEvent e)
	{
		List<Player> players = plugin.getServer().getWorld(IsNightTime.findOverWorld().get(0)).getPlayers();
		if (e.getType() == BloodMoonEvent.Type.START)
		{
			//Bukkit.broadcastMessage("Started");
			for (int i = 0; i < players.size(); i++)
			{
				Player cur = players.get(i);
				cur.playSound(cur.getLocation(), Sound.ENTITY_ENDER_DRAGON_DEATH, 0.5F, 0.5F);
			}
		}
		
		World w = plugin.getServer().getWorld(IsNightTime.findOverWorld().get(0));
		if (e.getType() == BloodMoonEvent.Type.END)
		{
			List<LivingEntity> entity = w.getLivingEntities();
			BukkitTask task = new DelayedEntityRemove(plugin, entity).runTaskTimer(this.plugin, 10, 10);
//			for (int i = 0; i < w.getLivingEntities().size(); i++)
//			{
//				Entity cur = null;
//				cur = entity.get(i);
//				if (cur != null)
//				{
//					if (cur.getCustomName() != null && cur.getCustomName().contains(Utils.chat("&4&lBlood")))
//					{
//						LivingEntity live = (LivingEntity) entity.get(i);
//						entity.get(i).setHealth(0);
//					}
//				}
//			}
		}
	}
}

package me.NorthAlaska.BloodMoonEventV2.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Spider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;

public class EntityTargetListener implements Listener
{
	private Main plugin;

	public EntityTargetListener(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void ironGolemPreventAttack(EntityTargetEvent e)
	{
		if (e.getEntity() instanceof Monster)
		{
			if (!(e.getTarget() instanceof IronGolem))
				return;
			if (e.getTarget().getCustomName() != null && e.getTarget().getCustomName().equals(Utils.chat("&4&lBlood Golem")))
				e.setCancelled(true);
		}
		if (!(e.getEntity() instanceof IronGolem))
		{
			return;
		}
		else if (e.getEntity().getCustomName() != null && !(e.getEntity().getCustomName().equals(Utils.chat("&4&lBlood Golem"))))
		{
			return;
		}
		else if (e.getTarget() instanceof Monster)
		{
			e.setCancelled(true);
		}
	}
	
}

package me.NorthAlaska.BloodMoonEvent.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntitySpawnEvent;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntitySlime;

import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import me.NorthAlaska.BloodMoonEvent.Main;
import me.NorthAlaska.BloodMoonEvent.mobs.*;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;

public class SpawnEvent implements Listener
{
	private Main plugin;
	
	public SpawnEvent(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void bloodMoonSpawning(CreatureSpawnEvent e)
	{
		int random = Utils.nextInt(1, 100);
		WorldServer w = ((CraftWorld)e.getLocation().getWorld()).getHandle();
		Location loc = e.getLocation();
		if (e.getEntity() instanceof Monster && e.getSpawnReason() != SpawnReason.DEFAULT)
		{
			if (e.getLocation().getBlock().isLiquid())
			{
				return;
			}
			if (e.getLocation().getWorld().getEnvironment() == Environment.NETHER)
			{
				return;
			}
			if (!(NightEvent.getBloodMoon()))
			{
				return;
			}
				
			if (loc.getY() < 60 && !(plugin.getConfig().getBoolean("spawnUnderGround")))
			{
				return;
			}
			
			if (e.getEntity() instanceof Spider)
			{
				BloodSpider spider = new BloodSpider(loc, plugin);
				w.addEntity(spider);
				if (spider.isJockey())
					spider.addRider((Spider)spider.getBukkitEntity());
				e.setCancelled(true);
			}
			else if(e.getEntity() instanceof Zombie)
			{
				StrongZombie zombie = new StrongZombie(loc, plugin);
				w.addEntity(zombie);
				e.setCancelled(true);
			}
			else if(e.getEntity() instanceof Skeleton)
			{
				BloodSkeleton skeleton = new BloodSkeleton(loc, plugin, false);
				w.addEntity(skeleton);
				e.setCancelled(true);
			}
			else if (random <= 10)
			{
				EvilGolem golem = new EvilGolem(loc, plugin);
				w.addEntity(golem);
				e.setCancelled(true);
			}
			else if (random <= 20)
			{
				EntitySlime slime = new EntitySlime(EntityTypes.aD, ((CraftWorld) loc.getWorld()).getHandle());
				slime.setPosition(loc.getX(), loc.getY(), loc.getZ());
				Slime s = (Slime) slime.getBukkitEntity();
				s.setSize(Utils.nextInt(1, 5));
				w.addEntity(slime);
				e.setCancelled(true);
			}
			else
			{
				return;
			}
			//e.setCancelled(true);
		}
	}
	
	
	public void createMob(String s)
	{
		
	}
}

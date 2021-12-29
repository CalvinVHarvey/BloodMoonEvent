package me.NorthAlaska.BloodMoonEventV2.events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.persistence.PersistentDataType;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.mobs.*;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;

public class SpawnEvent implements Listener
{
	private Main plugin;
	
	private static Main plugin1;
	
	private static ArrayList<IronGolem> golems;
	
	public SpawnEvent(Main plugin)
	{
		this.plugin1 = plugin;
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void bloodMoonSpawning(CreatureSpawnEvent e)
	{
		int random = Utils.nextInt(1, 100);
		World w = e.getLocation().getWorld();
		Location loc = e.getLocation();
		if (e.getEntity() instanceof Monster && e.getSpawnReason() != SpawnReason.CUSTOM)
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
				Spider spider = (Spider) w.spawnEntity(loc, EntityType.SPIDER);
				new SpiderConverter(spider, plugin);
				e.setCancelled(true);
			}
			else if(e.getEntity() instanceof Zombie)
			{
				Zombie zombie = (Zombie) w.spawnEntity(loc, EntityType.ZOMBIE);
				new ZombieConverter(zombie, plugin);
				e.setCancelled(true);
			}
			else if(e.getEntity() instanceof Skeleton)
			{
				Entity ent = w.spawnEntity(loc, EntityType.SKELETON);
				new SkeletonConverter((Skeleton)ent, plugin);
				e.setCancelled(true);
			}
			else if (random <= 10)
			{
				IronGolem iron = (IronGolem) w.spawnEntity(loc, EntityType.IRON_GOLEM);
				new IronGolemConverter(iron, plugin);
				if (golems == null)
				{
					golems = new ArrayList<IronGolem>();
				}
				golems.add(iron);
				e.setCancelled(true);
			}
			else if (random <= 20)
			{
				Slime slime = (Slime) w.spawnEntity(loc, EntityType.SLIME);
				slime.setSize(Utils.nextInt(1, 5));
				e.setCancelled(true);
			}
			else if (e.getEntity() instanceof Creeper)
			{
				Creeper creep = (Creeper)w.spawnEntity(loc, EntityType.CREEPER);
				new CreeperJockey(creep, plugin);
				e.setCancelled(true);
			}
			else
			{
				return;
			}
		}
	}
	
	public static ArrayList<IronGolem> getGolems()
	{
		return golems;
	}
	
	public static void setGolems(ArrayList<IronGolem> golems1)
	{
		golems = golems1;
	}
	
	public static int find(IronGolem i)
	{
		NamespacedKey n = new NamespacedKey(plugin1, "bloodID");
		for (int ind = 0; ind < golems.size(); ind++)
		{
			if (golems.get(ind).getPersistentDataContainer().get(n, PersistentDataType.INTEGER) == i.getPersistentDataContainer().get(n, PersistentDataType.INTEGER))
			{
				return ind;
			}
		}
		return -1;
	}
	
}

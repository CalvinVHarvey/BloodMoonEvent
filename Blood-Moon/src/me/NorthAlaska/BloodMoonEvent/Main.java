package me.NorthAlaska.BloodMoonEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.NorthAlaska.BloodMoonEvent.commands.BloodMoon;
import me.NorthAlaska.BloodMoonEvent.commands.GiveRandomLoot;
import me.NorthAlaska.BloodMoonEvent.commands.SummonBloodZombie;
import me.NorthAlaska.BloodMoonEvent.events.*;
import me.NorthAlaska.BloodMoonEvent.utils.Configuration;
import me.NorthAlaska.BloodMoonEvent.utils.ItemBuilder;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;

public class Main extends JavaPlugin
{
	
	private Configuration items;
	
	private ItemBuilder loot;
	
	@Override
	public void onEnable()
	{
		Entity s; 
		saveDefaultConfig();
        BukkitScheduler scheduler = getServer().getScheduler();
        new NightEvent(this);
        new SummonBloodZombie(this);
        new EntityTargetListener(this);
        new BloodMoon(this);
        new SpawnEvent(this);
        new BloodMoonEventListener(this);
        new GiveRandomLoot(this);
        new PlayerUseEventListener(this);
        new DeathEventListener(this);
        Bukkit.getConsoleSender().sendMessage("Creating or Loading looter.yml");
        items = new Configuration(this, this.getDataFolder(), "looter", true, true);
        items.reload();
        Bukkit.getConsoleSender().sendMessage("Loading Items from looter.yml");
        loot = new ItemBuilder(this);
        
        
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() 
        {
            @Override
            public void run() 
            {
            	IsNightTime time = new IsNightTime(getPlugin());
            	Bukkit.getPluginManager().callEvent(time);
            }
        }, 0L, 20L);
	}
	
	public void onDisable()
	{
	
	}
	
	public Configuration getItems()
	{
		return items;
	}
	
	public ItemBuilder getLoot()
	{
		return loot;
	}
	
//	public Configuration getMobs()
//	{
//		return mobs;
//	}
	
	public Main getPlugin()
	{
		return this;
	}
}

package me.NorthAlaska.BloodMoonEventV2;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import me.NorthAlaska.BloodMoonEventV2.events.*;
import me.NorthAlaska.BloodMoonEventV2.mobs.IronGolemConverter;
import me.NorthAlaska.BloodMoonEventV2.utils.Configuration;
import me.NorthAlaska.BloodMoonEventV2.utils.Data;
import me.NorthAlaska.BloodMoonEventV2.utils.ItemBuilder;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;
import me.NorthAlaska.BloodMoonEventV2.commands.*;


public class Main extends JavaPlugin
{
	
	private Configuration items;
	
	private Configuration blood;
	
	private Data data;
	
	private ItemBuilder loot;
	
	@Override
	public void onEnable()
	{
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
        new ChunkListener(this);
        this.getCommand("bloodmoon").setTabCompleter(new TabComplete(this));
        Bukkit.getConsoleSender().sendMessage("Creating or Loading looter.yml");
        items = new Configuration(this, this.getDataFolder(), "looter", true, true);
        items.reload();
        blood = new Configuration(this, this.getDataFolder(), "bloodID", true, true);
        blood.reload();
        
        if (blood.getConfig().getInt("count") > 0)
        	IronGolemConverter.setCounter(blood.getConfig().getInt("count"));
        
        IsNightTime.setPlugin(this);
        
		List<LivingEntity> list = this.getServer().getWorld(IsNightTime.findOverWorld().get(0)).getLivingEntities();
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) instanceof IronGolem)
			{
				if (list.get(i).getCustomName() != null && list.get(i).getCustomName().equals(Utils.chat("&4&lBlood Golem")))
				{
					if (SpawnEvent.getGolems() != null)
						SpawnEvent.getGolems().add((IronGolem) list.get(i));
					else
					{
						SpawnEvent.setGolems(new ArrayList<IronGolem>());
						SpawnEvent.getGolems().add((IronGolem)list.get(i));
					}
				}
			}
		}
        
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
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() 
    	{
        	@Override
        	public void run() 
        	{
        		if (SpawnEvent.getGolems() != null)	
        			for (int i = 0 ; i < SpawnEvent.getGolems().size(); i++)
        			{
        				List<Entity> e = SpawnEvent.getGolems().get(i).getNearbyEntities(50, 50, 50);
        				for (Entity e1 : e)
        				{
        					if (e1 instanceof Player)
        					{
        						//SpawnEvent.getGolems().get(i).attack(e1);
        						SpawnEvent.getGolems().get(i).setTarget((LivingEntity) e1);
        					}
        				}
        			}
        	}
    	}, 0L, 100L);
	}
	
	@Override
	public void onDisable()
	{
		blood.save();
	}
	
	public Configuration getItems()
	{
		return items;
	}
	
	public Configuration getBlood()
	{
		return blood;
	}
	
	public void setBlood(Configuration blood)
	{
		this.blood = blood;
	}
	public void setItems(Configuration items)
	{
		this.items = items;
	}
	
	public ItemBuilder getLoot()
	{
		return loot;
	}
	
	public void setLoot(ItemBuilder loot)
	{
		this.loot = loot;
	}
		
	public Main getPlugin()
	{
		return this;
	}
}

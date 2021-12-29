package me.NorthAlaska.BloodMoonEventV2.mobs;

import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Phantom;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;

public class CreeperJockey 
{
	private Creeper creeper;
	private Main plugin;
	private World w;
	
	public CreeperJockey(Creeper creeper, Main plugin)
	{
		w = creeper.getLocation().getWorld();
		this.creeper = creeper;
		this.plugin = plugin;
		creeper.setCustomName(Utils.chat("&4&lBlood Creeper"));
		creeper.setCustomNameVisible(true);
		int health = Utils.nextInt(plugin.getConfig().getInt("creeperMinHealth"), plugin.getConfig().getInt("creeperMaxHealth"));
		creeper.setMaxHealth(health);
		creeper.setHealth(health);
		int random = Utils.nextInt(1, 100);
		if (random <= plugin.getConfig().getDouble("creeperChargedPercent"))
			creeper.setPowered(true);
		random = Utils.nextInt(1, 100);
		creeper.setExplosionRadius(plugin.getConfig().getInt("creeperExplosionRadius"));
		if (random <= plugin.getConfig().getDouble("creeperJockeyChance"))
		{
			Phantom phantom = (Phantom)w.spawnEntity(creeper.getLocation(), EntityType.PHANTOM);
			phantom.setMaxHealth(phantom.getHealth() * 2);
			phantom.setHealth(phantom.getHealth() * 2);
			
			phantom.addPassenger(creeper);
		}
	}
	
}

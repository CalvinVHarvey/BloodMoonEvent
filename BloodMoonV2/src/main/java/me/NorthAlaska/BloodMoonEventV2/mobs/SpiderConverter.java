package me.NorthAlaska.BloodMoonEventV2.mobs;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;

public class SpiderConverter 
{
	private Spider spider;
	private Main plugin;
	private boolean isJockey;
	
	public SpiderConverter(Spider spider, Main plugin)
	{
		this.spider = spider;
		this.plugin = plugin;
		int health = Utils.nextInt(plugin.getConfig().getInt("spiderMinHealth"), plugin.getConfig().getInt("spiderMaxHealth"));
		spider.setCustomName(Utils.chat("&4&lBlood Spider"));
		spider.setCustomNameVisible(true);
		spider.setMaxHealth(health);
		spider.setHealth(health);
		determineJockey();
		generateLoot(spider);
	}
	
	public boolean isJockey()
	{
		return isJockey;
	}
	
	public void generateLoot(Spider s)
	{
		generatePotions(s);
		addRider(s);
	}
	
	public void addRider(Spider s)
	{
		World w = s.getLocation().getWorld();
		if(isJockey)
		{
			Skeleton skeleton = (Skeleton)w.spawnEntity(s.getLocation(), EntityType.SKELETON);
			new SkeletonConverter(skeleton, plugin);
			s.addPassenger(skeleton);
		}
	}
	
	public void generatePotions(Spider s)
	{
		int random = Utils.nextInt(1, 100);
		int speedLevel = Utils.nextInt(1, 3);
		int infinite = 5000;
		int strengthLevel = Utils.nextInt(plugin.getConfig().getInt("minStrength"), plugin.getConfig().getInt("maxStrength"));
		int resLevel = Utils.nextInt(plugin.getConfig().getInt("minRes"), plugin.getConfig().getInt("maxRes"));
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, infinite, speedLevel);
		PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, infinite, strengthLevel);
		if (random <= 30)
		{
			s.addPotionEffect(speed);
			s.addPotionEffect(strength);
		}
		else if(random <= 60)
			s.addPotionEffect(strength);
		else if(random <= 100)
		{
			s.addPotionEffect(strength);
		}
	}
	
	private void determineJockey()
	{
		int random = Utils.nextInt(1, 100);
		if (random <= plugin.getConfig().getDouble("jockeyChance"))
			isJockey = true;
		else
			isJockey = false;
	}
}

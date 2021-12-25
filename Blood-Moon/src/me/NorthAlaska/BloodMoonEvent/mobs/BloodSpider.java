package me.NorthAlaska.BloodMoonEvent.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.RegionAccessor;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.NorthAlaska.BloodMoonEvent.Main;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.network.protocol.game.PacketPlayOutMount;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntitySpider;


public class BloodSpider extends EntitySpider
{
	private BloodSkeleton blood;
	int health;
	private Main plugin;
	private Location loc;
	private boolean isJockey;
	public BloodSpider(Location loc, Main plugin) 
	{
		super(EntityTypes.aI, ((CraftWorld) loc.getWorld()).getHandle());
		this.loc = loc;
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		this.plugin = plugin;
		this.setCustomNameVisible(true);
		this.setCustomName(new ChatComponentText(Utils.chat("&4&lBlood Spider")));
		Spider s = (Spider)this.getBukkitEntity();
		determineJockey();
		health = Utils.nextInt(plugin.getConfig().getInt("spiderMinHealth"), plugin.getConfig().getInt("spiderMaxHealth"));
		s.setMaxHealth(health);
		s.setHealth(health);
		generateLoot(s);
		
	}
	
	public boolean isJockey()
	{
		return isJockey;
	}
	
	public void generateLoot(Spider s)
	{
		blood = new BloodSkeleton(loc, plugin, false);
		generatePotions(s);
	}
	
	public void addRider(Spider s)
	{
		if(isJockey)
		{
			WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
			world.addEntity(blood);
			s.addPassenger(blood.getBukkitEntity());
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
		PotionEffect resistance = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, infinite, resLevel);
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

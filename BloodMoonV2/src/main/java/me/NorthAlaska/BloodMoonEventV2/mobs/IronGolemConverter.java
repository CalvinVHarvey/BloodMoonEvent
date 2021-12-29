package me.NorthAlaska.BloodMoonEventV2.mobs;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.IronGolem;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.NorthAlaska.BloodMoonEventV2.Main;
import me.NorthAlaska.BloodMoonEventV2.utils.Utils;


public class IronGolemConverter
{
	private IronGolem iron;
	private Main plugin;
	private static int counter;
	public IronGolemConverter(IronGolem iron, Main plugin) 
	{
		this.plugin = plugin;
		this.iron = iron;
		int health = Utils.nextInt(plugin.getConfig().getInt("ironGolemMinHealth"), plugin.getConfig().getInt("ironGolemMaxHealth"));
		iron.setMaxHealth(health);
		iron.setHealth(health);
		iron.setCustomName(Utils.chat("&4&lBlood Golem"));
		iron.setCustomNameVisible(true);
		iron.getPersistentDataContainer().set(new NamespacedKey(plugin, "bloodID"), PersistentDataType.INTEGER, counter++);
		plugin.getBlood().getConfig().set("count", counter);
		createEffects(iron);
	}
	
	
	public void createEffects(IronGolem i)
	{
		int random = Utils.nextInt(1, 100);
		int strengthLevel = Utils.nextInt(plugin.getConfig().getInt("minStrength"), plugin.getConfig().getInt("maxStrength"));
		int speedLevel = Utils.nextInt(1, 3);
		PotionEffect strength = null;
		if (strengthLevel > 0)	
			strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 50000, strengthLevel);
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 50000, speedLevel);
		PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, 50000, 1, true);

		if (random <= 50 && strength != null)
			i.addPotionEffect(strength);
		else if(random <= 80 && speed != null && strength != null)
		{
			i.addPotionEffect(speed);
			i.addPotionEffect(strength);
		}
		else if(random <= 100)
		{
			//i.addPotionEffect(invis);
			i.addPotionEffect(strength);
		}
	}
	
	public static int getCounter()
	{
		return counter;
	}
	public static void setCounter(int counter1)
	{
		counter = counter1;
	}
	

	
}

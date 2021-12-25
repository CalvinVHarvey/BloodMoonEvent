package me.NorthAlaska.BloodMoonEvent.mobs;

import net.minecraft.world.entity.animal.EntityIronGolem;
import net.minecraft.world.entity.player.EntityHuman;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.IronGolem;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.NorthAlaska.BloodMoonEvent.Main;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.ai.goal.target.PathfinderGoalNearestAttackableTarget;

public class EvilGolem extends EntityIronGolem
{
	private Main plugin;
	public EvilGolem(Location loc, Main plugin)
	{
		super(EntityTypes.P, ((CraftWorld) loc.getWorld()).getHandle());
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		this.plugin = plugin;
		this.setCustomNameVisible(true);
		this.setCustomName(new ChatComponentText(Utils.chat("&4&lBlood Golem")));
		IronGolem iron = (IronGolem)this.getBukkitEntity();
		createEffects(iron);
		int health = Utils.nextInt(plugin.getConfig().getInt("ironGolemMinHealth"), plugin.getConfig().getInt("ironGolemMaxHealth"));
		iron.setMaxHealth(health);
		iron.setHealth(health);
	}
	
	public void createEffects(IronGolem i)
	{
		int random = Utils.nextInt(1, 100);
		int strengthLevel = Utils.nextInt(plugin.getConfig().getInt("minStrength"), plugin.getConfig().getInt("maxStrength"));
		int speedLevel = Utils.nextInt(1, 3);
		PotionEffect strength = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 50000, strengthLevel);
		PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 50000, speedLevel);
		PotionEffect invis = new PotionEffect(PotionEffectType.INVISIBILITY, 50000, 1, true);

		if (random <= 50)
			i.addPotionEffect(strength);
		else if(random <= 80)
		{
			i.addPotionEffect(speed);
			i.addPotionEffect(strength);
		}
		else if(random <= 100)
		{
			i.addPotionEffect(invis);
			i.addPotionEffect(strength);
		}
	}
	
	@Override
	public void initPathfinder()
	{
		super.initPathfinder();
		this.bQ.a(0, new PathfinderGoalNearestAttackableTarget<EntityHuman>(this, EntityHuman.class, false));
	}

}

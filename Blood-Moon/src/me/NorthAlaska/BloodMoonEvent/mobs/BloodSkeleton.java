package me.NorthAlaska.BloodMoonEvent.mobs;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.NorthAlaska.BloodMoonEvent.Main;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;
import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntitySkeleton;

public class BloodSkeleton extends EntitySkeleton
{
	private int health;
	private Main plugin;
	private Location loc;
	private Skeleton z;
	public BloodSkeleton(Location loc, Main plugin, boolean jockey)
	{
		super(EntityTypes.aB, ((CraftWorld)loc.getWorld()).getHandle());
		this.loc = loc;
		this.plugin = plugin;	
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		this.setCustomNameVisible(true);
		this.setCustomName(new ChatComponentText(Utils.chat("&4&lBlood Skeleton")));
		health = Utils.nextInt(plugin.getConfig().getInt("skeletonMinHealth"), plugin.getConfig().getInt("skeletonMaxHealth"));
		z = (Skeleton)this.getBukkitEntity();
		z.setMaxHealth(health);
		z.setHealth(health);
		z.setCanPickupItems(false);
		generateLoot(z);
	}
	public Location getLoc()
	{
		return loc;
	}
	
	
	public Skeleton getSkeleton()
	{
		return z;
	}
	
	private void generateLoot(Skeleton z)
	{
		createEffects(z);
		createArmor(z);
		createWeapon(z);
		z.getEquipment().setBootsDropChance((float) plugin.getConfig().getDouble("bootsDropChance"));
		z.getEquipment().setHelmetDropChance((float) plugin.getConfig().getDouble("helmetDropChance"));
		z.getEquipment().setChestplateDropChance((float) plugin.getConfig().getDouble("chestplateDropChance"));
		z.getEquipment().setLeggingsDropChance((float) plugin.getConfig().getDouble("leggingDropChance"));
		z.getEquipment().setItemInMainHandDropChance((float) plugin.getConfig().getDouble("weaponDropChance"));
	}
	
	public void createWeapon(Skeleton z)
	{
		int random = (int)(Math.random() * 3);
		Material[] weapons = {Material.BOW, Material.CROSSBOW, Material.BOW};
		z.getEquipment().setItemInMainHand(weapon(weapons[random]));
	}
	
	private void createArmor(Skeleton z) 
	{
		int chestplate = (int)(Math.random() * 5);
		if (chestplate == 0)
		{
			z.getEquipment().setChestplate(chestplate(Material.NETHERITE_CHESTPLATE));
		}
		else if (chestplate == 2)
		{
			z.getEquipment().setChestplate(chestplate(Material.DIAMOND_CHESTPLATE));
		}
		else if (chestplate == 4)
		{
			z.getEquipment().setChestplate(chestplate(Material.IRON_CHESTPLATE));
		}
		
		int leggings = (int)(Math.random() * 10);
		if (leggings == 0)
			z.getEquipment().setLeggings(chestplate(Material.NETHERITE_LEGGINGS));
		else if (leggings == 4)
			z.getEquipment().setLeggings(chestplate(Material.DIAMOND_LEGGINGS));
		else if (leggings == 8)
			z.getEquipment().setLeggings(chestplate(Material.IRON_LEGGINGS));
		
		int boots = (int)(Math.random() * 10);
		
		if (boots == 0)
			z.getEquipment().setBoots(chestplate(Material.NETHERITE_BOOTS));
		if (boots == 4)
			z.getEquipment().setBoots(chestplate(Material.DIAMOND_BOOTS));
		if (boots == 8)
			z.getEquipment().setBoots(chestplate(Material.IRON_BOOTS));
		
			
	}
	
	private ItemStack weapon(Material m)
	{
		int enchants = (int)(Math.random() * 100);
		int quick = (int)(Math.random() * 5) + 1;
		int pierce = Utils.nextInt(1, 15);
		int multi = Utils.nextInt(0, 1);
		ItemStack weapon = new ItemStack(m, 1);
		if (m == Material.CROSSBOW)
		{
			weapon.addUnsafeEnchantment(Enchantment.PIERCING, pierce);
			if (multi > 0)	
				weapon.addUnsafeEnchantment(Enchantment.MULTISHOT, multi);
			weapon.addUnsafeEnchantment(Enchantment.QUICK_CHARGE, quick);
		}
		else
		{
			int power = Utils.nextInt(plugin.getConfig().getInt("minPower"), plugin.getConfig().getInt("maxPower") + 5);
			if (power > 0 && power < plugin.getConfig().getInt("maxPower") - 5)
				weapon.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, power);
			int flame = Utils.nextInt(0, 1);
			if (flame > 0)
				weapon.addEnchantment(Enchantment.ARROW_FIRE, flame);
			int punch = Utils.nextInt(0, 3);
			if (punch > 0)
				weapon.addUnsafeEnchantment(Enchantment.ARROW_KNOCKBACK, punch);
		}
		return weapon;
	}
	
	private ItemStack chestplate(Material m)
	{
		int enchant = (int)(Math.random() * 10);
		ItemStack helmet1 = new ItemStack(m, 1);
		if (enchant != 0 && enchant < 5)
			helmet1.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, enchant);
		if (enchant == 2)
			helmet1.addEnchantment(Enchantment.THORNS, 3);
		return helmet1;
	}

	private void createEffects(Skeleton z)
	{
		ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
		int strength = Utils.nextInt(plugin.getConfig().getInt("minStrength"), plugin.getConfig().getInt("maxStrength"));
		int random = (int)(Math.random() * 5);
		if (random == 0)
		{
			PotionEffect e = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5000, strength);
			PotionEffect e1 = new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 5000, 1);
			z.addPotionEffect(e);
			z.addPotionEffect(e1);
		}
		else if(random == 1)
		{
			PotionEffect e = new PotionEffect(PotionEffectType.SPEED, 5000, 3);
			z.addPotionEffect(e);
		}
		else if(random == 2)
		{
			PotionEffect e1 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5000, strength);
			z.addPotionEffect(e1);
		}
		else if(random == 3)
		{
			PotionEffect e = new PotionEffect(PotionEffectType.SPEED, 5000, 3);
			PotionEffect e1 = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5000, strength);
			z.addPotionEffect(e);
			z.addPotionEffect(e1);
		}
		else if(random == 4)
		{
			PotionEffect e = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 5000, strength);
			z.addPotionEffect(e);
		}
	}
}

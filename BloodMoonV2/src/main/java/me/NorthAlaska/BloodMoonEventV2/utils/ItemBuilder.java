package me.NorthAlaska.BloodMoonEventV2.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.NamespacedKey;


import me.NorthAlaska.BloodMoonEventV2.Main;

public class ItemBuilder 
{
	private HashMap<ItemStack, Double> hash;
	private Main plugin;
	private FileConfiguration config;
	private ArrayList<Double> percents;
	private ArrayList<ItemStack> materials;
	private ArrayList<String> mobs;
	
	public ItemBuilder(Main plugin)
	{
		this.plugin = plugin;
		materials = new ArrayList<ItemStack>();
		percents = new ArrayList<Double>();
		mobs = new ArrayList<String>();
		config = plugin.getItems().getConfig();
		//Bukkit.getConsoleSender().sendMessage("Constructing Class");
		
		for (String key : plugin.getItems().getConfig().getKeys(false))
		{
			ItemStack temp = null;
			Material type = null;
			int amount = -1;
			String name = null;
			List<String> lore = new ArrayList<String>();
			Double percent = null;
			ItemMeta im = null;
			String mobType = null;
			for (String elem : plugin.getItems().getConfig().getConfigurationSection(key).getKeys(false))
			{
				
				if (elem.equals("type"))
				{
					//Bukkit.getConsoleSender().sendMessage("Material Located");
					type = Material.matchMaterial(config.getConfigurationSection(key).getString("type"));
				}
				else if (elem.equals("display_name"))
				{
					//Bukkit.getConsoleSender().sendMessage("Displaye Name Located");
					name = config.getConfigurationSection(key).getString("display_name");
				}
				else if (elem.equals("amount"))
				{
					//Bukkit.getConsoleSender().sendMessage("Amount Located");
					amount = config.getConfigurationSection(key).getInt("amount");
				}
				else if (elem.equals("lore"))
				{
					//Bukkit.getConsoleSender().sendMessage("Lore located");
					lore.add(config.getConfigurationSection(key).getString("lore"));
				}
				else if (elem.equals("chanceForDrop"))
				{
					//Bukkit.getConsoleSender().sendMessage("Drop Chance Located");
					percent = config.getConfigurationSection(key).getDouble("chanceForDrop");
				}
				else if (elem.equals("mobType"))
				{
					mobType = config.getConfigurationSection(key).getString("mobType");
				}
				
				if (elem.equals("enchantments"))
				{
					//Bukkit.getConsoleSender().sendMessage("Enchants Located");
					
					for (String ench : config.getConfigurationSection(key).getConfigurationSection(elem).getKeys(false))
					{
						String enchName = config.getConfigurationSection(key).getConfigurationSection(elem).getConfigurationSection(ench).getString("enchType");
						int level = config.getConfigurationSection(key).getConfigurationSection(elem).getConfigurationSection(ench).getInt("level");
						Enchantment enchant = Enchantment.getByKey(NamespacedKey.minecraft(enchName.toLowerCase()));
						temp.addUnsafeEnchantment(enchant, level);
					}
					
				}
				
				if (temp == null && type != null && amount != -1)
				{
					temp = new ItemStack(type, amount);
					//Bukkit.getConsoleSender().sendMessage("Material: " +type);
				}
			}
			if (temp != null)
				im = temp.getItemMeta();
			if (name != null)	
				im.setDisplayName(Utils.chat(name));
			if (lore != null && lore.size() > 0)
				im.setLore(lore);
			if (temp != null)	
				temp.setItemMeta(im);
			if (temp != null)
			{
				materials.add(temp);
				percents.add(percent);
				mobs.add(mobType);
			}
			
		}
	}
	
	public HashMap<ItemStack, Double> getHash()
	{
		return hash;
	}
	
	public ArrayList<ItemStack> calcDrop(EntityType ent)
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		EntityType entType = null;
		double rand = (Math.random() * 100);
		for (int i = 0; i < materials.size(); i++)
		{
			if (!(mobs.get(i).equalsIgnoreCase("all")))
				entType = EntityType.fromName(mobs.get(i));
			rand = (Math.random() * 100);
			if (rand <= percents.get(i) && (ent == entType || mobs.get(i).equalsIgnoreCase("all")))
				drops.add(materials.get(i));
		}
		return drops;
	}
	
	public ArrayList<ItemStack> calcDrop()
	{
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
		double rand = (Math.random() * 100);
		for (int i = 0; i < materials.size(); i++)
		{
			rand = (Math.random() * 100);
			if (rand <= percents.get(i))
				drops.add(materials.get(i));
		}
		return drops;
	}
	
}

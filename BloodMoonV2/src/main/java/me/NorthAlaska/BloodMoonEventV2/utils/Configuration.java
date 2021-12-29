package me.NorthAlaska.BloodMoonEventV2.utils;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.NorthAlaska.BloodMoonEventV2.Main;

public class Configuration 
{
	private final boolean createIfNotExist;
	private boolean resource;
	private Main plugin;
	
	private FileConfiguration config;
	private File file;
	private File path;
	private String name;
	
	public Configuration (Main plugin, File path, String name, boolean createIfNotExist, boolean resource)
	{
		this.createIfNotExist = createIfNotExist;
		this.plugin = plugin;
		this.path = path;
		this.name = name +".yml";
		this.resource = resource;
		create();
	}
	
	public Configuration(Main plugin, String path, String name, boolean createIfNotExist, boolean resource)
	{
		this(plugin, new File(path), name, createIfNotExist, resource);
	}
	
	public FileConfiguration getConfig()
	{
		return config;
	}
	
	public File reloadFile() 
	{
		  file = new File(path, name);
		  return file;
	}
	public void create() 
	{
		if (file == null) 
		{
		    reloadFile();
		}
		if (!createIfNotExist || file.exists()) 
		{
		    return;
		}
		file.getParentFile().mkdirs();
		if (resource) 
		{
			plugin.saveResource(name, false);
		} else 
		{ try 
		{
			file.createNewFile();
		} catch (Exception exc) 
		{
			exc.printStackTrace();
		}
		}if (config == null) 
		{
			reloadConfig();
		}
	  }
	  public FileConfiguration reloadConfig() 
	  {
		    config = YamlConfiguration.loadConfiguration(file);
		    return config;
	  }
	  
	  public void reload()
	  {
		  reloadFile();
		  reloadConfig();
	  }
	  
	  public void save()
	  {
		  try
		  {
			  config.save(file);
		  }
		  catch (Exception ex)
		  {
			  ex.printStackTrace();
		  }
	  }
	

}

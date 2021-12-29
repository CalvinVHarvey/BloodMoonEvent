package me.NorthAlaska.BloodMoonEventV2.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import me.NorthAlaska.BloodMoonEventV2.Main;

public class BloodMoonEvent extends Event
{
	private static final HandlerList handlers = new HandlerList();
	private Main plugin;
	private Type type;
	public BloodMoonEvent(Main plugin, Type type)
	{
		this.plugin = plugin;
		this.type = type;
		
	}
	public Type getType()
	{
		return type;
	}
	public void setType(Type type)
	{
		this.type = type;
	}
	public enum Type
	{
		START,
		END
	}
	
	@Override
	public HandlerList getHandlers() 
	{
		return handlers;
	}
	public static HandlerList getHandlerList()
	{
		return handlers;
	}

}

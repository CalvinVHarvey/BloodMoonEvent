package me.NorthAlaska.BloodMoonEvent.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import me.NorthAlaska.BloodMoonEvent.Main;
import me.NorthAlaska.BloodMoonEvent.utils.Utils;

public class PlayerUseEventListener implements Listener
{
	private Main plugin;
	
	public PlayerUseEventListener(Main plugin)
	{
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void useEvent(PlayerInteractEvent e)
	{
		long time = plugin.getServer().getWorld(IsNightTime.findOverWorld().get(0)).getTime();
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		Action reason = e.getAction();
		if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR)
		{
			if (reason == Action.RIGHT_CLICK_AIR && p.getItemInHand().getType() == Material.BLAZE_ROD && p.getItemInHand().getItemMeta() != null && p.getItemInHand().getItemMeta().getDisplayName().equals(Utils.chat("&4&lBlood Staff")))
			{
				if (time > 13000 && time < 23000 && !(NightEvent.getBloodMoon()))	
				{
					NightEvent.setBloodMoon(time, true);
					if (p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR)	
						inv.getItemInHand().setAmount(inv.getItemInHand().getAmount() - 1);
				}
				else if (NightEvent.getBloodMoon())
					p.sendMessage(Utils.chat("&cTheres Already A Blood Moon!"));
				else
					p.sendMessage(Utils.chat("&cYou Can Only Use This At Night!"));
			}
		}
	}

}

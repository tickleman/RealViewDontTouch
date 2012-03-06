package fr.crafter.tickleman.realviewdonttouch;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

//############################################################################## RealPlayerListener
public class RealPlayerListener implements Listener
{

	RealViewDontTouchPlugin plugin;

	//---------------------------------------------------------------------------- RealPlayerListener
	public RealPlayerListener(RealViewDontTouchPlugin plugin)
	{
		this.plugin = plugin;
	}

	//------------------------------------------------------------------------------ onPlayerInteract
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block block = event.getClickedBlock();
			Player player = event.getPlayer();
			if ((player instanceof Player) && (block != null) && block.getType().equals(Material.CHEST)) {
				if (plugin.getWaitForClick().contains(player)) {
					if (plugin.getBlockList().switchState(block.getLocation())) {
						player.sendMessage(plugin.tr("This chest can now only be viewed"));
					} else {
						player.sendMessage(plugin.tr("This chest is not view-only anymore"));
					}
					plugin.getWaitForClick().remove(player);
				}
			}
		}
	}

	//--------------------------------------------------------------------------------- onPlayerLogin
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		plugin.getWaitForClick().remove(event.getPlayer());
	}

	//---------------------------------------------------------------------------------- onPlayerQuit
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		plugin.getWaitForClick().remove(event.getPlayer());
	}

}

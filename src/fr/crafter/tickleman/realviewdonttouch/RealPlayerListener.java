package fr.crafter.tickleman.realviewdonttouch;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

//############################################################################## RealPlayerListener
public class RealPlayerListener extends PlayerListener
{

	RealViewDontTouchPlugin plugin;

	//---------------------------------------------------------------------------- RealPlayerListener
	public RealPlayerListener(RealViewDontTouchPlugin plugin)
	{
		this.plugin = plugin;
	}

	//------------------------------------------------------------------------------ onPlayerInteract
	@Override
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		if ((player instanceof Player) && block.getType().equals(Material.CHEST)) {
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

	//--------------------------------------------------------------------------------- onPlayerLogin
	@Override
	public void onPlayerLogin(PlayerLoginEvent event)
	{
		plugin.getWaitForClick().remove(event.getPlayer());
	}

	//---------------------------------------------------------------------------------- onPlayerQuit
	@Override
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		plugin.getWaitForClick().remove(event.getPlayer());
	}

}

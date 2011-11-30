package fr.crafter.tickleman.realviewdonttouch;

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

package fr.crafter.tickleman.realviewdonttouch;

import org.bukkit.Location;
import org.getspout.spoutapi.event.inventory.InventoryClickEvent;
import org.getspout.spoutapi.event.inventory.InventoryListener;

//########################################################################### RealInventoryListener
public class RealInventoryListener extends InventoryListener
{

	RealViewDontTouchPlugin plugin;

	//------------------------------------------------------------------------- RealInventoryListener
	public RealInventoryListener(RealViewDontTouchPlugin plugin)
	{
		this.plugin = plugin;
	}

	//------------------------------------------------------------------------------ onInventoryClick
	@Override
	public void onInventoryClick(InventoryClickEvent event)
	{
		plugin.getLog().debug("click");
		Location location = event.getLocation();
		if ((location != null) && plugin.getBlockList().isViewOnly(location)) {
			event.getPlayer().sendMessage(plugin.tr("This chest is view-only"));
			event.setCancelled(true);
		}
	}

}

package fr.crafter.tickleman.realviewdonttouch;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

//########################################################################### RealInventoryListener
public class RealInventoryListener implements Listener
{

	RealViewDontTouchPlugin plugin;

	//------------------------------------------------------------------------- RealInventoryListener
	public RealInventoryListener(RealViewDontTouchPlugin plugin)
	{
		this.plugin = plugin;
	}

	//------------------------------------------------------------------------------ onInventoryClick
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInventoryClick(InventoryClickEvent event)
	{
		plugin.getLog().debug("click");
		// todo : know where is the chest
		/*
		Location location = event.getInventory();
		if ((location != null) && plugin.getBlockList().isViewOnly(location)) {
			event.getPlayer().sendMessage(plugin.tr("This chest is view-only"));
			event.setCancelled(true);
		}
		*/
	}

}

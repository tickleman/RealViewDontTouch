package fr.crafter.tickleman.realviewdonttouch;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
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
		if (
			event.getWhoClicked() instanceof Player
			&& event.getInventory().getHolder() instanceof BlockState
		) {
			Location location = ((BlockState) event.getInventory().getHolder()).getLocation();
			if ((location != null) && plugin.getBlockList().isViewOnly(location)) {
				((Player) event.getWhoClicked()).sendMessage(plugin.tr("This chest is view-only"));
				event.setCancelled(true);
			}
		}
	}

}

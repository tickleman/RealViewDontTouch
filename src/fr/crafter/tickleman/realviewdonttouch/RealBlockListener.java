package fr.crafter.tickleman.realviewdonttouch;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;

import fr.crafter.tickleman.realplugin.RealLocation;

//############################################################################### RealBlockListener
public class RealBlockListener implements Listener
{

	RealViewDontTouchPlugin plugin;

	//----------------------------------------------------------------------------- RealBlockListener
	public RealBlockListener(RealViewDontTouchPlugin plugin)
	{
		this.plugin = plugin;
	}

	//---------------------------------------------------------------------------------- onBlockBreak
	private void autoRemoveBlock(BlockEvent event)
	{
		Block block = event.getBlock();
		if (block.getType().equals(Material.CHEST)) {
			Location location = block.getLocation();
			if (plugin.getBlockList().isViewOnly(location)) {
				plugin.getBlockList().delete(location);
			}
		}
	}

	//---------------------------------------------------------------------------------- onBlockBreak
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent event)
	{
		autoRemoveBlock(event);
	}

	//----------------------------------------------------------------------------------- onBlockBurn
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBurn(BlockBurnEvent event)
	{
		autoRemoveBlock(event);
	}

	//--------------------------------------------------------------------------------- onBlockDamage
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockDamage(BlockDamageEvent event)
	{
		Block block = event.getBlock();
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

	//----------------------------------------------------------------------------------- onBlockFade
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockFade(BlockFadeEvent event)
	{
		autoRemoveBlock(event);
	}

	//--------------------------------------------------------------------------------- onBlockIgnite
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		autoRemoveBlock(event);
	}

	//---------------------------------------------------------------------------------- onBlockPlace
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent event)
	{
		Block block = event.getBlock();
		if (block.getType().equals(Material.CHEST)) {
			Location location = block.getLocation();
			Location neighbor = RealLocation.neighbor(location);
			if ((neighbor != null) && plugin.getBlockList().isViewOnly(neighbor)) {
				// block placed near a view-only block : view-only this block too
				plugin.getBlockList().put(location);
			} else if (plugin.getBlockList().isViewOnly(location)) {
				// block placed into a ghost view-only block place : remove view-only on this block
				plugin.getBlockList().delete(location);
			}
		}
	}

	//--------------------------------------------------------------------------------- onBlockSpread
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockSpread(BlockSpreadEvent event)
	{
		autoRemoveBlock(event);
	}

}

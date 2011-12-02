package fr.crafter.tickleman.realviewdonttouch;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockSpreadEvent;

//############################################################################### RealBlockListener
public class RealBlockListener extends BlockListener
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
	@Override
	public void onBlockBreak(BlockBreakEvent event)
	{
		autoRemoveBlock(event);
	}

	//----------------------------------------------------------------------------------- onBlockBurn
	@Override
	public void onBlockBurn(BlockBurnEvent event)
	{
		autoRemoveBlock(event);
	}

	//--------------------------------------------------------------------------------- onBlockDamage
	@Override
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
	@Override
	public void onBlockFade(BlockFadeEvent event)
	{
		autoRemoveBlock(event);
	}

	//--------------------------------------------------------------------------------- onBlockIgnite
	@Override
	public void onBlockIgnite(BlockIgniteEvent event)
	{
		autoRemoveBlock(event);
	}

	//--------------------------------------------------------------------------------- onBlockSpread
	@Override
	public void onBlockSpread(BlockSpreadEvent event)
	{
		autoRemoveBlock(event);
	}

}

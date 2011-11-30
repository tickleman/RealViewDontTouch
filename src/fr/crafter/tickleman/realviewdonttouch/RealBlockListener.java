package fr.crafter.tickleman.realviewdonttouch;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;

//############################################################################### RealBlockListener
public class RealBlockListener extends BlockListener
{

	RealViewDontTouchPlugin plugin;

	//----------------------------------------------------------------------------- RealBlockListener
	public RealBlockListener(RealViewDontTouchPlugin plugin)
	{
		this.plugin = plugin;
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
			}
		}
	}

}

package fr.crafter.tickleman.realviewdonttouch;

import java.util.HashSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginManager;

import fr.crafter.tickleman.realplugin.RealPlugin;

//######################################################################### RealViewDontTouchPlugin
public class RealViewDontTouchPlugin extends RealPlugin
{

	ViewOnlyBlockList blockList;

	private HashSet<Player> waitForClick = new HashSet<Player>();

	//---------------------------------------------------------------------------------- getBlockList
	public ViewOnlyBlockList getBlockList()
	{
		return blockList;
	}

	//------------------------------------------------------------------------------- getWaitForClick
	public HashSet<Player> getWaitForClick()
	{
		return waitForClick;
	}

	//------------------------------------------------------------------------------- onPlayerCommand
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
		if (sender instanceof Player) {
			String command = cmd.getName().toLowerCase();
			Player player = (Player)sender;
			if (command.equals("viewonly") && hasPermission(player, "realviewdonttouch.viewonly")) {
				player.sendMessage(tr("Click the chest you want to switch view-only mode"));
				waitForClick.add(player);
				return true;
			}
		}
		return false;
	}

	//-------------------------------------------------------------------------------------- onEnable
	@Override
	public void onDisable()
	{
		super.onDisable();
	}

	//-------------------------------------------------------------------------------------- onEnable
	@Override
	public void onEnable()
	{
		super.onEnable();
		blockList = new ViewOnlyBlockList(this);
		blockList.load();
		// register events
		RealBlockListener     blockListener     = new RealBlockListener(this);
		RealInventoryListener inventoryListener = new RealInventoryListener(this);
		RealPlayerListener    playerListener    = new RealPlayerListener(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.BLOCK_BREAK,     blockListener,     Event.Priority.Lowest,  this);
		pm.registerEvent(Event.Type.BLOCK_BURN,      blockListener,     Event.Priority.Lowest,  this);
		pm.registerEvent(Event.Type.BLOCK_DAMAGE,    blockListener,     Event.Priority.Lowest,  this);
		pm.registerEvent(Event.Type.BLOCK_FADE,      blockListener,     Event.Priority.Lowest,  this);
		pm.registerEvent(Event.Type.BLOCK_IGNITE,    blockListener,     Event.Priority.Lowest,  this);
		pm.registerEvent(Event.Type.BLOCK_PLACE,     blockListener,     Event.Priority.Lowest,  this);
		pm.registerEvent(Event.Type.BLOCK_SPREAD,    blockListener,     Event.Priority.Lowest,  this);
		pm.registerEvent(Event.Type.CUSTOM_EVENT,    inventoryListener, Event.Priority.Lowest, this);
		pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener,    Priority.Lowest,        this);
		pm.registerEvent(Event.Type.PLAYER_LOGIN,    playerListener,    Priority.Lowest,        this);
		pm.registerEvent(Event.Type.PLAYER_QUIT,     playerListener,    Priority.Lowest,        this);
	}

}

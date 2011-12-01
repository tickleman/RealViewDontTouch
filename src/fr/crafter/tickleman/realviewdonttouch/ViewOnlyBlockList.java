package fr.crafter.tickleman.realviewdonttouch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;

import fr.crafter.tickleman.realplugin.RealLocation;
import fr.crafter.tickleman.realplugin.RealPlugin;

//############################################################################### ViewOnlyBlockList
public class ViewOnlyBlockList
{

	private final String fileName;

	private RealPlugin plugin;

	/** View-only blocks list : "x;y;z;world" */
	private Set<String> list = new HashSet<String>();

	//----------------------------------------------------------------------------- ViewOnlyBlockList
	public ViewOnlyBlockList(final RealPlugin plugin)
	{
		this(plugin, "viewonlyblocks");
	}

	//----------------------------------------------------------------------------- ViewOnlyBlockList
	public ViewOnlyBlockList(RealPlugin plugin, String fileName)
	{
		this.plugin = plugin;
		this.fileName = plugin.getDataFolder().getPath() + "/" + fileName + ".txt";
	}

	//---------------------------------------------------------------------------------------- delete
	public void delete(Location location)
	{
		list.remove(RealLocation.getId(location));
	}

	//------------------------------------------------------------------------------------ isViewOnly
	public boolean isViewOnly(Location location)
	{
		return list.contains(RealLocation.getId(location));
	}

	//------------------------------------------------------------------------------------------ load
	public ViewOnlyBlockList load()
	{
		plugin.getLog().debug("ShopList.load()");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String buffer;
			while ((buffer = reader.readLine()) != null) {
				String[] buf = buffer.split(";");
				if ((buffer.length() > 0) && (buffer.charAt(0) != '#') && (buf.length > 3)) {
					list.add(buf[0] + ";" + buf[1] + ";" + buf[2] + ";" + buf[3]);
				}
			}
		} catch (Exception e) {
			plugin.getLog().warning("File read error " + fileName + " (will create one)");
			save();
		}
		try { reader.close(); } catch (Exception e) {}
		return this;
	}

	//------------------------------------------------------------------------------------------- put
	public void put(Location location)
	{
		list.add(RealLocation.getId(location));
	}

	//------------------------------------------------------------------------------------------ save
	public void save()
	{
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fileName));
			writer.write("#x;y;z;world\n");
			for (String loc : list) {
				writer.write(loc + "\n");
			}
			writer.flush();
		} catch (Exception e) {
			plugin.getLog().severe("File save error " + fileName);
		}
		try { writer.close(); } catch (Exception e) {}
	}

	//----------------------------------------------------------------------------------- switchState
	public boolean switchState(Location location)
	{
		String loc = RealLocation.getId(location);
		if (list.contains(loc)) {
			list.remove(loc);
			save();
			return false;
		} else {
			list.add(loc);
			save();
			return true;
		}
	}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.gs.commands;

import eu.derbed.openmu.gs.muObjects.MuInventory;
import eu.derbed.openmu.gs.muObjects.MuItemStats;
import eu.derbed.openmu.gs.muObjects.MuStoreableItem;
import eu.derbed.openmu.gs.serverPackets.SPutItemInInventory;
import eu.derbed.openmu.gs.templates.MuItemHex;

/**
 * Command used for creating new items in characters inventory.<br>
 * Format: \MakeItem GroupIndex Index Dur Lvl Opt ExcOpt Luck Skill<br>
 * The item is placed on the first available position in inventory, if that
 * exists.
 *
 * @author Marcel
 */
public class CmdMake2 extends GsBaseCommand {
	private MuStoreableItem _item;
	private boolean _run;

	@Override
	public boolean RunCommand() {
		if (!_run) {
			return false;
		}
		if (_run) {
			_run = _cli.getActiveChar().getInventory().storeItem(_item);
			_cli.getActiveChar().sendPacket(
					new SPutItemInInventory(_item.getItemHex().toByteArray(),
							(byte) 12));
		}
		// System.out.println(_item);
		return _run;
	}

	@Override
	public void ParseArgs(final String[] args) {
		_run = true;
		final MuItemHex hex = MuItemHex.MakeItem(14, 0, 0, 0, 0, 0, false, false);
		_item = new MuStoreableItem(MuInventory.InventoryWindow, (byte) 0,
				hex, MuItemStats.getItemStats((byte) 14, (byte) 0));
	}

	@Override
	public String getCmdString() {
		return "MakeItem2";
	}

	@Override
	public String getHelpToCommand() {
		return "Create item in inventory with paramameters: GroupIndex Index Dur Lvl Opt ExcOpt Luck Skill";
	}

	@Override
	public String getShortDesc() {
		return "Create item in inventory.";
	}

}
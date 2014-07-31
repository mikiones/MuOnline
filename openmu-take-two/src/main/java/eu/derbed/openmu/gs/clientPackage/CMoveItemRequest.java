package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuInventory;
import eu.derbed.openmu.gs.muObjects.MuStoreableItem;
import eu.derbed.openmu.gs.serverPackets.SMoveItemResult;

/**
 * @author Marcel, Miki
 */
@Header (0x24)
public class CMoveItemRequest extends SimpleClientPackage {
//	example 24 00 01 c0 00 16 00 00 00 0c

	// 23 ad 7f 0c

	private static final Logger log = LoggerFactory.getLogger(CMoveItemRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) throws IOException {
		byte[] decrypt = dataDecrypter.data;
		// Invalid values
		boolean _result = true;
		MuInventory _fromInventory = null;
		MuInventory _toInventory = null;
		byte[] _itemHex = {0, 0, 0, 0, 0};
		// 24 00 01 c0 00 16 00 00 00 0c
		// 24 00 0c e3 00 00 80 00 00 14
		int _windowFrom = decrypt[1];
		int _windowTo = decrypt[8];
		int _slotFrom = decrypt[2];
		int _slotTo = decrypt[9];

		log.debug("Move Item Request : from wid[{}] slot[{}] to wid[{}] slot[{}]", _windowFrom, _slotFrom, _windowTo, _slotTo);

		switch (_windowFrom) {
			case MuInventory.InventoryWindow:
				_fromInventory = client.getActiveChar().getInventory();
				break;
			case MuInventory.TradeWindow:
				// _fromInventory = _client.getActiveChar().getTradeInventory();
				_result = false;
				break;
			case MuInventory.VaultWindow:
				// _fromInventory = _client.getWarehouse();
				_result = false;
				break;
			default:
				_result = false;
				break;
		}
		if (_result) {
			log.debug("source inventory found");
			switch (_windowTo) {
				case MuInventory.InventoryWindow:
					_toInventory = client.getActiveChar().getInventory();
					break;
				case MuInventory.TradeWindow:
					// _toInventory =
					// _client.getActiveChar().getTradeInventory();
					_result = false;
					break;
				case MuInventory.VaultWindow:
					// _toInventory = _client.getWarehouse();
					_result = false;
					break;
				default:
					_result = false;
					break;
			}
			if (_result) {
				final MuStoreableItem _item = _fromInventory.getItem(_slotFrom);
				_result = _item != null;
				if (_result) {
					_result = _fromInventory.removeItem(_item);
					if (_result) {
						_result = _toInventory.storeItem(_item, _slotTo);
						if (!_result) {
							_toInventory.storeItem(_item, _slotFrom);
						}
						_itemHex = _item.getItemHex().toByteArray();
					}
				}
			}
		}
		log.debug("result: " + _result);
		if (!_result) {
			client.getActiveChar().sendPacket(
					new SMoveItemResult(_windowFrom, _slotFrom, _itemHex));
		} else {
			client.getActiveChar().sendPacket(
					new SMoveItemResult(_windowTo, _slotTo, _itemHex));
		}
	}

}

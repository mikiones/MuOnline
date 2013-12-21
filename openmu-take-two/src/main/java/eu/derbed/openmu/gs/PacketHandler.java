package eu.derbed.openmu.gs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.derbed.openmu.gs.clientPackage.CA0Request;
import eu.derbed.openmu.gs.clientPackage.CAddFrendRequest;
import eu.derbed.openmu.gs.clientPackage.CAddLvlPointsRequest;
import eu.derbed.openmu.gs.clientPackage.CAttackOnId;
import eu.derbed.openmu.gs.clientPackage.CBuyItemRequest;
import eu.derbed.openmu.gs.clientPackage.CChangeDirectoryOrStatus;
import eu.derbed.openmu.gs.clientPackage.CCharacterListRequest;
import eu.derbed.openmu.gs.clientPackage.CClientSettingsSaveRequest;
import eu.derbed.openmu.gs.clientPackage.CDeleteChar;
import eu.derbed.openmu.gs.clientPackage.CEnterInGateRequest;
import eu.derbed.openmu.gs.clientPackage.CItemDropFromInwentoryRequest;
import eu.derbed.openmu.gs.clientPackage.CItemPickUpRequest;
import eu.derbed.openmu.gs.clientPackage.CItemUseRequest;
import eu.derbed.openmu.gs.clientPackage.CMoveCharacter;
import eu.derbed.openmu.gs.clientPackage.CMoveItemRequest;
import eu.derbed.openmu.gs.clientPackage.CNewCharacterRequest;
import eu.derbed.openmu.gs.clientPackage.CNpcRunRequest;
import eu.derbed.openmu.gs.clientPackage.CPasVeryfcation;
import eu.derbed.openmu.gs.clientPackage.CPublicMsg;
import eu.derbed.openmu.gs.clientPackage.CSelectCharacterOrExitRequest;
import eu.derbed.openmu.gs.clientPackage.CSelectedCharacterEnterRequest;
import eu.derbed.openmu.utils.PackageUtil;


/**
 * This class ...
 *
 * @version $Revision: 1.18 $ $Date: 2004/10/26 20:43:03 $
 */
public class PacketHandler {
	private Logger log = LoggerFactory.getLogger(getClass());
	// .getLogger(PacketHandler.class.getName());
	private final ClientThread _client;

	public PacketHandler(ClientThread client) {
		_client = client;
	}

	public void handlePacket(byte[] data) throws IOException, Throwable {
		// int pos = 0;
		// System.out.println("lenght="+data.length);
		final int id = data[0] & 0xff;
		int id2 = 0;

		if (data.length > 1) {
			id2 = data[1] & 0xff;
		}
		PackageUtil.logPackage(log, data, "[C->S]");
		switch (id) {
		case 0xa0:
			new CA0Request(data, _client);
			break;
		case 0x00:
			new CPublicMsg(data, _client);
			break;
		case 0x18:
			new CChangeDirectoryOrStatus(data, _client);
			break;
		case 0x1C:
			new CEnterInGateRequest(data, _client);
			break;
		case 0x22:
			new CItemPickUpRequest(data, _client);
			break;
		case 0x23:
			new CItemDropFromInwentoryRequest(data, _client);
			break;
		case 0x24:
			new CMoveItemRequest(data, _client);
			break;
		case 0x26:
			new CItemUseRequest(data, _client);
			break;
		case 0x30:
			new CNpcRunRequest(data, _client);
			break;
		case 0x32:
			new CBuyItemRequest(data, _client);
			break;
		case 0xd7:
			new CMoveCharacter(data, _client);
			break;
		case 0xd9:
			new CAttackOnId(data, _client);
			break;
		case 0xc1:
			new CAddFrendRequest(data, _client);
			break;
		case 0xf1: {
			switch (id2) {
			case 0x01:
				new CPasVeryfcation(data, _client);
				break;
			case 0x02:
				new CSelectCharacterOrExitRequest(data, _client);
				break;
			default:

				break;
			}
		}
			break;
		case 0xf3: {
			switch (id2) {
			case 0x00: {
				new CCharacterListRequest(data, _client);
			}
				break;
			case 0x01: {
				new CNewCharacterRequest(data, _client);
			}
				break;
			case 0x02: {
				new CDeleteChar(data, _client);
			}
				break;
			case 0x03: {
				new CSelectedCharacterEnterRequest(data, _client);
			}
				break;
			case 0x06: {
				new CAddLvlPointsRequest(data, _client);
			}
				break;
			case 0x30: {
				new CClientSettingsSaveRequest(data, _client);
			}
				break;
			default: {
				System.out.println("Unknown Packet or no implament: "
						+ Integer.toHexString(id));

			}
				break;
			}
		}
			break;
		// 24 00 0c e3 00 00 80 00 00 14
		default:
			System.out.println("Unknown Packet or no implament: "
					+ Integer.toHexString(id));

		}

	}

}

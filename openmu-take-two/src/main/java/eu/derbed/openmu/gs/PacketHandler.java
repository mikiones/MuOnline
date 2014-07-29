package eu.derbed.openmu.gs;

import static com.notbed.muonline.util.UPacket.logTransfer;

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
import eu.derbed.openmu.gs.clientPackage.CCharacterManipulator;
import eu.derbed.openmu.gs.clientPackage.CClientSettingsSaveRequest;
import eu.derbed.openmu.gs.clientPackage.CDeleteChar;
import eu.derbed.openmu.gs.clientPackage.CEnterInGateRequest;
import eu.derbed.openmu.gs.clientPackage.CItemDropFromInwentoryRequest;
import eu.derbed.openmu.gs.clientPackage.CItemPickUpRequest;
import eu.derbed.openmu.gs.clientPackage.CItemUseRequest;
import eu.derbed.openmu.gs.clientPackage.CLoginRequest;
import eu.derbed.openmu.gs.clientPackage.CMoveCharacter;
import eu.derbed.openmu.gs.clientPackage.CMoveItemRequest;
import eu.derbed.openmu.gs.clientPackage.CNewCharacterRequest;
import eu.derbed.openmu.gs.clientPackage.CNpcRunRequest;
import eu.derbed.openmu.gs.clientPackage.CPublicMsg;
import eu.derbed.openmu.gs.clientPackage.CSelectedCharacterEnterRequest;
import eu.derbed.openmu.gs.clientPackage.ClientPackage;


/**
 * This class ...
 *
 * @version $Revision: 1.18 $ $Date: 2004/10/26 20:43:03 $
 */
public class PacketHandler {

	private static final Logger log = LoggerFactory.getLogger(PacketHandler.class);

	// .getLogger(PacketHandler.class.getName());
	private final ClientThread _client;

	public PacketHandler(ClientThread client) {
		_client = client;
	}

	public void handlePacket(byte[] data) throws IOException {
		// int pos = 0;
		// System.out.println("lenght="+data.length);
		final int id = data[0] & 0xff;
		logTransfer(log, data, "[C->S]");
		ClientPackage cp = null;
		switch (id) {
		case 0xa0:
			cp = new CA0Request();
			break;
		case 0x00:
			cp = new CPublicMsg();
			break;
		case 0x18:
			cp = new CChangeDirectoryOrStatus();
			break;
		case 0x1C:
			cp = new CEnterInGateRequest();
			break;
		case 0x22:
			cp = new CItemPickUpRequest();
			break;
		case 0x23:
			cp = new CItemDropFromInwentoryRequest();
			break;
		case 0x24:
			cp = new CMoveItemRequest();
			break;
		case 0x26:
			cp = new CItemUseRequest();
			break;
		case 0x30:
			cp = new CNpcRunRequest();
			break;
		case 0x32:
			cp = new CBuyItemRequest();
			break;
		case 0xd7:
			cp = new CMoveCharacter();
			break;
		case 0xd9:
			cp = new CAttackOnId();
			break;
		case 0xc1:
			cp = new CAddFrendRequest();
			break;
		case 0xf1:
			cp = new CLoginRequest().resolve(data, _client);
			break;
		case 0xf3:
			cp = new CCharacterManipulator().resolve(data, _client);
			break;
		// 24 00 0c e3 00 00 80 00 00 14
		default:
			log.debug("Unknown Packet or no implament: "
					+ Integer.toHexString(id));

		}
		if (null == cp) {
			log.debug("Unknown implementation " + Integer.toHexString(id));
		} else {
			log.debug("Received {}", cp.getClass().getSimpleName());
			cp.process(data, _client);
		}
	}

}

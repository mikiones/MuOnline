package eu.derbed.openmu.fs;

import static eu.derbed.openmu.utils.UPacket.logTransfer;

import java.io.IOException;

import eu.derbed.openmu.base.LoggableObject;
import eu.derbed.openmu.fs.clientPackets.FSChatMessage;
import eu.derbed.openmu.fs.clientPackets.HelloFriendServer;
import eu.derbed.openmu.fs.clientPackets.ServerListRequest;


/**
 * This class ...
 *
 * @version $Revision: 1.18 $ $Date: 2004/10/26 20:43:03 $
 */
public class PacketHandler extends LoggableObject {
	private final FriendTheard _friend;

	public PacketHandler(FriendTheard friend) {
		_friend = friend;
	}

	public void handlePacket(byte[] data) throws IOException, Throwable {
		// int pos = 0;
		// System.out.println("lenght="+data.length);
		final int id = data[0] & 0xff;
		int id2 = 0;

		if (data.length > 1) {
			id2 = data[1] & 0xff;
		}
		logTransfer(log, data, "FS[C->S]");
		switch (id) {
		case 0x00: // Hi Cs
			new HelloFriendServer(data, _friend);
			break;
		case 0x02: // Server list plase
			new ServerListRequest(data, _friend);
			break;
		case 0x04: // cros servers chat service
			switch (id2) {
			case 0x00:
				new FSChatMessage(data, _friend);
				break;
			default:
				System.out.println("FS:Unknown Package subtype 0x04"
						+ Integer.toHexString(id2));
			}
			break;

		default:
			System.out.println("FS:Unknown Packet or no implament: "
					+ Integer.toHexString(id));

		}

	}

}

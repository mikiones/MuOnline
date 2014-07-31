package eu.derbed.openmu.gs.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.CommandHandler;
import eu.derbed.openmu.gs.clientPackage.SimpleClientPackage;
import eu.derbed.openmu.gs.serverPackets.SPublicMsg;

@Header (0x00)
class CPublicMsg extends SimpleClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CPublicMsg.class);

	private static final byte[] _learnSkill = {(byte) 0xc1, 0x08, (byte) 0xf3, 0x11,
			(byte) 0xfe, 0x00, 0x11, 0x00};
	private static final byte[] _itemMove = {(byte) 0xC3, 0x0A, 0x24, 0x00, 0x0D,
			(byte) 0xB4, 0x10, 0x20, (byte) 0xC0, 0x00, 0x00};
	private static final byte[] _skilllist = {(byte) 0xc1, 0x08, (byte) 0xf3, 0x11,
			0x01, 0x00, 0x11, 0x01};
	private static final byte[] _itemPack = {(byte) 0xc4, 0x00, 0x06 + 6,
			(byte) 0xf3, 0x10, 0x01, 0x01, (byte) 0xC0, 0x00, 0x16, 0x00, 0x00};

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) throws IOException {

		final String _from = dataDecrypter.readS(1, 10).trim();
		final String _msg = dataDecrypter.readS(11, dataDecrypter.data.length - 12);
		log.debug("Public message from {}: {}.", _from, _msg);
		if (_msg.charAt(0) == '\\') {
			if (!CommandHandler.getInstancec().Execude(client,
					_msg.substring(1))) {
				client.getConnection().sendPacket(
						new SPublicMsg("System",
								"Wrong Command try again !"));
			}

			switch (_msg.charAt(1)) {
				case '1':
					client.getConnection().sendPacket(_learnSkill);
					break;
				case '2':
					client.getConnection().sendPacket(_itemMove);
					break;
				case '3':
					client.getConnection().sendPacket(_skilllist);
					break;
				case '4':
					client.getConnection().sendPacket(_itemPack);
					break;
			}

		} else {
			client.getActiveChar()
					.getCurrentWorldRegion()
					.broadcastPacketWideArea(client.getActiveChar(),
							client.getActiveChar().getX() / 3,
							client.getActiveChar().getY() / 3,
							new SPublicMsg(_from, _msg));
			client.getActiveChar().sendPacket(new SPublicMsg(_from, _msg));
		}
	}

}

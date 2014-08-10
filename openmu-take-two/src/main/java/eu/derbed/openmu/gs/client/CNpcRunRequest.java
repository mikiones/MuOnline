/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.derbed.openmu.gs.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuNpcInstance;

/**
 * @author Miki i Linka
 */
@Header (0x30)
class CNpcRunRequest implements ClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CNpcRunRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#process(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public void process(final byte[] data, final ClientThread client) throws IOException {
		final int _idNpcToRun = data[1];
		log.debug("Request to npc it :" + _idNpcToRun + " ");
		final Object o = client.getWorld().getObject(_idNpcToRun);
		if (o instanceof MuNpcInstance) {
			final MuNpcInstance npc = (MuNpcInstance) o;
			log.debug("object name '" + npc.getName() + "'");
		} else {
			log.debug("error objct requested is not kind of munpcinstance!!");
		}

		client.getConnection().sendPacket(
				new byte[]{(byte) 0xc1, (byte) 0x07, (byte) 0xf3,
						(byte) 0x40, (byte) 0x01, (byte) 0x1b, 0x00});
		client.getConnection().sendPacket(
				new byte[]{(byte) 0xc3, (byte) 0x07, (byte) 0x30,
						(byte) 0xb9, (byte) 0xf7, (byte) 0xf9, (byte) 0x5c // ,(byte)0xb5
				});
		client.getConnection().sendPacket(
				new byte[]{(byte) 0xc2, (byte) 0x00, (byte) 0x9c,
						(byte) 0x31, (byte) 0x00, (byte) 0x19, (byte) 0x00,
						(byte) 0x8d, (byte) 0x00, (byte) 0xff, (byte) 0x80,
						(byte) 0x00, (byte) 0x01, (byte) 0x87, (byte) 0x00,
						(byte) 0xff, (byte) 0x80, (byte) 0x00, (byte) 0x02,
						(byte) 0x8e, (byte) 0x00, (byte) 0xff, (byte) 0x80,
						(byte) 0x00, (byte) 0x03, (byte) 0x93, (byte) 0x00,
						(byte) 0xff, (byte) 0x80, (byte) 0x00, (byte) 0x04,
						(byte) 0x8c, (byte) 0x00, (byte) 0xff, (byte) 0x80,
						(byte) 0x00, (byte) 0x05, (byte) 0x90, (byte) 0x00,
						(byte) 0xff, (byte) 0x80, (byte) 0x00, (byte) 0x06,
						(byte) 0x95, (byte) 0x00, (byte) 0x00, (byte) 0x80,
						(byte) 0x00, (byte) 0x07, (byte) 0x96, (byte) 0x00,
						(byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x08,
						(byte) 0x97, (byte) 0x00, (byte) 0x00, (byte) 0x80,
						(byte) 0x00, (byte) 0x09, (byte) 0x98, (byte) 0x00,
						(byte) 0x00, (byte) 0x80, (byte) 0x00, (byte) 0x0a,
						(byte) 0x00, (byte) 0x04, (byte) 0xff, (byte) 0x00,
						(byte) 0x00, (byte) 0x0b, (byte) 0x01, (byte) 0x04,
						(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x0c,
						(byte) 0x02, (byte) 0x04, (byte) 0xff, (byte) 0x00,
						(byte) 0x00, (byte) 0x0d, (byte) 0x03, (byte) 0x84,
						(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x16,
						(byte) 0x04, (byte) 0x84, (byte) 0xff, (byte) 0x00,
						(byte) 0x00, (byte) 0x17, (byte) 0x05, (byte) 0x84,
						(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x18,
						(byte) 0x06, (byte) 0x84, (byte) 0xff, (byte) 0x00,
						(byte) 0x00, (byte) 0x19, (byte) 0x07, (byte) 0x84,
						(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x1a,
						(byte) 0x08, (byte) 0x84, (byte) 0xff, (byte) 0x00,
						(byte) 0x00, (byte) 0x23, (byte) 0x09, (byte) 0x84,
						(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x2d,
						(byte) 0x0a, (byte) 0x84, (byte) 0xff, (byte) 0x00,
						(byte) 0x00, (byte) 0x30, (byte) 0x0b, (byte) 0x84,
						(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x3a,
						(byte) 0x0c, (byte) 0x84, (byte) 0xff, (byte) 0x00,
						(byte) 0x00, (byte) 0x2f, (byte) 0x0d, (byte) 0x84,
						(byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x3c,
						(byte) 0x0e, (byte) 0x84, (byte) 0xff, (byte) 0x00,
						(byte) 0x00});
		// _client.getConnection().sendPacket(new byte[]{
		// (byte) 0xc1, (byte) 0x06, (byte) 0x14,
		// (byte) 0x01, (byte) 0x03, (byte) 0x32
		// });

	}

}

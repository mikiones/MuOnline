package eu.derbed.openmu.gs.client;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuCharacterBase;
import eu.derbed.openmu.gs.muObjects.MuCharacterList;
import eu.derbed.openmu.gs.muObjects.MuCharacterWear;
import eu.derbed.openmu.gs.serverPackets.SNewCharacterAnsfer;

@Header ({0xf3, 0x01})
class CNewCharacterRequest extends SimpleClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CNewCharacterRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(final DataDecrypter decrypter, final ClientThread client) throws IOException {

		final String _name = decrypter.readS(2, 10).trim();
		final byte[] decrypt = decrypter.data;
		final int _class = decrypt[12] * 2;
		log.debug(String.valueOf(decrypt.length));
		log.debug("Create Character '{}' Requested class {}" , _name, _class);

		final MuCharacterList list = client.getChList();

		boolean success = false;
		final int position = list.getFirstFreeSlot();
		final MuCharacterBase newCB = new MuCharacterBase(_name, 1, _class,
				position, new MuCharacterWear());
		if (!list.isFull()) {
			try {
				success = client.storeNewChar(client.getUser().getId(),
						_name, _class);
				if (success) {
					list.addNew(newCB);
				}
			} catch (final SQLException ex) {
				log.error("Failed to store new character", ex);
			}
		}

		client.getConnection().sendPacket(
				new SNewCharacterAnsfer(newCB, success, position));
	}

}

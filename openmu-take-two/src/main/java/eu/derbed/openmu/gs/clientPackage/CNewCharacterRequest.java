package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuCharacterBase;
import eu.derbed.openmu.gs.muObjects.MuCharacterWear;
import eu.derbed.openmu.gs.serverPackets.SNewCharacterAnsfer;


public class CNewCharacterRequest extends SimpleClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CNewCharacterRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter decrypter, ClientThread client) throws IOException {
		final String _name = decrypter.readS(2, 10).trim();
		byte[] decrypt = decrypter.data;

		final int _class = decrypt[12] * 2;
		log.debug(String.valueOf(decrypt.length));
		log.debug("Create Character '{}' Requested class {}" , _name, _class);

		final int position = client.getChList().getFirstFreeSlot();

		final MuCharacterBase newCB = new MuCharacterBase(_name, 1, _class,
				position, new MuCharacterWear());

		final boolean success = client.storeNewChar(client.getUser().getId(),
				_name, _class);
		if (success) {
			client.getChList().addNew(newCB);
		}
		client.getConnection().sendPacket(
				new SNewCharacterAnsfer(newCB, success, position));
	}

}

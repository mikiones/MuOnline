package eu.derbed.openmu.gs.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.clientPackage.SimpleClientPackage;
import eu.derbed.openmu.gs.muObjects.MuPcInstance;

@Header ({0xf3, 0x03})
class CSelectedCharacterEnterRequest extends SimpleClientPackage {

	protected final static Logger log = LoggerFactory.getLogger(CSelectedCharacterEnterRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(final DataDecrypter dataDecrypter, final ClientThread client) {
		final String selected = SelectedName(dataDecrypter);
		final MuPcInstance cha = client.loadCharFromDisk(selected);

		log.debug("Selected Character: {}", selected);
		client.setActiveChar(cha);
	}

	/**
	 * @param decrypter
	 * @return
	 */
	private static String SelectedName(final DataDecrypter decrypter) {
		return decrypter.readS(2, 10);
	}

}

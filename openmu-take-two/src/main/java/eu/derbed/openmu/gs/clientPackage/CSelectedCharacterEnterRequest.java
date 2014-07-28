package eu.derbed.openmu.gs.clientPackage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuPcInstance;


public class CSelectedCharacterEnterRequest extends SimpleClientPackage {

	protected final static Logger log = LoggerFactory.getLogger(CSelectedCharacterEnterRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) {
		final String selected = SelectedName(dataDecrypter);
		final MuPcInstance cha = client.loadCharFromDisk(selected);

		log.debug("Selected Character: {}", selected);
		client.setActiveChar(cha);
	}

	/**
	 * @param decrypter
	 * @return
	 */
	private static String SelectedName(DataDecrypter decrypter) {
		return decrypter.readS(2, 10);
	}

}

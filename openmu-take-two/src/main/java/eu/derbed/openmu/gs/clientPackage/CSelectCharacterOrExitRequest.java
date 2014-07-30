package eu.derbed.openmu.gs.clientPackage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;

import eu.derbed.openmu.gs.ClientThread;

public class CSelectCharacterOrExitRequest extends SimpleClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CSelectCharacterOrExitRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) {
		switch (dataDecrypter.data[2]) {
			case 0x00:
				log.debug("exit game request");
				break;
			case 0x01:
				log.debug("Change server request");
				break;
			case 0x02:
				log.debug("Change character request");
				break;
			}

	}

}

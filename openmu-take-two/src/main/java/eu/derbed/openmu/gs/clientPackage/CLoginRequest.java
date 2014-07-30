package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.derbed.openmu.gs.ClientThread;

/**
 * @author Alexandru Bledea
 * @since Jul 29, 2014
 */
public class CLoginRequest implements ClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CLoginRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#process(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public void process(byte[] data, ClientThread client) throws IOException {
		int id2 = 0;
		if (data.length > 1) {
			id2 = data[1] & 0xff;
		}
		ClientPackage cp = null;
		switch (id2) {
			case 0x01:
				cp = new CPasVeryfcation();
				break;
			case 0x02:
				cp = new CSelectCharacterOrExitRequest();
				break;
		}
		if (null == cp) {
			log.debug("Failed to identify package.");
			return;
		}
		log.debug("Package identified: {}", cp.getClass().getSimpleName());
		cp.process(data, client);
	}
}

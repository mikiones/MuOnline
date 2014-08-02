package eu.derbed.openmu.gs.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.clientPackage.SimpleClientPackage;

@Header ({0xf1, 0x02})
class CSelectCharacterOrExitRequest extends SimpleClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CSelectCharacterOrExitRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(final DataDecrypter dataDecrypter, final ClientThread client) {
		switch (dataDecrypter.data[2]) {
			case 0x00:
				try {
					client.getConnection().close();
				} catch (final IOException e) {
//					just close for now
				}
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

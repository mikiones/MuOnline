package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.Header;
import com.notbed.muonline.util.UPacket;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.client.ClientPackage;

/**
 * @author Alexandru Bledea
 * @since Jul 29, 2014
 */
@Header (0xf3)
public class CCharacterManipulator implements ClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CCharacterManipulator.class);

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
			case 0x00:
				cp = new CCharacterListRequest();
				break;
			case 0x01:
				cp = new CNewCharacterRequest();
				break;
			case 0x02:
				cp = new CDeleteChar();
				break;
			case 0x03:
				cp = new CSelectedCharacterEnterRequest();
				break;
			case 0x06:
				cp = new CAddLvlPointsRequest();
				break;
			case 0x30:
				cp = new CClientSettingsSaveRequest();
				break;
		}
		if (null == cp) {
			log.debug("Failed to identify package {}", UPacket.fillHex(id2));
			return;
		}
		log.debug("Package identified: {}", cp.getClass().getSimpleName());
		cp.process(data, client);
	}

}

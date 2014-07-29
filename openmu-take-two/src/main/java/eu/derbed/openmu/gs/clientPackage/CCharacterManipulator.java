package eu.derbed.openmu.gs.clientPackage;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.PackageResolver;

/**
 * @author Alexandru Bledea
 * @since Jul 29, 2014
 */
public class CCharacterManipulator implements PackageResolver {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.PackageResolver#resolve(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public ClientPackage resolve(byte[] data, ClientThread client) {
		int id2 = 0;
		if (data.length > 1) {
			id2 = data[1] & 0xff;
		}

		switch (id2) {
			case 0x00:
				return new CCharacterListRequest();
			case 0x01:
				return new CNewCharacterRequest();
			case 0x02:
				return new CDeleteChar();
			case 0x03:
				return new CSelectedCharacterEnterRequest();
			case 0x06:
				return new CAddLvlPointsRequest();
			case 0x30:
				return new CClientSettingsSaveRequest();
		}
		return null;
	}

}

package eu.derbed.openmu.gs.clientPackage;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.PackageResolver;

/**
 * @author Alexandru Bledea
 * @since Jul 29, 2014
 */
public class CLoginRequest implements PackageResolver{

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
			case 0x01:
				return new CPasVeryfcation();
			case 0x02:
				return new CSelectCharacterOrExitRequest();
		}
		return null;
	}

}

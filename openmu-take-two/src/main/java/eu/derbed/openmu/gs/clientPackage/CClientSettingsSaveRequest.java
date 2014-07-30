package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import eu.derbed.openmu.gs.ClientThread;

public class CClientSettingsSaveRequest implements ClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#process(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public void process(byte[] data, ClientThread client) throws IOException {
		client.getClientSettings().fromByteArray(data, 2);
		client.storeClientSettingsInDb();
	}

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#getIdentifier()
	 */
	@Override
	public int getIdentifier() {
//		0xf3 MUST be parent
		return 0x30;
	}

}

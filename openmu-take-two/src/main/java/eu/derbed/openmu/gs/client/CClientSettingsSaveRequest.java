package eu.derbed.openmu.gs.client;

import java.io.IOException;

import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;

@Header ({0xf3, 0x30})
class CClientSettingsSaveRequest implements ClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#process(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public void process(final byte[] data, final ClientThread client) throws IOException {
		client.getClientSettings().fromByteArray(data, 2);
		client.storeClientSettingsInDb();
	}

}

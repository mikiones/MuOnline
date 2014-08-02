package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.client.ClientPackage;

@Header ({0xf3, 0x30})
public class CClientSettingsSaveRequest implements ClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#process(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public void process(byte[] data, ClientThread client) throws IOException {
		client.getClientSettings().fromByteArray(data, 2);
		client.storeClientSettingsInDb();
	}

}

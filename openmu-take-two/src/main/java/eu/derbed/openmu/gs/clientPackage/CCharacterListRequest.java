package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuCharacterList;
import eu.derbed.openmu.gs.serverPackets.SCharacterListAnsfer;


public class CCharacterListRequest implements ClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#process(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public void process(byte[] data, ClientThread client) throws IOException {
		MuCharacterList result = new MuCharacterList();
		try {
			MuCharacterList chList = client.getChList();
			if (chList.needRead()) {
				client.readCharacterList();
			}
			result = chList;
		} finally {
			client.getConnection().sendPacket(
				new SCharacterListAnsfer(result));
		}
	}

}

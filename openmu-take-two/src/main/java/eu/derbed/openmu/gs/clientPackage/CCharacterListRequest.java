package eu.derbed.openmu.gs.clientPackage;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuCharacterList;
import eu.derbed.openmu.gs.serverPackets.SCharacterListAnsfer;


public class CCharacterListRequest extends ClientBasePacket {

	public CCharacterListRequest(byte[] decrypt, ClientThread cl)
			throws Throwable {
		super(decrypt);
		MuCharacterList result = new MuCharacterList();
		try {
			MuCharacterList chList = cl.getChList();
			if (chList.needRead()) {
				cl.readCharacterList();
			}
			result = chList;
		} finally {
		cl.getConnection().sendPacket(
				new SCharacterListAnsfer(result));
		}
	}

	@Override
	public String getType() {
		return null;
	}

}

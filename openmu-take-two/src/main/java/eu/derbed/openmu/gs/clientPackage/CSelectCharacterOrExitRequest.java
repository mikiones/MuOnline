package eu.derbed.openmu.gs.clientPackage;

import eu.derbed.openmu.gs.ClientThread;

public class CSelectCharacterOrExitRequest extends ClientBasePacket {
	public CSelectCharacterOrExitRequest(byte[] decrypt, ClientThread _client) {
		super(decrypt);
		switch (decrypt[2]) {
		case 0x00:
			System.out.println("exit game request");
			break;
		case 0x01:
			System.out.println("Change server request");
			break;
		case 0x02:
			System.out.println("Change character request");
			break;
		}

	}

}

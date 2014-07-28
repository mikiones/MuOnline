package eu.derbed.openmu.gs.clientPackage;

import com.notbed.muonline.util.DataDecrypter;

import eu.derbed.openmu.gs.ClientThread;

public class CSelectCharacterOrExitRequest extends SimpleClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) {
		switch (dataDecrypter.data[2]) {
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

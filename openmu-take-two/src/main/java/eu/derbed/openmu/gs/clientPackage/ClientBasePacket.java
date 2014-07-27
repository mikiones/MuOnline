package eu.derbed.openmu.gs.clientPackage;

import com.notbed.muonline.util.DataDecrypter;



/**
 * This class ...
 *
 * @version $Revision: 1.5 $ $Date: 2004/10/23 17:26:18 $
 */
public abstract class ClientBasePacket {

	protected final DataDecrypter decrypter;

	/**
	 * @param decrypt
	 */
	public ClientBasePacket(byte[] decrypt) {
		this.decrypter = new DataDecrypter(decrypt);
	}

}

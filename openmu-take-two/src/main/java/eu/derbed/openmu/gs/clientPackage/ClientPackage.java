package eu.derbed.openmu.gs.clientPackage;

import eu.derbed.openmu.gs.ClientThread;

/**
 * @author Alexandru Bledea
 * @since Jul 27, 2014
 */
public interface ClientPackage {

	/**
	 * @param data
	 * @param client
	 */
	void process(byte[] data, ClientThread client);

}

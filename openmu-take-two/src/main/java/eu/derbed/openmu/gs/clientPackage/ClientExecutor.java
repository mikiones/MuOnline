package eu.derbed.openmu.gs.clientPackage;

import eu.derbed.openmu.gs.ClientThread;

/**
 * @author Alexandru Bledea
 * @since Jul 27, 2014
 */
public interface ClientExecutor { // probably temporary

	/**
	 * @param data
	 * @param client
	 */
	void execute(byte[] data, ClientThread client);

}

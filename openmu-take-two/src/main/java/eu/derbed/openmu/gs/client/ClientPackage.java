package eu.derbed.openmu.gs.client;

import java.io.IOException;

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
	void process(byte[] data, ClientThread client) throws IOException;

}

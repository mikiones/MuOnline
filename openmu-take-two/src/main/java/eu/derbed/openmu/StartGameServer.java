/**
 *
 */
package eu.derbed.openmu;

import java.io.IOException;


/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public class StartGameServer {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new GameServer().start();
	}

}

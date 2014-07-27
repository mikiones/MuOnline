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

		if (true) { // remove this
			System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
			System.setProperty("org.slf4j.simpleLogger.showLogName", "false");
			System.setProperty("org.slf4j.simpleLogger.showShortLogName", "true");
		}
		new GameServer().start();
	}

}

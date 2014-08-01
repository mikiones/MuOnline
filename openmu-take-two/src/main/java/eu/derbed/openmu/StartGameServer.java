/**
 *
 */
package eu.derbed.openmu;

import java.io.IOException;

import com.notbed.muonline.util.RegistrationException;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public class StartGameServer {

	/**
	 * @param args
	 * @throws IOException
	 * @throws RegistrationException
	 */
	public static void main(final String[] args) throws IOException, RegistrationException {

		if (true) { // remove this
			System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
			System.setProperty("org.slf4j.simpleLogger.showLogName", "false");
			System.setProperty("org.slf4j.simpleLogger.showShortLogName", "true");
		}
		new GameServer().run();
	}

}

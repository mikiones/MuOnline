/**
 *
 */
package eu.derbed.openmu;

import java.io.IOException;
import java.sql.SQLException;

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
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(final String[] args) throws IOException, RegistrationException, InstantiationException, IllegalAccessException,
			ClassNotFoundException, SQLException {

		if (true) { // remove this
			System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "debug");
			System.setProperty("org.slf4j.simpleLogger.showLogName", "false");
			System.setProperty("org.slf4j.simpleLogger.showShortLogName", "true");
		}
		new OldGameServer().run();
	}

}

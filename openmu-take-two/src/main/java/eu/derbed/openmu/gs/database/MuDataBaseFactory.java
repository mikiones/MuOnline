package eu.derbed.openmu.gs.database;

//~--- non-JDK imports --------------------------------------------------------

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

import eu.derbed.openmu.gs.GameServerConfig;

public class MuDataBaseFactory {

	private final String driver;
	private final String url;
	private final String username;
	private final String password;

	private static MuDataBaseFactory _instance;

	private DataSource _source;
	private Statement _syst;

	public MuDataBaseFactory() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Properties db = GameServerConfig.databse;
		driver = db.getProperty("driver");
		url = db.getProperty("url");
		username = db.getProperty("user");
		password = db.getProperty("password");

		try {

			// Properties serverSettings = new Properties();
			// InputStream is =
			// getClass().getResourceAsStream("/data/server.cfg");
			// serverSettings.load(is);

			Class.forName(driver).newInstance();

			final DataSource unpooled = DataSources.unpooledDataSource(url,
					username, password);
			_source = DataSources.pooledDataSource(unpooled);

			// _source =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/mu_online",
			// "root", "mikione");
			// _syst=_source.createStatement();

			/* Test the connection */

			// _source.close();
			_source.getConnection().close();

		} catch (Throwable t) {
			String message = "Failed to connect to '%s' using user '%s', password '%s', driver '%s'";
			message = String.format(message, url, username, password, driver);
			throw new IllegalStateException(message, t);
		}

	}

	public static MuDataBaseFactory getInstance() throws SQLException {
		if (_instance == null) {
			throw new IllegalStateException("MuDataBaseFactory not initialized!");
		}

		return _instance;
	}

	/**
	 *
	 */
	public static void initiate() {
		try {
			_instance = new MuDataBaseFactory();
		} catch (Throwable t) {
			throw new IllegalStateException("Failed to initiate Database factory", t);
		}
	}

	public Connection getConnection() throws SQLException {
		return _source.getConnection();
	}

	public Statement getSyst() {
		return _syst;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com

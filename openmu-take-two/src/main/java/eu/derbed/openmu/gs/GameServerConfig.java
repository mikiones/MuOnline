package eu.derbed.openmu.gs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

/**
 *
 * @author Marcel
 */
public final class GameServerConfig {

	private final Properties database = new Properties();
	public final Properties gs = new Properties();
	public final Properties cs = new Properties();

	public final boolean testMode;

	private final String confFolder;
	private final String itemFile;
	private final String mapsFolder;

	/**
	 * @param confFolder
	 */
	public GameServerConfig(final String confFolder) {
		this.confFolder = confFolder;
		this.itemFile = confFolder + "/data/item.txt";
		this.mapsFolder = confFolder + "/data/item.txt";

		loadConfig();

		testMode = Boolean.valueOf(gs.getProperty("testmode", "false"));
	}

	/**
	 *
	 */
	private void loadConfig() {
		load(confFolder, "/conf/database.ini", database);
		load(confFolder, "/conf/gameserver.ini", gs);
		load(confFolder, "/conf/connectserver.ini", cs);
	}

	/**
	 * @param conf
	 * @param file
	 * @param dest
	 */
	private static void load(final String conf, final String file, final Properties dest) {
		try (InputStream in = new FileInputStream(conf + file)) {
			dest.load(in);
		} catch (final IOException e) {
			throw new IllegalStateException("Failed to load " + file);
		}
	}

	/**
	 * @return
	 */
	public boolean isTestMode() {
		return testMode;
	}

	/**
	 * @return
	 */
	public String getItemFile() {
		return itemFile;
	}

	/**
	 * @return the mapsFolder
	 */
	public String getMapsFolder() {
		return mapsFolder;
	}

	/**
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DataSource getDataSource() throws SQLException {
		try {
			Class.forName(database.getProperty("driver"));
		} catch (final ClassNotFoundException e) {
			throw new SQLException("Database driver is not available", e);
		}

		final String url = database.getProperty("url");
		final String username = database.getProperty("user");
		final String password = database.getProperty("password");

		final DataSource source = DataSources.unpooledDataSource(url, username, password);
		final DataSource polled = DataSources.pooledDataSource(source);

		polled.getConnection().close();

		return polled;
	}
}

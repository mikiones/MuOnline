package eu.derbed.openmu.gs;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Marcel
 */
public final class GameServerConfig {

	public final Properties databse = new Properties();
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
		load(confFolder, "/conf/database.ini", databse);
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

}

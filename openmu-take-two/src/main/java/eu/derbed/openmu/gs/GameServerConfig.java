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

	private static final String GLOBAL_CONF = "global.conf";

	private static final GameServerConfig INSTANCE = new GameServerConfig();

	public static final Properties global = new Properties();
	public static final Properties databse = new Properties();
	public static final Properties gs = new Properties();
	public static final Properties cs = new Properties();

	public final boolean testMode;

	/**
	 * @return
	 */
	public static GameServerConfig getInstance() {
		return INSTANCE;
	}

	/**
	 *
	 */
	private GameServerConfig() {
		global.put("global.home", System.getProperty("user.dir"));
		global.put(GLOBAL_CONF, global.getProperty("global.home") + "/src/main/resources");

		final String conf = getConf();
		global.put("global.itemFile", conf + "/data/item.txt");
		global.put("global.mapsDir", conf + "/data/maps/");

		loadConfig();

		testMode = Boolean.valueOf(gs.getProperty("testmode", "false"));
	}

	/**
	 * @return
	 */
	private String getConf() {
		return global.getProperty(GLOBAL_CONF);
	}

	/**
	 *
	 */
	private void loadConfig() {
		final String conf = getConf();
		load(conf, "/conf/database.ini", databse);
		load(conf, "/conf/gameserver.ini", gs);
		load(conf, "/conf/connectserver.ini", cs);
	}

	/**
	 * @param conf
	 * @param file
	 * @param dest
	 */
	private void load(String conf, String file, Properties dest) {
		try (InputStream in = new FileInputStream(conf + file)) {
			dest.load(in);
		} catch (IOException e) {
			throw new IllegalStateException("Failed to load " + file);
		}
	}

	/**
	 * @return
	 */
	public static boolean isTestMode() {
		return getInstance().testMode;
	}

}

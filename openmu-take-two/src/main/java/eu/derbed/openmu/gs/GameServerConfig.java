package eu.derbed.openmu.gs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.LoggerFactory;

/**
 *
 * @author Marcel
 */
public final class GameServerConfig {
	private static GameServerConfig _instance;
	public static final Properties global = new Properties();
	public static final Properties databse = new Properties();
	public static final Properties gs = new Properties();
	public static final Properties logs = new Properties();
	public static final Properties cs = new Properties();

	public final boolean testMode;

	public static GameServerConfig getInstance() throws IOException {
		if (_instance == null) {
			_instance = new GameServerConfig();
		}
		return _instance;
	}

	private GameServerConfig() throws IOException {
		//global.put("global.home", System.getProperty("user.home")+"/.jmuserv/");
		global.put("global.home", System.getProperty("user.dir"));
		global.put("global.conf", global.getProperty("global.home") + "/src/main/resources");
		global.put("global.itemFile", global.getProperty("global.conf") + "/data/item.txt");
		global.put("global.mapsDir", global.getProperty("global.conf") + "/data/maps/");
		loadConfig();

		testMode = new Boolean(gs.getProperty("testmode", "false"));
	}

	private void loadConfig() throws IOException {
		databse.load(new FileInputStream(global.getProperty("global.conf") + "/conf/database.ini"));
		gs.load(new FileInputStream(global.getProperty("global.conf") + "/conf/gameserver.ini"));
		logs.load(new FileInputStream(global.getProperty("global.conf") + "/conf/mulog.ini"));
		cs.load(new FileInputStream(global.getProperty("global.conf") + "/conf/connectserver.ini"));
	}

	public void findDataDirectory() {
		final String home = System.getProperty("user.dir");
		System.out.println(home);
	}

	/**
	 * @return
	 */
	public static boolean isTestMode() {
		try {
			return getInstance().testMode;
		} catch (Throwable t) {
			LoggerFactory.getLogger(GameServerConfig.class).error("Failed to get instance", t);
			return true;
		}
	}

}

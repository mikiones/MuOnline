package eu.derbed.openmu.gs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Marcel
 */
public class GameServerConfig {
	static private GameServerConfig _instance;
	static public Properties global = new Properties();
	static public Properties databse = new Properties();
	static public Properties gs = new Properties();
	static public Properties logs = new Properties();
	static public Properties cs = new Properties();


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

}

package eu.derbed.openmu.gs;

import java.io.IOException;
import java.util.Properties;

import com.notbed.muonline.util.IoUtil;

/**
 *
 * @author Marcel
 */
public final class GameServerConfig {

	public final Properties gs;
	public final Properties cs;

	public final boolean testMode;

	private final String itemFile;
	private final String mapsFolder;

	/**
	 * @param confFolder
	 * @throws IOException
	 */
	public GameServerConfig(final String confFolder) throws IOException {
		this.itemFile = confFolder + "/data/item.txt";
		this.mapsFolder = confFolder + "/data/maps/";

		gs = IoUtil.load(confFolder, "/conf/gameserver.ini");
		cs = IoUtil.load(confFolder, "/conf/connectserver.ini");

		testMode = Boolean.valueOf(gs.getProperty("testmode", "false"));
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

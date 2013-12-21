/**
 *
 */
package eu.derbed.openmu.base;

import org.apache.commons.lang3.StringUtils;

import eu.derbed.openmu.gs.GameServerConfig;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public abstract class Packet extends LoggableObject {

	/**
	 *
	 */
	public Packet() {
		logType();
	}

	/**
	 *
	 */
	private final void logType() {
		if (GameServerConfig.isTestMode()) {
			String type = getType();
			if (!StringUtils.isEmpty(type)) {
				log.info(type);
			}
		}
	}

	/**
	 * @return scope of the packet for debugging purposes
	 */
	public abstract String getType();

}

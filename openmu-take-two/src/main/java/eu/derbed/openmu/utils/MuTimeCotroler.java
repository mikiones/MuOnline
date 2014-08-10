/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.derbed.openmu.utils;

import static java.lang.System.currentTimeMillis;

/**
 *
 * @author Miki i Linka
 */
public class MuTimeCotroler {

	private final long _serverStartTime = currentTimeMillis();

	/**
	 *
	 * @return the time
	 */
	public int getGameTime() {
		return (int) ((currentTimeMillis() - _serverStartTime) / 1000);
	}
}

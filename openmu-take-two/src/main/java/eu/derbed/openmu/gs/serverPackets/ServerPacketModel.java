/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.gs.serverPackets;

import java.io.IOException;

/**
 *
 * @author Miki
 */
public interface ServerPacketModel {

	/**
	 *
	 * @return zwraca zarartosc w byte[]
	 */
	byte[] getBytes();

	byte[] getContent() throws IOException, Throwable;

	int getLength();

	boolean testMe();

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.gs.serverPackets;

import java.io.IOException;
import java.nio.channels.ByteChannel;

import com.notbed.muonline.util.ByteContainer;

/**
 *
 * @author Miki
 */
public interface ServerPacketModel extends ByteContainer {

	/**
	 *
	 * @return zwraca zarartosc w byte[]
	 */
	byte[] getBytes();

	int getLength();

	boolean testMe();

}

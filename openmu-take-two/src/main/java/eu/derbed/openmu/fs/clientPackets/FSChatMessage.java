/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.fs.clientPackets;

import eu.derbed.openmu.fs.FriendTheard;
import eu.derbed.openmu.gs.clientPackage.ClientBasePacket;


/**
 * 
 * @author mikiones
 */
public class FSChatMessage extends ClientBasePacket {

	byte size;
	String message;

	public FSChatMessage(byte[] decrypt, FriendTheard _fs) {
		super(decrypt);
		size = decrypt[02];
		System.out.println("FS:> Size of package:" + decrypt.length + "]");
		Dec3bit(03, size);
		message = readS(03, size);
		System.out.println("FS:> Message[" + message + "] Size : ["
				+ Integer.toHexString(size) + "]");
	}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.fs.clientPackets;

import eu.derbed.openmu.fs.ClientBasePacket;
import eu.derbed.openmu.fs.FriendTheard;


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
		decrypter.dec3bit(03, size);
		message = decrypter.readS(03, size);
		System.out.println("FS:> Message[" + message + "] Size : ["
				+ Integer.toHexString(size) + "]");
	}

}

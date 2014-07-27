/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.gs.clientPackage;

import eu.derbed.openmu.gs.ClientThread;

/**
 * 
 * @author Miki i Linka
 */
public class CAddFrendRequest extends ClientBasePacket {

	public CAddFrendRequest(byte[] _decrypt, ClientThread _client) {
		super(_decrypt);
		System.out.println("|-Add Friend Request:");
		String _name = readS(2, 10);
		_name = _name.trim();
		System.out.println("_name = " + _name);
	}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.gs.clientPackage;

import com.notbed.muonline.util.DataDecrypter;

import eu.derbed.openmu.gs.ClientThread;

/**
 * 
 * @author Miki i Linka
 */
public class CAddFrendRequest extends SimpleClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter decrypter, ClientThread client) {
		System.out.println("|-Add Friend Request:");
		String _name = decrypter.readS(2, 10);
		_name = _name.trim();
		System.out.println("_name = " + _name);
	}

}

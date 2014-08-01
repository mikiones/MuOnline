/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.gs.client;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.clientPackage.SimpleClientPackage;

/**
 *
 * @author Miki i Linka
 */
@Header (0xc1)
class CAddFrendRequest extends SimpleClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(final DataDecrypter decrypter, final ClientThread client) {
		System.out.println("|-Add Friend Request:");
		String _name = decrypter.readS(2, 10);
		_name = _name.trim();
		System.out.println("_name = " + _name);
	}

}

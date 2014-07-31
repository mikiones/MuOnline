/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;

/**
 * 
 * @author Miki i Linka
 */
@Header (0x32)
public class CBuyItemRequest implements ClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CBuyItemRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#process(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public void process(byte[] data, ClientThread client) throws IOException {
		log.debug("Rquest to buy item from slot {}", data[1]);
	}

}

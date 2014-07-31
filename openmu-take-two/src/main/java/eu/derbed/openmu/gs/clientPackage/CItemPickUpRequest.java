/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuWorld;

/**
 * @author Miki i Linka
 */
@Header (0x22)
public class CItemPickUpRequest extends SimpleClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CItemPickUpRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) throws IOException {
		// decrypt[1]=0x00 :// to fix with |0x80
		final int id = dataDecrypter.data[2];
		// MuObject[] obj =
		// _client.getActiveChar().getCurrentWorldRegion().getVisibleObjects();
		// for (int i=0; i<obj.length; i++)
		// if (((MuObject)obj[i]).getObjectId() == id) {
		// ((MuObject)obj[i]).getCurrentWorldRegion();
		// MuWorld.getInstance().removeObject(obj[i]);
		// break;
		// }
		MuWorld.getInstance().removeObject(id);
		log.debug("Request to pickup item id {}", id);
	}

	// 0x22 opcode

}

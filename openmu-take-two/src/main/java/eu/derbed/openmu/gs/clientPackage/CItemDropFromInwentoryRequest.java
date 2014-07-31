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

/**
 * @author Miki
 */
@Header (0x23)
public class CItemDropFromInwentoryRequest extends SimpleClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CItemDropFromInwentoryRequest.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) throws IOException {
		// readC();
		final int _xPos = dataDecrypter.data[1] & 0xff;
		final int _yPos = dataDecrypter.data[2] & 0xff;
		final int _slotFrom = dataDecrypter.data[3] & 0xff;
		// 23 ad 7f 0c
		log.debug("Drop Request from slot[{}] to wsp [ {}, {} ]", _slotFrom, _xPos, _yPos);
	}// 26 0c 00
		// 23 ad 7f 0c
}

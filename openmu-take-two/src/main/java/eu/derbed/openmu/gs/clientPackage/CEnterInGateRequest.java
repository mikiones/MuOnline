/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import com.notbed.muonline.util.DataDecrypter;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuGate;
import eu.derbed.openmu.gs.muObjects.MuWorld;
import eu.derbed.openmu.gs.serverPackets.SGateEnterAnsfer;


/**
 * 
 * @author Miki i Linka, MarcelGh
 */
public class CEnterInGateRequest extends SimpleClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) throws IOException {
		final int GateNb = dataDecrypter.data[1] & 0xff; // number of gate in gate.bmp
		// System.out.println("Request to enter in gate id:" + GateNb);
		// fMap = decrypt[2] & 0xff;
		// x = decrypt[3] & 0xff;
		// y = decrypt[4] & 0xff;
		// direction = decrypt[5] & 0xff;
		// TODO: must test packet mapid and coordinates to match current chars
		// mapid and coords
		// and if those coords are in range defined by the gate and only then
		// perform teleport
		// WARNING: sometimes received coordinates are 0x00 and direction is not
		// valid
		final MuGate gate = MuWorld.getInstance().getGate(GateNb);
		final MuGate gateTo = MuWorld.getInstance().getGate(gate.getToGate());
		client.getConnection().sendPacket(
				new SGateEnterAnsfer(gateTo, client.getActiveChar()
						.getDirection()));
	}

}

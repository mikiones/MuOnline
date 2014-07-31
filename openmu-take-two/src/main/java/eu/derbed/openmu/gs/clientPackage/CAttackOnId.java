package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuMonsterInstance;
import eu.derbed.openmu.gs.muObjects.MuObject;
import eu.derbed.openmu.gs.muObjects.MuPcInstance;
import eu.derbed.openmu.gs.muObjects.MuWorld;

@Header (0xd9)
public class CAttackOnId implements ClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CAttackOnId.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#process(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public void process(byte[] data, ClientThread client) throws IOException {
		final int _id = (data[2] & 0xff);
		// _id|= _decrypt[2] << 8 & 0xff00;
//		final short _r |= (decrypt[4]);// r

		// System.out.println("Atack On target "+ _id+ "in r "+_r);
		final MuObject t = MuWorld.getInstance().getObject(_id);
		if (t instanceof MuMonsterInstance) {
			final MuMonsterInstance mon = (MuMonsterInstance) t;
			client.getActiveChar().startAttack(mon);
			client.getActiveChar().StartCombat();
			mon.setTarget(client.getActiveChar());
		} else if (t instanceof MuPcInstance) {
			log.debug("Atack on player not done yet11");
		} else {
			log.debug("Hmm atack to id {} ??? {} its not caind of MuPcInstance", _id, t);
			// mon.StartCombat();
			// mon.printMyStatus();
			// }
		}
	}

}

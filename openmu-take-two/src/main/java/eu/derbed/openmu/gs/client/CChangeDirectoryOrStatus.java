package eu.derbed.openmu.gs.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuPcInstance;
import eu.derbed.openmu.gs.serverPackets.SDirectionOrStatusChange;

@Header (0x18)
class CChangeDirectoryOrStatus extends SimpleClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CChangeDirectoryOrStatus.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) throws IOException {
		final byte _direction = dataDecrypter.data[1];
		final byte _status = dataDecrypter.data[2];

		final MuPcInstance pc = client.getActiveChar();
		pc.setDirection(_direction);
		pc.setStatus(_status);
		if (pc.getCurrentWorldRegion() != null) {
			pc.getCurrentWorldRegion().broadcastPacketWideArea(pc,
					pc.getCurrentMuMapPointX(), pc.getCurrentMuMapPointY(),
					new SDirectionOrStatusChange(pc, _status));
		}

		log.debug("Object new direction to {} and status {}.", _direction, _status);
	}

}

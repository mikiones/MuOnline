package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import com.notbed.muonline.util.DataDecrypter;

import eu.derbed.openmu.gs.ClientThread;

/**
 * @author Alexandru Bledea
 * @since Jul 27, 2014
 */
public abstract class SimpleClientPackage implements ClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#process(byte[], eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public final void process(byte[] data, ClientThread client) throws IOException {
		process(new DataDecrypter(data), client);
	}

	/**
	 * @param dataDecrypter
	 * @param client
	 */
	protected abstract void process(DataDecrypter dataDecrypter, ClientThread client) throws IOException;

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.ClientPackage#getIdentifier()
	 */
	@Override
	public int getIdentifier() {
		return Integer.MAX_VALUE; // dummy
	}
}

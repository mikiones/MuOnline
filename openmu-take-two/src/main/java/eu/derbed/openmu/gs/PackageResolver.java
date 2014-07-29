package eu.derbed.openmu.gs;

import eu.derbed.openmu.gs.clientPackage.ClientPackage;

/**
 * @author Alexandru Bledea
 * @since Jul 29, 2014
 */
public interface PackageResolver {

	/**
	 * @param data
	 * @param client
	 * @return
	 */
	ClientPackage resolve(byte[] data, ClientThread client);

}

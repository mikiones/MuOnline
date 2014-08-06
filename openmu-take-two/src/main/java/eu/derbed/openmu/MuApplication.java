/**
 *
 */
package eu.derbed.openmu;

import java.sql.Connection;
import java.sql.SQLException;

import com.notbed.muonline.util.PacketResolver;
import com.notbed.muonline.util.RegistrationException;

import eu.derbed.openmu.gs.client.ClientPackage;
import eu.derbed.openmu.gs.client.ClientPacketResolver;
import eu.derbed.openmu.gs.database.DataAccess;

/**
 * @author Alexandru Bledea
 * @since Aug 5, 2014
 */
public final class MuApplication {

	private final DataAccess dataAccess;
	private final PacketResolver<ClientPackage> resolver;

	/**
	 * @param dataAccess
	 * @throws RegistrationException
	 */
	public MuApplication(final DataAccess dataAccess) throws RegistrationException {
		this.dataAccess = dataAccess;
		this.resolver = new ClientPacketResolver();

	}

	/**
	 * @return the resolver
	 */
	public PacketResolver<ClientPackage> getResolver() {
		return resolver;
	}

	/**
	 * @return the dataAccess
	 */
	public DataAccess getDataAccess() {
		return dataAccess;
	}

	/**
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return dataAccess.getConnection();
	}

}

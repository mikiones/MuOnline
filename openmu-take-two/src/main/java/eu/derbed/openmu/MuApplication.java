/**
 *
 */
package eu.derbed.openmu;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.PacketResolver;
import com.notbed.muonline.util.RegistrationException;

import eu.derbed.openmu.gs.GameServerConfig;
import eu.derbed.openmu.gs.client.ClientPackage;
import eu.derbed.openmu.gs.client.ClientPacketResolver;
import eu.derbed.openmu.gs.muObjects.MuWorld;
import eu.derbed.util.database.DataAccess;

/**
 * @author Alexandru Bledea
 * @since Aug 5, 2014
 */
public final class MuApplication {

	private static final Logger log = LoggerFactory.getLogger(MuApplication.class);

	private final DataAccess dataAccess;
	private final PacketResolver<ClientPackage> resolver;
	private final GameServerConfig gameServerConfig;
	private final MuWorld world;

	/**
	 * @param gameServerConfig
	 * @param dataAccess
	 * @throws RegistrationException
	 */
	public MuApplication(final GameServerConfig gameServerConfig, final DataAccess dataAccess) throws RegistrationException {
		this.dataAccess = dataAccess;
		this.gameServerConfig = gameServerConfig;
		this.resolver = new ClientPacketResolver();

		final int range = Integer.parseInt(gameServerConfig.gs.getProperty("gs.playerVisibility"));
		final String mapsFolder = gameServerConfig.getMapsFolder();
		final MuWorld world = new MuWorld(mapsFolder, range);
		{
			log.info("Init Regions:...");
			world.initWorld(gameServerConfig.getItemFile());
			log.info("Init Gates.....");
			world.InitGates();
		}
		this.world = world;
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

	/**
	 * @return the gameServerConfig
	 */
	public GameServerConfig getGameServerConfig() {
		return gameServerConfig;
	}

	/**
	 * @return the world
	 */
	public MuWorld getWorld() {
		return world;
	}

}

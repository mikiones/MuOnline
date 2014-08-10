package eu.derbed.openmu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.RegistrationException;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.CommandHandler;
import eu.derbed.openmu.gs.GameServerConfig;

public class OldGameServer {

	private final Logger log = LoggerFactory.getLogger(getClass());

	// Socket listener

	private ServerSocket _serverSocket;
	public static int _port;

	private final MuApplication app;

	/**
	 * @throws IOException
	 * @throws RegistrationException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public OldGameServer()
			throws IOException, RegistrationException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		final String confFolder = System.getProperty("user.dir") + "/src/main/resources";
		final GameServerConfig gsConfig = new GameServerConfig(confFolder);
		final MuDataBaseFactory databaseFactory = new MuDataBaseFactory(gsConfig.databse);
		app = new MuApplication(gsConfig, databaseFactory);

		log.info("WorkingDir: " + System.getProperty("user.dir"));

//		_ip = GameServerConfig.gs.getProperty("gs.ip");
		_port = Integer.parseInt((app.getGameServerConfig().gs.getProperty("gs.port")));

		log.debug("used mem: " + getUsedMemoryMB() + "MB");

		final String hostname = "*";

		// String port = "port";
		// _port = 55901;

		if (!"*".equals(hostname)) {
			final InetAddress adr = InetAddress.getByName(hostname);
//			_ip = adr.getHostAddress();
			_serverSocket = new ServerSocket(_port, 50, adr);
			// application.AddLog("GameServer listening on IP:" + _ip + " Port "
			log.info("GameServer Listening on port " + _port);
			// + _port);
		} else {
			_serverSocket = new ServerSocket(_port);
			// application.AddLog("GameServer listening on all available IPs on Port "
			// + _port);
		}

		// new File("data/clans").mkdirs();
		// new File("data/crests").mkdirs();

		// keep the references of Singletons to prevent garbage collection
		// Runtime.getRuntime().addShutdownHook(Shutdown.getInstance());
	}

	/**
	 *
	 */
	public void run() {
		try {
			CommandHandler.getInstancec();


			while (true) {
				try {
					new ClientThread(_serverSocket.accept(), app);
				} catch (final IOException e) {
					// not a real problem
				}
			}

		} catch (final Throwable t) {
			log.error("Game Server Crashed!", t);
		}
	}

	public static long getUsedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
				.freeMemory()) / 1024 / 1024;
	}

}

package eu.derbed.openmu;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.CommandHandler;
import eu.derbed.openmu.gs.GameServerConfig;
import eu.derbed.openmu.gs.database.MuDataBaseFactory;
import eu.derbed.openmu.gs.muObjects.MuWorld;

public class GameServer extends Thread {

	private final Logger log = LoggerFactory.getLogger(getClass());

	// Socket listener

	private ServerSocket _serverSocket;
	public static int _port;

	/**
	 * @throws IOException
	 */
	public GameServer() throws IOException {
		super("AppMain");

		log.info("WorkingDir: " + System.getProperty("user.dir"));

		GameServerConfig.getInstance();

//		_ip = GameServerConfig.gs.getProperty("gs.ip");
		_port = Integer.parseInt((GameServerConfig.gs.getProperty("gs.port")));

		System.out.println("used mem:" + getUsedMemoryMB() + "MB");

		final String hostname = "*";

		// String port = "port";
		// _port = 55901;

		if (!"*".equals(hostname)) {
			final InetAddress adr = InetAddress.getByName(hostname);
//			_ip = adr.getHostAddress();
			_serverSocket = new ServerSocket(_port, 50, adr);
			log.info("GameServer Listening on port " + _port);
			// application.AddLog("GameServer listening on IP:" + _ip + " Port "
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

	@Override
	public void run() {
		try {
			log.info("Init Regions:...");
			MuWorld.getInstance().initWorld();
			log.info("Init Gates.....");
			MuWorld.getInstance().InitGates();
			CommandHandler.getInstancec();

			MuDataBaseFactory.initiate();

			while (true) {
				try {
					final Socket connection = _serverSocket.accept();
					new ClientThread(connection);
				} catch (final IOException e) {
					// not a real problem
				}
			}

		} catch (Throwable t) {
			log.error("Game Server Crashed!", t);
		}
	}

	public long getUsedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
				.freeMemory()) / 1024 / 1024;
	}

}

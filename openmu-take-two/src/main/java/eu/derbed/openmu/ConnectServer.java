package eu.derbed.openmu;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import eu.derbed.openmu.cs.CSChanellPipelineFactory;
import eu.derbed.openmu.cs.ServerList;
import eu.derbed.openmu.gs.GameServerConfig;

/**
 *
 * @author mikiones
 * @version $ref
 */
public class ConnectServer {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final String confFolder = System.getProperty("user.dir") + "/src/main/resources";
		final ServerList serverList = new ServerList();

		serverList.load(new GameServerConfig(confFolder).cs);
		final ServerBootstrap CS = new ServerBootstrap(
				new NioServerSocketChannelFactory(
						Executors.newCachedThreadPool(),
						Executors.newCachedThreadPool()));
		CS.setPipelineFactory(new CSChanellPipelineFactory(serverList));
		CS.setOption("tcpNoDelay", true);
		CS.setOption("keepAlive", true);
		CS.bind(new InetSocketAddress(44405));

	}
}

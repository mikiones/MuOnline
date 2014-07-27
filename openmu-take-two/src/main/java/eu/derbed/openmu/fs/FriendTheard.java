/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.derbed.openmu.fs;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.notbed.muonline.util.MuSocket;

/**
 * 
 * @author Miki i Linka
 */
public class FriendTheard extends Thread {
	private final MuSocket _connection;
	private final eu.derbed.openmu.fs.PacketHandler _handler;

	public FriendTheard(Socket _con) throws IOException {
		_connection = new MuSocket(_con);
		_handler = new eu.derbed.openmu.fs.PacketHandler(this);
		start();
	}

	public MuSocket getConnection() {
		return _connection;

	}

	@Override
	public void run() {
		boolean _while = true;
		while (_while) {
			try {

				// System.out.println("czekam na odpowiedz");
				final byte[] decrypt = _connection.getPacket();
				if (decrypt != null) {
					_handler.handlePacket(decrypt);
				} else {
					_while = false;
				}
				// System.out.println("odebralem");
			} catch (final IOException ex) {
				Logger.getLogger(FriendTheard.class.getName()).log(
						Level.SEVERE, null, ex);
			} catch (final Throwable ex) {
				Logger.getLogger(FriendTheard.class.getName()).log(
						Level.SEVERE, null, ex);

			}
			// System.out.println("odebralem");
		}

	}
}

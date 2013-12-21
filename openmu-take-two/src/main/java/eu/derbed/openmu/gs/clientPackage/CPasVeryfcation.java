package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.database.MuDataBaseFactory;
import eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer;


public class CPasVeryfcation extends ClientBasePacket {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public CPasVeryfcation(byte[] decrypt, ClientThread client)
			throws IOException, Throwable {
		super(decrypt);
		_pas = "";
		_use = "";
		Dec3bit(2, 10);
		Dec3bit(12, 10);
		_use = readS(2, 10);
		_pas = readS(12, 10);
		log.debug("Authentication request received [user: '{}', password: '{}']", _use, _pas);

		if (!doesUserNameExist(_use)) {
			log.debug("User '{}' does not exist in the database!", _use);
			client.getConnection().sendPacket(new SLoginAuthAnsfer((byte) 0));
			log.debug("Authentication failed.");
			return;
		}
		System.out.println("User znaleziony pobieram reszte danych:");
		client.readUser(_use);
		System.out.println("User:[" + client.getUser().getUser() + "] pass: ["
				+ client.getUser().getPass() + "] postaci ["
				+ client.getUser().getCh_c() + "].");

		// if(client.getUser()==null)client.makeNewUser(_use,_pas);

		// System.out.println("User[" + _use + "]Pass[" + _pas + "]");
		System.out.println("\n" + printData(_decrypt, _decrypt.length));
		if (client.getUser() != null) {
			if ((_use.compareTo(client.getUser().getUser()) == 0)
					|| (_pas.compareTo(client.getUser().getPass()) == 0)) {
				System.out.println("pass inv");
				client.getConnection().sendPacket(
						new SLoginAuthAnsfer(
								(byte) SLoginAuthAnsfer.PA_InvaltPassword));
			} else {
				System.out.println("pass ok");
				client.getConnection()
						.sendPacket(
								new SLoginAuthAnsfer(
										(byte) SLoginAuthAnsfer.PA_PassOK));
			}
		}
	}

	private String _pas;

	private String _use;

	@Override
	public String getType() {
		return "f101 authPack";
	}

	private String printData(byte[] data, int len) {
		final StringBuffer result = new StringBuffer();

		int counter = 0;

		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				result.append(fillHex(i, 4) + ": ");
			}

			result.append(fillHex(data[i] & 0xff, 2) + " ");
			counter++;
			if (counter == 16) {
				result.append("   ");

				int charpoint = i - 15;
				for (int a = 0; a < 16; a++) {
					final int t1 = data[charpoint++];
					if (t1 > 0x1f && t1 < 0x80) {
						result.append((char) t1);
					} else {
						result.append('.');
					}
				}

				result.append("\n");
				counter = 0;
			}
		}

		final int rest = data.length % 16;
		if (rest > 0) {
			for (int i = 0; i < 17 - rest; i++) {
				result.append("   ");
			}

			int charpoint = data.length - rest;
			for (int a = 0; a < rest; a++) {
				final int t1 = data[charpoint++];
				if (t1 > 0x1f && t1 < 0x80) {
					result.append((char) t1);
				} else {
					result.append('.');
				}
			}

			result.append("\n");
		}

		return result.toString();
	}

	private String fillHex(int data, int digits) {
		String number = Integer.toHexString(data);

		for (int i = number.length(); i < digits; i++) {
			number = "0" + number;
		}

		return number;
	};

	public boolean doesUserNameExist(String name) {
		boolean result = false;


		try (Connection con = MuDataBaseFactory.getInstance().getConnection()) {
			final PreparedStatement statement = con
					.prepareStatement("SELECT * FROM users where u_user=?");
			// ("select * from users where u_login=?");
			statement.setString(1, name);
			final ResultSet rset = statement.executeQuery();
			result = rset.next();
			con.close();
		} catch (final SQLException e) {
			log.error("Failed to check if user exists.", e);
		}
		return result;
	}
}

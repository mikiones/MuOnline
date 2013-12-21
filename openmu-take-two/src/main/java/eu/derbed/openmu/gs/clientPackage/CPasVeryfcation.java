package eu.derbed.openmu.gs.clientPackage;

import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_AccInvalt;
import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_InvaltPassword;
import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_PassOK;
import static eu.derbed.openmu.utils.UPacket.logTransfer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.database.MuDataBaseFactory;
import eu.derbed.openmu.gs.muObjects.MuUser;
import eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer;
import eu.derbed.openmu.utils.UString;


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
//		AAA fix logic
		boolean correctCredentials = doesUserNameExist(_use);
		final byte response;

		if (!correctCredentials) {
			log.debug("User '{}' does not exist in the database!", _use);
			response = PA_AccInvalt;
		} else {
			client.readUser(_use);
			MuUser user = client.getUser();

			correctCredentials = user != null;
			if (correctCredentials) {
				String userName = UString.nvl(user.getUser());
				String pass = UString.nvl(user.getPass());

				logTransfer(log, _decrypt);

				correctCredentials = StringUtils.equals(_use, userName) && StringUtils.equals(_pas, pass);

			}

			if (correctCredentials) {
				log.debug("Logged in.");
				response = PA_PassOK;
			} else {
				log.debug("Wrong password.");
				response = PA_InvaltPassword;
			}

		}


		client.getConnection().sendPacket(new SLoginAuthAnsfer(response));
	}

	private String _pas;

	private String _use;

	@Override
	public String getType() {
		return "f101 authPack";
	}

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

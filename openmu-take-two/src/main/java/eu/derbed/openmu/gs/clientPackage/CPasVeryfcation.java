package eu.derbed.openmu.gs.clientPackage;

import static com.notbed.muonline.util.UPacket.logTransfer;
import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_AccInvalt;
import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_InvaltPassword;
import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_PassOK;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import com.notbed.muonline.util.UString;

import eu.derbed.openmu.database.LoadUser;
import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.database.MuDataBaseFactory;
import eu.derbed.openmu.gs.muObjects.MuUser;
import eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer;
import eu.derbed.util.ICallback;

public class CPasVeryfcation extends ClientBasePacket {

	/**
	 * @param decrypt
	 * @param client
	 * @throws IOException
	 * @throws Throwable
	 */
	public CPasVeryfcation(byte[] decrypt, final ClientThread client)
			throws IOException, Throwable {
		super(decrypt);

		Dec3bit(2, 10);
		Dec3bit(12, 10);

		logTransfer(log, _decrypt);

		final String username = readS(2, 10);
		final String password = readS(12, 10);

		log.debug("Authentication request received [user: '{}', password: '{}']", username, password);

		MuDataBaseFactory.execute(new LoadUser(username, new ICallback<MuUser>() {

			@Override
			public void resultArrived(MuUser user) throws Throwable {

				final byte response;

				if (user == null) {

					log.debug("User '{}' does not exist in the database!", username);
					response = PA_AccInvalt;

				} else {

					String dbUsername = UString.trimNvl(user.getUser());
					String dbPassword = UString.trimNvl(user.getPass());

					boolean correctCredentials = StringUtils.equals(username, dbUsername) && StringUtils.equals(password, dbPassword);

					if (correctCredentials) {
						log.debug("Logged in.");
						response = PA_PassOK;
						client.setUser(user);
					} else {
						log.debug("Wrong password.");
						response = PA_InvaltPassword;
					}

				}

				client.getConnection().sendPacket(new SLoginAuthAnsfer(response));

			}
		}));
	}

}

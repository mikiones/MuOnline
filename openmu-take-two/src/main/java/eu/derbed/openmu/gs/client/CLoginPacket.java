package eu.derbed.openmu.gs.client;

import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_AccInvalt;
import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_InvaltPassword;
import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_PassOK;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;
import com.notbed.muonline.util.UPacket;
import com.notbed.muonline.util.UString;

import eu.derbed.openmu.database.LoadUser;
import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuUser;
import eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer;
import eu.derbed.util.CallbackException;
import eu.derbed.util.ICallback;

/**
 *
 */
@Header ({0xf1, 0x01})
class CLoginPacket extends SimpleClientPackage {

	private final static Logger log = LoggerFactory.getLogger(CLoginPacket.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(eu.derbed.openmu.gs.clientPackage.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(final DataDecrypter decrypter, final ClientThread client) throws IOException {
		UPacket.logTransfer(log, decrypter.data);
		decrypter.dec3bit(2, 10);
		decrypter.dec3bit(12, 10);
		UPacket.logTransfer(log, decrypter.data);
		decrypter.logTransfer(log);

		final String username = decrypter.readS(2, 10);
		final String password = decrypter.readS(12, 10);

		log.debug("Authentication request received [user: '{}', password: '{}']", username, password);

		client.getDataAccess().execute(new LoadUser(username, new ICallback<MuUser>() {

			/* (non-Javadoc)
			 * @see eu.derbed.util.ICallback#resultArrived(java.lang.Object)
			 */
			@Override
			public void resultArrived(final MuUser user) throws CallbackException, IOException {

				final byte response;

				if (user == null) {

					log.debug("User '{}' does not exist in the database!", username);
					response = PA_AccInvalt;

				} else {

					final String dbUsername = UString.trimNvl(user.getUser());
					final String dbPassword = UString.trimNvl(user.getPass());

					final boolean correctCredentials = StringUtils.equals(username, dbUsername) && StringUtils.equals(password, dbPassword);

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

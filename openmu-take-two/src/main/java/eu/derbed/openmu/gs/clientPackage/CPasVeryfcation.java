package eu.derbed.openmu.gs.clientPackage;

import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_AccInvalt;
import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_InvaltPassword;
import static eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer.PA_PassOK;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;
import com.notbed.muonline.util.UString;

import eu.derbed.openmu.database.LoadUser;
import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.database.MuDataBaseFactory;
import eu.derbed.openmu.gs.muObjects.MuUser;
import eu.derbed.openmu.gs.serverPackets.SLoginAuthAnsfer;
import eu.derbed.util.ICallback;

/**
 *
 */
@Header ({0xf1, 0x01})
public class CPasVeryfcation extends SimpleClientPackage {

	private final static Logger log = LoggerFactory.getLogger(CPasVeryfcation.class);

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(eu.derbed.openmu.gs.clientPackage.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	protected void process(final DataDecrypter decrypter, final ClientThread client) {
		decrypter.dec3bit(2, 10);
		decrypter.dec3bit(12, 10);

		decrypter.logTransfer(log);

		final String username = decrypter.readS(2, 10);
		final String password = decrypter.readS(12, 10);

		log.debug("Authentication request received [user: '{}', password: '{}']", username, password);

		MuDataBaseFactory.execute(new LoadUser(username, new ICallback<MuUser>() {

			@Override
			public void resultArrived(MuUser user) throws Throwable {

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

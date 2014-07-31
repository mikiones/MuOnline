package eu.derbed.openmu.gs.clientPackage;

import java.io.IOException;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.database.MuCharacterListDB;
import eu.derbed.openmu.gs.serverPackets.SDeleteChar;


/**
 * 
 * @author Marcel , Mikione
 * 
 */
@Header ({0xf3, 0x02})
public class CDeleteChar extends SimpleClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.clientPackage.SimpleClientPackage#process(com.notbed.muonline.util.DataDecrypter, eu.derbed.openmu.gs.ClientThread)
	 */
	protected void process(DataDecrypter decrypter, ClientThread _client) throws IOException {
		String p_code = _client.getUser().getChCode();
		// TODO sometimes if its nathing set i DB there is null so w relace it
		// as ""
		if (p_code == null) {
			p_code = "";
		}
		int result = 0x02;
		final String _name = decrypter.readS(2, 10);
		final String _personalcode = decrypter.readS(12, 7);
		if (_personalcode.compareTo(p_code) == 0) {
			result = 0x01;
		}
		// if (_personalcode.length() != 7)
		// result = 0x02;
		if (_client.getChList().getChar(_name).isInGuild()) {
			result = 0x00;
		}
		if (result == 0x01) {
			_client.getChList().removeChar(_name);
			final MuCharacterListDB cdb = new MuCharacterListDB(_client
					.getUser().getId());
			cdb.removeCharacterFromDB(_name);
		}
		_client.getConnection().sendPacket(new SDeleteChar(_name, result));
	}

}

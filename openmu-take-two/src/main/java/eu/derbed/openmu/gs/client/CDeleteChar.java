package eu.derbed.openmu.gs.client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.notbed.muonline.util.DataDecrypter;
import com.notbed.muonline.util.Header;
import com.notbed.muonline.util.IoUtil;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.database.MuCharacterListDB;
import eu.derbed.openmu.gs.serverPackets.SDeleteChar;

/**
 *
 * @author Marcel , Mikione
 *
 */
@Header ({0xf3, 0x02})
class CDeleteChar extends SimpleClientPackage {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.client.ClientPackage#process(com.notbed.muonline.util.Data, eu.derbed.openmu.gs.ClientThread)
	 */
	@Override
	public void process(final DataDecrypter decrypter, final ClientThread _client) throws IOException {
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
			Connection connection = null;
			try {
				connection = _client.getDataAccess().getConnection();
				final MuCharacterListDB cdb = new MuCharacterListDB(_client
						.getUser().getId(), connection);
				cdb.removeCharacterFromDB(_name);
			} catch (final SQLException e) {
				throw new IOException("Failed to get database connection");
			} finally {
				IoUtil.close(connection);
			}
		}
		_client.getConnection().sendPacket(new SDeleteChar(_name, result));
	}

}

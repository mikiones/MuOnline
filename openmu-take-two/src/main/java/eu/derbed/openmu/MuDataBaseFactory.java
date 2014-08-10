package eu.derbed.openmu;

//~--- non-JDK imports --------------------------------------------------------

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import eu.derbed.openmu.gs.database.DataAccess;
import eu.derbed.util.CallbackException;
import eu.derbed.util.database.DatabaseHelper;
import eu.derbed.util.database.ResultStatementEvaluator;

class MuDataBaseFactory implements DataAccess {

	private final DatabaseHelper dbhelper;

	private final DataSource source;
	private Statement _syst;

	/**
	 * @param db
	 * @throws SQLException
	 */
	public MuDataBaseFactory(final DataSource source) throws SQLException {
		// Properties serverSettings = new Properties();
		// InputStream is =
		// getClass().getResourceAsStream("/data/server.cfg");
		// serverSettings.load(is);

		this.source = source;
		dbhelper = new DatabaseHelper(this);
	}

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.utils.database.IConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return source.getConnection();
	}

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.MuDataBaseFactory#execute(eu.derbed.util.database.ResultStatementEvaluator)
	 */
	@Override
	public <R> void execute(final ResultStatementEvaluator<R> evaluator) throws IOException {
		try {
			dbhelper.execute(evaluator);
		} catch (final CallbackException e) {
			throw new IOException("Failed to execute the callback", e);
		}
	}

	public Statement getSyst() {
		return _syst;
	}
}


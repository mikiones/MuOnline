package eu.derbed.openmu;

//~--- non-JDK imports --------------------------------------------------------

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

import eu.derbed.openmu.gs.database.DataAccess;
import eu.derbed.util.CallbackException;
import eu.derbed.util.database.DatabaseHelper;
import eu.derbed.util.database.ResultStatementEvaluator;

class MuDataBaseFactory implements DataAccess {

	private final String driver;
	private final String url;
	private final String username;
	private final String password;

	private final DatabaseHelper dbhelper;

	private DataSource _source;
	private Statement _syst;

	/**
	 * @param db
	 */
	public MuDataBaseFactory(final Properties db) {
		driver = db.getProperty("driver");
		url = db.getProperty("url");
		username = db.getProperty("user");
		password = db.getProperty("password");

		try {

			// Properties serverSettings = new Properties();
			// InputStream is =
			// getClass().getResourceAsStream("/data/server.cfg");
			// serverSettings.load(is);

			Class.forName(driver).newInstance();

			final DataSource unpooled = DataSources.unpooledDataSource(url,
					username, password);
			_source = DataSources.pooledDataSource(unpooled);

			// _source =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/mu_online",
			// "root", "mikione");
			// _syst=_source.createStatement();

			/* Test the connection */

			// _source.close();
			_source.getConnection().close();

		} catch (final Throwable t) {
			String message = "Failed to connect to '%s' using user '%s', password '%s', driver '%s'";
			message = String.format(message, url, username, password, driver);
			throw new IllegalStateException(message, t);
		}

		dbhelper = new DatabaseHelper(this);
	}

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.utils.database.IConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return _source.getConnection();
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

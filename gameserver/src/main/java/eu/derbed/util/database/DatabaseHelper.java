/**
 *
 */
package eu.derbed.util.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

import eu.derbed.util.CallbackException;
import eu.derbed.util.LoggableObject;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public final class DatabaseHelper extends LoggableObject implements DataAccess {

	private final DataSource connectionProvider;

	/**
	 * @param rawProperties
	 * @throws SQLException
	 */
	public DatabaseHelper(final Properties rawProperties) throws SQLException {
		final Properties database = new Properties(rawProperties);
		try {
			Class.forName(database.getProperty("driver"));
		} catch (final ClassNotFoundException e) {
			throw new SQLException("Database driver is not available", e);
		}

		final String url = database.getProperty("url");
		final String username = database.getProperty("user");
		final String password = database.getProperty("password");

		final DataSource source = DataSources.unpooledDataSource(url, username, password);
		final DataSource polled = DataSources.pooledDataSource(source);

		polled.getConnection().close();

		this.connectionProvider = polled;
	}

	/* (non-Javadoc)
	 * @see eu.derbed.util.database.DataAccess#execute(eu.derbed.util.database.ResultStatementEvaluator)
	 */
	@Override
	public <R> void execute(final ResultStatementEvaluator<R> evaluator) throws CallbackException, IOException {

		try (Connection con = connectionProvider.getConnection()) {
			final R evaluate = evaluator.evaluate(con);
			evaluator.resultArrived(evaluate);
		} catch (final SQLException ex) {
			throw new CallbackException("Failed to work with database", ex);
		}

	}

	/* (non-Javadoc)
	 * @see eu.derbed.util.database.IConnectionProvider#getConnection()
	 */
	@Override
	public Connection getConnection() throws SQLException {
		return connectionProvider.getConnection();
	}

}

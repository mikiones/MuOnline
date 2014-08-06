/**
 *
 */
package eu.derbed.util.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import eu.derbed.util.CallbackException;
import eu.derbed.util.LoggableObject;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public final class DatabaseHelper extends LoggableObject {

	private final IConnectionProvider connectionProvider;

	/**
	 * @param connectionProvider
	 */
	public DatabaseHelper(final IConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	/**
	 * @param evaluator
	 * @throws CallbackException
	 * @throws IOException
	 */
	public <R> void execute(final ResultStatementEvaluator<R> evaluator) throws CallbackException, IOException {

		try (Connection con = connectionProvider.getConnection()) {
			final R evaluate = evaluator.evaluate(con);
			evaluator.resultArrived(evaluate);
		} catch (final SQLException ex) {
			throw new CallbackException("Failed to work with database", ex);
		}

	}

}

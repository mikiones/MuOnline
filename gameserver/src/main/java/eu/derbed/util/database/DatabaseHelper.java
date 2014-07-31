/**
 *
 */
package eu.derbed.util.database;

import java.sql.Connection;
import java.sql.Statement;

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
	public DatabaseHelper(IConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	/**
	 * @param evaluator
	 * @param callback
	 * @throws Throwable
	 */
	public <S extends Statement, R> void execute(ResultStatementEvaluator<S, R> evaluator) throws Throwable {

		try (Connection con = connectionProvider.getConnection()) {
			R evaluate = evaluator.evaluate(con);
			evaluator.resultArrived(evaluate);
		}

	}

}

/**
 *
 */
package eu.derbed.util.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import eu.derbed.util.ICallback;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public abstract class ResultStatementEvaluator<S extends Statement, R> implements IConnectionEvaluator<R>, ICallback<R> {

	protected final String query;

	/**
	 * @param query
	 */
	public ResultStatementEvaluator(String query) {
		this.query = query;
	}

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.utils.IEvaluator#evaluate(java.lang.Object)
	 */
	@Override
	public final R evaluate(Connection parameter) throws SQLException {
		return evaluate(createStatement(parameter));
	}

	/**
	 * @param stmt
	 * @return
	 */
	protected abstract R evaluate(S stmt) throws SQLException;

	/**
	 * @param parameter
	 * @return
	 */
	protected abstract S createStatement(Connection parameter) throws SQLException;

}

/**
 *
 */
package eu.derbed.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import eu.derbed.util.Callback;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public abstract class ResultStatementEvaluator<R> implements IConnectionEvaluator<R>, Callback<R> {

	protected final String query;

	/**
	 * @param query
	 */
	public ResultStatementEvaluator(final String query) {
		this.query = query;
	}

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.utils.IEvaluator#evaluate(java.lang.Object)
	 */
	@Override
	public final R evaluate(final Connection parameter) throws SQLException {
		return evaluate(createStatement(parameter));
	}

	/**
	 * @param stmt
	 * @return
	 */
	protected abstract R evaluate(PreparedStatement stmt) throws SQLException;

	/**
	 * @param parameter
	 * @return
	 */
	protected abstract PreparedStatement createStatement(Connection parameter) throws SQLException;

}

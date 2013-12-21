/**
 *
 */
package eu.derbed.util.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.derbed.util.ICallback;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public abstract class RsStmtEval<X> extends PreparedStatementEvaluator<ResultSet, X> {

	/**
	 * @param query
	 * @param processor
	 */
	public RsStmtEval(String query, ICallback<X> processor) {
		super(query, processor);
	}

	/* (non-Javadoc)
	 * @see eu.derbed.util.database.ResultStatementEvaluator#evaluate(java.sql.Statement)
	 */
	@Override
	protected final ResultSet evaluate(PreparedStatement stmt) throws SQLException {
		return stmt.executeQuery();
	}

}

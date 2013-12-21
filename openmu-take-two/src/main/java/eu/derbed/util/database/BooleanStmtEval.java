/**
 *
 */
package eu.derbed.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import eu.derbed.util.ICallback;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public abstract class BooleanStmtEval<Q> extends PreparedStatementEvaluator<Boolean, Q> {

	/**
	 * @param query
	 * @param processor
	 */
	public BooleanStmtEval(String query, ICallback<Q> processor) {
		super(query, processor);
	}

	/* (non-Javadoc)
	 * @see eu.derbed.util.database.ResultStatementEvaluator#evaluate(java.sql.Statement)
	 */
	@Override
	protected final Boolean evaluate(PreparedStatement stmt) throws SQLException {
		return stmt.execute();
	}

}

/**
 *
 */
package eu.derbed.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import eu.derbed.util.ICallback;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public abstract class PreparedStatementEvaluator<R, Q> extends ResultStatementEvaluator<PreparedStatement, R> {

	protected final ICallback<Q> callback;

	/**
	 * @param query
	 * @param callback
	 */
	public PreparedStatementEvaluator(String query, ICallback<Q> callback) {
		super(query);
		this.callback = callback;
	}

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.utils.database.ResultStatementEvaluator#createStatement(java.sql.Connection)
	 */
	@Override
	protected final PreparedStatement createStatement(Connection parameter) throws SQLException {
		PreparedStatement prepareStatement = parameter.prepareStatement(query);
		setParameters(prepareStatement);
		return prepareStatement;
	}

	/**
	 * @param stmt
	 * @throws SQLException
	 */
	protected abstract void setParameters(PreparedStatement stmt) throws SQLException;

}

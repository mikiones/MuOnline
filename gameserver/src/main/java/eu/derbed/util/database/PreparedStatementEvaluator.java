/**
 *
 */
package eu.derbed.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.derbed.util.ICallback;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public abstract class PreparedStatementEvaluator<Q> extends ResultStatementEvaluator<PreparedStatement, ResultSet> {

	protected final ICallback<Q> callback;

	/**
	 * @param query
	 * @param callback
	 */
	public PreparedStatementEvaluator(final String query, final ICallback<Q> callback) {
		super(query);
		this.callback = callback;
	}

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.utils.database.ResultStatementEvaluator#createStatement(java.sql.Connection)
	 */
	@Override
	protected final PreparedStatement createStatement(final Connection parameter) throws SQLException {
		final PreparedStatement prepareStatement = parameter.prepareStatement(query);
		setParameters(prepareStatement);
		return prepareStatement;
	}

	/**
	 * @param stmt
	 * @throws SQLException
	 */
	protected abstract void setParameters(PreparedStatement stmt) throws SQLException;

	/* (non-Javadoc)
	 * @see eu.derbed.util.database.ResultStatementEvaluator#evaluate(java.sql.Statement)
	 */
	@Override
	protected final ResultSet evaluate(final PreparedStatement stmt) throws SQLException {
		return stmt.executeQuery();
	}

}

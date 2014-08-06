/**
 *
 */
package eu.derbed.util.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.derbed.util.Callback;
import eu.derbed.util.CallbackAdapter;
import eu.derbed.util.CallbackException;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public abstract class PreparedStatementEvaluator<Q> extends ResultStatementEvaluator<ResultSet> {

	protected final Callback<Q> callback;

	/**
	 * @param query
	 */
	public PreparedStatementEvaluator(final String query) {
		this(query, null);
	}

	/**
	 * @param query
	 * @param callback
	 */
	public PreparedStatementEvaluator(final String query, final Callback<Q> callback) {
		super(query);
		this.callback = CallbackAdapter.maybeWrap(callback);
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

	/* (non-Javadoc)
	 * @see eu.derbed.util.ICallback#resultArrived(java.lang.Object)
	 */
	@Override
	public final void resultArrived(final ResultSet result) throws CallbackException, IOException {
		try {
			process(result);
		} catch (final SQLException ex) {
			throw new CallbackException("Failed to process resultset", ex);
		}
	}

	/**
	 * @param result
	 */
	protected abstract void process(final ResultSet result) throws SQLException, CallbackException, IOException;

}

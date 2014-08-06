/**
 *
 */
package eu.derbed.openmu.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.derbed.openmu.gs.muObjects.MuUser;
import eu.derbed.util.ICallback;
import eu.derbed.util.database.PreparedStatementEvaluator;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public class LoadUser extends PreparedStatementEvaluator<MuUser> {

	private final String name;

	/**
	 * @param name
	 * @param callback
	 */
	public LoadUser(final String name, final ICallback<MuUser> callback) {
		super("SELECT * FROM users where u_user=?", callback);
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see eu.derbed.util.ICallback#resultArrived(java.lang.Object)
	 */
	@Override
	public void resultArrived(final ResultSet rs) throws Throwable {
		MuUser user = null;
		if (rs.next()) {
			user = new MuUser(rs.getInt("u_id"), rs.getString("u_user"),
					rs.getString("u_pass"), rs.getInt("u_flag"),
					rs.getInt("u_ch_c"), rs.getString("u_vol_code"));
		}

		callback.resultArrived(user);
	}

	/* (non-Javadoc)
	 * @see eu.derbed.util.database.PreparedStatementEvaluator#setParameters(java.sql.PreparedStatement)
	 */
	@Override
	protected void setParameters(final PreparedStatement stmt) throws SQLException {
		stmt.setString(1, name);
	}

}

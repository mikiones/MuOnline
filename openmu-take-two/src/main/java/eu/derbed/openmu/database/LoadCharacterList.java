/**
 *
 */
package eu.derbed.openmu.database;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.derbed.openmu.gs.database.MuCharactersDb;
import eu.derbed.openmu.gs.muObjects.MuCharacterBase;
import eu.derbed.openmu.gs.muObjects.MuCharacterList;
import eu.derbed.openmu.gs.muObjects.MuCharacterWear;
import eu.derbed.util.CallbackException;
import eu.derbed.util.database.PreparedStatementEvaluator;

/**
 * @author Alexandru Bledea
 * @since Aug 6, 2014
 */
public final class LoadCharacterList extends PreparedStatementEvaluator<MuCharacterList> {

	private static final String QUERY = String.format("select * from %s where %s = ?", MuCharactersDb.CH_TAB, MuCharactersDb.US_ID);

	private final int userId;
	private final MuCharacterList list;

	/**
	 * @param userId
	 * @param list
	 */
	public LoadCharacterList(final int userId, final MuCharacterList list) {
		super(QUERY);
		this.list = list;
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see eu.derbed.util.database.PreparedStatementEvaluator#process(java.sql.ResultSet)
	 */
	@Override
	protected void process(final ResultSet rs) throws SQLException, CallbackException, IOException {
		int i = 0;
		while (rs.next()) {
			list.addNew(new MuCharacterBase(rs.getString(
					MuCharactersDb.CH_NAME).trim(), rs
					.getInt(MuCharactersDb.CH_LVL), rs
					.getInt(MuCharactersDb.CH_CLASS), i++,
					new MuCharacterWear()));
		}
		callback.resultArrived(list);
	}

/* (non-Javadoc)
 * @see eu.derbed.util.database.PreparedStatementEvaluator#setParameters(java.sql.PreparedStatement)
 */
	@Override
	protected void setParameters(final PreparedStatement stmt) throws SQLException {
		stmt.setInt(1, userId);
	}

}

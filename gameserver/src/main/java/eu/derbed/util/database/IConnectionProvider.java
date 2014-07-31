/**
 *
 */
package eu.derbed.util.database;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public interface IConnectionProvider {

	/**
	 * @return
	 */
	Connection getConnection() throws SQLException;

}

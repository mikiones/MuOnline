/**
 *
 */
package eu.derbed.openmu.gs.database;

import java.sql.Statement;

import eu.derbed.util.database.IConnectionProvider;
import eu.derbed.util.database.ResultStatementEvaluator;

/**
 * @author Alexandru Bledea
 * @since Aug 5, 2014
 */
public interface DataAccess extends IConnectionProvider {

	/**
	 * @param evaluator
	 */
	<S extends Statement, R> void execute(final ResultStatementEvaluator<S, R> evaluator);

}

/**
 *
 */
package eu.derbed.openmu.gs.database;

import java.io.IOException;

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
	<R> void execute(final ResultStatementEvaluator<R> evaluator) throws IOException;

}

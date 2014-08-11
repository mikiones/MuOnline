/**
 *
 */
package eu.derbed.util.database;

import java.io.IOException;

import eu.derbed.util.CallbackException;

/**
 * @author Alexandru Bledea
 * @since Aug 5, 2014
 */
public interface DataAccess extends IConnectionProvider {

	/**
	 * @param evaluator
	 * @throws CallbackException
	 * @throws IOException
	 */
	<R> void execute(final ResultStatementEvaluator<R> evaluator) throws CallbackException, IOException;

}

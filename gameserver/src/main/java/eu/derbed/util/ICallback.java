/**
 *
 */
package eu.derbed.util;

import java.io.IOException;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public interface ICallback<A> {

	/**
	 * @param result
	 * @throws CallbackException
	 * @throws IOException
	 */
	void resultArrived(A result) throws CallbackException, IOException;

}

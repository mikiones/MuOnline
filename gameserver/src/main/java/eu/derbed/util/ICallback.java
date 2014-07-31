/**
 *
 */
package eu.derbed.util;


/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public interface ICallback<A> {

	/**
	 * @param result
	 * @throws Throwable
	 */
	void resultArrived(A result) throws Throwable;

}

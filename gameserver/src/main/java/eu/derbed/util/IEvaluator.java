/**
 *
 */
package eu.derbed.util;


/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public interface IEvaluator<P, R> {

	/**
	 * @param parameter
	 * @return
	 * @throws Throwable
	 */
	R evaluate(P parameter) throws Throwable;

}

/**
 *
 */
package eu.derbed.util;

/**
 * @author Alexandru Bledea
 * @since Aug 6, 2014
 */
public class CallbackException extends Exception {

	/**
	 * @param message
	 * @param cause
	 */
	public CallbackException(final String message, final Throwable cause) {
		super(message, cause);
	}
}

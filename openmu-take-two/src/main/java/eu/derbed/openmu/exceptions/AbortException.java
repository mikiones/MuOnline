/**
 *
 */
package eu.derbed.openmu.exceptions;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public class AbortException extends Exception {

	/**
	 * @param message
	 * @param cause
	 */
	public AbortException(String message, Throwable cause) {
		super(message, cause);
	}

}

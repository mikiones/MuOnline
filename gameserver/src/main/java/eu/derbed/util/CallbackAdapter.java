/**
 *
 */
package eu.derbed.util;

import java.io.IOException;

/**
 * @author Alexandru Bledea
 * @since Aug 6, 2014
 */
public final class CallbackAdapter<A> implements Callback<A> {

	@SuppressWarnings ("rawtypes")
	private static Callback INSTANCE = new CallbackAdapter<>();

	/**
	 *
	 */
	private CallbackAdapter() {
	}

	/* (non-Javadoc)
	 * @see eu.derbed.util.ICallback#resultArrived(java.lang.Object)
	 */
	@Override
	public void resultArrived(final A result) throws CallbackException, IOException {
	}

	/**
	 * @param callback
	 * @return
	 */
	@SuppressWarnings ("unchecked")
	public static <A> Callback<A> maybeWrap(final Callback<A> callback) {
		if (null == callback) {
			return INSTANCE;
		}
		return callback;
	}
}

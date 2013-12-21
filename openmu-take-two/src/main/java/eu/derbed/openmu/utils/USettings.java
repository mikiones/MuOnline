/**
 *
 */
package eu.derbed.openmu.utils;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public final class USettings {

	/**
	 * @param possiblyNullObject
	 * @param defaultValue
	 * @return
	 */
	public static <G> G nvl(G possiblyNullObject, G defaultValue) {
		if (possiblyNullObject == null) {
			return defaultValue;
		}
		return possiblyNullObject;
	}

}

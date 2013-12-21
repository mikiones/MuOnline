/**
 *
 */
package eu.derbed.openmu.utils;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public final class UString {

	/**
	 * @param s
	 * @return
	 */
	public static boolean empty(String s) {
		return s == null || s.trim().isEmpty();
	}

	/**
	 * @param possiblyNullString
	 * @return
	 */
	public static String nvl(String possiblyNullString) {
		return USettings.nvl(possiblyNullString, "");
	}

	/**
	 * @param possiblyNullString
	 * @return
	 */
	public static String trimNvl(String possiblyNullString) {
		return nvl(possiblyNullString).trim();
	}

	/**
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equalTrim(String s1, String s2) {
		s1 = trimNvl(s1);
		s2 = trimNvl(s2);
		return s1.equals(s2);
	}

}

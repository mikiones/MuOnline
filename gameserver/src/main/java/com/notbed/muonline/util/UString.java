/**
 *
 */
package com.notbed.muonline.util;


/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public final class UString {

	/**
	 *
	 */
	private UString() {
	}

	/**
	 * @param s
	 * @return
	 */
	public static boolean empty(final String s) {
		return s == null || s.trim().isEmpty();
	}

	/**
	 * @param possiblyNullString
	 * @return
	 */
	public static String nvl(final String possiblyNullString) {
		if (null == possiblyNullString) {
			return "";
		}
		return possiblyNullString;
	}

	/**
	 * @param possiblyNullString
	 * @return
	 */
	public static String trimNvl(final String possiblyNullString) {
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

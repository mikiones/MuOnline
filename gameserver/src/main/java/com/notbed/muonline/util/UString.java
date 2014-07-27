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
	public static boolean equalTrim(final String s1, final String s2) {
		return trimNvl(s1).equals(trimNvl(s2));
	}

}

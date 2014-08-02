/**
 *
 */
package com.notbed.muonline.util;

import java.util.Locale;

import org.slf4j.Logger;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public final class UPacket {

	/**
	 *
	 */
	private UPacket() {
	}

	/**
	 * Convert byte array to String for that Hex editor look
	 * @param log
	 * @param data
	 */
	public static void logTransfer(final Logger log, final byte[] data) {
		logTransfer(log, data, null);
	}

	/**
	 * Convert byte array to String for that Hex editor look
	 *
	 * @param log
	 * @param data
	 * @param string
	 * @param forceUpperCase
	 */
	public static void logTransfer(final Logger log, final byte[] data, final String string) {
		final StringBuffer result = new StringBuffer();

		int counter = 0;
		final int len = data.length;
		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				if (!UString.empty(string)) {
					result.append(string);
					result.append(' ');
				}
				result.append(fillHex(i, 4) + ": ");
			}

			result.append(fillHex(data[i] & 0xff, 2) + " ");
			counter++;
			if (counter == 16) {
				result.append("   ");

				int charpoint = i - 15;
				for (int a = 0; a < 16; a++) {
					final int t1 = data[charpoint++];
					if (t1 > 0x1f && t1 < 0x80) {
						result.append((char) t1);
					} else {
						result.append('.');
					}
				}

				flush(log, result);
				counter = 0;
			}
		}

		final int rest = data.length % 16;
		if (rest > 0) {
			for (int i = 0; i < 17 - rest; i++) {
				result.append("   ");
			}

			int charpoint = data.length - rest;
			for (int a = 0; a < rest; a++) {
				final int t1 = data[charpoint++];
				if (t1 > 0x1f && t1 < 0x80) {
					result.append((char) t1);
				} else {
					result.append('.');
				}
			}
		}
		flush(log, result);
	}

	/**
	 * @param s
	 */
	private static void flush(final Logger log, final StringBuffer sb) {
		final String string = sb.toString().toLowerCase();
		log.debug(string);
		sb.setLength(0);
	}

	/**
	 * @param data
	 * @return
	 */
	public static String fillHex(final int data) {
		return fillHex(data, 2);
	}

	/**
	 * @param data
	 * @param digits
	 * @return
	 */
	public static String fillHex(final int data, final int digits) {
		final String number = Integer.toHexString(data).toUpperCase(Locale.ENGLISH);
		final int len = number.length();
		if (len >= digits) {
			return number;
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = len; i < digits; i++) {
			sb.append('0');
		}
		sb.append(number);
		return sb.toString();
	}

}

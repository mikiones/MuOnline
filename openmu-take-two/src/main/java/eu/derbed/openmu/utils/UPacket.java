/**
 *
 */
package eu.derbed.openmu.utils;

import static eu.derbed.openmu.utils.UString.empty;

import org.slf4j.Logger;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public final class UPacket {

	/**
	 * Convert byte array to String for that Hex editor look
	 *
	 * @param log
	 * @param data
	 */
	public static void logTransfer(Logger log, byte[] data) {
		logTransfer(log, data, null);
	}

	/**
	 * Convert byte array to String for that Hex editor look
	 *
	 * @param log
	 * @param data
	 * @param forceUpperCase
	 */
	public static void logTransfer(Logger log, byte[] data, boolean forceUpperCase) {
		logTransfer(log, data, null, forceUpperCase);
	}

	/**
	 * Convert byte array to String for that Hex editor look
	 *
	 * @param log
	 * @param data
	 * @param string
	 */
	public static void logTransfer(Logger log, byte[] data, String string) {
		logTransfer(log, data, string, false);
	}

	/**
	 * Convert byte array to String for that Hex editor look
	 *
	 * @param log
	 * @param data
	 * @param string
	 * @param forceUpperCase
	 */
	public static void logTransfer(Logger log, byte[] data, String string, boolean forceUpperCase) {
		final StringBuffer result = new StringBuffer();

		int counter = 0;
		int len = data.length;
		for (int i = 0; i < len; i++) {
			if (counter % 16 == 0) {
				if (!empty(string)) {
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

				flush(log, result, forceUpperCase);
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
		flush(log, result, forceUpperCase);
	}

	/**
	 * @param s
	 */
	private static void flush(Logger log, StringBuffer sb, boolean forceUpperCase) {
		String string = sb.toString();
		if (forceUpperCase) {
			string = string.toLowerCase();
		}
		log.debug(string);
		sb.setLength(0);
	}

	/**
	 * @param data
	 * @param digits
	 * @return
	 */
	private static String fillHex(int data, int digits) {
		String number = Integer.toHexString(data);

		for (int i = number.length(); i < digits; i++) {
			number = "0" + number;
		}

		return number;
	}


}

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
public final class PackageUtil {

	/**
	 * @param log
	 * @param data
	 * @param len
	 * @param string
	 */
	public static void logPackage(Logger log, byte[] data, String string) {
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

				debug(log, result);
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
		debug(log, result);
	}

	/**
	 * @param s
	 */
	private static void debug(Logger log, StringBuffer sb) {
		log.debug(sb.toString());
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

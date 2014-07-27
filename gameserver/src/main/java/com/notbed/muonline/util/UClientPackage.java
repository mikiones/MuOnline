package com.notbed.muonline.util;


/**
 * @author Alexandru Bledea
 * @since Jul 27, 2014
 */
public final class UClientPackage {

	/**
	 * what is this !?
	 */
	private static final byte[] d3key = { (byte) 0xfc, (byte) 0xcf, (byte) 0xab };

	/**
	 * 
	 */
	private UClientPackage() {
	}

	/**
	 * @param array
	 * @param start
	 * @param len
	 */
	public static void dec3bit(byte[] array, int start, int len) {
		for (int i = start; i < start + len; i++) {
			array[i] = (byte) (array[i] ^ d3key[(i - start) % 3]);
		}
	}

}

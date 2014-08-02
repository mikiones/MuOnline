package com.notbed.muonline.util;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru Bledea
 * @since Jul 27, 2014
 */
final class PackageUtil {

	private static Logger log = LoggerFactory.getLogger(PackageUtil.class);

	private static final byte[] KEY = {(byte) 0xe7, (byte) 0x6D, (byte) 0x3a,
		(byte) 0x89, (byte) 0xbc, (byte) 0xB2, (byte) 0x9f, (byte) 0x73,
		(byte) 0x23, (byte) 0xa8, (byte) 0xfe, (byte) 0xb6, (byte) 0x49,
		(byte) 0x5d, (byte) 0x39, (byte) 0x5d, (byte) 0x8a, (byte) 0xcb,
		(byte) 0x63, (byte) 0x8d, (byte) 0xea, (byte) 0x7d, (byte) 0x2b,
		(byte) 0x5f, (byte) 0xc3, (byte) 0xb1, (byte) 0xe9, (byte) 0x83,
		(byte) 0x29, (byte) 0x51, (byte) 0xe8, (byte) 0x56};

	/**
	 *
	 */
	private PackageUtil() {
	}

	/**
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static byte[] read(final InputStream inputStream) throws IOException {
		final int typ = inputStream.read();
		log.debug("Type is {}", UPacket.fillHex(typ));

		int lengthHi = 0;
		int lengthLo = 0;
		int length = 0;
		int pos = 0;

		switch (typ) {
			case 0xc1:
			case 0xc3: {

				length = inputStream.read();
				pos = 2;
				// System.out.println("c1c3:S"+length);
			}
			break;
			case 0xc2:
			case 0xc4: {

				lengthLo = inputStream.read();
				lengthHi = inputStream.read();
				pos = 3;
				length = lengthHi * 256 + lengthLo;
				// System.out.println("c2c4:S"+length);
			}
			break;
			default: {
				log.error("Unknown Header type {}", typ);
			}
		}

		if (length < 0) {
			log.debug("Client terminated connection");
			throw new IOException("EOF");
		}

		final byte[] incoming = new byte[length - pos];
		int receivedBytes = pos;
		int newBytes = 0;
		while (newBytes != -1 && receivedBytes < length) {
			newBytes = inputStream.read(incoming, 0, length - pos);
			receivedBytes = receivedBytes + newBytes;

		}

		if (incoming.length <= 0) {
			return null;
		}
		return dec(incoming, pos);
	}

	/**
	 * Decrypt byte array
	 * @param buf array to decrypt
	 * @param pos offset in arary
	 * @return unencrypted array
	 */
	private static byte[] dec(final byte[] buf, final int pos) {
		final byte[] a = new byte[buf.length];

		a[0] = buf[0];
		byte t = 0;

		int b = pos + 1;
		for (int i = 0; i < (buf.length - 1); i++, b++) {
			if (b >= 32) {
				b = 0;
			}
			t = dec1(buf[i], buf[i + 1], b);
			a[i + 1] = t;
		}

		return a;

	}

	/**
	 * @param a actual byte to decrypt
	 * @param n nextbyte
	 * @param pos position in array
	 * @return decrypted byte
	 */
	private static byte dec1(final byte a, final byte n, final int pos) {
		final byte t = (byte) (a ^ KEY[pos]);
		return (byte) (n ^ t);
	}

}

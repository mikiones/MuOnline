package eu.derbed.openmu.packetBase.crypt;

import static com.notbed.muonline.util.UPacket.logTransfer;

import org.slf4j.LoggerFactory;

public class crypDecryptC3C4Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final decC3test test = new decC3test();
		final byte[] crypt = { (byte) 0xc3, 0x18, (byte) 0x87, 0x19, 0x2c,
				(byte) 0xf6, 0x63, (byte) 0xc5, 0x32, (byte) 0xa8, 0x4c, 0x0c,
				0x39, 0x72, 0x0f, 0x54, (byte) 0xe6, 0x07, 0x66, 0x62,
				(byte) 0xb7, 0x70, 0x3d, 0x03 };
		final byte[] dec = new byte[(crypt[1] - 2) * 8 / 11];
		test.DecryptC3asServer(dec, crypt, crypt[1]);
		logTransfer(LoggerFactory.getLogger(crypDecryptC3C4Test.class), dec);
		// =c3 10 e5 0e 87 99 73 64 14 37 9f 60 d6 02 00 00

	}

}

package eu.derbed.openmu.gs.clientPackage;

import org.slf4j.Logger;

import com.notbed.muonline.util.UClientPackage;
import com.notbed.muonline.util.UPacket;

/**
 * @author Alexandru Bledea
 * @since Jul 27, 2014
 */
public final class DataDecrypter {

	public final byte[] data;

	protected int _off = 3; // skip packet type id

	/**
	 * @param data
	 */
	public DataDecrypter(byte[] data) {
		this.data = data;
	}

	public int readD() {
		int result = data[_off++] & 0xff;
		result |= data[_off++] << 8 & 0xff00;
		result |= data[_off++] << 0x10 & 0xff0000;
		result |= data[_off++] << 0x18 & 0xff000000;
		return result;
	}

	public int readC() {
		return data[_off++] & 0xff;
	}

	public int readH() {
		int result = data[_off++] & 0xff;
		result |= data[_off++] << 8 & 0xff00;
		return result;
	}

	public int readH(int of) {
		int result = data[of] & 0xff;
		result |= data[of + 1] << 8 & 0xff00;
		return result;
	}

	public double readF() {
		long result = data[_off++] & 0xff;
		result |= data[_off++] << 8 & 0xff00;
		result |= data[_off++] << 0x10 & 0xff0000;
		result |= data[_off++] << 0x18 & 0xff000000;
		result |= (long) data[_off++] << 0x20 & 0xff00000000l;
		result |= (long) data[_off++] << 0x28 & 0xff0000000000l;
		result |= (long) data[_off++] << 0x30 & 0xff000000000000l;
		result |= (long) data[_off++] << 0x38 & 0xff00000000000000l;
		return Double.longBitsToDouble(result);
	}

	public String readS(int from, int to) {
		String result = null;
		try {
			result = new String(data, from, to, "ISO-8859-1");
			// System.out.println(result+"\nl:"+result.length()+"\n"+result.indexOf(0x00));

			if (result.indexOf(0x00) != -1) {
				result = result.substring(0, result.indexOf(0x00));
			}
		} catch (final Exception e) {
			result = "";
		}

		_off += result.length() * 2 + 2;
		return result;
	}

	public byte[] readB(int length) {
		final byte[] result = new byte[length];
		System.arraycopy(data, _off, result, 0, length);
		_off += length;

		return result;
	}

	/**
	 * @param start
	 * @param len
	 */
	public void dec3bit(final int start, final int len) {
		UClientPackage.dec3bit(data, start, len);
	}

	/**
	 * @param log
	 */
	public void logTransfer(Logger log) {
		UPacket.logTransfer(log, data);
	}

}

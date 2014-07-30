package eu.derbed.openmu.gs.clientPackage;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Alexandru Bledea
 * @since Jul 30, 2014
 */
public class Data {

	private final Iterator<Byte> data;

	/**
	 * @param data
	 */
	public Data(byte[] data) {
		this.data = Arrays.asList(ArrayUtils.toObject(data)).iterator();
	}

	/**
	 * @return
	 */
	private byte next() {
		return data.next();
	}

	/**
	 * @return
	 */
	public int readC() {
		return next() & 0xff;
	}
}

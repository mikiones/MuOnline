/**
 *
 */
package com.notbed.muonline.util;

/**
 * @author Alexandru Bledea
 * @since Jul 30, 2014
 */
public interface PacketResolver<T> {

	/**
	 * @param data
	 * @return
	 */
	T resolvePacket(final Data data);

}

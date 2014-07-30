package com.notbed.muonline.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Alexandru Bledea
 * @since Jul 30, 2014
 */
final class HeaderUtil {

	/**
	 * 
	 */
	private HeaderUtil() {
	}

	/**
	 * @param object
	 * @return
	 */
	static String getName(final Object object) {
		if (object instanceof Map<?, ?>) {
			return getName(((Map<?, ?>) object).values());
		}
		final Class<? extends Object> class1 = object.getClass();
		return class1.getSimpleName() + getHeaderFormatted(getHeader(class1));
	}

	/**
	 * @param header
	 * @return
	 */
	private static String getHeaderFormatted(final int[] header) {
		if (0 == header.length) {
			return "";
		}
		final StringBuilder sb = new StringBuilder("[ ");
		for (final int element : header) {
			sb.append(UPacket.fillHex(element));
			sb.append(' ');
		}
		return sb.append(']').toString();
	}
	
	/**
	 * @param header
	 * @return
	 */
	static String getHeaderFormatted(final Object object) {
		return getHeaderFormatted(getHeader(object.getClass()));
	}

	/**
	 * @param entries
	 */
	static String getName(final Collection<?> entries) {
		final StringBuilder sb = new StringBuilder();
		final boolean moreThanOneElement = entries.size() > 1;
		if (moreThanOneElement) {
			sb.append('\'');
		}
		final Iterator<?> it = entries.iterator();
		boolean hasNext = it.hasNext();
		while (hasNext) {
			sb.append(getName(it.next()));
			hasNext = it.hasNext();
			if (hasNext) {
				sb.append(", ");
			}
		}
		if (moreThanOneElement) {
			sb.append('\'');
		}
		return sb.toString();
	}

	/**
	 * @param object
	 * @return
	 */
	static int[] getHeader(final Class<?> clasz) {
		if (!clasz.isAnnotationPresent(Header.class)) {
			return ArrayUtils.EMPTY_INT_ARRAY;
		}
		return clasz.getAnnotation(Header.class).value();
	}

}

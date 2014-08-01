package com.notbed.muonline.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

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
		if (null == object) {
			return "null"; // bad, hopefully never happens
		}
		if (object instanceof Collection<?>) {
			return getNameFromCollection((Collection<?>) object);
		}
		if (object instanceof Map<?, ?>) {
			return getName(((Map<?, ?>) object).values());
		}
		return getNameWithHeader(object);
	}

	/**
	 * @param header
	 * @return
	 */
	private static String getHeaderFormatted(final int[] header) {
		final StringBuilder sb = new StringBuilder("[ ");
		for (final int element : header) {
			sb.append(UPacket.fillHex(element));
			sb.append(' ');
		}
		return sb.append(']').toString();
	}

	/**
	 * @param object
	 * @return
	 */
	private static String getNameWithHeader(final Object object) {
		final Class<? extends Object> class1 = object.getClass();
		final String className = class1.getSimpleName();
		if (!hasHeader(class1)) {
			return className;
		}
		return className + getHeaderFormatted(getHeader(class1));
	}

	/**
	 * @param entries
	 */
	private static String getNameFromCollection(final Collection<?> entries) {
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
	 * @param clasz
	 * @return
	 */
	private static boolean hasHeader(final Class<?> clasz) {
		return clasz.isAnnotationPresent(Header.class);
	}

	/**
	 * @param object
	 * @return
	 */
	static int[] getHeader(final Class<?> clasz) {
		return clasz.getAnnotation(Header.class).value();
	}

}

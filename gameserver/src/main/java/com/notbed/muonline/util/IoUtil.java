package com.notbed.muonline.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru Bledea
 * @since Jul 26, 2014
 */
public final class IoUtil {

	private static final Logger log = LoggerFactory.getLogger(IoUtil.class);

	/**
	 * 
	 */
	private IoUtil() {
	}

	/**
	 * @param closeables
	 */
	public static void close(final Collection<Closeable> closeables) {
		if (null == closeables) {
			return;
		}
		for (final Closeable closeable : closeables) {
			if (null == closeable) {
				continue;
			}
			try {
				closeable.close();
			} catch (IOException ex) {
				log.error("Failed to close " + closeable.getClass(), ex);
			}
		}
	}

}

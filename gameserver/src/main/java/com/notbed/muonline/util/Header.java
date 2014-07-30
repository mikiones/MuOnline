/**
 *
 */
package com.notbed.muonline.util;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Contains the header metadata of a packet.
 *
 * @author Alexandru Bledea
 * @since Jul 30, 2014
 */
@Retention (RUNTIME)
@Target (TYPE)
public @interface Header {

	/**
	 * @return
	 */
	int[] value();
}

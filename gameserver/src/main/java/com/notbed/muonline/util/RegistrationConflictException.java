/**
 *
 */
package com.notbed.muonline.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * @author Alexandru Bledea
 * @since Jul 30, 2014
 */
class RegistrationConflictException extends RegistrationException {

	private static final String CONFLICT = "Attempting to register %s but header conflicts with %s!";

	private final Object failedObject;
	private final Collection<Object> reasons = new HashSet<>();

	/**
	 * @param message
	 */
	RegistrationConflictException(final Object failed, final Collection<?> reason) {
		super(String.format(CONFLICT, HeaderUtil.getName(failed), HeaderUtil.getName(reason)));
		this.failedObject = failed;
		reasons.addAll(reason);
	}

	/**
	 * @return
	 */
	public Object getFailedObject() {
		return failedObject;
	}

	/**
	 * @return
	 */
	public Collection<Object> getReasons() {
		return reasons;
	}

}

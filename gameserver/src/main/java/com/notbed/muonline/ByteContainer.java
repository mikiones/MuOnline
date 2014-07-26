package com.notbed.muonline;

import java.io.IOException;

/**
 * @author Alexandru Bledea
 * @since Jul 26, 2014
 */
public interface ByteContainer {

	/**
	 * @return
	 */
	byte[] getContent() throws IOException;

}

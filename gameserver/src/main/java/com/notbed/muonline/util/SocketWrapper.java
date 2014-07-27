package com.notbed.muonline.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/**
 * @author Alexandru Bledea
 * @since Jul 27, 2014
 */
public class SocketWrapper implements Closeable {

	private final Closeable socket;
	protected final InputStream in;
	protected final OutputStream out;

	/**
	 * @param socket
	 * @throws IOException
	 */
	public SocketWrapper(final Socket socket) throws IOException {
		this.socket = socket;
		in = socket.getInputStream();
		out = socket.getOutputStream();
	}

	/* (non-Javadoc)
	 * @see java.lang.AutoCloseable#close()
	 */
	@Override
	public final void close() throws IOException {
		IoUtil.close(Arrays.asList(in, out, socket));
	}

}

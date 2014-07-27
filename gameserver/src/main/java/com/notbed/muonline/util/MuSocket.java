package com.notbed.muonline.util;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru Bledea
 * @since Jul 27, 2014
 */
public final class MuSocket extends SocketWrapper {

	private static final Logger log = LoggerFactory.getLogger(MuSocket.class);

	/**
	 * @param socket
	 * @throws IOException
	 */
	public MuSocket(final Socket socket) throws IOException {
		super(socket);
	}

	/**
	 * get data packets and parse it to single packages if wos sended more then
	 * 1
	 *
	 * @return Decrypted xor algh package
	 * @throws java.io.IOException
	 */
	public byte[] getPacket() throws IOException {
		return PackageUtil.read(in);
	}

	/**
	 * This method will be called indirectly by several threads, to notify one
	 * client about all parallel events in the world. it has to be either
	 * synchronized like this, or it might be changed to stack packets in a
	 * outbound queue. advantage would be that the calling thread is independent
	 * of the amount of events that the target gets. if one target receives
	 * hundreds of events in parallel, all event sources will have to wait until
	 * the packets are send... for now, we use the direct communication
	 *
	 * @param data
	 * @throws IOException
	 */
	public void sendPacket(final byte[] data) throws IOException {
		synchronized (this) {
			// this is time consuming.. only enable for debugging
			UPacket.logTransfer(log, data, "[S->C]");
			out.write(data, 0, data.length);
			out.flush();
		}
	}

	/**
	 * @param byteContainer
	 * @throws IOException
	 */
	public void sendPacket(final ByteContainer byteContainer) throws IOException{
		sendPacket(byteContainer.getContent());
	}

}

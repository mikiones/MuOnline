package eu.derbed.openmu.gs.client;

import com.notbed.muonline.util.PacketResolver;
import com.notbed.muonline.util.PacketResolverImpl;
import com.notbed.muonline.util.RegistrationException;

import eu.derbed.openmu.gs.clientPackage.ClientPackage;

/**
 * @author Alexandru Bledea
 * @since Jul 31, 2014
 */
public class ClientPacketResolver implements PacketResolver<ClientPackage> {

	private final PacketResolverImpl<ClientPackage> resolver = new PacketResolverImpl<>(ClientPackage.class);

	/**
	 * @throws RegistrationException
	 */
	public ClientPacketResolver() throws RegistrationException {
		resolver.register(new CLoginPacket());
	}

	/* (non-Javadoc)
	 * @see com.notbed.muonline.util.PacketResolver#resolvePacket(byte[])
	 */
	@Override
	public ClientPackage resolvePacket(final byte[] data) {
		return resolver.resolvePacket(data);
	}

}

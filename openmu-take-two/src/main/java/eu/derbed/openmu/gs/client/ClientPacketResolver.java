package eu.derbed.openmu.gs.client;

import com.notbed.muonline.util.PacketResolver;
import com.notbed.muonline.util.PacketResolverImpl;
import com.notbed.muonline.util.RegistrationException;

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
//		maybe load this from xml?
		resolver.register(new CLoginPacket());
		resolver.register(new CSelectCharacterOrExitRequest());
		resolver.register(new CPublicMsg());
		resolver.register(new CChangeDirectoryOrStatus());
		resolver.register(new CMoveCharacter());
		resolver.register(new CAttackOnId());
		resolver.register(new CBuyItemRequest());
		resolver.register(new CItemPickUpRequest());
		resolver.register(new CAddFrendRequest());
		resolver.register(new CEnterInGateRequest());
		resolver.register(new CA0Request());
		resolver.register(new CItemDropFromInwentoryRequest());
		resolver.register(new CMoveItemRequest());
		resolver.register(new CNpcRunRequest());
		resolver.register(new CAddLvlPointsRequest());
		resolver.register(new CCharacterListRequest());
		resolver.register(new CNewCharacterRequest());
		resolver.register(new CDeleteChar());
		resolver.register(new CSelectedCharacterEnterRequest());
		resolver.register(new CClientSettingsSaveRequest());
		resolver.register(new CItemUseRequest());
	}

	/* (non-Javadoc)
	 * @see com.notbed.muonline.util.PacketResolver#resolvePacket(byte[])
	 */
	@Override
	public ClientPackage resolvePacket(final byte[] data) {
		return resolver.resolvePacket(data);
	}

}

/**
 *	Some classes don't need a type
 */
package eu.derbed.openmu.gs.serverPackets;

/**
 * @author Alexandru Bledea
 * @since Dec 21, 2013
 */
public abstract class ServerBasePacketNoType extends ServerBasePacket {

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.base.Packet#getType()
	 */
	@Override
	public final String getType() {
		return null;
	}

}

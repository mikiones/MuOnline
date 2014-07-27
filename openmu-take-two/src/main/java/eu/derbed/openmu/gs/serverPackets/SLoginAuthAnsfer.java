package eu.derbed.openmu.gs.serverPackets;

import java.io.IOException;

public class SLoginAuthAnsfer extends ServerBasePacketNoType {

	private byte f = 0; // flafa

	public static final int PA_InvaltPassword = 0x00;
	public static final int PA_PassOK = 0x01;
	public static final int PA_AccInvalt = 0x02;
	public static final int PA_AccAlredyConnected = 0x03;
	public static final int PA_ServerIsFull = 0x04;
	public static final int PA_AccBlacked = 0x05;
	public static final int PA_NewVersionOfGamereq = 0x06;
	public static final int PA_NoChargeInfo = 0x09;

	public SLoginAuthAnsfer(byte fl) {
		f = fl;
	}

	/* (non-Javadoc)
	 * @see eu.derbed.openmu.gs.serverPackets.ServerPacketModel#getContent()
	 */
	@Override
	public byte[] getContent() throws IOException {
		mC1Header(0xf1, 0x01, 0x05);
		_bao.write(f);
		_bao.write(0x00);
		return _bao.toByteArray();
	}

}

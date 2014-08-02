package eu.derbed.openmu.gs.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.Header;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.muObjects.MuPcInstance;
import eu.derbed.openmu.gs.serverPackets.SInwentoryList;
import eu.derbed.openmu.gs.serverPackets.SLiveStats;
import eu.derbed.openmu.gs.serverPackets.SManaStaminaStats;
import eu.derbed.openmu.gs.serverPackets.SSelectedCharacterEnterAnsfer;

@Header (0xa0)
class CA0Request implements ClientPackage {

	private static final Logger log = LoggerFactory.getLogger(CA0Request.class);

	// private byte[] _itemPack={(byte)
	// 0xc4,0x00,0x06,(byte)
	// 0xf3,0x10,0x00,0x//00};

	// private byte[] _a0ansferPack={(byte) 0xc1,0x05,(byte) 0xa0,0x01,(byte)
	// 0xaa,0x00};
	// private byte[] _learnSkill={(byte) 0xc1 ,0x08 ,(byte) 0xf3 ,0x11 ,(byte)
	// 0xfe ,0x01 ,0x04 ,0x00};

	@Override
	public void process(final byte[] data, final ClientThread client) throws java.io.IOException {
//		final SSkillList _skilPack = new SSkillList();

		final MuPcInstance cha = client.getActiveChar();
		cha.setNetConnection(client.getConnection());

		final SSelectedCharacterEnterAnsfer _characterStatsPack = new SSelectedCharacterEnterAnsfer();
		final SManaStaminaStats _manaStaminaMaximalsPack = new SManaStaminaStats(
				SManaStaminaStats._UPDATE_MAX);
		final SLiveStats _liveMaximalsPack = new SLiveStats(
				SLiveStats._UPDATE_MAX);

		setCharacterStats(_characterStatsPack, cha);
		setSkillList();
		setManaStaminaMaximals(_manaStaminaMaximalsPack, cha);
		setLiveMaximals(_liveMaximalsPack, cha);

		client.getConnection().sendPacket(
				new byte[]{(byte) 0xc1, (byte) 0x05, (byte) 0xa0,
						(byte) 0x01, (byte) 0xaa});
		client.getConnection().sendPacket(_characterStatsPack);
		client.getConnection().sendPacket(
				new SInwentoryList(client.getActiveChar().getInventory()));
		client.getConnection().sendPacket(_liveMaximalsPack);
		client.getConnection().sendPacket(_manaStaminaMaximalsPack);

		// _client.getConnection().sendPacket(_itemPack);
		// _client.getConnection().sendPacket(_inwentoryPack);
		// _client.getConnection().sendPacket(_skilPack);
		// Must add it, even it if already exists, so meeting packets are
		// sent
		// Previously sent meeting packets are disregarded by the client
		client.getActiveChar().getCurrentWorldRegion()
		.addObject(client.getActiveChar());
		// _client.getConnection().sendPacket(_a0ansferPack);
		// _client.getConnection().sendPacket(_learnSkill);

	}

	/**
	 * @param _characterStatsPack
	 * @param cha
	 */
	private static void setCharacterStats(final SSelectedCharacterEnterAnsfer _characterStatsPack, final MuPcInstance cha) {

		_characterStatsPack.setStatistic(cha.getStr(), cha.getAgi(),
				cha.getVit(), cha.getEne(), cha.getCom());
		_characterStatsPack.setExperance(cha.getExp(), cha.getExpOnNewLvl(),
				cha.getLp());
		_characterStatsPack.setPosicion(cha.getM(), cha.getX(), cha.getY(),
				cha.getStatus());
		_characterStatsPack.setZen(cha.getZen());
		log.debug("Stats loaded");
	}

	/**
	 * @param _liveMaximalsPack
	 * @param cha
	 */
	private static void setLiveMaximals(final SLiveStats _liveMaximalsPack, final MuPcInstance cha) {
		_liveMaximalsPack.setLive(cha.getMaxHp());
	}

	/**
	 * @param _manaStaminaMaximalsPack
	 * @param cha
	 */
	private static void setManaStaminaMaximals(final SManaStaminaStats _manaStaminaMaximalsPack, final MuPcInstance cha) {
		_manaStaminaMaximalsPack.setMana(cha.getMaxMp());
		_manaStaminaMaximalsPack.setStamina(cha.getMaxSp());
	}

	/**
	 *
	 */
	private static void setSkillList() {
		log.debug("Skills loaded (no they are not!)");
	}

}

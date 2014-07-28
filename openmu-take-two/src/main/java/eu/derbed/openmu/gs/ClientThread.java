package eu.derbed.openmu.gs;

import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.notbed.muonline.util.MuSocket;

import eu.derbed.openmu.exceptions.AbortException;
import eu.derbed.openmu.gs.database.MuCharacterListDB;
import eu.derbed.openmu.gs.database.MuCharactersDb;
import eu.derbed.openmu.gs.database.MuDataBaseFactory;
import eu.derbed.openmu.gs.muObjects.MuCharacterBase;
import eu.derbed.openmu.gs.muObjects.MuCharacterInventory;
import eu.derbed.openmu.gs.muObjects.MuCharacterList;
import eu.derbed.openmu.gs.muObjects.MuCharacterWear;
import eu.derbed.openmu.gs.muObjects.MuClientSettings;
import eu.derbed.openmu.gs.muObjects.MuNpcInstance;
import eu.derbed.openmu.gs.muObjects.MuPcInstance;
import eu.derbed.openmu.gs.muObjects.MuUser;
import eu.derbed.openmu.gs.muObjects.MuWorld;
import eu.derbed.openmu.gs.serverPackets.SHello;


/**
 * This class To generaly replesent all interface from connection to clases in
 * server small core
 *
 * @author Mikione
 */
public class ClientThread extends Thread {

	private final Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * User and login data
	 */
	private String _loginName;
	private MuUser user = null;
	// propably never used
	private int _idConection;
	/**
	 * character datas
	 */
	// stats
	private MuPcInstance _activeChar;
	// list of charactwers
	private final MuCharacterList ChList = new MuCharacterList();
	// settings actual played character
	private MuClientSettings _clientSettings = null;
	/**
	 * world
	 */
	private final MuWorld _world;
	/**
	 * Actual Took with NPC
	 */
	private MuNpcInstance _activeNpc = null;

	/**
	 * set active npc
	 *
	 * @param i
	 */
	public void setActiveNpc(MuNpcInstance i) {
		_activeNpc = i;
	}

	/**
	 * get active npc if null then no be eny yet and until we dont click to npc
	 * then be last one as active
	 *
	 * @return active bpc if null there nobe jet any
	 */
	public MuNpcInstance getActiveNpc() {
		return _activeNpc;
	}

//	private final byte[] _cryptkey = { (byte) 0x94, (byte) 0x35, (byte) 0x00,
//			(byte) 0x00, (byte) 0xa1, (byte) 0x6c, (byte) 0x54, (byte) 0x87 // these
//	};
	private final MuSocket _connection;
	private final PacketHandler _handler;

	/**
	 * constructor
	 *
	 * @param client
	 *            Socket to connect to client
	 * @throws java.io.IOException
	 */
	public ClientThread(Socket client) throws IOException {

		_connection = new MuSocket(client);
		_handler = new PacketHandler(this);
		_world = MuWorld.getInstance();

		start();
	}

	/**
	 *
	 * @return character list of login user
	 */
	public MuCharacterList getChList() {
		return ChList;
	}

	/**
	 * return Connection to client If null then lost it
	 *
	 * @return
	 */
	public MuSocket getConnection() {
		return _connection;
	}

	/**
	 *
	 * @return login name
	 */
	public String getLoginName() {
		return _loginName;
	}

	/**
	 * get user data
	 *
	 * @return
	 */
	public MuUser getUser() {
		return user;
	}

	/**
	 * Read Character ListFrom Database
	 *
	 * @Isuase 1 read wear set bits as well
	 * @isuase 2 move to database section
	 * @throws java.io.IOException
	 */
	public void readCharacterList() throws IOException {

		final int ilosc_p = user.getCh_c();
		final int id = user.getId();

		try (Connection con = MuDataBaseFactory.getInstance().getConnection()) {
//			close
			final PreparedStatement statement = con
					.prepareStatement("select * from " + MuCharactersDb.CH_TAB
							+ " where " + MuCharactersDb.US_ID + " =?");
			statement.setInt(1, id);
//			close
			final ResultSet rset = statement.executeQuery();
			for (int i = 0; i < ilosc_p; i++) {
				if (rset.next()) {
					ChList.addNew(new MuCharacterBase(rset.getString(
							MuCharactersDb.CH_NAME).trim(), rset
							.getInt(MuCharactersDb.CH_LVL), rset
							.getInt(MuCharactersDb.CH_CLASS), i,
							new MuCharacterWear()));
				}
			}
			con.close(); // properly close
		} catch (final SQLException e) {
			throw new IOException("Failed to load character list", e);
		}
		ChList.noNeedRead();
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(MuUser user) {
		this.user = user;
	}

	@Override
	public void run() {

		final IdFactory _id = IdFactory.getInstance();
		_idConection = _id.newId();
		log.info("Client Thread Started ...");

		try {
			_connection.sendPacket(new SHello(_idConection, "09928"));
			boolean _while = true;
			while (_while) {

				// System.out.println("czekam na odpowiedz");
				final byte[] decrypt = _connection.getPacket();
				if (decrypt != null) {
					_handler.handlePacket(decrypt);
				} else {
					_while = false;
				}
				// System.out.println("odebralem");

			}
		} catch (Throwable t) {
			String username = user == null ? "" : user.getUser();
			String message = String.format("Client thread [%s] encountered an error", username.trim());
			log.error(message, t);
		} finally {
			try {
				if (_activeChar != null) // this should only happen on
				// connection loss
				{
					// notify the world about our disconnect
					_activeChar.deleteMe();

					try {
						saveCharToDataBase(_activeChar);
					} catch (final Exception e2) {
						// ignore any problems here
					}
				}

				_connection.close();
			} catch (final Exception e1) {
			} finally {
				try {
					_connection.close();
				} catch (IOException e) {
					log.error("Failed to close client", e);
				}
				// remove the account
			}
			// remove the account
		}
		IdFactory.getInstance().deleteId(_idConection);

		String username = user == null ? "" : user.getUser();
		log.info("Client Thread [{}] stopped.", username.trim());
	}

	public int getIdConnection() {
		return _idConection;
	}

	/**
	 * save all things after close connection or change character
	 *
	 * @Isuasse 1 storge inwentory, skills, settings
	 * @param char1
	 */
	private void saveCharToDataBase(MuPcInstance char1) {
		storeChar(char1);
		// storeInventory(cha);
		// storeSkills(cha);
		// storeShortcuts(cha);
		// storeWarehouse(cha);
		// IdFactory.getInstance().saveCurrentState();

	}

	/**
	 * Save Character Stats in DAtabase
	 *
	 * @ISuase1 Critical Need implementation
	 * @param char1
	 */
	private void storeChar(MuPcInstance char1) {
//		AAA actually store character
		log.info("Character data stored in database");

	}

	/**
	 * @author Marcel Stores a new character in database.
	 * @param user_id
	 * @param char_name
	 * @param char_class
	 * @return boolean
	 */
	public boolean storeNewChar(int id, String name, int clas) {
		final MuCharacterListDB cdb = new MuCharacterListDB(id);
		return cdb.addNewCharacter(name, clas);
	}

	/**
	 * set login name
	 *
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		_loginName = loginName;
	}

	/**
	 * @todo add restore character wear look
	 * @Isase 1 Move to database section restore character from Db using name of
	 *        character
	 * @param name
	 *            of character to restore
	 * @return new pcinstane obiect
	 */
	private MuPcInstance restoreChar(String name) {
		final MuPcInstance oldChar = new MuPcInstance();
		oldChar.setNetConnection(getConnection());
		try {
			java.sql.Connection con = null;
			con = MuDataBaseFactory.getInstance().getConnection();
			final PreparedStatement statement = con
					.prepareStatement("select*  from " + MuCharactersDb.CH_TAB
							+ " where " + MuCharactersDb.CH_NAME + " = '"
							+ name + "' ");
			final ResultSet rset = statement.executeQuery();
			while (rset.next()) {
				// oldChar.setDbId(rset.getInt("ch_id"));
				oldChar.setLvl(rset.getInt(MuCharactersDb.CH_LP));
				oldChar.setName(rset.getString(MuCharactersDb.CH_NAME));
				oldChar.setCom(rset.getInt(MuCharactersDb.CH_COM));
				oldChar.setStr(rset.getInt(MuCharactersDb.CH_STR));
				oldChar.setAgi(rset.getInt(MuCharactersDb.CH_AGI));
				oldChar.setVit(rset.getInt(MuCharactersDb.CH_VIT));
				oldChar.setEne(rset.getInt(MuCharactersDb.CH_ENR));
				oldChar.setLP(rset.getInt(MuCharactersDb.CH_LP));
				oldChar.setClas(rset.getInt(MuCharactersDb.CH_CLASS));
				oldChar.setM((byte) rset.getInt(MuCharactersDb.CH_POS_M));
				oldChar.setX(rset.getInt(MuCharactersDb.CH_POS_X));
				oldChar.setY(rset.getInt(MuCharactersDb.CH_POS_Y));
				oldChar.setObiectId((short) _idConection);
				oldChar.SetHpMpSp();
				// domekind of read muweat
				oldChar.SetWearLook(new MuCharacterWear());
			}
			System.out.println("x" + oldChar.getX() + "y" + oldChar.getY());
			oldChar.setCurrentWorldRegion(MuWorld.getInstance().getMap(
					oldChar.getM()));
			// MuWorld.getInstance().storeObject(oldChar);
			rset.close();
			statement.close();
			con.close();
		} catch (final Exception e) {
			log.error("Cannot restore charatre data from database!!", e);
		}
		log.info("Character restored from database succesfuly");
		;
		return oldChar;
	}

	/**
	 * Get all things-need-to-play from database
	 *
	 * @isuase 1 what when selected name is not in user login character list
	 *         -ToCHeck
	 * @param selected
	 *            name of selected character
	 * @return
	 */
	public MuPcInstance loadCharFromDisk(String selected) {
		MuPcInstance character = new MuPcInstance();
		character.setNetConnection(_connection);

		character = restoreChar(selected);

		if (character != null) {
			restoreInventory(character);
			restoreSkills(character);
			restoreShortCuts(character);
			restoreWarehouse(character);
			// if (character.getClanId() != 0)
		} else {
			log.error("Cannot restore '{}' character from database!!", selected);
		}

		// setCharacter(character);
		return character;
	}

	/**
	 * Restore Rerhause from DB
	 *
	 * @isuase should be after get charater list Warehause is thissame for all
	 *         character !!
	 * @Isuase Critic: Implementation
	 * @param character
	 */
	private void restoreWarehouse(MuPcInstance character) {
		// TODO Auto-generated method stub
	}

	/**
	 * Resttorre settings from Database
	 *
	 * @Isuase 1 Now get default value Must to get data from DB
	 * @param character
	 */
	private void restoreShortCuts(MuPcInstance character) {
		_clientSettings = new MuClientSettings();
		_clientSettings.LoadDefault();

	}

	/**
	 * Restore Skills List from DB
	 *
	 * @ISUASE 1 CRITIC: Imlementation
	 * @param character
	 */
	private void restoreSkills(MuPcInstance character) {
		// TODO Auto-generated method stub
	}

	/**
	 * Restore Inwentory List from DB
	 *
	 * @ISUASE 1 CRITIC: Implementation
	 * @param character
	 */
	private void restoreInventory(MuPcInstance character) {
		final MuCharacterInventory inw = new MuCharacterInventory();
		// inw.storeItem(new MuStoreableItem(inw.InventoryWindow, 1, new
		// MuItemHex().MakeItem(1, 1, 20, 1, 1, 1, false, false)));
		character.set_inventory(inw);

	}

	/**
	 * Set active character
	 *
	 * @param cha
	 *            character chose in character list to play
	 */
	public void setActiveChar(MuPcInstance cha) {
		_activeChar = cha;
		if (cha != null) {
			// we store the connection in the player object so that external
			// events can directly send events to the players client
			// might be changed later to use a central event management and
			// distribution system
			_activeChar.setNetConnection(_connection);

			// update world data

			// _world.storeObject(_activeChar);
		}
	}

	/**
	 * return atual played character
	 *
	 * @return
	 */
	public MuPcInstance getActiveChar() {
		return _activeChar;
	}

	/**
	 * get client settings actual played character
	 *
	 * @return
	 */
	public MuClientSettings getClientSettings() {

		return _clientSettings;
	}

	/**
	 * Store Client Settings after logout or change character or also HardDc
	 *
	 * @ISUASE 1 CRITIC: Implementation
	 */
	public void storeClientSettingsInDb() {
//		AAA implement this

		log.info("Client Settings saved in DB!");

	}
}

package eu.derbed.openmu.cs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import eu.derbed.openmu.cs.codec.data.ServerEntry;

public class ServerList {
	private final List<ServerEntry> gsArray = new ArrayList<ServerEntry>();

	/**
	 * 
	 * @param pos
	 * @param flag
	 * @return {@link ServerEntry} gs allocated with speciefed pos & flag
	 * @throws ServerEntryNotFound
	 *             if requared gs not registred
	 */
	public ServerEntry get(int pos, int flag) throws ServerEntryNotFound {

		for (ServerEntry e : gsArray) {
			if (e.pos == pos || e.flag == flag)
				return e;
		}

		throw new ServerEntryNotFound(pos, flag);

	}

	/**
	 * registre GS with CS
	 * @param serv
	 */
	public void addServer(ServerEntry serv) {
			System.out.println("Add:" + serv);
			gsArray.add(serv);
	}

	/**
	 * @param cs
	 */
	public void load(final Properties cs) {
		HashSet<String> names = new HashSet<String>();
		for (Entry en : cs.entrySet()) {
			// System.out.println(en);
			String key = (String) en.getKey();
			String val = (String) en.getValue();
			if (key.startsWith("gs.")) {
				String name = key.substring(3, key.indexOf(".", 4));
				names.add(name);
			}
		}

		for (String s : names) {
			String propertyPref = "gs." + s;
			ServerEntry t = new ServerEntry(s, cs.getProperty(propertyPref
					+ ".Host"), Integer.parseInt(cs.getProperty(propertyPref
					+ ".Port")), (byte) (Integer.parseInt(cs
					.getProperty(propertyPref + ".Nb"))),
					(byte) (Integer.parseInt(cs.getProperty(propertyPref
							+ ".Flag"))), (byte) 0);
			addServer(t);
		}

	}

	public List<ServerEntry> asArrayList() {
		return gsArray;
	}
}

package eu.derbed.openmu.gs.muObjects;

public class MuUser {

	/**
	 *
	 */
	private final String user;
	private final String pass;
	private int id;
	int flag;
	private final String p_code; // code to delete chars

	public MuUser() {
		user = "";
		pass = "";
		flag = 0;
		p_code = "";
	}

	public MuUser(final int u_id, final String us, final String pa, final int f,
			final String ch_code) {
		id = u_id;
		user = us;
		flag = f;
		pass = pa;
		p_code = ch_code;
	}

	public int getFlag() {
		return flag;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public String getPass() {
		return pass;
	}

	public String getUser() {
		return user;
	}

	public String getChCode() {
		return p_code;
	}
}

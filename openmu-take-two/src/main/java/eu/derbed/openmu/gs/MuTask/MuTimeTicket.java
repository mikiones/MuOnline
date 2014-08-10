/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.derbed.openmu.gs.MuTask;

import eu.derbed.openmu.utils.MuTimeCotroler;

/**
 * The TimeTicket class
 *
 * @author Miki i Linka
 * @version
 */
public class MuTimeTicket {

	private final int tick;
	private final MuTimeCotroler controller = new MuTimeCotroler();

	/**
	 * set ticket to s sec
	 *
	 * @param s
	 *            how long ticket is ok
	 */
	public MuTimeTicket(final int s) {
		tick = controller.getGameTime() + s;
	}

	/**
	 *
	 * @return true when ticket expired
	 */
	public boolean TicketEnd() {
		return tick < controller.getGameTime();
	}

	/**
	 *
	 * @return time when ticket expired
	 */
	public int getTime() {
		return tick;
	}

	/**
	 * compare 2 tickets
	 *
	 * @param a
	 *            1'st ticket
	 * @param b
	 *            2'nd ticket
	 * @return true when 1'st ticket expired before secend
	 */
	public static boolean compareTicket(final MuTimeTicket a, final MuTimeTicket b) {
		return a.getTime() < b.getTime();
	}

}

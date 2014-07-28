package eu.derbed.openmu.gs.clientPackage;

import com.notbed.muonline.util.DataDecrypter;

import eu.derbed.openmu.gs.ClientThread;
import eu.derbed.openmu.gs.serverPackets.SAddLvlPointsAnsfer;

public class CAddLvlPointsRequest extends SimpleClientPackage {

	@Override
	protected void process(DataDecrypter dataDecrypter, ClientThread client) {
		byte _whichPointToAdd = dataDecrypter.data[2];
		final boolean czy = client.getActiveChar().getLp() > 0;
		if (czy) {
			switch (_whichPointToAdd) {
			case 0x00: {
				System.out.println("Add 1 pont to str");

				client.getActiveChar().incStr();
				client.getActiveChar().decLP();
			}
				break;
			case 0x01: {
				System.out.println("Add 1 pont to agi");
				client.getActiveChar().incAgi();
				client.getActiveChar().decLP();
			}
				break;
			case 0x02: {
				System.out.println("Add 1 pont to vit");
				client.getActiveChar().incVit();
				client.getActiveChar().decLP();
			}
				break;
			case 0x03: {
				System.out.println("Add 1 pont to ene");
				client.getActiveChar().incEne();
				client.getActiveChar().decLP();
			}
				break;
			default:
				break;
			}
			// TODO add reading the new mana and stamina
			client.getActiveChar().sendPacket(
					new SAddLvlPointsAnsfer(_whichPointToAdd, true, 15, 15));

		} else {
			client.getActiveChar().sendPacket(
					new SAddLvlPointsAnsfer((byte) 0x00, false, 10, 10));
		}

	}

}

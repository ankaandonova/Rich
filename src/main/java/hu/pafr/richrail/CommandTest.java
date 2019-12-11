package hu.pafr.richrail;

import hu.pafr.richrail.database.Database;
import hu.pafr.richrail.spoor.Spoor;

public class CommandTest {
	public static void main(String[] args) throws CloneNotSupportedException {
		Spoor spoor = new Spoor(1, 0.0);
		Database database = Database.getDatabase();
		database.getLocomotiefFromSpoor(spoor);
		
		database.getWagonsFromLocomotief(spoor.getLocomotiefen().get(0));
		
		//RichRailCli.voerCommandUit("new train id");	
	}
}

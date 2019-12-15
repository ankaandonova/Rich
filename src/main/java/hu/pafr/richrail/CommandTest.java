package hu.pafr.richrail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.database.LocomotiefDao;
import hu.pafr.richrail.database.LocomotiefDaoImpl;
import hu.pafr.richrail.database.SpoorDao;
import hu.pafr.richrail.database.SpoorDaoImpl;
import hu.pafr.richrail.database.WagonDao;
import hu.pafr.richrail.database.WagonDaoImpl;
import hu.pafr.richrail.domein.locomotief.Builder;
import hu.pafr.richrail.domein.locomotief.Locomotief;
import hu.pafr.richrail.domein.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.domein.spoor.Spoor;
import hu.pafr.richrail.domein.wagon.Factory;
import hu.pafr.richrail.domein.wagon.Wagon;
import hu.pafr.richrail.domein.wagon.WagonFactory;
import hu.pafr.richrail.parser.RichRailCli;
import hu.pafr.richrail.parser.RichRailUitvoerListener;

public class CommandTest {
	public static void main(String[] args) throws CloneNotSupportedException, FileNotFoundException {

		RichRailUitvoerListener richrail;
		// newcommand
		richrail = RichRailCli.voerCommandUit("new train tr2");
		System.out.println(richrail.getMessage());
		Locomotief tr2 = (Locomotief) richrail.getObject();
		System.out.println(tr2.getNaam());
		System.out.println("");

		richrail = RichRailCli.voerCommandUit("new wagon w1");
		Wagon w1 = (Wagon) richrail.getObject();
		System.out.println(richrail.getMessage());
		System.out.println(w1.getNaam());
		System.out.println("");

		// remove command
		richrail = RichRailCli.voerCommandUit("remove w1 from tr2");
		Locomotief l1 = (Locomotief) richrail.getObject();
		System.out.println(richrail.getMessage());
		System.out.println(l1.getNaam());
		System.out.println("");

//		//addcommand 
		richrail =RichRailCli.voerCommandUit("add w1 to l1");
		l1 = (Locomotief) richrail.getObject();
		System.out.println(richrail.getMessage());
		System.out.println(w1.getNaam());
		System.out.println("");
		
//		//getcommand 
		richrail =RichRailCli.voerCommandUit("getnumseats train l1");
		System.out.println(richrail.getMessage());
		System.out.println("");
		
		richrail =RichRailCli.voerCommandUit("getnumseats wagon w1");
		System.out.println(richrail.getMessage());
		System.out.println("");
		
//		//delete command
		richrail =RichRailCli.voerCommandUit("delete wagon w1");
		System.out.println(richrail.getMessage());
		System.out.println("");
		
		richrail =RichRailCli.voerCommandUit("delete train t1");
		System.out.println(richrail.getMessage());
		System.out.println("");
		
		// ================================================================================
		// ================================================================================
		// ================================================================================
		// ================================================================================
		// ================================================================================
		// ================================================================================
		// ================================================================================
		// ================================================================================
		// ================================================================================
		richrail =RichRailCli.voerCommandUit("new wagon wg2 numseats 15");
		w1 = (Wagon) richrail.getObject();
		System.out.println(richrail.getMessage());
		System.out.println(w1.getNaam());
		System.out.println("");
	}
}

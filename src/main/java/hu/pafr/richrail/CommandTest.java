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
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.parser.RichRailCli;
import hu.pafr.richrail.parser.RichRailUitvoerListener;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class CommandTest {
	public static void main(String[] args) throws CloneNotSupportedException, FileNotFoundException {
		
		Builder builder = new LocomotiefBuilder();
		builder.setNaam("trein121");
		builder.setVertrekPunt("utrecht");
		builder.setEindBestemming("amsterdam");
		builder.setType_moter("bezsine");
		builder.setHoogte(1.8);
		builder.setLengte(50.0);
		builder.setGps(false);
		builder.setMax_snelheid(180.0);
		builder.setStoelen(5);
		Locomotief locomotief = builder.build();
		
		Factory factory = new WagonFactory();
		Wagon wagon = factory.createWagon("wag1on1", 98, 0);
		locomotief.setWagon(wagon);
		
		Spoor spoor = new Spoor(1, 300.0);
		Spoor spoor4 = new Spoor(6, 300.0);
		spoor.addLocomotief(locomotief);
		
		SpoorDao spoorDaoImpl = new SpoorDaoImpl();
		List<Spoor> sporen = new ArrayList<Spoor>();
		sporen.add(spoor);
		sporen.add(spoor4);
		spoorDaoImpl.opslaan(sporen);
		
		
		for(Spoor spoor1 : spoorDaoImpl.lezen()) {
			System.out.println(spoor1.getNummer());
		}
		LocomotiefDao locomotiefDao = new LocomotiefDaoImpl();
		locomotiefDao.getWagonsFromLocomotief(locomotief);
		
		System.out.println("========================================");
		
//		database.getWagonsFromLocomotief(spoor.getLocomotiefen().get(0));
		
		RichRailUitvoerListener richrail;
		//newcommand 
		richrail = RichRailCli.voerCommandUit("new train tr2");
		System.out.println(richrail.getMessage());
		Locomotief l1 = (Locomotief) richrail.getObject();
		System.out.println(l1.getNaam());
		System.out.println("");
		
		richrail =RichRailCli.voerCommandUit("new wagon w1");
		Wagon w1 = (Wagon) richrail.getObject();
		System.out.println(richrail.getMessage());
		System.out.println(w1.getNaam());
		System.out.println("");
		
		//================================================================================
//		richrail =RichRailCli.voerCommandUit("new wagon wg2 numseats 15");
//		w1 = (Wagon) richrail.getObject();
//		System.out.println(richrail.getMessage());
//		System.out.println(w1.getNaam());
//		System.out.println("");

		//addcommand 
		richrail =RichRailCli.voerCommandUit("add w1 to l1");
		l1 = (Locomotief) richrail.getObject();
		System.out.println(richrail.getMessage());
		System.out.println(w1.getNaam());
		System.out.println("");
		
		//remove command
		richrail =RichRailCli.voerCommandUit("remove w1 from l1");
		
		//getcommand 
		richrail =RichRailCli.voerCommandUit("getnumseats train l1");

		richrail =RichRailCli.voerCommandUit("getnumseats wagon w1");
		
		//delete command
		richrail =RichRailCli.voerCommandUit("delete wagon w1");

		richrail =RichRailCli.voerCommandUit("delete wagon w1");
		
	}
}

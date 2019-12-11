package hu.pafr.richrail;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.database.Database;
import hu.pafr.richrail.database.LocomotiefDao;
import hu.pafr.richrail.database.LocomotiefDaoImpl;
import hu.pafr.richrail.database.SpoorDao;
import hu.pafr.richrail.database.SpoorDaoImpl;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.parser.RichRailCli;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class App {
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
		
		
		
		
		
		
		RichRailCli.voerCommandUit("new train id");
		System.out.println("dsfhdjsbfjkdsjkfn");
		
		// locomotief maken

		// spoor aangemaakt
		sporen.add(spoor);

		Spoor spoor1 = new Spoor(16, 312.2);
		sporen.add(spoor1);


		// locomotief aanmaken
		builder.setNaam("grote trein");
		builder.setVertrekPunt("Amsterdam");
		builder.setEindBestemming("Utrecht");
		builder.setType_moter( "diesel");
		builder.setHoogte(2.00);
		builder.setLengte(20.00);
		builder.setGps(true);
		builder.setMax_snelheid(150.00);
		builder.setStoelen(20);
		Locomotief locomotief1 = builder.build();

		builder.setNaam("trein 1");
		builder.setVertrekPunt("utrecht");
		builder.setEindBestemming("amsterdam");
		builder.setType_moter("bezine");
		builder.setHoogte(1.8);
		builder.setLengte(50.0);
		builder.setGps(false);
		builder.setMax_snelheid(180.0);
		builder.setStoelen(5);
		Locomotief locomotief2 = builder.build();
		
		// wagon aanmaken
		Wagon wagon5 = factory.createWagon("sinterklaas", 98, 0);

		
		//clone locmotief
		System.out.println(locomotief1);
		Locomotief locomotief3 = (Locomotief) locomotief1.clone();
		System.out.println(locomotief3.getNaam());
		System.out.println(locomotief3);

		//clone wagon
		wagon.clone();
		
		// toevoegen
		spoor.addLocomotief(locomotief1);
		locomotief1.setWagon(wagon5);
		locomotief1.setWagon(wagon5);
		locomotief1.setWagon(wagon);
		
		spoor.addLocomotief(locomotief2);
		locomotief1.setWagon(wagon);
		
		spoor.addLocomotief(locomotief3);

		// verwijderen
		spoor.verwijderLocomotief(locomotief3);
		locomotief1.verwijderWagon(wagon);

		// singelot (er word maximaal 1 database oobject aangemaakt)
		Database database = Database.getDatabase();
		System.out.println(database);
		Database database2 = Database.getDatabase();
		System.out.println(database2);
		
		System.out.println(database2);

		//opslaan in database
		SpoorDao spoorDao = new SpoorDaoImpl();
		spoorDao.opslaan(sporen);
		System.out.println("test");

		//uit database lezen
		List <Spoor> sporen1 = spoorDao.lezen();
		for(Spoor spoor3 : sporen1) {
			System.out.println("spoor "+ spoor3.getNummer());
			for(Locomotief locomotieft : spoor3.getLocomotiefen()) {
				System.out.println("locomotief: "+ locomotieft.getNaam());
				for(Wagon wagon1 : locomotieft.getWagons()) {
					System.out.println("Wagon: "+ wagon1.getNaam());
				}
			}
		}
		System.out.println("test");
		
	}
}

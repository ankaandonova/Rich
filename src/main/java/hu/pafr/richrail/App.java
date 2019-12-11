package hu.pafr.richrail;

import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.database.Database;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.parser.RichRailCli;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class App {
	public static void main(String[] args) throws CloneNotSupportedException {
		
		RichRailCli.voerCommandUit("new train id");
		System.out.println("dsfhdjsbfjkdsjkfn");
		
		List<Spoor> sporen = new ArrayList<Spoor>();
		// locomotief maken
		Factory factory = new WagonFactory();
		Builder builder = new LocomotiefBuilder();

		// spoor aangemaakt
		Spoor spoor = new Spoor(1, 12.0);
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
		Wagon wagon = factory.createWagon("sinterklaas", 98, 0);

		
		//clone locmotief
		System.out.println(locomotief1);
		Locomotief locomotief3 = (Locomotief) locomotief1.clone();
		System.out.println(locomotief3.getNaam());
		System.out.println(locomotief3);

		//clone wagon
		wagon.clone();
		
		// toevoegen
		spoor.addLocomotief(locomotief1);
		locomotief1.voegWagonToe(wagon);
		locomotief1.voegWagonToe(wagon);
		locomotief1.voegWagonToe(wagon);
		
		spoor.addLocomotief(locomotief2);
		locomotief1.voegWagonToe(wagon);
		
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
		database.sporenOpslaan(sporen);
		System.out.println("test");

		//uit database lezen
		List <Spoor> sporen1 = database.lezen();
		for(Spoor spoor3 : sporen1) {
			System.out.println("spoor "+ spoor3.getNummer());
			for(Locomotief locomotief : spoor3.getLocomotiefen()) {
				System.out.println("locomotief: "+ locomotief.getNaam());
				for(Wagon wagon1 : locomotief.getWagons()) {
					System.out.println("Wagon: "+ wagon1.getNaam());
				}
			}
		}
		System.out.println("test");
		
	}
}

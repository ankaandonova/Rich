package hu.pafr.richrail;

import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.database.Database;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class App {
	public static void main(String[] args) throws CloneNotSupportedException {
		List<Spoor> sporen = new ArrayList<Spoor>();
		// locomotief maken
		Factory factory = new WagonFactory();
		Builder builder = new LocomotiefBuilder();

		// spoor aangemaakt
		Spoor spoor = new Spoor(1, 12.0);
		sporen.add(spoor);

		Spoor spoor1 = new Spoor(16, 312.2);
		sporen.add(spoor1);

		// locomotief
		String naam = "grote trein";
		String vertrekPunt = "Amsterdam";
		String eindBestemming = "Utrecht";
		String type_moter = "diesel";
		Double hoogte = 2.00;
		Double lengte = 20.00;
		boolean gps = true;
		Double max_snelheid = 150.00;
		int stoelen = 20;
		
		// locomotief aanmaken
		builder.setNaam(naam);
		builder.setVertrekPunt(vertrekPunt);
		builder.setEindBestemming(eindBestemming);
		builder.setType_moter(type_moter);
		builder.setHoogte(hoogte);
		builder.setLengte(lengte);
		builder.setGps(gps);
		builder.setMax_snelheid(max_snelheid);
		builder.setStoelen(stoelen);
		Locomotief locomotief1 = builder.build();

		builder.setNaam(naam);
		builder.setVertrekPunt(vertrekPunt);
		builder.setEindBestemming(eindBestemming);
		builder.setType_moter(type_moter);
		builder.setHoogte(hoogte);
		builder.setLengte(lengte);
		builder.setGps(gps);
		builder.setMax_snelheid(max_snelheid);
		builder.setStoelen(stoelen);
		Locomotief locomotief2 = builder.build();
		
		// wagon aanmaken
		Wagon wagon = factory.createWagon("sinterklaas", 98, 0);

		
		//clone locmotief
		System.out.println(locomotief1.getNaam());
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
		spoor.verwijderLocomotief(locomotief2);
		locomotief1.verwijderWagon(wagon);

		// singelot (er word maximaal 1 database oobject aangemaakt)
		Database database = Database.getDatabase();
		System.out.println(database);
		Database database2 = Database.getDatabase();
		System.out.println(database2);
		
		//opslaan in database
		database.opslaan(sporen);
		
		//uit database lezen
		database.lezen();
	}
}

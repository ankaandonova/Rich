package hu.pafr.richrail;

import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.database.Database;
import hu.pafr.richrail.locomotief.Factory;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefFactory;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Builder;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonBuilder;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		List<Spoor> sporen = new ArrayList<Spoor>();
		// locomotief maken
		Factory factory = new LocomotiefFactory();
		Builder wagonBuilder = new WagonBuilder();

		//spoor aangemaakt
		Spoor spoor = new Spoor(1, 12.0);
		sporen.add(spoor);
		
		Spoor spoor1 = new Spoor(16, 312.2);
		sporen.add(spoor1);

		// locomotief
		String naam = "grote trein";
		String vertrekPunt = "Amsterdam";
		String eindBestemming = "Utrecht";
		// subklasse
		String type_moter = "diesel";
		Double hoogte = 2.00;
		Double lengte = 20.00;
		boolean gps = true;
		Double max_sneleheid = 150.00;
		int stoelen = 20;
		// locomotief aanmaken
		Locomotief locomotief1 = factory.createLocomotief(naam, vertrekPunt, eindBestemming, type_moter, hoogte, lengte,
				gps, max_sneleheid, stoelen);
		Locomotief locomotief2 = factory.createLocomotief(naam, vertrekPunt, eindBestemming, type_moter, hoogte, lengte,
				gps, max_sneleheid, stoelen);
		Locomotief locomotief3 = factory.createLocomotief(naam, vertrekPunt, eindBestemming, type_moter, hoogte, lengte,
				gps, max_sneleheid, stoelen);
		// wagon aanmaken
		wagonBuilder.setBed(0);
		wagonBuilder.setStoel(98);
		wagonBuilder.setNaam("sinterklaas");
		Wagon wagon = wagonBuilder.build();

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
		
		// opslaan
		Database.opslaan(sporen);
		Database.lezen();
	}
}

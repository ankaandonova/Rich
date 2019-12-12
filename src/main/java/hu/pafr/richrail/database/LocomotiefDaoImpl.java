package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Wagon;

public class LocomotiefDaoImpl implements LocomotiefDao{

	Database database = Database.getDatabase();
	WagonDao wagonDaoImpl = new WagonDaoImpl();
	JsonAdapter adapter = new JsonAdapter();
	
	@SuppressWarnings("unchecked")
	public void saveLocomotief(Locomotief locomotief) throws FileNotFoundException { 
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");
		Locomotief databaseLocomotief = findLosseLocomotief(locomotief);
		if (databaseLocomotief == null) {
			System.out.println("de locomotief is al in de database");
			alleLocomotiefen.add(createLocomotiefJSONObject(locomotief));
		} else {
			alleLocomotiefen.add(createLocomotiefJSONObject(locomotief));
			System.out.println("schrijf vnog een verander functie want hij bestaat al");
		}
		databaseObject.put("locomotiefen", alleLocomotiefen);
		database.setDatabaseJson(databaseObject);
	}
	


	
	@SuppressWarnings("rawtypes")
	public List<Locomotief> getLocomotiefen() throws FileNotFoundException { 
		List<Locomotief> locomotiefen = new ArrayList<Locomotief>();

		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");

		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			locomotiefen.add(adapter.getLocomotiefFromJsonObject((JSONObject) iterator.next()));
		}
		return locomotiefen;
	}
	
	


	@SuppressWarnings({ "rawtypes" })
	public Locomotief getLocomotiefFromSpoor(Spoor spoor) throws FileNotFoundException { 
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");
		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			JSONObject locomotiefJson = (JSONObject) iterator.next();
			Locomotief locoomotiefFromDb = adapter.getLocomotiefFromJsonObject(locomotiefJson);
			if(locoomotiefFromDb.getSpoor().getNummer() == spoor.getNummer()) {
				return locoomotiefFromDb;
			}
		}
		return null;
	}



	@SuppressWarnings({ "rawtypes" })
	public void getWagonsFromLocomotief(Locomotief locomotief) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleSporen = (JSONArray) databaseObject.get("sporen");
		Iterator iterator = alleSporen.iterator();

		while (iterator.hasNext()) {
			// gaat elk spoor langs
			JSONObject spoorJson = (JSONObject) iterator.next();
			SpoorDao spoorDao = new SpoorDaoImpl();
			Spoor spoor = spoorDao.getSporenFromJsonObject(spoorJson);
			System.out.println("spoor " + spoor.getNummer());
			System.out.println("locomotief: " + locomotief.getNaam());
			controleerLocomotieven(locomotief, spoor);
		}
	}

	@SuppressWarnings("rawtypes")
	private void controleerLocomotieven(Locomotief locomotief, Spoor spoor) {
		for (Locomotief locomotief1 : spoor.getLocomotiefen()) {
			System.out.println("naam " + locomotief1.getNaam());
			// locomotieven van het spoor uit de database worden doorgegaan
			if (locomotief1.getNaam().equals(locomotief.getNaam())) {
				// de locomotief is gevonden in de database
				for (Wagon wagon : locomotief1.getWagons()) {
					locomotief.setWagon(wagon);
				}
			}
		}
	}


	@SuppressWarnings("rawtypes")
	private Locomotief findLosseLocomotief(Locomotief locomotief) throws FileNotFoundException {
		for (Locomotief locomotiefInDatabase : getLosseLocomotiefen()) {
			if (locomotiefInDatabase.getNaam().equals(locomotief.getNaam())) {
				return locomotiefInDatabase;
			}
		}
		return null;
	}

	public List<Locomotief> getAlleLocomotiefen() throws FileNotFoundException {
		List<Locomotief> locomotiefen = new ArrayList<Locomotief>();

		for (Locomotief locomotief : getLosseLocomotiefen()) {
			locomotiefen.add(locomotief);
		}
		for (Locomotief locomotief : getLocomotiefenInSpoor()) {
			locomotiefen.add(locomotief);
		}
		return locomotiefen;
	}



	public List<Locomotief> getLocomotiefenInSpoor() throws FileNotFoundException { 
		List<Locomotief> locomotiefen = new ArrayList<Locomotief>();

		SpoorDao spoorDao = new SpoorDaoImpl();
		List<Spoor> sporen = spoorDao.getSporen();
		for (Spoor spoor : sporen) {
			for (Locomotief locomotief : spoor.getLocomotiefen()) {
				locomotiefen.add(locomotief);
			}
		}
		return locomotiefen;
	}


}

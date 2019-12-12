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

public class LocomotiefDaoImpl implements LocomotiefDao {

	Database database = Database.getDatabase();
	WagonDao wagonDaoImpl = new WagonDaoImpl();

	@SuppressWarnings({ "rawtypes" })
	public Locomotief getLocomotiefenFromJsonObject(JSONObject locomotiefJson) {
		Builder locomotiefBuilder = new LocomotiefBuilder();
		locomotiefBuilder.setNaam((String) locomotiefJson.get("naam"));
		locomotiefBuilder.setVertrekPunt((String) locomotiefJson.get("vertrekpunt"));
		locomotiefBuilder.setEindBestemming((String) locomotiefJson.get("eindpunt"));
		locomotiefBuilder.setType_moter((String) locomotiefJson.get("type_moter"));
		locomotiefBuilder.setHoogte((Double) locomotiefJson.get("hoogte"));
		locomotiefBuilder.setLengte((Double) locomotiefJson.get("lengte"));
		locomotiefBuilder.setGps((boolean) locomotiefJson.get("gps"));
		locomotiefBuilder.setMax_snelheid((Double) locomotiefJson.get("max_snelheid"));
		locomotiefBuilder.setStoelen(Integer.parseInt((String) locomotiefJson.get("stoelen")));
		Locomotief locomotief = locomotiefBuilder.build();

		List<Wagon> wagons = new ArrayList<Wagon>();
		JSONArray alleWagons = (JSONArray) locomotiefJson.get("wagons");
		Iterator iterator = alleWagons.iterator();
		while (iterator.hasNext()) {
			wagons.add(wagonDaoImpl.getWagonsFromJsonObject((JSONObject) iterator.next()));
		}
		locomotief.setWagons(wagons);
		return locomotief;
	}

	@SuppressWarnings({ "rawtypes" })
	public void getLocomotiefFromSpoor(Spoor spoor) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleSporen = (JSONArray) databaseObject.get("sporen");
		Iterator iterator = alleSporen.iterator();
		while (iterator.hasNext()) {
			// gaat elk spoor langs
			JSONObject spoorJson = (JSONObject) iterator.next();
			int nummer = Integer.parseInt((String) spoorJson.get("nummer"));
			if (nummer == spoor.getNummer()) {
				// als het nummer van het meegegeven spoor gelijk is aan het geloopte
				JSONArray alleLocomotiefen = (JSONArray) spoorJson.get("locomotiefen");
				Iterator iterator2 = alleLocomotiefen.iterator();
				while (iterator2.hasNext()) {
					// locomotieven van het spoor uit de database worden doorgegaan
					Locomotief locomotief = getLocomotiefenFromJsonObject((JSONObject) iterator2.next());
					spoor.addLocomotief(locomotief);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject createLocomotiefJSONObject(Locomotief locomotief) {
		JSONObject locomotiefJson = new JSONObject();
		locomotiefJson.put("naam", locomotief.getNaam());
		locomotiefJson.put("vertrekpunt", locomotief.getVertrekPunt());
		locomotiefJson.put("eindpunt", locomotief.getEindBestemming());
		locomotiefJson.put("lengte", locomotief.getLengte());
		locomotiefJson.put("hoogte", locomotief.getHoogte());
		locomotiefJson.put("gps", locomotief.isGps());
		locomotiefJson.put("max_snelheid", locomotief.getMax_snelheid());
		locomotiefJson.put("stoelen", Integer.toString(locomotief.getStoelen()));
		locomotiefJson.put("type_moter", locomotief.getType_moter());
		JSONArray wagons = new JSONArray();
		for (Wagon wagon : locomotief.getWagons()) {
			wagons.add(wagonDaoImpl.createWagonJSONObject(wagon));
		}
		locomotiefJson.put("wagons", wagons);
		return locomotiefJson;
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

	@SuppressWarnings("unchecked")
	public void saveLocomotief(Locomotief locomotief) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");
		Locomotief databaseLocomotief = findLosseLocomotief(locomotief);
		if (databaseLocomotief == null) {
			System.out.println("de locomotief is al in de database");
			alleLocomotiefen.add(createLocomotiefJSONObject(locomotief));
		} else {
			System.out.println("schrijf vnog een verander functie want hij bestaat al");
		}
		databaseObject.put("locomotiefen", alleLocomotiefen);
		database.setDatabaseJson(databaseObject);
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

	@SuppressWarnings("rawtypes")
	public List<Locomotief> getLosseLocomotiefen() throws FileNotFoundException {
		List<Locomotief> locomotiefen = new ArrayList<Locomotief>();

		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");

		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			locomotiefen.add(getLocomotiefenFromJsonObject((JSONObject) iterator.next()));
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

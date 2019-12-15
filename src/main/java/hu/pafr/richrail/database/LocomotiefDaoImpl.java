package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hu.pafr.richrail.domein.locomotief.Builder;
import hu.pafr.richrail.domein.locomotief.Locomotief;
import hu.pafr.richrail.domein.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.domein.spoor.Spoor;
import hu.pafr.richrail.domein.wagon.Wagon;

public class LocomotiefDaoImpl implements LocomotiefDao {

	Database database = Database.getDatabase();
	WagonDao wagonDaoImpl = new WagonDaoImpl();
	JsonAdapter adapter = new JsonAdapter();

	@SuppressWarnings("unchecked")
	public void save(Locomotief locomotief) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");
		if (getLocomotief(locomotief) == null) {
			alleLocomotiefen.add(adapter.createLocomotiefJSONObject(locomotief));
		} else {
			update(locomotief);
		}
		databaseObject.put("locomotiefen", alleLocomotiefen);
		database.setDatabaseJson(databaseObject);

		// wagons worden opgeslagen
		for (Wagon wagon : locomotief.getWagons()) {
			wagon.setLocomotief(locomotief);
			wagonDaoImpl.save(wagon);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean update(Locomotief locomotief) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");
		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			JSONObject locomotiefObject = (JSONObject) iterator.next();
			Locomotief locomotiefFromDatabase = adapter.getLocomotiefFromJsonObject(locomotiefObject);
			if (locomotiefFromDatabase != null) {
				if (locomotiefFromDatabase.getNaam().equals(locomotief.getNaam())) {
					Spoor spoor = locomotief.getSpoor();
					if (spoor == null) {
						locomotiefObject.put("spoor", "");
					} else {
						locomotiefObject.put("spoor", Integer.toString(spoor.getNummer()));
					}

					locomotiefObject.put("naam", locomotief.getNaam());
					locomotiefObject.put("vertrekpunt", locomotief.getVertrekPunt());
					locomotiefObject.put("eindpunt", locomotief.getEindBestemming());
					locomotiefObject.put("lengte", locomotief.getLengte());
					locomotiefObject.put("hoogte", locomotief.getHoogte());
					locomotiefObject.put("gps", locomotief.isGps());
					locomotiefObject.put("max_snelheid", locomotief.getMax_snelheid());
					locomotiefObject.put("stoelen", Integer.toString(locomotief.getStoelen()));
					locomotiefObject.put("type_moter", locomotief.getType_moter());
					databaseObject.put("locomotiefen", alleLocomotiefen);
					database.setDatabaseJson(databaseObject);
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean remove(Locomotief locomotief) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");
		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			JSONObject locomotiefObject = (JSONObject) iterator.next();
			Locomotief locomotiefFromDatabase = adapter.getLocomotiefFromJsonObject(locomotiefObject);
			if (locomotiefFromDatabase != null) {
				if (locomotiefFromDatabase.getNaam().equals(locomotief.getNaam())) {
					locomotiefObject.remove("naam");
					locomotiefObject.remove("vertrekpunt");
					locomotiefObject.remove("eindpunt");
					locomotiefObject.remove("lengte");
					locomotiefObject.remove("hoogte");
					locomotiefObject.remove("gps");
					locomotiefObject.remove("max_snelheid");
					locomotiefObject.remove("stoelen");
					locomotiefObject.remove("type_moter");
					locomotiefObject.remove("spoor");
				}
			}
		}
		databaseObject.put("locomotiefen", alleLocomotiefen);
		database.setDatabaseJson(databaseObject);
		return false;
	}

	@SuppressWarnings("rawtypes")
	public Locomotief getLocomotief(Locomotief locomotief) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");

		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			JSONObject locomotiefJson = (JSONObject) iterator.next();
			Locomotief locoomotiefFromDb = adapter.getLocomotiefFromJsonObject(locomotiefJson);
			if (locoomotiefFromDb != null) {
				if (locoomotiefFromDb.getNaam().equals(locomotief.getNaam())) {
					return locoomotiefFromDb;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List<Locomotief> getLocomotiefen() throws FileNotFoundException {
		List<Locomotief> locomotiefen = new ArrayList<Locomotief>();

		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleLocomotiefen = (JSONArray) databaseObject.get("locomotiefen");

		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			Locomotief locomotiefJson = (Locomotief) adapter.getLocomotiefFromJsonObject((JSONObject) iterator.next());
			if(locomotiefJson != null) {
				locomotiefen.add((Locomotief) locomotiefJson);
			}
		}
		return locomotiefen;
	}

}

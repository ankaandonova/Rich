package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.spoor.Spoor;

public class SpoorDaoImpl implements SpoorDao{
	Database database = Database.getDatabase();
	
	@SuppressWarnings("unchecked")
	public void saveSporen(List<Spoor> sporen) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleSporen = new JSONArray();
		for(Spoor spoor : sporen) {
			alleSporen.add(spoorToJson(spoor));
		}
		databaseObject.put("sporen", alleSporen);
		database.setDatabaseJson(databaseObject);
	}

	@SuppressWarnings("unchecked")
	public JSONObject spoorToJson(Spoor spoor) {
		LocomotiefDao LocomotiefDaoImpl = new LocomotiefDaoImpl();
		JSONObject spoorObject = new JSONObject();
		spoorObject.put("nummer", Integer.toString(spoor.getNummer()));
		spoorObject.put("lengte", spoor.getLengte());
		JSONArray locomotiefen = new JSONArray();
		for (Locomotief locomotief : spoor.getLocomotiefen()) {
			locomotiefen.add(LocomotiefDaoImpl.createLocomotiefJSONObject(locomotief));
		}
		spoorObject.put("locomotiefen", locomotiefen);	
		return spoorObject;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public List<Spoor> getSporen() throws FileNotFoundException {
		List<Spoor> sporen = new ArrayList<Spoor>();
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleSporen = (JSONArray) databaseObject.get("sporen");
		Iterator iterator = alleSporen.iterator();
		while (iterator.hasNext()) {
			sporen.add(getSporenFromJsonObject((JSONObject) iterator.next()));
		}
		return sporen;
	}

	@SuppressWarnings("rawtypes")
	public Spoor getSporenFromJsonObject(JSONObject spoorJson) {
		int nummer = Integer.parseInt((String) spoorJson.get("nummer"));
		LocomotiefDao LocomotiefDaoImpl = new LocomotiefDaoImpl();
		Double lengte = (Double) spoorJson.get("lengte");
		Spoor spoor = new Spoor(nummer, lengte);

		JSONArray alleLocomotiefen = (JSONArray) spoorJson.get("locomotiefen");
		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			Locomotief locomotief = LocomotiefDaoImpl.getLocomotiefenFromJsonObject((JSONObject) iterator.next());
			spoor.addLocomotief(locomotief);
		}
		return spoor;
	}
}

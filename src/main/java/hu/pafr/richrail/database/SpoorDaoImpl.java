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

public class SpoorDaoImpl implements SpoorDao {
	Database database = Database.getDatabase();
	JsonAdapter adapter = new JsonAdapter();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void saveSpoor(Spoor spoor) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleSporen = (JSONArray) databaseObject.get("sporen");
		boolean bestondAl = false;
		Iterator iterator = alleSporen.iterator();
		while (iterator.hasNext()) {
			JSONObject spoorObject = (JSONObject) iterator.next();
			Spoor spoorUitDatabase = adapter.getSpoorFromJsonObject(spoorObject);
			if (spoorUitDatabase.getNummer() == spoor.getNummer()) {
				System.out.println("het spoor bestond al in de database");
				bestondAl = true;
				spoorObject.put("lengte", spoor.getLengte());
			}
		}
		if (!bestondAl) {
			System.out.println("nieuw spoor aangemaakt");
			alleSporen.add(spoorToJson(spoor));
		}

		databaseObject.put("sporen", alleSporen);
		database.setDatabaseJson(databaseObject);
	}



	@SuppressWarnings("rawtypes")
	public Spoor getSpoor(Spoor spoor) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleSporen = (JSONArray) databaseObject.get("sporen");
		Iterator iterator = alleSporen.iterator();
		while (iterator.hasNext()) {
			Spoor spoorUitDatabase = adapter.getSpoorFromJsonObject((JSONObject) iterator.next());
			if (spoorUitDatabase.getNummer() == spoor.getNummer()) {
				return spoorUitDatabase;
			}
		}
		return spoor;
	}


}

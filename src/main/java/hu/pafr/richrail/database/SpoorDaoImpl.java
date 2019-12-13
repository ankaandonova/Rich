package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
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

	@SuppressWarnings({ "unchecked" })
	public void save(Spoor spoor) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleSporen = (JSONArray) databaseObject.get("sporen");
		System.out.println(getSpoor(spoor));
		if(getSpoor(spoor) == null) {
			System.out.println("nieuw spoor aangemaakt");
			alleSporen.add(adapter.spoorToJson(spoor));
		} else {	
			System.out.println("spoor word geupdate");
			update(spoor);
		}
		databaseObject.put("sporen", alleSporen);
		database.setDatabaseJson(databaseObject);
		
		LocomotiefDao locomotiefDao = new LocomotiefDaoImpl();
		for(Locomotief locomotief : spoor.getLocomotiefen()) {
			locomotief.setSpoor(spoor);
			locomotiefDao.save(locomotief);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(Spoor spoor) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleSporen = (JSONArray) databaseObject.get("sporen");
		Iterator iterator = alleSporen.iterator();
		while (iterator.hasNext()) {
			JSONObject spoorObject = (JSONObject) iterator.next();
			Spoor spoorFromDatabase = adapter.getSpoorFromJsonObject(spoorObject);
			if(spoorFromDatabase.getNummer() == spoor.getNummer()) {
				spoorObject.put("lengte", spoor.getLengte());
			}
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
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Spoor> getSporen() throws FileNotFoundException {
		List<Spoor> sporen = new ArrayList<Spoor>();
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleSporen = (JSONArray) databaseObject.get("sporen");
		Iterator iterator = alleSporen.iterator();
		while (iterator.hasNext()) {
			Spoor spoorUitDatabase = adapter.getSpoorFromJsonObject((JSONObject) iterator.next());
			sporen.add(spoorUitDatabase);
		}
		return sporen;
	}
}

package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class WagonDaoImpl implements WagonDao {
	Database database = Database.getDatabase();
	
	@SuppressWarnings("unchecked")
	public JSONObject createWagonJSONObject(Wagon wagon) {
		JSONObject wagonJson = new JSONObject();
		wagonJson.put("naam", wagon.getNaam());
		wagonJson.put("bedden", Integer.toString(wagon.getBedden()));
		wagonJson.put("stoelen", Integer.toString(wagon.getStoelen()));
		return wagonJson;
	}

	public Wagon getWagonsFromJsonObject(JSONObject wagonJson) {
		String naam = (String) wagonJson.get("naam");
		int bedden = Integer.parseInt((String) wagonJson.get("bedden"));
		int stoelen = Integer.parseInt((String) wagonJson.get("stoelen"));

		Factory factory = new WagonFactory();
		return factory.createWagon(naam, stoelen, bedden);
	}
	@SuppressWarnings("unchecked")
	public void saveWagon(Wagon wagon) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleWagonnen = (JSONArray) databaseObject.get("wagonnen");
		Wagon databaseWagon = (Wagon) findLosseWagon(wagon, alleWagonnen);
		if(databaseWagon == null) {
			System.out.println("de locomotief is al in de database");
			alleWagonnen.add(createWagonJSONObject(wagon));
		} else {
			System.out.println("schrijf vnog een verander functie want hij bestaat al");
		}
		databaseObject.put("wagonnen", alleWagonnen);
		database.setDatabaseJson(databaseObject);
	}
	
	@SuppressWarnings("rawtypes")
	public Wagon findLosseWagon(Wagon wagon, JSONArray alleWagonnen) {
		Iterator iterator = alleWagonnen.iterator();
		while (iterator.hasNext()) {
			Wagon wagonInDatabase = getWagonsFromJsonObject((JSONObject) iterator.next());
			if(wagonInDatabase.getNaam().equals(wagon.getNaam())) {
				return wagonInDatabase;
			}
		}
		return null;
	}
}

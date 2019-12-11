package hu.pafr.richrail.database;

import java.io.FileNotFoundException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class WagonDaoImpl implements WagonDao {
	Database database = Database.getDatabase();

	@SuppressWarnings("unchecked")
	public void save(Wagon wagon) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		
		JSONArray wagonnen = (JSONArray) databaseObject.get("wagonnen");
		JSONObject wagonJson = createWagonJSONObject(wagon);
		wagonnen.add(wagonJson);
		database.setDatabaseJson(databaseObject);
	}

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
		System.out.println("wagon " + (String) wagonJson.get("bedden"));
		System.out.println("wagonJson " + wagonJson);
		int bedden = Integer.parseInt((String) wagonJson.get("bedden"));
		int stoelen = Integer.parseInt((String) wagonJson.get("stoelen"));

		Factory factory = new WagonFactory();
		return factory.createWagon(naam, stoelen, bedden);
	}
}

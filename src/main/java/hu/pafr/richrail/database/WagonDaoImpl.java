package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class WagonDaoImpl implements WagonDao {
	Database database = Database.getDatabase();
	JsonAdapter adapter = new JsonAdapter();

	@SuppressWarnings("unchecked")
	public void save(Wagon wagon) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleWagonnen = (JSONArray) databaseObject.get("wagonnen");
		if (getWagon(wagon) == null) {
			JSONObject wagonObject = adapter.createWagonJSONObject(wagon);
			alleWagonnen.add(wagonObject);
		} else {
			update(wagon);
		}
		databaseObject.put("wagonnen", alleWagonnen);
		database.setDatabaseJson(databaseObject);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "null" })
	public boolean update(Wagon wagon) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleWagonnen = (JSONArray) databaseObject.get("wagonnen");
		Iterator iterator = alleWagonnen.iterator();
		while (iterator.hasNext()) {
			JSONObject wagonObject = (JSONObject) iterator.next();
			Wagon wagonFromDatabase = adapter.getWagonsFromJsonObject(wagonObject);
			if (wagonFromDatabase != null) {
				if (wagonFromDatabase.getNaam().equals(wagon.getNaam())) {
					Locomotief locomotief = wagon.getLocomotief();
					if (locomotief != null) {
						wagonObject.put("locomotief", locomotief.getNaam());
					} else {
						wagonObject.put("locomotief", "");
					}

					wagonObject.put("stoelen", Integer.toString(wagon.getStoelen()));
					wagonObject.put("bedden", Integer.toString(wagon.getBedden()));
					wagonObject.put("naam", wagon.getNaam());
					databaseObject.put("wagonnen", alleWagonnen);
					database.setDatabaseJson(databaseObject);
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean remove(Wagon wagon) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleWagonnen = (JSONArray) databaseObject.get("wagonnen");
		Iterator iterator = alleWagonnen.iterator();
		while (iterator.hasNext()) {
			JSONObject wagonJSONObject = (JSONObject) iterator.next();
			Wagon wagonInDatabase = adapter.getWagonsFromJsonObject(wagonJSONObject);
			if (wagonInDatabase != null) {
				if (wagonInDatabase.getNaam().equals(wagon.getNaam())) {
					wagonJSONObject.remove("naam");
					wagonJSONObject.remove("bedden");
					wagonJSONObject.remove("locomotief");
					wagonJSONObject.remove("stoelen");
					database.setDatabaseJson(databaseObject);
					return true;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public Wagon getWagon(Wagon wagon) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleWagonnen = (JSONArray) databaseObject.get("wagonnen");
		Iterator iterator = alleWagonnen.iterator();
		while (iterator.hasNext()) {
			Wagon wagonInDatabase = adapter.getWagonsFromJsonObject((JSONObject) iterator.next());
			if (wagonInDatabase != null) {
				if (wagonInDatabase.getNaam().equals(wagon.getNaam())) {
					return wagonInDatabase;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public List<Wagon> getWagonnen() throws FileNotFoundException {
		List<Wagon> wagonnen = new ArrayList<Wagon>();

		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray alleWagonnen = (JSONArray) databaseObject.get("wagonnen");

		Iterator iterator = alleWagonnen.iterator();
		while (iterator.hasNext()) {
			Wagon wagon = adapter.getWagonsFromJsonObject((JSONObject) iterator.next());
			
			if(wagon != null) {
				wagonnen.add(wagon);
			}
		}
		return wagonnen;
	}
}

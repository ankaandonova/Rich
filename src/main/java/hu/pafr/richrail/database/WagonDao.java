package hu.pafr.richrail.database;

import java.io.FileNotFoundException;

import org.json.simple.JSONObject;

import hu.pafr.richrail.wagon.Wagon;

public interface WagonDao {
	public void save(Wagon wagon) throws FileNotFoundException;

	public JSONObject createWagonJSONObject(Wagon wagon);

	public Wagon getWagonsFromJsonObject(JSONObject wagonJson);
}

package hu.pafr.richrail.database;

import java.io.FileNotFoundException;

import org.json.simple.JSONObject;

import hu.pafr.richrail.wagon.Wagon;

public interface WagonDao {
	public JSONObject createWagonJSONObject(Wagon wagon);

	public Wagon getWagonsFromJsonObject(JSONObject wagonJson);
	
	public void saveWagon(Wagon wagon) throws FileNotFoundException;
}

package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.List;

import org.json.simple.JSONObject;

import hu.pafr.richrail.spoor.Spoor;

public interface SpoorDao {
	public void opslaan(List<Spoor> sporen);

	public List<Spoor> lezen() throws FileNotFoundException;
	
	public Spoor getSporenFromJsonObject(JSONObject spoorJson);
}

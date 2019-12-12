package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.List;

import org.json.simple.JSONObject;

import hu.pafr.richrail.spoor.Spoor;

public interface SpoorDao {
	public void saveSporen(List<Spoor> sporen) throws FileNotFoundException;

	public List<Spoor> getSporen() throws FileNotFoundException;
	
	public Spoor getSporenFromJsonObject(JSONObject spoorJson);
}

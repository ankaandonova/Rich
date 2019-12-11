package hu.pafr.richrail.database;

import java.io.FileNotFoundException;

import org.json.simple.JSONObject;

import hu.pafr.richrail.locomotief.Locomotief;

public interface LocomotiefDao {
	public void save(Locomotief locomotief) throws FileNotFoundException;

	public Locomotief getLocomotiefenFromJsonObject(JSONObject locomotiefJson);

	public JSONObject createLocomotiefJSONObject(Locomotief locomotief);

	public void getWagonsFromLocomotief(Locomotief locomotief);
}

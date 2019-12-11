package hu.pafr.richrail.database;

import java.io.FileNotFoundException;

import org.json.simple.JSONObject;

import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.spoor.Spoor;

public interface LocomotiefDao {
	public Locomotief getLocomotiefenFromJsonObject(JSONObject locomotiefJson);

	public void getLocomotiefFromSpoor(Spoor spoor) throws FileNotFoundException;

	public JSONObject createLocomotiefJSONObject(Locomotief locomotief);

	public void getWagonsFromLocomotief(Locomotief locomotief) throws FileNotFoundException;

}

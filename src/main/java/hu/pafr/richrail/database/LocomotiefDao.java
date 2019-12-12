package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.List;

import org.json.simple.JSONObject;

import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Wagon;

public interface LocomotiefDao {
	public Locomotief getLocomotiefenFromJsonObject(JSONObject locomotiefJson);

	public List<Locomotief> getAlleLocomotiefen() throws FileNotFoundException;
	
	public Locomotief getLocomotiefFromSpoor(Spoor spoor) throws FileNotFoundException;

	public JSONObject createLocomotiefJSONObject(Locomotief locomotief);

	public void saveLocomotief(Locomotief locomotief) throws FileNotFoundException;

	public void getWagonsFromLocomotief(Locomotief locomotief) throws FileNotFoundException;

	public void removeWagon(Locomotief locomotief, Wagon wagon) throws FileNotFoundException;

}

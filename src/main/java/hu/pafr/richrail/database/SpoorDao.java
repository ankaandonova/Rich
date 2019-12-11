package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.List;

import hu.pafr.richrail.spoor.Spoor;

public interface SpoorDao {
	public void save(Spoor spoor) throws FileNotFoundException;

	public List<Spoor> getSporen() throws FileNotFoundException;

	public void getLocomotiefFromSpoor(Spoor spoor);

}

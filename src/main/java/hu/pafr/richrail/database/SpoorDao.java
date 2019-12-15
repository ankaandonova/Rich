package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.List;

import hu.pafr.richrail.domein.spoor.Spoor;

public interface SpoorDao {
	public void save(Spoor spoor) throws FileNotFoundException;

	public boolean remove(Spoor spoor) throws FileNotFoundException;

	public boolean update(Spoor spoor) throws FileNotFoundException;

	public Spoor getSpoor(Spoor spoor) throws FileNotFoundException;

	public List<Spoor> getSporen() throws FileNotFoundException;
}

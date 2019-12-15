package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.List;

import hu.pafr.richrail.domein.wagon.Wagon;

public interface WagonDao {

	public void save(Wagon wagon) throws FileNotFoundException;

	public boolean update(Wagon wagon) throws FileNotFoundException;

	public boolean remove(Wagon wagon) throws FileNotFoundException;

	public Wagon getWagon(Wagon wagon) throws FileNotFoundException;

	public List<Wagon> getWagonnen() throws FileNotFoundException;

}

package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.util.List;

import hu.pafr.richrail.locomotief.Locomotief;

public interface LocomotiefDao {
	public void save(Locomotief locomotief) throws FileNotFoundException;

	public boolean update(Locomotief locomotief) throws FileNotFoundException;

	public boolean remove(Locomotief locomotief) throws FileNotFoundException;

	public Locomotief getLocomotief(Locomotief locomotief) throws FileNotFoundException;

	public List<Locomotief> getLocomotiefen() throws FileNotFoundException;

}

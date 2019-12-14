package hu.pafr.richrail.wagon;

import java.io.FileNotFoundException;
import java.util.List;

import hu.pafr.richrail.database.WagonDao;
import hu.pafr.richrail.database.WagonDaoImpl;
import hu.pafr.richrail.locomotief.Locomotief;

public interface Wagon {

	public Object clone() throws CloneNotSupportedException;

	public boolean moveWagon(Locomotief locomotief) throws FileNotFoundException;
	
	public String getNaam();

	public void setNaam(String naam);

	public int getStoelen();

	public void setStoelen(int stoelen);

	public int getBedden();

	public void setBedden(int bedden);

	public void remove();

	public Locomotief getLocomotief();

	public void setLocomotief(Locomotief locomotief);

	public List<Wagon> getLosseWagonnen();

	public static Wagon getWagonDromDatabase(Wagon wagon) throws FileNotFoundException {
		WagonDao wagonDao= new WagonDaoImpl();
		return wagonDao.getWagon(wagon);
	}
}

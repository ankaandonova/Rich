package hu.pafr.richrail.locomotief;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.database.LocomotiefDao;
import hu.pafr.richrail.database.LocomotiefDaoImpl;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Wagon;

public interface Locomotief {
	public Object clone() throws CloneNotSupportedException;

	public static Locomotief getLocomotiefFromDatabase(Locomotief locomotief) throws FileNotFoundException {
		LocomotiefDao locomotiefDao = new LocomotiefDaoImpl();
		return locomotiefDao.getLocomotief(locomotief);
	}

	public static List<Locomotief> getLosseLocomotieven() {
		List<Locomotief> locomotieven = new ArrayList<Locomotief>();
		LocomotiefDao locomotiefDao = new LocomotiefDaoImpl();
		try {
			for (Locomotief locomotief : locomotiefDao.getLocomotiefen()) {
				if (locomotief.getSpoor() == null) {
					locomotieven.add(locomotief);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return locomotieven;
	}

	public static List<Locomotief> getLocomotievenFromDatabase() {
		List<Locomotief> locomotieven = new ArrayList<Locomotief>();
		LocomotiefDao locomotiefDao = new LocomotiefDaoImpl();
		try {
			for (Locomotief locomotief : locomotiefDao.getLocomotiefen()) {
				locomotieven.add(locomotief);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return locomotieven;
	}

	public String getNaam();

	public boolean update() throws FileNotFoundException;

	public void save() throws FileNotFoundException;

	public Spoor getSpoor();

	public void setNaam(String naam);

	public String getVertrekPunt();

	public void setVertrekPunt(String vertrekPunt);

	public String getEindBestemming();

	public void setEindBestemming(String eindBestemming);

	public void setWagons(List<Wagon> wagons);

	public void setWagon(Wagon wagon);

	public void verwijderWagon(Wagon wagon);

	public List<Wagon> getWagons();

	public String getType_moter();

	public void setType_moter(String type_moter);

	public Double getHoogte();

	public void setHoogte(Double hoogte);

	public Double getLengte();

	public void setLengte(Double lengte);

	public boolean isGps();

	public void setGps(boolean gps);

	public Double getMax_snelheid();

	public void setMax_snelheid(Double max_snelheid);

	public int getStoelen();

	public void setStoelen(int stoelen);

	public void getWagonnenFromDatabase() throws FileNotFoundException;

	public boolean removeWagon(Wagon wagon) throws FileNotFoundException;

	public void remove();

	public void setSpoor(Spoor spoor);

	public boolean moveLocomotief(Spoor spoor) throws FileNotFoundException;
}

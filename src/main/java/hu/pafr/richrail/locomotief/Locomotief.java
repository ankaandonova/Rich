package hu.pafr.richrail.locomotief;

import java.io.FileNotFoundException;
import java.util.List;

import hu.pafr.richrail.wagon.Wagon;

public interface Locomotief {
	public Object clone() throws CloneNotSupportedException;

	public String getNaam();

	public void setNaam(String naam);

	public String getVertrekPunt();

	public void setVertrekPunt(String vertrekPunt);

	public String getEindBestemming();

	public void setEindBestemming(String eindBestemming);

	public void setWagons(List<Wagon> wagons);

	public void setWagon(Wagon wagon);

	public void verwijderWagon(Wagon wagon);

	public List<Wagon> getWagons();

	// subklasse
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

}

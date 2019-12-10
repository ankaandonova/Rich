package hu.pafr.richrail.locomotief;

import java.util.List;

import hu.pafr.richrail.wagon.Wagon;

public interface Builder {
	public void reset();

	public void setWagons(List<Wagon> wagons);

	public void setNaam(String naam);

	public void setVertrekPunt(String vertrekPunt);

	public void setEindBestemming(String eindBestemming);

	public void setType_moter(String type_moter);

	public void setHoogte(Double hoogte);

	public void setLengte(Double lengte);

	public void setGps(boolean gps);

	public void setMax_snelheid(Double max_snelheid);

	public void setStoelen(int stoelen);
	
	public Locomotief build();

}
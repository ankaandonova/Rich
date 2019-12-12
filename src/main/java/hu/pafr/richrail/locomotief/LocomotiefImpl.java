package hu.pafr.richrail.locomotief;

import java.io.FileNotFoundException;
import java.util.ArrayList; 
import java.util.List;

import hu.pafr.richrail.database.LocomotiefDao;
import hu.pafr.richrail.database.LocomotiefDaoImpl;
import hu.pafr.richrail.wagon.Wagon;

public class LocomotiefImpl implements Locomotief, Cloneable {
	private LocomotiefDao locomotiefDao = new LocomotiefDaoImpl();
	
	private List<Wagon> wagons = new ArrayList<Wagon>();

	private String naam;
	private String vertrekPunt;
	private String eindBestemming;
	private String type_moter;
	private Double hoogte;
	private Double lengte;
	private boolean gps;
	private Double max_snelheid;
	private int stoelen;

	
	
	public LocomotiefImpl(String naam, String vertrekPunt, String eindBestemming, String type_moter,
			Double hoogte, Double lengte, boolean gps, Double max_snelheid, int stoelen) {
		this.naam = naam;
		this.vertrekPunt = vertrekPunt;
		this.eindBestemming = eindBestemming;
		this.type_moter = type_moter;
		this.hoogte = hoogte;
		this.lengte = lengte;
		this.gps = gps;
		this.max_snelheid = max_snelheid;
		this.stoelen = stoelen;
	}


	@Override
	public boolean removeWagon(Wagon wagon) throws FileNotFoundException {
		getWagonnenFromDatabase();
		for(Wagon wagon1 : wagons) {
			if(wagon1.getNaam() == wagon.getNaam()) {
				locomotiefDao.removeWagon(this, wagon);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void getWagonnenFromDatabase() throws FileNotFoundException {
		locomotiefDao.getWagonsFromLocomotief(this);
	}
	@Override
	public void remove() {
		System.out.println("remove uit ddb");
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public String getVertrekPunt() {
		return vertrekPunt;
	}

	public void setVertrekPunt(String vertrekPunt) {
		this.vertrekPunt = vertrekPunt;
	}

	public String getEindBestemming() {
		return eindBestemming;
	}

	public void setEindBestemming(String eindBestemming) {
		this.eindBestemming = eindBestemming;
	}

	public void setWagons(List<Wagon> wagons) {
		this.wagons = wagons;
	}

	public void setWagon(Wagon wagon) {
		wagons.add(wagon);
	}

	public void verwijderWagon(Wagon wagon) {
		wagons.remove(wagon);
	}

	public List<Wagon> getWagons() {
		return wagons;
	}

	public String getType_moter() {
		return type_moter;
	}

	public void setType_moter(String type_moter) {
		this.type_moter = type_moter;
	}

	public Double getHoogte() {
		return hoogte;
	}

	public void setHoogte(Double hoogte) {
		this.hoogte = hoogte;
	}

	public Double getLengte() {
		return lengte;
	}

	public void setLengte(Double lengte) {
		this.lengte = lengte;
	}

	public boolean isGps() {
		return gps;
	}

	public void setGps(boolean gps) {
		this.gps = gps;
	}

	public Double getMax_snelheid() {
		return max_snelheid;
	}

	public void setMax_snelheid(Double max_snelheid) {
		this.max_snelheid = max_snelheid;
	}

	public int getStoelen() {
		return stoelen;
	}

	public void setStoelen(int stoelen) {
		this.stoelen = stoelen;
	}



}
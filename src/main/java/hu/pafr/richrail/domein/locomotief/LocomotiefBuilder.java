package hu.pafr.richrail.domein.locomotief;

import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.domein.wagon.Wagon;

public class LocomotiefBuilder implements Builder {
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

	public void reset(){
		this.naam = null;
		this.eindBestemming = null;
		this.vertrekPunt = null;
		this.type_moter = null;
		this.hoogte = null;
		this.lengte = null;
		this.gps = false;
		this.max_snelheid = null;
		this.stoelen = 0;
	};
	
	public void setWagons(List<Wagon> wagons) {
		this.wagons = wagons;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setVertrekPunt(String vertrekPunt) {
		this.vertrekPunt = vertrekPunt;
	}

	public void setEindBestemming(String eindBestemming) {
		this.eindBestemming = eindBestemming;
	}

	public void setType_moter(String type_moter) {
		this.type_moter = type_moter;
	}

	public void setHoogte(Double hoogte) {
		this.hoogte = hoogte;
	}

	public void setLengte(Double lengte) {
		this.lengte = lengte;
	}

	public void setGps(boolean gps) {
		this.gps = gps;
	}

	public void setMax_snelheid(Double max_snelheid) {
		this.max_snelheid = max_snelheid;
	}

	public void setStoelen(int stoelen) {
		this.stoelen = stoelen;
	}

	public Locomotief build() {
		return new LocomotiefImpl(naam, vertrekPunt, eindBestemming, type_moter, hoogte, lengte, gps, max_snelheid,
				stoelen);
	}
}

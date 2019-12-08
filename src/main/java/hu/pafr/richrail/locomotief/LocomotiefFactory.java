package hu.pafr.richrail.locomotief;

public class LocomotiefFactory implements Factory{
	
	public Locomotief createLocomotief(String naam, String vertrekPunt, String eindBestemming, String type_moter,
			Double hoogte, Double lengte, boolean gps, Double max_snelheid, int stoelen) {
		switch(type_moter) {
		case "electrisch":
			return new ElectrischeLocomotief(naam, vertrekPunt, eindBestemming, type_moter,
					hoogte, lengte, gps, max_snelheid, stoelen);
		case "diesel":
			return new  DieselLocomotief(naam, vertrekPunt, eindBestemming, type_moter,
					hoogte, lengte, gps, max_snelheid, stoelen);
		case "benzine":
			return new  BenzineLocomotief(naam, vertrekPunt, eindBestemming, type_moter,	
				hoogte, lengte, gps, max_snelheid, stoelen);
		}
		return null;
	}

}

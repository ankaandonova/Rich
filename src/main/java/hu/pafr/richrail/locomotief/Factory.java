package hu.pafr.richrail.locomotief;

public interface Factory {
	
	public Locomotief createLocomotief(String naam, String vertrekPunt, String eindBestemming, String type_moter,
			Double hoogte, Double lengte, boolean gps, Double max_sneleheid, int stoelen);
}

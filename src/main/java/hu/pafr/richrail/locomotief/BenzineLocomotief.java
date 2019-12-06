package hu.pafr.richrail.locomotief;

public class BenzineLocomotief extends Locomotief {
	private String type_moter;
	private Double hoogte;
	private Double lengte;
	private boolean gps;
	private Double max_sneleheid;
	private int stoelen;
	private int s;

	public BenzineLocomotief(String naam, String vertrekPunt, String eindBestemming, String type_moter,
				Double hoogte, Double lengte, boolean gps, Double max_sneleheid, int stoelen) {
			super(naam, vertrekPunt, eindBestemming);
			this.type_moter = type_moter;
			this.hoogte = hoogte;
			this.lengte = lengte;
			this.gps = gps;
			this.max_sneleheid = max_sneleheid;
			this.stoelen = stoelen;
		}
}
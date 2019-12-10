package hu.pafr.richrail.wagon;


public class WagonFactory implements Factory {
	public Wagon createWagon(String naam, int stoelen, int bedden) {
		Wagon wagon = null;
		if(bedden > 0) {
			wagon = new SlaapWagon(naam, stoelen, bedden);
		} else if(stoelen > 0) {
			wagon = new SlaapWagon(naam, stoelen, bedden);
		} else {
			wagon = new SlaapWagon(naam, stoelen, bedden);
		}
		return wagon;
	}
}

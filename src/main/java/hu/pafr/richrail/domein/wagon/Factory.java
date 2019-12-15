package hu.pafr.richrail.domein.wagon;


public interface Factory {
	public Wagon createWagon(String naam, int stoelen, int bedden);
}

package hu.pafr.richrail.wagon;


public interface Factory {
	public Wagon createWagon(String naam, int stoelen, int bedden);
}

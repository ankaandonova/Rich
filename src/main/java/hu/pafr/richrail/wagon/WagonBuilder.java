package hu.pafr.richrail.wagon;


public class WagonBuilder implements Builder {
	private String naam;
	private int stoelen;
	private int bedden;

	public void setNaam(String naam) {
		this.naam = naam;

	}

	public void setStoel(int stoelen) {
		this.stoelen = stoelen;

	}

	public void setBed(int bedden) {
		this.bedden = bedden;

	}
}

package hu.pafr.richrail.wagon;

public class WagonBuilder implements Builder {
	private String naam;
	private int stoelen;
	private int bedden;

	public void reset() {
		this.naam = null;
		this.stoelen = 0;
		this.bedden = 0;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setStoel(int stoelen) {
		this.stoelen = stoelen;
	}

	public void setBed(int bedden) {
		this.bedden = bedden;
	}
	
	public Wagon build() {
		return new Wagon(naam, stoelen, bedden); 
	}
}

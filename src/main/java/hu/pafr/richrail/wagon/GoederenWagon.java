package hu.pafr.richrail.wagon;

public class GoederenWagon implements Cloneable, Wagon {
	private String naam;
	private int stoelen;
	private int bedden;

	public GoederenWagon(String naam, int stoelen, int bedden) {
		this.naam = naam;
		this.stoelen = stoelen;
		this.bedden = bedden;
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

	public int getStoelen() {
		return stoelen;
	}

	public void setStoelen(int stoelen) {
		this.stoelen = stoelen;
	}

	public int getBedden() {
		return bedden;
	}

	public void setBedden(int bedden) {
		this.bedden = bedden;
	}
}

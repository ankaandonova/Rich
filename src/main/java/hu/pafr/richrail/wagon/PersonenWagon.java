package hu.pafr.richrail.wagon;

import hu.pafr.richrail.locomotief.Locomotief;

public class PersonenWagon implements Cloneable, Wagon {
	private String naam;
	private int stoelen;
	private int bedden;
	private Locomotief locomotief;

	public PersonenWagon(String naam, int stoelen, int bedden) {
		this.naam = naam;
		this.stoelen = stoelen;
		this.bedden = bedden;
	}
	
	public Locomotief getLocomotief() {
		return locomotief;
	}
	
	public void setLocomotief(Locomotief locomotief) {
		this.locomotief = locomotief;
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
	@Override
	public void remove() {
		System.out.println("remove uit ddb");
	}


}

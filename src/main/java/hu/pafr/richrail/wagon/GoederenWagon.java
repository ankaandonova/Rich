package hu.pafr.richrail.wagon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.database.WagonDao;
import hu.pafr.richrail.database.WagonDaoImpl;
import hu.pafr.richrail.locomotief.Locomotief;

public class GoederenWagon implements Cloneable, Wagon {
	private WagonDao wagonDao = new WagonDaoImpl();
	
	private String naam;
	private int stoelen;
	private int bedden;
	private Locomotief locomotief;

	public GoederenWagon(String naam, int stoelen, int bedden) {
		this.naam = naam;
		this.stoelen = stoelen;
		this.bedden = bedden;
	}

	public boolean moveWagon(Locomotief locomotief) throws FileNotFoundException {
		this.setLocomotief(locomotief);
		wagonDao.update(this);
		return false;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Locomotief getLocomotief() {
		return locomotief;
	}
	
	public void setLocomotief(Locomotief locomotief) {
		this.locomotief = locomotief;
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
		try {
			wagonDao.remove(this);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

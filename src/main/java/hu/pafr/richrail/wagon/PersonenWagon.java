package hu.pafr.richrail.wagon;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.database.WagonDao;
import hu.pafr.richrail.database.WagonDaoImpl;
import hu.pafr.richrail.locomotief.Locomotief;

public class PersonenWagon implements Cloneable, Wagon {
	private WagonDao wagonDao = new WagonDaoImpl();

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

	@Override
	public boolean remove() throws FileNotFoundException {
		return wagonDao.remove(this);
	}

	public boolean moveWagon(Locomotief locomotief) throws FileNotFoundException {
		this.setLocomotief(locomotief);
		wagonDao.update(this);
		return false;
	}

	public void setLocomotief(Locomotief locomotief) {
		this.locomotief = locomotief;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Wagon wagon = (Wagon) super.clone();
		wagon.setNaam(naam + "(1)");
		try {
			wagon.save();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return wagon;
	}

	public void save() throws FileNotFoundException {
		WagonDao wagonDao = new WagonDaoImpl();
		wagonDao.save(this);
	}

	public boolean update() throws FileNotFoundException {
		return wagonDao.update(this);
	};

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

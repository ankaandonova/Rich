package hu.pafr.richrail.spoor;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.database.LocomotiefDao;
import hu.pafr.richrail.database.LocomotiefDaoImpl;
import hu.pafr.richrail.database.SpoorDao;
import hu.pafr.richrail.database.SpoorDaoImpl;
import hu.pafr.richrail.locomotief.Locomotief;

public class Spoor {
	SpoorDao spoorDao = new SpoorDaoImpl();
	LocomotiefDao locomotiefDao = new LocomotiefDaoImpl();

	List<Locomotief> locomotiefen = new ArrayList<Locomotief>();
	private int nummer;
	private Double lengte;

	public Spoor(int nummer, Double lengte) {
		this.nummer = nummer;
		this.lengte = lengte;
	}

	public List<Spoor> getSporen() throws FileNotFoundException {
		return spoorDao.getSporen();
	}

	public boolean update() throws FileNotFoundException {
		return spoorDao.update(this);
	}

	public void remove() throws FileNotFoundException {
		spoorDao.remove(this);
	}

	public void save() throws FileNotFoundException {
		spoorDao.save(this);
	}

	public void getLocomotiefenFromDatabase() {
		try {
			for (Locomotief locomotef : locomotiefDao.getLocomotiefen()) {
				System.out.println("==================" + nummer);
				System.out.println(locomotef.getNaam());
				if (locomotef.getSpoor() != null) {
					// de locomotief heeft een spoor
					System.out.println(locomotef.getSpoor().getNummer());
					if (locomotef.getSpoor().getNummer() == nummer) {

						// de locomotief hoort bij het spoor
						locomotiefen.add(locomotef);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addLocomotief(Locomotief locomotief) {
		locomotiefen.add(locomotief);
		System.out.println(locomotiefen);
	}

	public List<Locomotief> getLocomotiefen() {
		return locomotiefen;
	}

	public void verwijderLocomotief(Locomotief locomotief) {
		locomotiefen.remove(locomotief);
		System.out.println(locomotiefen);
	}

	public int getNummer() {
		return nummer;
	}

	public void setNummer(int nummer) {
		this.nummer = nummer;
	}

	public Double getLengte() {
		return lengte;
	}

	public void setLengte(Double lengte) {
		this.lengte = lengte;
	}

}

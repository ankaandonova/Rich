package hu.pafr.richrail.spoor;

import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.locomotief.Locomotief;

public class Spoor {

	private List<Locomotief> locomotiefen = new ArrayList<Locomotief>();
	
	private int nummer;
	private Double lengte;

	public Spoor(int nummer, Double lengte) {
		this.nummer = nummer;
		this.lengte = lengte;
	}

	public void addLocomotief(Locomotief locomotief) {
		locomotiefen.add(locomotief);
		System.out.println(locomotiefen);
	}
	
	public List<Locomotief> getLocomotiefen(){
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

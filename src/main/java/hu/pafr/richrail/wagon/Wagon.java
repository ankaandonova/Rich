package hu.pafr.richrail.wagon;

import java.io.FileNotFoundException;

import hu.pafr.richrail.locomotief.Locomotief;

public interface Wagon {

	public Object clone() throws CloneNotSupportedException;

	public boolean moveWagon(Locomotief locomotief) throws FileNotFoundException;
	
	public String getNaam();

	public void setNaam(String naam);

	public int getStoelen();

	public void setStoelen(int stoelen);

	public int getBedden();

	public void setBedden(int bedden);

	public void remove();

	public Locomotief getLocomotief();

	public void setLocomotief(Locomotief locomotief);
}

package hu.pafr.richrail.wagon;

public interface Wagon {

	public Object clone() throws CloneNotSupportedException;

	public String getNaam();

	public void setNaam(String naam);

	public int getStoelen();

	public void setStoelen(int stoelen);

	public int getBedden();

	public void setBedden(int bedden);

	public void remove();
}

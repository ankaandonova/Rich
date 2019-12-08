package hu.pafr.richrail.wagon;

public interface Builder {
	public void reset();
	public void setNaam(String naam);
	public void setStoel(int stoelen);
	public void setBed (int bedden);
	public Wagon build();
}
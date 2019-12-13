package hu.pafr.richrail;

import java.io.FileNotFoundException;
import java.util.List;

import hu.pafr.richrail.database.LocomotiefDao;
import hu.pafr.richrail.database.LocomotiefDaoImpl;
import hu.pafr.richrail.database.SpoorDao;
import hu.pafr.richrail.database.SpoorDaoImpl;
import hu.pafr.richrail.database.WagonDao;
import hu.pafr.richrail.database.WagonDaoImpl;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class App {
	public static void main(String[] args) throws CloneNotSupportedException, FileNotFoundException {
		SpoorDao spoorDao = new SpoorDaoImpl();
		LocomotiefDao locomotiefDao = new LocomotiefDaoImpl();
		WagonDao wagonDao = new WagonDaoImpl();
		
		Builder builder = new LocomotiefBuilder();
		Factory factory = new WagonFactory();
		
		Spoor spoor = new Spoor(5, 302.0);
		
		builder.setNaam("trein2");
		builder.setVertrekPunt("sssssterdam");
		builder.setEindBestemming("sdadsahjdbashjdsrecht");
		builder.setType_moter( "diesel");
		builder.setHoogte(2.00);
		builder.setLengte(20.00);
		builder.setGps(true);
		builder.setMax_snelheid(1520.00);
		builder.setStoelen(20);
		Locomotief locomotief = builder.build();
		locomotief.setSpoor(spoor);
		
		Wagon wagon = factory.createWagon("wagon2", 9228, 0);
		locomotief.setWagon(wagon);
		
		spoor.addLocomotief(locomotief);
		
		
	//save 
		spoorDao.save(spoor);
		//locomotiefDao.save(locomotief);
		//wagonDao.save(wagon);

	//get hier word gwn het object opgehaalt met al zijn attributen
		//hier worden de locmotieven en wagonnen niet bij opgehaalt
			//System.out.println(wagonDao.getWagon(wagon).getStoelen());
		//hier worden de wagonnen niet bij opgehaalt
			//System.out.println(locomotiefDao.getLocomotief(locomotief).getStoelen());
		//hier worden de wagon wel bij opgehaalt :P
			//System.out.println(spoorDao.getSpoor(spoor).getLengte());
		
	//getAlle 
		//hier worden de locmotieven en wagonnen niet bij opgehaalt
			//List<Spoor> sporen = spoorDao.getSporen();
		//hier worden de wagonnen niet bij opgehaalt
			//List<Locomotief> locomotiefen = locomotiefDao.getLocomotiefen();
		//hier worden de wagonnnen wel bij opgehaalt :P
			//List<Wagon> wagonnen = wagonDao.getWagonnen();
		
	//remove
		//System.out.println(wagonDao.remove(wagon));
		//System.out.println(locomotiefDao.remove(locomotief));
		
	//update
		//System.out.println(locomotiefDao.update(locomotief));
		//System.out.println(wagonDao.update(wagon));

		//move
		System.out.println(wagon.moveWagon(locomotief));
//		System.out.println(locomotief.moveLocomotief());
		
	}
}

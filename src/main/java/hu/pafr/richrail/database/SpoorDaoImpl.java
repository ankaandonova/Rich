package hu.pafr.richrail.database;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.spoor.Spoor;

public class SpoorDaoImpl implements SpoorDao{
	Database database = Database.getDatabase();
	LocomotiefDao locomotiefDaoImpl = new LocomotiefDaoImpl();
	WagonDao wagonDaoimpl = new WagonDaoImpl();

	@SuppressWarnings("unchecked")
	public void save(Spoor spoor) throws FileNotFoundException {
		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray sporen = (JSONArray) databaseObject.get("sporen");
		
		JSONObject spoorObject = new JSONObject();
		spoorObject.put("lengte", spoor.getLengte());
		spoorObject.put("lengte", spoor.getNummer());
		
		JSONArray locomotiefen = new JSONArray();
		for (Locomotief locomotief : spoor.getLocomotiefen()) {
			locomotiefen.add(locomotiefDaoImpl.createLocomotiefJSONObject(locomotief));
		}
		spoorObject.put("locomotiefen", locomotiefen);
		sporen.add(spoorObject);
		database.setDatabaseJson(databaseObject);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Spoor> getSporen() throws FileNotFoundException {
		List<Spoor> sporen = new ArrayList<Spoor>();

		JSONObject databaseObject = database.getDatabaseJson();
		JSONArray sporenJson = (JSONArray) databaseObject.get("sporen");
		Iterator iterator = sporenJson.iterator();
		while (iterator.hasNext()) {
			JSONObject spooor = (JSONObject) iterator.next();
			System.out.println("========================");
			System.out.println(spooor.get("lengte"));
//			for(JSONObject lococmotief : (JSONArray) spooor.get("locomotiefen")) {
//				Locomotief locomotief = locomotiefDaoImpl.getLocomotiefenFromJsonObject(spooor);;
//			}
//			
		}

		return sporen;
	}

	@SuppressWarnings("rawtypes")
	private Spoor getSporenFromJsonObject(JSONObject spoorJson) {
		int nummer = Integer.parseInt((String) spoorJson.get("nummer"));
		Double lengte = (Double) spoorJson.get("lengte");
		Spoor spoor = new Spoor(nummer, lengte);

		JSONArray alleLocomotiefen = (JSONArray) spoorJson.get("locomotiefen");
		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			Locomotief locomotief = locomotiefDaoImpl.getLocomotiefenFromJsonObject((JSONObject) iterator.next());
			spoor.addLocomotief(locomotief);
		}
		return spoor;
	}

	@SuppressWarnings("rawtypes")
	private Spoor getLocomotiefenFromSpoorObject(JSONObject spoorJson) {
		int nummer = Integer.parseInt((String) spoorJson.get("nummer"));
		Double lengte = (Double) spoorJson.get("lengte");
		Spoor spoor = new Spoor(nummer, lengte);

		JSONArray alleLocomotiefen = (JSONArray) spoorJson.get("locomotiefen");
		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			Locomotief locomotief = getLocomotiefenFromJsonObject((JSONObject) iterator.next());
			spoor.addLocomotief(locomotief);
		}
		return spoor;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void getLocomotiefFromSpoor(Spoor spoor) {
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("database.json")) {
			//haalt alle sporen uit de database op
			Object obj = jsonParser.parse(reader);
			JSONArray alleSporen = (JSONArray) obj;
			Iterator iterator = alleSporen.iterator();
			while (iterator.hasNext()) {
				//gaat elk spoor langs
				JSONObject spoorJson = (JSONObject) iterator.next();
				int nummer = Integer.parseInt((String) spoorJson.get("nummer"));
				System.out.println("spoor" + nummer);
				if(nummer == spoor.getNummer()) {
					System.out.println(spoor.getNummer()+" gelijk met "+nummer);
					//als het nummer van het meegegeven spoor gelijk is aan het geloopte spoornummer
					JSONArray alleLocomotiefen = (JSONArray) spoorJson.get("locomotiefen");
					Iterator iterator2 = alleLocomotiefen.iterator();
					while (iterator2.hasNext()) {
						//locomotieven van het spoor uit de database worden doorgegaan
						Locomotief locomotief = locomotiefDaoImpl.getLocomotiefenFromJsonObject((JSONObject) iterator2.next());
						spoor.addLocomotief(locomotief);
					}	
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}

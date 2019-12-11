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

import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class Database {
	private static Database database;

	private Database() {
		
	}
	
	public static Database getDatabase() {
		if (Database.database == null) {
			Database.database = new Database();
		}
		return Database.database;
	}

	@SuppressWarnings("unchecked")
	public void opslaan(List<Spoor> sporen) {
		JSONArray sporenJson = new JSONArray();
		for (Spoor spoor : sporen) {
			JSONObject spoorObject = new JSONObject();
			spoorObject.put("nummer", Integer.toString(spoor.getNummer()));
			spoorObject.put("lengte", spoor.getLengte());
			JSONArray locomotiefen = new JSONArray();
			for (Locomotief locomotief : spoor.getLocomotiefen()) {
				locomotiefen.add(createLocomotiefJSONObject(locomotief));
			}
			spoorObject.put("locomotiefen", locomotiefen);
			sporenJson.add(spoorObject);
		}
		// naar JSON file schrijven
		try (FileWriter file = new FileWriter("database.json")) {
			file.write(sporenJson.toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private JSONObject createLocomotiefJSONObject(Locomotief locomotief) {
		JSONObject locomotiefJson = new JSONObject();
		locomotiefJson.put("naam", locomotief.getNaam());
		locomotiefJson.put("vertrekpunt", locomotief.getVertrekPunt());
		locomotiefJson.put("eindpunt", locomotief.getEindBestemming());
		locomotiefJson.put("lengte", locomotief.getLengte());
		locomotiefJson.put("hoogte", locomotief.getHoogte());
		locomotiefJson.put("gps", locomotief.isGps());
		locomotiefJson.put("max_snelheid", locomotief.getMax_snelheid());
		locomotiefJson.put("stoelen", Integer.toString(locomotief.getStoelen()));
		locomotiefJson.put("type_moter", locomotief.getType_moter());
		JSONArray wagons = new JSONArray();
		for (Wagon wagon : locomotief.getWagons()) {
			wagons.add(createWagonJSONObject(wagon));
		}
		locomotiefJson.put("wagons", wagons);
		return locomotiefJson;
	}

	@SuppressWarnings("unchecked")
	private JSONObject createWagonJSONObject(Wagon wagon) {
		JSONObject wagonJson = new JSONObject();
		wagonJson.put("naam", wagon.getNaam());
		wagonJson.put("bedden", Integer.toString(wagon.getBedden()));
		wagonJson.put("stoelen", Integer.toString(wagon.getStoelen()));
		return wagonJson;
	}

	@SuppressWarnings({ "rawtypes" })
	public List<Spoor> lezen() {
		JSONParser jsonParser = new JSONParser();
		List<Spoor> sporen = new ArrayList<Spoor>();
		try (FileReader reader = new FileReader("database.json")) {
			Object obj = jsonParser.parse(reader);
			JSONArray alleSporen = (JSONArray) obj;
			Iterator iterator = alleSporen.iterator();
			while (iterator.hasNext()) {
				sporen.add(getSporenFromJsonObject((JSONObject) iterator.next()));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
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
			Locomotief locomotief = getLocomotiefenFromJsonObject((JSONObject) iterator.next());
			spoor.addLocomotief(locomotief);
		}
		return spoor;
	}

	@SuppressWarnings({ "rawtypes" })
	private Locomotief getLocomotiefenFromJsonObject(JSONObject locomotiefJson) {
		String naam = (String) locomotiefJson.get("naam");
		System.out.println(locomotiefJson);
		String type_motor = (String) locomotiefJson.get("type_moter");
		int stoelen = Integer.parseInt((String) locomotiefJson.get("stoelen"));
		String vertrekpunt = (String) locomotiefJson.get("vertrekpunt");
		String eindBestemming = (String) locomotiefJson.get("eindpunt");
		Double lengte = (Double) locomotiefJson.get("lengte");
		Double hoogte = (Double) locomotiefJson.get("hoogte");
		boolean gps = (boolean) locomotiefJson.get("gps");
		Double max_snelheid = (Double) locomotiefJson.get("max_snelheid");

		Builder locomotiefBuilder = new LocomotiefBuilder();
		locomotiefBuilder.setNaam(naam);
		locomotiefBuilder.setVertrekPunt(vertrekpunt);
		locomotiefBuilder.setEindBestemming(eindBestemming);
		locomotiefBuilder.setType_moter(type_motor);
		locomotiefBuilder.setHoogte(hoogte);
		locomotiefBuilder.setLengte(lengte);
		locomotiefBuilder.setGps(gps);
		locomotiefBuilder.setMax_snelheid(max_snelheid);
		locomotiefBuilder.setStoelen(stoelen);
		Locomotief locomotief = locomotiefBuilder.build();
		
		List<Wagon> wagons = new ArrayList<Wagon>();
		JSONArray alleWagons = (JSONArray) locomotiefJson.get("wagons");
		Iterator iterator = alleWagons.iterator();
		while (iterator.hasNext()) {
			wagons.add(getWagonsFromJsonObject((JSONObject) iterator.next()));
		}
		locomotief.setWagons(wagons);
		return locomotief;
	}

	private Wagon getWagonsFromJsonObject(JSONObject wagonJson) {
		String naam = (String) wagonJson.get("naam");
		System.out.println("wagon "+(String) wagonJson.get("bedden"));
		System.out.println("wagonJson "+ wagonJson);
		int bedden = Integer.parseInt((String) wagonJson.get("bedden"));
		int stoelen = Integer.parseInt((String) wagonJson.get("stoelen"));
		
		Factory factory = new WagonFactory();
		return factory.createWagon(naam, stoelen, bedden);
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
						Locomotief locomotief = getLocomotiefenFromJsonObject((JSONObject) iterator2.next());
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
	

	@SuppressWarnings({ "rawtypes" })
	public void getWagonsFromLocomotief(Locomotief locomotief){
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("database.json")) {
			//haalt alle sporen uit de database op
			Object obj = jsonParser.parse(reader);
			JSONArray alleSporen = (JSONArray) obj;
			Iterator iterator = alleSporen.iterator();
			while (iterator.hasNext()) {
				//gaat elk spoor langs
				JSONObject spoorJson = (JSONObject) iterator.next();				
				JSONArray alleLocomotiefen = (JSONArray) spoorJson.get("locomotiefen");
				Iterator iterator2 = alleLocomotiefen.iterator();
				while (iterator2.hasNext()) {
					//locomotieven van het spoor uit de database worden doorgegaan
					JSONObject locomotiefJson = (JSONObject) iterator2.next();
					Locomotief locomotief1 = getLocomotiefenFromJsonObject(locomotiefJson);
					if(locomotief1.getNaam().equals(locomotief.getNaam())) {
						// de locomotief is gevonden in de database
						JSONArray alleWagonsVanLocomotief = (JSONArray) locomotiefJson.get("wagons");
						Iterator iterator3 = alleWagonsVanLocomotief.iterator();
						while (iterator3.hasNext()) {
							locomotief.voegWagonToe(getWagonsFromJsonObject((JSONObject) iterator3.next()));
						}
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
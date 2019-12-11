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
import hu.pafr.richrail.wagon.Wagon;

public class LocomotiefDaoImpl implements LocomotiefDao {
	
	Database database = Database.getDatabase();
	WagonDao wagonDaoimpl = new WagonDaoImpl();
	
	@SuppressWarnings("unchecked")
	public void save(Locomotief locomotief) throws FileNotFoundException {		
		JSONObject databaseObject = database.getDatabaseJson();
		
		JSONArray locomotiefen = (JSONArray) databaseObject.get("locomotiefen");
		
		JSONObject locomotiefJson = createLocomotiefJSONObject(locomotief);
		locomotiefen.add(locomotiefJson);
		
		database.setDatabaseJson(databaseObject);
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject createLocomotiefJSONObject(Locomotief locomotief) {
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
		JSONArray wagonnen = new JSONArray();
		for (Wagon wagon : locomotief.getWagons()) {
			wagonnen.add(wagonDaoimpl.createWagonJSONObject(wagon));
		}
		locomotiefJson.put("wagonnen", wagonnen);
		return locomotiefJson;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public Locomotief getLocomotiefenFromJsonObject(JSONObject locomotiefJson) {
		String naam = (String) locomotiefJson.get("naam");
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
			wagons.add(wagonDaoimpl.getWagonsFromJsonObject((JSONObject) iterator.next()));
		}
		locomotief.setWagons(wagons);
		return locomotief;
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

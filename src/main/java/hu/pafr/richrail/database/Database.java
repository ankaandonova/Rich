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

import hu.pafr.richrail.locomotief.Factory;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefFactory;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonBuilder;

public class Database {
	@SuppressWarnings("unchecked")
	public static void opslaan(List<Spoor> sporen) {
		JSONArray sporenJson = new JSONArray();
		for (Spoor spoor : sporen) {
			JSONObject spoorObject = new JSONObject();
			JSONObject spoorJson = new JSONObject();
			spoorJson.put("nummer", spoor.getNummer());
			spoorJson.put("lengte", spoor.getLengte());
			JSONArray locomotiefen = new JSONArray();
			for (Locomotief locomotief : spoor.getLocomotiefen()) {
				locomotiefen.add(createLocomotiefJSONObject(locomotief));
			}
			spoorJson.put("locomotiefen", locomotiefen);
			spoorObject.put("spoor", spoorJson);
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
	private static JSONObject createLocomotiefJSONObject(Locomotief locomotief) {
		JSONObject locomotiefJson = new JSONObject();
		locomotiefJson.put("naam", locomotief.getNaam());
		locomotiefJson.put("vertrekpunt", locomotief.getVertrekPunt());
		locomotiefJson.put("eindpunt", locomotief.getEindBestemming());
		locomotiefJson.put("eindpunt", locomotief.getHoogte());
		locomotiefJson.put("lengte", locomotief.getLengte());
		locomotiefJson.put("hoogte", locomotief.getHoogte());
		locomotiefJson.put("gps", locomotief.isGps());
		locomotiefJson.put("max_snelheid", locomotief.getMax_snelheid());
		locomotiefJson.put("stoelen", locomotief.getStoelen());
		locomotiefJson.put("type_moter", locomotief.getType_moter());
		JSONArray wagons = new JSONArray();
		for (Wagon wagon : locomotief.getWagons()) {
			wagons.add(createWagonJSONObject(wagon));
		}
		locomotiefJson.put("wagons", wagons);
		return locomotiefJson;
	}

	@SuppressWarnings("unchecked")
	private static JSONObject createWagonJSONObject(Wagon wagon) {
		JSONObject wagonJson = new JSONObject();
		wagonJson.put("naam", wagon.getNaam());
		wagonJson.put("bedden", wagon.getBedden());
		wagonJson.put("stoelen", wagon.getStoelen());
		return wagonJson;
	}

	@SuppressWarnings({ "rawtypes" })
	public static List<Spoor> lezen() {
		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader("database.json")) {
			Object obj = jsonParser.parse(reader);
			List<Spoor> sporen = new ArrayList<Spoor>();
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
		return null;
	}

	@SuppressWarnings("rawtypes")
	private static Spoor getSporenFromJsonObject(JSONObject spoorJson) {
		System.out.println(spoorJson);
		JSONObject spoorObject = (JSONObject) spoorJson.get("spoor");
		System.out.println(spoorObject);
		int nummer = Integer.parseInt((String) spoorObject.get("nummer"));
		Double lengte = (Double) spoorObject.get("lengte");
		Spoor spoor = new Spoor(nummer, lengte);

		List<Locomotief> locomotiefen = new ArrayList<Locomotief>();
		JSONArray alleLocomotiefen = (JSONArray) spoorObject.get("locomotiefen");
		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			locomotiefen.add(getLocomotiefenFromJsonObject((JSONObject) iterator.next()));
		}
		return spoor;
	}

	@SuppressWarnings({ "rawtypes" })
	private static Locomotief getLocomotiefenFromJsonObject(JSONObject locomotiefJson) {
		String naam = (String) locomotiefJson.get("naam");
		String type_motor = (String) locomotiefJson.get("type_motor");
		int stoelen = Integer.parseInt((String) locomotiefJson.get("stoelen"));
		String vertrekpunt = (String) locomotiefJson.get("vertrekpunt");
		String eindBestemming = (String) locomotiefJson.get("eindpunt");
		Double lengte = (Double) locomotiefJson.get("lengte");
		Double hoogte = (Double) locomotiefJson.get("hoogte");
		boolean gps = (boolean) locomotiefJson.get("gps");
		Double max_snelheid = (Double) locomotiefJson.get("max_snelheid");

		Factory factory = new LocomotiefFactory();
		Locomotief locomotief = factory.createLocomotief(naam, vertrekpunt, eindBestemming, type_motor, hoogte, lengte,
				gps, max_snelheid, stoelen);

		List<Wagon> wagons = new ArrayList<Wagon>();
		JSONArray alleWagons = (JSONArray) locomotiefJson.get("wagons");
		Iterator iterator = alleWagons.iterator();
		while (iterator.hasNext()) {
			wagons.add(getWagonsFromJsonObject((JSONObject) iterator.next()));
		}
		locomotief.setWagons(wagons);
		return locomotief;
	}

	private static Wagon getWagonsFromJsonObject(JSONObject wagonJson) {
		String naam = (String) wagonJson.get("naam");
		int bedden = Integer.parseInt((String) wagonJson.get("bedden"));
		int stoelen = Integer.parseInt((String) wagonJson.get("stoelen"));
		WagonBuilder wagonBuilder = new WagonBuilder();
		wagonBuilder.setNaam(naam);
		wagonBuilder.setBed(bedden);
		wagonBuilder.setStoel(stoelen);
		return wagonBuilder.build();
	}
}
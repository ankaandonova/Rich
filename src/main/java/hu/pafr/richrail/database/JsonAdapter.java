package hu.pafr.richrail.database;

import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class JsonAdapter {

	@SuppressWarnings("rawtypes")
	public Spoor getSpoorFromJsonObject(JSONObject spoorJson) {
		int nummer = Integer.parseInt((String) spoorJson.get("nummer"));
		LocomotiefDao LocomotiefDaoImpl = new LocomotiefDaoImpl();
		Double lengte = (Double) spoorJson.get("lengte");
		Spoor spoor = new Spoor(nummer, lengte);

		JSONArray alleLocomotiefen = (JSONArray) spoorJson.get("locomotiefen");
		Iterator iterator = alleLocomotiefen.iterator();
		while (iterator.hasNext()) {
			Locomotief locomotief = LocomotiefDaoImpl.getLocomotiefenFromJsonObject((JSONObject) iterator.next());
			spoor.addLocomotief(locomotief);
		}
		return spoor;
	}

	@SuppressWarnings("unchecked")
	public JSONObject spoorToJson(Spoor spoor) {
		LocomotiefDao LocomotiefDaoImpl = new LocomotiefDaoImpl();
		JSONObject spoorObject = new JSONObject();
		spoorObject.put("nummer", Integer.toString(spoor.getNummer()));
		spoorObject.put("lengte", spoor.getLengte());
		JSONArray locomotiefen = new JSONArray();
		for (Locomotief locomotief : spoor.getLocomotiefen()) {
			locomotiefen.add(LocomotiefDaoImpl.createLocomotiefJSONObject(locomotief));
		}
		spoorObject.put("locomotiefen", locomotiefen);
		return spoorObject;
	}

	public Locomotief getLocomotiefFromJsonObject(JSONObject locomotiefJson) {
		Builder locomotiefBuilder = new LocomotiefBuilder();
		locomotiefBuilder.setNaam((String) locomotiefJson.get("naam"));
		locomotiefBuilder.setVertrekPunt((String) locomotiefJson.get("vertrekpunt"));
		locomotiefBuilder.setEindBestemming((String) locomotiefJson.get("eindpunt"));
		locomotiefBuilder.setType_moter((String) locomotiefJson.get("type_moter"));
		locomotiefBuilder.setHoogte((Double) locomotiefJson.get("hoogte"));
		locomotiefBuilder.setLengte((Double) locomotiefJson.get("lengte"));
		locomotiefBuilder.setGps((boolean) locomotiefJson.get("gps"));
		locomotiefBuilder.setMax_snelheid((Double) locomotiefJson.get("max_snelheid"));
		locomotiefBuilder.setStoelen(Integer.parseInt((String) locomotiefJson.get("stoelen")));
		Locomotief locomotief = locomotiefBuilder.build();

		Spoor spoor = new Spoor(Integer.parseInt((String) locomotiefJson.get("spoor")), 0.00);
		;
		locomotief.setSpoor(spoor);

		return locomotief;
	}

	@SuppressWarnings("unchecked")
	public JSONObject createLocomotiefJSONObject(Locomotief locomotief) {
		JSONObject locomotiefJson = new JSONObject();
		locomotiefJson.put("naam", locomotief.getNaam());
		Spoor spoor = locomotief.getSpoor();
		locomotiefJson.put("spoor", Integer.toString(spoor.getNummer()));

		locomotiefJson.put("naam", locomotief.getNaam());
		locomotiefJson.put("vertrekpunt", locomotief.getVertrekPunt());
		locomotiefJson.put("eindpunt", locomotief.getEindBestemming());
		locomotiefJson.put("lengte", locomotief.getLengte());
		locomotiefJson.put("hoogte", locomotief.getHoogte());
		locomotiefJson.put("gps", locomotief.isGps());
		locomotiefJson.put("max_snelheid", locomotief.getMax_snelheid());
		locomotiefJson.put("stoelen", Integer.toString(locomotief.getStoelen()));
		locomotiefJson.put("type_moter", locomotief.getType_moter());

		return locomotiefJson;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject createWagonJSONObject(Wagon wagon) {
		JSONObject wagonJson = new JSONObject();
		wagonJson.put("naam", wagon.getNaam());
		wagonJson.put("bedden", Integer.toString(wagon.getBedden()));
		wagonJson.put("stoelen", Integer.toString(wagon.getStoelen()));
		return wagonJson;
	}
	
	public Wagon getWagonsFromJsonObject(JSONObject wagonJson) {
		String naam = (String) wagonJson.get("naam");
		int bedden = Integer.parseInt((String) wagonJson.get("bedden"));
		int stoelen = Integer.parseInt((String) wagonJson.get("stoelen"));

		Factory factory = new WagonFactory();
		return factory.createWagon(naam, stoelen, bedden);
	}
}

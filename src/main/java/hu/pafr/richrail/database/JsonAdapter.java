package hu.pafr.richrail.database;

import org.json.simple.JSONObject;

import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;

public class JsonAdapter {

	public Spoor getSpoorFromJsonObject(JSONObject spoorJson) {
		String spornummerString = (String) spoorJson.get("nummer");
		if(spornummerString != null) {
			int nummer = Integer.parseInt(spornummerString);
			Double lengte = (Double) spoorJson.get("lengte");
			Spoor spoor = new Spoor(nummer, lengte);
			return spoor;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject spoorToJson(Spoor spoor) {
		JSONObject spoorObject = new JSONObject();
		spoorObject.put("nummer", Integer.toString(spoor.getNummer()));
		spoorObject.put("lengte", spoor.getLengte());
		return spoorObject;
	}

	public Locomotief getLocomotiefFromJsonObject(JSONObject locomotiefJson) {
		Builder locomotiefBuilder = new LocomotiefBuilder();
		String naam = (String) locomotiefJson.get("naam");
		System.out.println("naam " + naam);
		if (naam == null) {
			return null;
		} else {
			locomotiefBuilder.setNaam(naam);
			locomotiefBuilder.setVertrekPunt((String) locomotiefJson.get("vertrekpunt"));
			locomotiefBuilder.setEindBestemming((String) locomotiefJson.get("eindpunt"));
			locomotiefBuilder.setType_moter((String) locomotiefJson.get("type_moter"));
			locomotiefBuilder.setHoogte((Double) locomotiefJson.get("hoogte"));
			locomotiefBuilder.setLengte((Double) locomotiefJson.get("lengte"));
			locomotiefBuilder.setGps((boolean) locomotiefJson.get("gps"));
			locomotiefBuilder.setMax_snelheid((Double) locomotiefJson.get("max_snelheid"));
			locomotiefBuilder.setStoelen(Integer.parseInt((String) locomotiefJson.get("stoelen")));
			Locomotief locomotief = locomotiefBuilder.build();
			String spoornummer = (String) locomotiefJson.get("spoor");
			if (spoornummer != "") {
				Spoor spoor = new Spoor(Integer.parseInt(spoornummer), 0.00);
				locomotief.setSpoor(spoor);
			}
			return locomotief;
		}
	}

	@SuppressWarnings({ "unchecked", "null" })
	public JSONObject createLocomotiefJSONObject(Locomotief locomotief) {
		JSONObject locomotiefJson = new JSONObject();
		locomotiefJson.put("naam", locomotief.getNaam());

		Spoor spoor = locomotief.getSpoor();
		if (spoor == null) {
			locomotiefJson.put("spoor", "");
		} else {
			locomotiefJson.put("spoor", Integer.toString(spoor.getNummer()));
		}

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

	@SuppressWarnings({ "unchecked", "null" })
	public JSONObject createWagonJSONObject(Wagon wagon) {
		JSONObject wagonJson = new JSONObject();
		wagonJson.put("naam", wagon.getNaam());
		System.out.println("wagon naam " + wagon.getNaam());
		Locomotief locomotief = wagon.getLocomotief();
		if (locomotief == null) {
			wagonJson.put("locomotief", "");
		} else {
			wagonJson.put("locomotief", locomotief.getNaam());
		}
		wagonJson.put("bedden", Integer.toString(wagon.getBedden()));
		wagonJson.put("stoelen", Integer.toString(wagon.getStoelen()));
		System.out.println("wagonJson " + wagonJson);
		return wagonJson;
	}

	public Wagon getWagonsFromJsonObject(JSONObject wagonJson) {
		String naam = (String) wagonJson.get("naam");
		if (naam == null) {
			return null;
		} else {
			int bedden = Integer.parseInt((String) wagonJson.get("bedden"));
			int stoelen = Integer.parseInt((String) wagonJson.get("stoelen"));
			Factory factory = new WagonFactory();
			Wagon wagon = factory.createWagon(naam, stoelen, bedden);

			Builder locomotiefBuilder = new LocomotiefBuilder();
			locomotiefBuilder.setNaam((String) wagonJson.get("locomotief"));
			Locomotief locomotief = locomotiefBuilder.build();
			wagon.setLocomotief(locomotief);
			return wagon;
		}
	}
}

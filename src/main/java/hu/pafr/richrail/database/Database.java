package hu.pafr.richrail.database;

import java.io.FileNotFoundException;   
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	
	public JSONObject getDatabaseJson() throws FileNotFoundException {
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader("database.json")) {
			Object obj = jsonParser.parse(reader);
			JSONObject database1 = (JSONObject) obj;
			return database1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setDatabaseJson(JSONObject database1) {
		try (FileWriter file = new FileWriter("database.json")) {
			file.write(database1.toJSONString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
package hu.pafr.richrail.Gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class AppController extends BorderPane {
	public static void main(String[] args) { 	
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("App.fxml"));
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

}
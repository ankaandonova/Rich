package hu.pafr.richrail.Gui;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class GUIcmd extends Application{
	
	private Scene scene;
	private Group group;
	private TextField spoor;

	
	@Override
	public void start(Stage window) throws Exception{
		
		spoor = new TextField();
		spoor.setPrefColumnCount(50);
		spoor.setPrefHeight(200);
		group = new Group(spoor);
	
		scene = new Scene(group,  900, 900);
		
		window.setTitle("RichRail");
		window.setScene(scene);
		window.show();
		
	}

	public static void main (String[] args) {
		launch(args);
	}
}

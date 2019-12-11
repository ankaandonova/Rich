package hu.pafr.richrail.Gui;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import hu.pafr.richrail.database.Database;


import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Wagon;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUItest extends Application {

	private Scene scene;
	private BorderPane schermBorder;
	private static HBox spoor;

	@Override
	public void start(Stage window) throws Exception, InvocationTargetException {
		List<Spoor> sporen = getDataFromDataBase();
		schermBorder = new BorderPane();

		// draw
		spoor = creatHBox();
		schermBorder.setTop(spoor);

		// spoor
		schermBorder.setLeft(GUISpoor.createSpoorKeuzeMenu());

		// trein
		schermBorder.setCenter(GUIlocomotief.createLocomotiefKeuzeMenu());

		// wagon
		schermBorder.setRight(GUIWagon.createWagonKeuzeMenu());

		scene = new Scene(schermBorder, 1900, 900);

		window.setTitle("RichRail");
		window.setScene(scene);
		window.show();

	}

	public List<Spoor> getDataFromDataBase() {
		Database database = Database.getDatabase();
		System.out.println(database.lezen());
		return database.lezen();
		
	}
	
	static void creatTrain() {
		Image benzineLocomotiefImg = new Image("locomotief.jpg");
		spoor.getChildren().add(new ImageView(benzineLocomotiefImg));
	}

	static void createWagon(Wagon wagon) {
		if (wagon.getBedden()> 0) {
		
			Image slaapWagonImg = new Image("slaapwagon.jpg");
			spoor.getChildren().add(new ImageView(slaapWagonImg));
		}else if (wagon.getStoelen() >0) {
			Image personenWagonImg = new Image("peronenwagon.jpg");
			spoor.getChildren().add(new ImageView(personenWagonImg));
		}else {
			Image transportWagonfImg = new Image("transportwagon.jpg");
			spoor.getChildren().add(new ImageView(transportWagonfImg));	
		}

	}
	
	

	@SuppressWarnings("static-access")
	private HBox creatHBox() {
		HBox hbox = new HBox();
		schermBorder.setMargin(hbox, new Insets(5));
		hbox.prefWidthProperty().bind(schermBorder.widthProperty());
		hbox.prefHeightProperty().bind(schermBorder.heightProperty().subtract(500));
		hbox.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 1 0 ; -fx-font-weight: bold;");
		hbox.setAlignment(Pos.BASELINE_CENTER);
		return hbox;
	}



	public static void main(String[] args) {
		launch(args);
	}
}

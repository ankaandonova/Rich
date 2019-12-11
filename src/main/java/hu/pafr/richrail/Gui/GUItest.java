package hu.pafr.richrail.Gui;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import hu.pafr.richrail.database.Database;
import hu.pafr.richrail.locomotief.Locomotief;

import hu.pafr.richrail.spoor.Spoor;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUItest extends Application {

	private Scene scene;
	private BorderPane schermBorder;
	private HBox spoor;

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
		return database.lezen();
	}

	private void createLocomotief(Locomotief locomotief) {
		switch (locomotief.getType_moter()) {
		case "electrisch":
			Image electrisheLocomotiefImg = new Image("electrisch.jpg");
			spoor.getChildren().add(new ImageView(electrisheLocomotiefImg));
			break;
		case "benzine":
			Image benzineLocomotiefImg = new Image("benzine.jpg");
			spoor.getChildren().add(new ImageView(benzineLocomotiefImg));
			break;
		case "diesel":
			Image dieselLocomotiefImg = new Image("diesel.jpg");
			spoor.getChildren().add(new ImageView(dieselLocomotiefImg));
			break;
		}

	}
	
	private void creatTrain() {
		Image benzineLocomotiefImg = new Image("benzine.jpg");
		spoor.getChildren().add(new ImageView(benzineLocomotiefImg));
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

package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException; 
import java.lang.reflect.InvocationTargetException;

import hu.pafr.richrail.domein.locomotief.Locomotief;
import hu.pafr.richrail.domein.spoor.Spoor;
import hu.pafr.richrail.domein.wagon.Wagon;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {
	private Scene scene;
	private static BorderPane schermBorder;
	private static VBox scherm1;

	@Override
	public void start(Stage window) throws Exception, InvocationTargetException {
		schermBorder = new BorderPane();
		// draw

		scherm1 = creatVBox();
		ScrollPane sc = new ScrollPane();
		sc.setContent(scherm1);
		schermBorder.setTop(sc);
		

		// spoor
		schermBorder.setLeft(GUISpoor.createSpoorKeuzeMenu());
		// trein
		schermBorder.setCenter(GUIlocomotief.createLocomotiefKeuzeMenu());
		// wagon
		schermBorder.setRight(GUIWagon.createWagonKeuzeMenu());
		
		scene = new Scene(schermBorder, 1350, 1000);

		window.setTitle("RichRail");
		window.setScene(scene);
		window.show();

	}

	public static void createTrain(int spoorNummer) throws FileNotFoundException {
		scherm1.getChildren().clear();

		Spoor spoor = new Spoor(spoorNummer, 0.0);
		spoor.getLocomotiefenFromDatabase();

		VBox vb = new VBox();
		for (Locomotief locomotief : spoor.getLocomotiefen()) {
			final HBox hb = new HBox();
			Label locomotiefLbl = new Label(); 
			String naam = locomotief.getNaam();
			locomotiefLbl.setText(naam);
			locomotiefLbl.setMinWidth(100);
			ImageView locomotiefImg = createLocomotief();
			hb.getChildren().addAll(locomotiefLbl,locomotiefImg);

			locomotief.getWagonnenFromDatabase();
			for (Wagon wagon : locomotief.getWagons()) {
				ImageView wagonImg = createWagon(wagon);
				hb.getChildren().add(wagonImg);
			}
			hb.setSpacing(5);
			vb.getChildren().addAll(hb);		
		}
		

	
		scherm1.getChildren().addAll(vb);
	}
	
	
	private static ImageView createLocomotief() {

		Image LocomotiefImg = new Image("file:locomotief.jpg");
		ImageView imgVw = new ImageView();
		imgVw.setImage(LocomotiefImg);
		imgVw.setFitHeight(180);
		imgVw.setFitWidth(200);
		return imgVw;
	}

	private static ImageView createWagon(Wagon wagon) throws FileNotFoundException {
		if (wagon.getBedden() > 0) {
			Image slaapWagonImg = new Image("file:slaapwagon.jpg");
			ImageView imgVw = new ImageView();
			imgVw.setImage(slaapWagonImg);
			imgVw.setFitHeight(190);
			imgVw.setFitWidth(300);
			return imgVw;
		} else if (wagon.getStoelen() > 0) {
			Image personenWagonImg = new Image("file:personenwagon.jpg");
			ImageView imgVw = new ImageView();
			imgVw.setImage(personenWagonImg);
			imgVw.setFitHeight(120);
			imgVw.setFitWidth(300);
		
			return imgVw;
		} else {
			Image transportWagonfImg = new Image("file:transportwagon.jpg");
			ImageView imgVw = new ImageView();
			imgVw.setImage(transportWagonfImg);
			imgVw.setFitHeight(190);
			imgVw.setFitWidth(300);
			return imgVw;
		}
	}

	private static VBox creatVBox() {
		VBox vbox = new VBox();
		vbox.setPrefHeight(250);
		vbox.prefWidthProperty().bind(schermBorder.widthProperty());
		vbox.setStyle("  -fx-border-width: 1 1 1 1 ; -fx-font-weight: bold;");
		vbox.setAlignment(Pos.BASELINE_LEFT);
		return vbox;
	}

	public static void main(String[] args) {
		launch(args);
	}

}

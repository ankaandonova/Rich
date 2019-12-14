package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Wagon;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUItest extends Application {

	private Scene scene;
	private BorderPane schermBorder;

	private static VBox scherm1;

	@Override
	public void start(Stage window) throws Exception, InvocationTargetException {
		schermBorder = new BorderPane();
		// draw
		
	
		scherm1 = creatVBox();
	
		
		schermBorder.setTop(scherm1);
		
		 
		// spoor
		schermBorder.setLeft(GUISpoor.createSpoorKeuzeMenu());
		// trein
		schermBorder.setCenter(GUIlocomotief.createLocomotiefKeuzeMenu());
		// wagon
		schermBorder.setRight(GUIWagon.createWagonKeuzeMenu());

		scene = new Scene(schermBorder, 900, 900);

		window.setTitle("RichRail");
		window.setScene(scene);
		window.show();
		
	}


	static void createTrain(int spoorNummer) throws FileNotFoundException {
		scherm1.getChildren().clear();
		
		Spoor spoor = new Spoor(spoorNummer, 0.0);
		spoor.getLocomotiefenFromDatabase();
		
		for (Locomotief locomotief : spoor.getLocomotiefen()) {
			System.out.println("locomotief "+ locomotief.getNaam());
			locomotief.getWagonnenFromDatabase();
			for(Wagon wagon : locomotief.getWagons()) {
				System.out.println(wagon.getNaam());
			}
			Image LocomotiefImg = new Image("file:locomotief.jpg");
			ImageView imgVw = new ImageView();
			imgVw.setImage(LocomotiefImg);
			scherm1.getChildren().add(imgVw);
			  createWagon(locomotief.getNaam());
		}
	}
	
	static void createLocomotief() { {
			Image LocomotiefImg = new Image("file:locomotief.jpg");
			ImageView imgVw = new ImageView();
			imgVw.setImage(LocomotiefImg);
			scherm1.getChildren().add(imgVw);
		}
	}
	

	static void createWagon(String locomotiefNaam) throws FileNotFoundException {

		Builder builder = new LocomotiefBuilder();
		builder.setNaam(locomotiefNaam);
		Locomotief locomotief = builder.build();
		locomotief.getWagonnenFromDatabase();
		for (Wagon wagon : locomotief.getWagons()) {
			if (wagon.getBedden() > 0) {

				Image slaapWagonImg = new Image("file:slaapwagon.jpg");
				scherm1.getChildren().add(new ImageView(slaapWagonImg));
			} else if (wagon.getStoelen() > 0) {

				Image personenWagonImg = new Image("file:personenwagon.jpg");
				scherm1.getChildren().add(new ImageView(personenWagonImg));
			} else {

				Image transportWagonfImg = new Image("file:transportwagon.jpg");
				scherm1.getChildren().add(new ImageView(transportWagonfImg));
			}
		}
	}


	protected static VBox creatVBox() {
		VBox vbox = new VBox();
		vbox.setPrefWidth(450);
		vbox.setStyle(" -fx-border-style: dotted; -fx-border-width: 1 1 1 1 ; -fx-font-weight: bold;");
		vbox.setAlignment(Pos.BASELINE_LEFT);
		return vbox;
	}

	
	public static void main(String[] args) {
		launch(args);
	}

}

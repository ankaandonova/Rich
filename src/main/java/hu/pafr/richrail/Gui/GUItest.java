package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import hu.pafr.richrail.database.LocomotiefDao;
import hu.pafr.richrail.database.LocomotiefDaoImpl;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUItest extends Application {

	private Scene scene;
	private BorderPane schermBorder;
	private static HBox scherm;
	private static VBox scherm1;

	@Override
	public void start(Stage window) throws Exception, InvocationTargetException {

		schermBorder = new BorderPane();

		// draw
		scherm = createHBox();

		scherm1 = creatVBox();
		schermBorder.setTop(scherm);
		schermBorder.setTop(scherm1);
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

	static void createTrain(int spoor) throws FileNotFoundException {
		Spoor sporen = new Spoor(spoor, 0.0);
		
		LocomotiefDao locomotiefDao = new LocomotiefDaoImpl() ;
		locomotiefDao.getLocomotiefFromSpoor(sporen);
		
		for (Locomotief locomotief : sporen.getLocomotiefen()) {
			System.out.println("=======locomotief " + locomotief.getNaam());
			Image benzineLocomotiefImg = new Image("locomotief.jpg");
			scherm1.getChildren().add(new ImageView(benzineLocomotiefImg));
		}

	}

	static void createWagon(String locomotief) throws FileNotFoundException {
		
		Builder builder = new LocomotiefBuilder();
		Locomotief locomotiefen= builder.build();
		LocomotiefDao locomotiefDao = new LocomotiefDaoImpl() ;
		locomotiefDao.getWagonsFromLocomotief(locomotiefen);
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + locomotiefen.getWagons());
		for (Wagon wagon : locomotiefen.getWagons()) {
			
			if (wagon.getBedden() > 0) {
			
				Image slaapWagonImg = new Image("slaapwagon.jpg");
				scherm1.getChildren().add(new ImageView(slaapWagonImg));
			} else if (wagon.getStoelen() > 0) {
		
				Image personenWagonImg = new Image("personenwagon.jpg");
				scherm1.getChildren().add(new ImageView(personenWagonImg));
			} else {
				
				Image transportWagonfImg = new Image("transportwagon.jpg");
				scherm1.getChildren().add(new ImageView(transportWagonfImg));
			}
		}
	}

	@SuppressWarnings("static-access")
	private HBox createHBox() {
		HBox hbox = new HBox();
		schermBorder.setMargin(hbox, new Insets(5));
		hbox.prefWidthProperty().bind(schermBorder.widthProperty());
		hbox.prefHeightProperty().bind(schermBorder.heightProperty().subtract(500));
		hbox.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 1 0 ; -fx-font-weight: bold;");
		hbox.setAlignment(Pos.BASELINE_CENTER);
		return hbox;
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

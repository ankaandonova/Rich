package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Wagon;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUItest extends Application {

	private Scene scene;
	private BorderPane schermBorder;
	private static VBox scherm1;

	static List<Locomotief> values = new ArrayList<Locomotief>();
	static Label[] labels = new Label[values.size()];
	static ProgressBar[] pbs = new ProgressBar[values.size()];
	static ProgressIndicator[] pins = new ProgressIndicator[values.size()];
	static HBox hbs[] = new HBox[values.size()];
	
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

//	Builder builder = new LocomotiefBuilder();
//	builder.setNaam(locomotiefNaam);
//	Locomotief locomotief = builder.build();
//	locomotief.getWagonnenFromDatabase();

	public static void createTrain(int spoorNummer) throws FileNotFoundException {
		scherm1.getChildren().clear();
		
		Group root = new Group();
		Scene scene = new Scene(root, 300, 150);
		Spoor spoor = new Spoor(spoorNummer, 0.0);
		
		spoor.getLocomotiefenFromDatabase();
		values = spoor.getLocomotiefen();
		System.out.println("===========================");
		System.out.println("size " + values.size());
		for (int i = 0; i < values.size(); i++) {
			System.out.println(values);
			System.out.println(values.size()+1);
			System.out.println(i);
			final Label label = new Label();
			label.setText("progress:" + values.get(i));

			final ProgressBar pb  = new ProgressBar();
			//pb.setProgress(values.get(i));

			final ProgressIndicator pin = new ProgressIndicator();
			//pin.setProgress(values.get(i));
			
			final HBox hb = hbs[i] = new HBox();
			hb.setSpacing(5);
			hb.setAlignment(Pos.CENTER);
			hb.getChildren().addAll(label, pb, pin);
		}

		final VBox vb = new VBox();
		vb.setSpacing(5);
		vb.getChildren().addAll(hbs);
		scherm1.getChildren().add(vb);
	}

	public static ImageView createLocomotief() {

		Image LocomotiefImg = new Image("file:locomotief.jpg");
		ImageView imgVw = new ImageView();
		imgVw.setImage(LocomotiefImg);
		
		
		return imgVw;
	}

	public static ImageView createWagon(Wagon wagon) throws FileNotFoundException {
		if (wagon.getBedden() > 0) {
			Image slaapWagonImg = new Image("file:slaapwagon.jpg");
			return new ImageView(slaapWagonImg);
		} else if (wagon.getStoelen() > 0) {
			Image personenWagonImg = new Image("file:personenwagon.jpg");
			return new ImageView(personenWagonImg);
		} else {
			Image transportWagonfImg = new Image("file:transportwagon.jpg");
			return new ImageView(transportWagonfImg);
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

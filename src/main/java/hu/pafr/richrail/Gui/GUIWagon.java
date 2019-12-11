package hu.pafr.richrail.Gui;

import java.util.List;

import hu.pafr.richrail.database.Database;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.spoor.Spoor;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUIWagon {
	protected static Scene scene;
	protected static BorderPane schermBorder;
	protected static ChoiceBox<String> choiceWagon;
	protected static Button selectWagon;
	protected static Button deleteWagon;
	protected static Button addWagon;
	protected static TextField wagonNaam;
	protected static TextField wagonStoel;
	protected static TextField wagonBedden;
	protected static HBox spoor;
	protected static Wagon Wagon;

	public static VBox createWagonKeuzeMenu() {

		VBox Wagon_VBox = creatVBox();
		Wagon_VBox.getChildren().addAll(
				new Label("Kies een wagon"), 
				choiceWagon = new ChoiceBox<>(),
				selectWagon = new Button("select"),
				deleteWagon = new Button("delete"), 
				new Label("Naam"),
				wagonNaam = new TextField(), 
				new Label("Stoelen"), 
				wagonStoel = new TextField(), 
				new Label("Bedden"),
				wagonBedden = new TextField(), 
				addWagon = new Button("Add"));
		Spoor spoor1 = new Spoor(0, 0.0);
		WagonEventHandler(spoor1);
		return Wagon_VBox;
	}

	
	protected static void WagonEventHandler(Spoor spoor) {
		Database database = Database.getDatabase();
		
		for(Locomotief locomotief : database.getLocomotiefFromSpoor(spoor)) {
			choiceWagon.getItems().add(locomotief.getNaam());
		}
		

		selectWagon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getChoiceWagon(choiceWagon);
				// Locomotief locomotief = new LocomotiefFactory.createLocomotief();
				Factory factory = new WagonFactory();
				Wagon wagon = factory.createWagon(null, 0, 0);
				GUItest.createWagon(Wagon);
			}
		});

		// wagon verwijderen
		deleteWagon.setOnAction(e -> deleteChoiceWagon(choiceWagon));

		// nieuwe wagon toevoegen
		addWagon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				wagonNaam.getText();
				wagonStoel.getText();
				wagonBedden.getText();
				System.out.println(wagonNaam.getText() + wagonStoel.getText() + wagonBedden.getText());
			}
		});
	}

	protected static VBox creatVBox() {
		VBox vbox = new VBox();
		vbox.setPrefWidth(450);
		vbox.setStyle(" -fx-border-style: dotted; -fx-border-width: 1 1 1 1 ; -fx-font-weight: bold;");
		vbox.setAlignment(Pos.BASELINE_LEFT);
		return vbox;

	}

	public static void getChoiceWagon(ChoiceBox<String> choiceWagon) {
		String wagon = choiceWagon.getValue();
		System.out.print(wagon);
	}

	public static void deleteChoiceWagon(ChoiceBox<String> choiceWagon) {
		String wagon = choiceWagon.getValue();
		choiceWagon.getItems().remove(wagon);
	}

}

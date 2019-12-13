package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;

import hu.pafr.richrail.database.LocomotiefDao;
import hu.pafr.richrail.database.LocomotiefDaoImpl;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.wagon.Wagon;
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

	public static VBox createWagonKeuzeMenu() throws FileNotFoundException {

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
		Builder builder = new LocomotiefBuilder();
		Locomotief locomotief= builder.build();
		WagonEventHandler(locomotief);
		return Wagon_VBox;
	}

	
	protected static void WagonEventHandler(Locomotief locomotief) throws FileNotFoundException {
		//choiceWagon.getItems().clear();
		if(locomotief.getNaam() != null) {
			locomotief.getWagonnenFromDatabase();			
		}
		
		for(Wagon wagon : locomotief.getWagons()) {
			System.out.println("wagon in de database  " + wagon.getNaam());
			choiceWagon.setValue(wagon.getNaam());
			choiceWagon.getItems().add(wagon.getNaam());
		}
		

		selectWagon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getChoiceWagon(choiceWagon);
			
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
		System.out.print("choice wagon" +wagon);
	}

	public static void deleteChoiceWagon(ChoiceBox<String> choiceWagon) {
		String wagon = choiceWagon.getValue();
		choiceWagon.getItems().remove(wagon);
	}

}

package hu.pafr.richrail.Gui;

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
		WagonEventHandler();
		return Wagon_VBox;
	}
	protected static void WagonEventHandler() {
		choiceWagon.getItems().addAll("1", "2", "3");
		choiceWagon.setValue("1");
		selectWagon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getChoiceWagon(choiceWagon);

			}
		});
		
		//wagon verwijderen
		deleteWagon.setOnAction(e -> deleteChoiceWagon(choiceWagon));
		
		//nieuwe wagon toevoegen
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

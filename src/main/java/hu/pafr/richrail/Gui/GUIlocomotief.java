package hu.pafr.richrail.Gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.layout.VBox;

public class GUIlocomotief {
	protected static ChoiceBox<String> choiceLocomotief;
	protected static Button selectLocomotief;
	protected static Button deleteLocomotief;
	protected static Button addLocomotief;
	protected static TextField vertrekPunt;
	protected static TextField eindBestemming;
	protected static TextField typeMotor;
	protected static TextField gps;
	protected static TextField locomotiefStoelen;
	protected static TextField locomotiefLengte;
	protected static TextField locomotiefNaam;

	protected static VBox createLocomotiefKeuzeMenu() {

		VBox Locomotief_VBox = creatVBox();
		Locomotief_VBox.getChildren().addAll(
				new Label("Kies een locomotief"), 
				choiceLocomotief = new ChoiceBox<>(),
				selectLocomotief = new Button("select"), 
				deleteLocomotief = new Button("delete"), 
				new Label("Naam"),
				locomotiefNaam = new TextField(), 
				new Label("Vertrek punt"), 
				vertrekPunt = new TextField(),
				new Label("Eind bestemming"), 
				eindBestemming = new TextField(), 
				new Label("Type motor"),
				typeMotor = new TextField(), 
				new Label("GPS"), gps = new TextField(), 
				new Label("Lengte"),
				locomotiefLengte = new TextField(), 
				new Label("Stoelen"), 
				locomotiefStoelen = new TextField(),
				addLocomotief = new Button("Add"));
		LocomotiefEventHanler();
		return Locomotief_VBox;

	}

	protected static void LocomotiefEventHanler() {
		choiceLocomotief.getItems().addAll("5", "2", "3");
		choiceLocomotief.setValue("5");
		selectLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getChoiceLocomotief(choiceLocomotief);
				// Locomotief locomotief = new LocomotiefFactory.createLocomotief();

			}
		});
		deleteLocomotief.setOnAction(e -> deleteChoiceLocomotief(choiceLocomotief));

		addLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				locomotiefNaam.getText();
				vertrekPunt.getText();
				eindBestemming.getText();
				typeMotor.getText();
				gps.getText();
				locomotiefLengte.getText();
				locomotiefStoelen.getText();
				System.out.println(locomotiefNaam.getText() + vertrekPunt.getText() + eindBestemming.getText()
						+ typeMotor.getText() + gps.getText() + locomotiefLengte.getText()
						+ locomotiefStoelen.getText());
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

	protected static void getChoiceLocomotief(ChoiceBox<String> choiceLocmotief) {
		String locomotief = choiceLocmotief.getValue();
		System.out.print(locomotief);
	}

	protected static void deleteChoiceLocomotief(ChoiceBox<String> choiceLocmotief) {
		String locomotief = choiceLocmotief.getValue();
		choiceLocmotief.getItems().remove(locomotief);
	}

}

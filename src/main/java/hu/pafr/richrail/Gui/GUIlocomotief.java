package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.spoor.Spoor;
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

	protected static VBox createLocomotiefKeuzeMenu() throws FileNotFoundException {

		VBox Locomotief_VBox = creatVBox();
		Locomotief_VBox.getChildren().addAll(new Label("Kies een locomotief"), choiceLocomotief = new ChoiceBox<>(),
				selectLocomotief = new Button("select"), deleteLocomotief = new Button("delete"), new Label("Naam"),
				locomotiefNaam = new TextField(), new Label("Vertrek punt"), vertrekPunt = new TextField(),
				new Label("Eind bestemming"), eindBestemming = new TextField(), new Label("Type motor"),
				typeMotor = new TextField(), new Label("GPS"), gps = new TextField(), new Label("Lengte"),
				locomotiefLengte = new TextField(), new Label("Stoelen"), locomotiefStoelen = new TextField(),
				addLocomotief = new Button("Add"));

		Spoor spoor = new Spoor(0, 0.0);
		LocomotiefEventHanler(spoor);
		return Locomotief_VBox;

	}

	protected static void LocomotiefEventHanler(Spoor spoor) throws FileNotFoundException {
		choiceLocomotief.getItems().clear();
		if(spoor.getNummer() != 0) {
			System.out.println( spoor.getNummer());
			spoor.getLocomotiefenFromDatabase();
		}

		for (Locomotief locomotief : spoor.getLocomotiefen()) {
			System.out.println("locomotief " + locomotief.getNaam());
			choiceLocomotief.setValue(locomotief.getNaam());
			choiceLocomotief.getItems().add(locomotief.getNaam());
		}

		selectLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					getChoiceLocomotief(choiceLocomotief);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

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

	protected static void getChoiceLocomotief(ChoiceBox<String> choiceLocmotief) throws FileNotFoundException {
		String naam = choiceLocmotief.getValue();

		Builder builder = new LocomotiefBuilder();
		builder.setNaam(naam);
		Locomotief locomotief1 = builder.build();

		GUIWagon.WagonEventHandler(locomotief1);
	}

	protected static void deleteChoiceLocomotief(ChoiceBox<String> choiceLocmotief) {
		String locomotiefNaam = choiceLocmotief.getValue();
		choiceLocmotief.getItems().remove(locomotiefNaam);
		Builder builder = new LocomotiefBuilder();
		builder.setNaam(locomotiefNaam);
		Locomotief locomotief = builder.build();
		locomotief.remove();
	}

}

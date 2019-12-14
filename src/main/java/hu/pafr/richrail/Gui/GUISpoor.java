package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;
import hu.pafr.richrail.spoor.Spoor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GUISpoor {
	protected static ChoiceBox<String> choiceSpoor;
	protected static Button selectSpoor;
	protected static Button deleteSpoor;
	protected static Button addSpoor;
	protected static TextField spoorNummer;
	protected static TextField lengteSpoor;

	public static Pane createSpoorKeuzeMenu() throws FileNotFoundException {
		Pane paneSpoor = createPane();

		Label SpoorLbl = SpoorLbl();
		HBox Spoor_HBox = Spoor_HBox();
		VBox VBox = VBox();
		HBox HBox = HBox();

		paneSpoor.getChildren().addAll(SpoorLbl, Spoor_HBox, VBox, HBox);
		Spoor spoor = new Spoor(1, 0.0);
		SpoorEventHandler(spoor);
		return paneSpoor;
	}

	protected static void SpoorEventHandler(Spoor spoor) throws FileNotFoundException {
		// nieuwe spoor toevoegen
		addSpoor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Spoor spoor2 = new Spoor(0, 0.0);
				lengteSpoor.getText();
				spoorNummer.getText();

				spoor2.setNummer(Integer.parseInt(spoorNummer.getText()));
				spoor2.setLengte(Double.parseDouble(lengteSpoor.getText()));
				System.out.println("nieuwe spoor nummer  " + spoor2.getNummer());
				System.out.println("nieuwe spoor  " + spoor2);

				try {
					if (!spoor2.update()) {
						spoor2.save();
						choiceSpoor.getItems().add(Integer.toString(spoor2.getNummer()));
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		for (Spoor sporen : spoor.getSporen()) {

			choiceSpoor.getItems().add(Integer.toString(sporen.getNummer()));

			System.out.println(choiceSpoor.getValue());
		}

		// spoor kiezen
		selectSpoor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					getChoiceSpoor(choiceSpoor);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

				try {
					GUItest.createTrain(Integer.parseInt(choiceSpoor.getValue()));
				} catch (NumberFormatException | FileNotFoundException e2) {
					e2.printStackTrace();
				}

			}
		});

		// spoor verwijderen
		deleteSpoor.setOnAction(e ->

		{
			try {
				deleteChoiceSpoor(choiceSpoor);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		});
	}

	protected static Pane createPane() {
		Pane pane = new Pane();
		pane.setPrefWidth(450);
		pane.setStyle(" -fx-border-style: dotted; -fx-border-width: 1 1 1 1 ; -fx-font-weight: bold; -fx-background-color:white");
		return pane;
	}

	protected static Label SpoorLbl() {
		Label SpoorLbl = new Label("Kies een spoor");
		SpoorLbl.setLayoutX(5);
		SpoorLbl.setLayoutY(10);
		return SpoorLbl;
	}

	protected static HBox Spoor_HBox() {
		HBox Spoor_HBox = new HBox();
		Spoor_HBox.getChildren().addAll(choiceSpoor = new ChoiceBox<>(), selectSpoor = new Button("select"));
		Spoor_HBox.setLayoutX(5);
		Spoor_HBox.setLayoutY(40);
		return Spoor_HBox;
	}

	protected static VBox VBox() {
		VBox VBox = new VBox();
		VBox.getChildren().addAll(new Label("Spoor nummer"), spoorNummer = new TextField(), new Label("Lengte"),
				lengteSpoor = new TextField());

		VBox.setLayoutX(5);
		VBox.setLayoutY(65);
		return VBox;
	}

	protected static HBox HBox() {
		HBox HBox = new HBox();
		HBox.getChildren().addAll(deleteSpoor = new Button("delete"), addSpoor = new Button("Toevoegen/wijzigen"));

		HBox.setLayoutX(5);
		HBox.setLayoutY(170);
		return HBox;
	}

	public static void getChoiceSpoor(ChoiceBox<String> choiceSpoor) throws FileNotFoundException {
		String nummer = choiceSpoor.getValue();

		System.out.print("spooornummer " + nummer);
		Spoor spoor1 = new Spoor(Integer.parseInt(nummer), 0.0);
		GUIlocomotief.LocomotiefEventHanler(spoor1);
	}

	public static void deleteChoiceSpoor(ChoiceBox<String> choiceSpoor) throws FileNotFoundException {
		String spoorNummerString = choiceSpoor.getValue();
		choiceSpoor.getItems().remove(spoorNummerString);
		Spoor spoor = new Spoor(Integer.parseInt(spoorNummerString), 0.0);
		spoor.remove();
	}
}

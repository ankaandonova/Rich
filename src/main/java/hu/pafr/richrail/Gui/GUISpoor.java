package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;
import hu.pafr.richrail.spoor.Spoor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUISpoor {
	protected static ChoiceBox<String> choiceSpoor;
	protected static Button selectSpoor;
	protected static Button deleteSpoor;
	protected static Button addSpoor;
	protected static TextField spoorNummer;
	protected static TextField lengteSpoor;
	protected static Label spoorLbl;
	protected static Button cmd;

	public static Spoor geselecteerdeSpoor = new Spoor(0, 0.0);
	
	public static Pane createSpoorKeuzeMenu() throws FileNotFoundException {
		Pane paneSpoor = createPane();

		Label SpoorLbl = SpoorLbl();
		HBox Spoor_HBox = Spoor_HBox();
		VBox VBox = VBox();
		HBox HBox = HBox();
		Label spoorLbl = spoor();
		Button cmd = cmd();

		paneSpoor.getChildren().addAll(SpoorLbl, Spoor_HBox, VBox, HBox, spoorLbl,cmd);
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
				
				try {
					if (!spoor2.update()) {
						spoor2.save();
						choiceSpoor.getItems().add(Integer.toString(spoor2.getNummer()));
						choiceSpoor.setValue(Integer.toString(spoor2.getNummer()));
						getChoiceSpoor(choiceSpoor);
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		for (Spoor sporen : Spoor.getSporenFromDatabase()) {
			choiceSpoor.getItems().add(Integer.toString(sporen.getNummer()));
		}

		// spoor kiezen
		selectSpoor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					getChoiceSpoor(choiceSpoor);
					GUI.createTrain(Integer.parseInt(choiceSpoor.getValue()));
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
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
		
		
		cmd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				BorderPane schermBorder = new BorderPane();
				
				HBox hbox = new HBox();
				VBox vbox = new VBox();
				TextField text = new TextField();
				Button commit = new Button("commit");
				Label label = new Label("messege");
				commit.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						GUIcmd.setText(label, text);
					}
				});
	
				vbox= GUIcmd.createVBox();
				vbox.getChildren().addAll(label);
				hbox.getChildren().addAll(text, commit);
				
				schermBorder.setTop(vbox);
				schermBorder.setCenter(hbox);

				Scene secondScene = new Scene(schermBorder, 500, 300);
				
				Stage newWindow = new Stage();
				newWindow.setTitle("RichRail cmd");
				newWindow.setScene(secondScene);
				newWindow.show();
	
			}

	
		});
	}

	protected static Pane createPane() {
		Pane pane = new Pane();
		pane.setMinWidth(450);
		pane.setStyle(" -fx-border-width: 1 1 1 1 ; -fx-font-weight: bold; -fx-background-color:white");
		return pane;
	}

	protected static Label SpoorLbl() {
		Label SpoorLbl = new Label("Kies een spoor");
		SpoorLbl.setLayoutX(5);
		SpoorLbl.setLayoutY(40);
		return SpoorLbl;
	}

	protected static HBox Spoor_HBox() {
		HBox Spoor_HBox = new HBox();
		Spoor_HBox.getChildren().addAll(choiceSpoor = new ChoiceBox<>(), selectSpoor = new Button("select"));
		Spoor_HBox.setLayoutX(5);
		Spoor_HBox.setLayoutY(60);
		return Spoor_HBox;
	}

	protected static VBox VBox() {
		VBox VBox = new VBox();
		VBox.getChildren().addAll(new Label("Spoor nummer"), spoorNummer = new TextField(), new Label("Lengte"),
				lengteSpoor = new TextField());

		VBox.setLayoutX(5);
		VBox.setLayoutY(85);
		return VBox;
	}

	protected static HBox HBox() {
		HBox HBox = new HBox();
		HBox.getChildren().addAll(deleteSpoor = new Button("delete"), addSpoor = new Button("Toevoegen/wijzigen"));

		HBox.setLayoutX(5);
		HBox.setLayoutY(190);
		return HBox;
	}

	protected static Label spoor() {
		spoorLbl = new Label("spoor naam");
		spoorLbl.setLayoutX(5);
		spoorLbl.setLayoutY(0);

		spoorLbl.setStyle("-fx-font-size: 20; -fx-padding: 5 60 5 60; ");
		return spoorLbl;
	}
	
	protected static Button cmd() {
		cmd= new Button("cmd");
		cmd.setLayoutX(5);
		cmd.setLayoutY(400);
		return cmd;
	}



	public static void getChoiceSpoor(ChoiceBox<String> choiceSpoor) throws FileNotFoundException {
		String nummer = choiceSpoor.getValue();
		geselecteerdeSpoor = new Spoor(Integer.parseInt(nummer), 0.0);
		geselecteerdeSpoor = Spoor.getSpoorFromDatabase(geselecteerdeSpoor);
		spoorNummer.setText(Integer.toString(geselecteerdeSpoor.getNummer()));
		lengteSpoor.setText(Double.toString(geselecteerdeSpoor.getLengte()));

		spoorLbl.setText(nummer);

		Spoor spoor1 = new Spoor(Integer.parseInt(nummer), 0.0);
		GUIlocomotief.LocomotiefEventHanler(spoor1);
	}

	public static void deleteChoiceSpoor(ChoiceBox<String> choiceSpoor) throws FileNotFoundException {
		String spoorNummerString = choiceSpoor.getValue();
		choiceSpoor.getItems().remove(spoorNummerString);
		Spoor spoor = new Spoor(Integer.parseInt(spoorNummerString), 0.0);
		spoor.remove();
		
		spoorNummer.setText(null);
		lengteSpoor.setText(null);
		spoorLbl.setText(null);

		geselecteerdeSpoor = null;
		GUI.createTrain(0);
	}
}

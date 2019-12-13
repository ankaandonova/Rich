package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;
import java.util.List;

import hu.pafr.richrail.database.Database;
import hu.pafr.richrail.database.SpoorDao;
import hu.pafr.richrail.database.SpoorDaoImpl;
import hu.pafr.richrail.spoor.Spoor;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GUISpoor {
	protected static ChoiceBox<String> choiceSpoor;
	protected static Button selectSpoor;
	protected static Button deleteSpoor;
	protected static Button addSpoor;
	protected static TextField spoorNummer;
	protected static TextField lengteSpoor;


	public static VBox createSpoorKeuzeMenu() throws FileNotFoundException {
		VBox Spoor_VBox = creatVBox();
		Spoor_VBox.getChildren().addAll(
				new Label("Kies een spoor"), 
				choiceSpoor = new ChoiceBox<>(),
				selectSpoor = new Button("select"), 
				deleteSpoor = new Button("delete"), 
				new Label("Spoor nummer"),
				spoorNummer = new TextField(), 
				new Label("Lengte"), 
				lengteSpoor = new TextField(),
				addSpoor = new Button("Add"));
		Spoor spoor = new Spoor(1, 0.0);
		SpoorEventHandler(spoor);
		return Spoor_VBox;
	}
	

	
	protected static void SpoorEventHandler(Spoor spoor) throws FileNotFoundException {
		//nieuwe spoor toevoegen
		
		for (Spoor sporen: spoor.getSporen()) {
			addSpoor.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					Spoor spoor2 = new Spoor(0,0.0);
					lengteSpoor.getText();
					spoorNummer.getText();
					spoor2.setNummer(Integer.parseInt(spoorNummer.getText()));
					spoor2.setLengte(Double.parseDouble(lengteSpoor.getText()));
					System.out.println("nieuwe spoor nummer  "+ spoor2.getNummer());
					System.out.println("nieuwe spoor  " +spoor2);
				}
			});
			
			choiceSpoor.getItems().add(Integer.toString(sporen.getNummer()));

			System.out.println(choiceSpoor.getValue());
		}

		//spoor kiezen
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


		//spoor verweijderen
		deleteSpoor.setOnAction(e -> deleteChoiceSpoor(choiceSpoor));
	}
	
	protected static VBox creatVBox() {
		VBox vbox = new VBox();
		vbox.setPrefWidth(450);
		vbox.setStyle(" -fx-border-style: dotted; -fx-border-width: 1 1 1 1 ; -fx-font-weight: bold;");
		vbox.setAlignment(Pos.BASELINE_LEFT);
		return vbox;
	}

	public static void getChoiceSpoor(ChoiceBox<String> choiceSpoor) throws FileNotFoundException {
		String nummer = choiceSpoor.getValue();
		
		System.out.print("spooornummer "+nummer);
		Spoor spoor1 = new Spoor(Integer.parseInt(nummer), 0.0);
		GUIlocomotief.LocomotiefEventHanler(spoor1);
	}

	public static void deleteChoiceSpoor(ChoiceBox<String> choiceSpoor) {
		String spoor = choiceSpoor.getValue();
		choiceSpoor.getItems().remove(spoor);
	}
}

package hu.pafr.richrail.Gui;

import java.util.List;

import hu.pafr.richrail.database.Database;
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


	public static VBox createSpoorKeuzeMenu() {
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
				
		SpoorEventHandler();
		return Spoor_VBox;
	}
	private static List<Spoor> getDataFromDataBase() {
		Database database = Database.getDatabase();
		return database.lezen();
	}
	
	protected static void SpoorEventHandler() {
		List<Spoor> spoor = getDataFromDataBase();
		for (Spoor sporen: spoor) {
			choiceSpoor.getItems().add(Integer.toString(sporen.getNummer()));
			System.out.println(choiceSpoor.getValue());
		}

		//spoor kiezen
		selectSpoor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				getChoiceSpoor(choiceSpoor);

			}
		});

		//nieuwe spoor toevoegen
		addSpoor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				lengteSpoor.getText();
				spoorNummer.getText();
				System.out.println(lengteSpoor.getText() + spoorNummer.getText());
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

	public static void getChoiceSpoor(ChoiceBox<String> choiceSpoor) {
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

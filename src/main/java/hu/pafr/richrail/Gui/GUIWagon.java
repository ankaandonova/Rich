package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;

import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.wagon.Factory;
import hu.pafr.richrail.wagon.Wagon;
import hu.pafr.richrail.wagon.WagonFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class GUIWagon {
	protected static Scene scene;
	protected static ChoiceBox<String> choiceWagon;
	protected static ChoiceBox<String> choiceLosseWagon;
	protected static ChoiceBox<String> wisselVanLocomotief;
	protected static Button selectLosseWagon;
	protected static Button clone;
	protected static Button wissel;
	protected static Button loskoppelen;
	protected static Button selectWagon;
	protected static Button deleteWagon;
	protected static Button addWagon;
	protected static TextField wagonNaam;
	protected static TextField wagonStoel;
	protected static TextField wagonBedden;
	protected static Wagon geselecteerdeWagon;
	protected static Label wagon;

	public static Pane createWagonKeuzeMenu() throws FileNotFoundException {
		Pane paneWagon = createPane();

		Label SelectWagon = SelectWagon();
		HBox Wagon_HBox = Wagon_HBox();
		Label losseWagons = losseWagons();
		HBox HBox = HBox();
		VBox Wagon_VBox = Wagon_VBox();
		HBox hbox = hbox();
		Label wisselenLbl = wisselenLbl();
		HBox Wisselen_hbox = Wisselen_hbox();
		Label loskoppelenLbl = loskoppelenLbl();
		HBox Loskoppelen_hbox = Loskoppelen_hbox();
		Label wagon = wagon();

		paneWagon.getChildren().addAll(SelectWagon, Wagon_HBox, losseWagons, HBox, Wagon_VBox, hbox, wisselenLbl,
				Wisselen_hbox, loskoppelenLbl, Loskoppelen_hbox,wagon);

		Builder builder = new LocomotiefBuilder();
		Locomotief locomotief = builder.build();
		loadLocomotievenSwitch();
		WagonEventHandler(locomotief);
		return paneWagon;
	}

	protected static void loadLocomotievenSwitch() {
		for(Locomotief locomotief : Locomotief.getLocomotievenFromDatabase()){
			wisselVanLocomotief.getItems().add(locomotief.getNaam());
		}
		
		for(Wagon wagon : Wagon.getLosseWagonsFromDatabase()) {
			choiceLosseWagon.getItems().add(wagon.getNaam());
		}
		
		selectLosseWagon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					getGeselecteerdeWagon(choiceLosseWagon.getValue());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		wissel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Builder builder = new LocomotiefBuilder();
				builder.setNaam(wisselVanLocomotief.getValue());
				Locomotief locomotief = builder.build();
				try {
					geselecteerdeWagon.moveWagon(locomotief);
					GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		loskoppelen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					geselecteerdeWagon.moveWagon(null);
					WagonEventHandler(GUIlocomotief.geselecteerdeLocomotief);
					loadLocomotievenSwitch();
					GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		clone.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					Wagon wagon = (Wagon) geselecteerdeWagon.clone();
					try {
						wagon.save();
						if(wagon.getLocomotief() == null) {
							choiceLosseWagon.getItems().add(wagon.getNaam());						
							choiceLosseWagon.setValue(wagon.getNaam());			
						} else {
							choiceWagon.getItems().add(wagon.getNaam());						
							choiceWagon.setValue(wagon.getNaam());							
						}
						GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	protected static void WagonEventHandler(Locomotief locomotief) throws FileNotFoundException {
		choiceWagon.getItems().clear();
		if (locomotief.getNaam() != null) {
			locomotief.getWagonnenFromDatabase();
			Wagon laatsteWagon = null; 
			for (Wagon wagon : locomotief.getWagons()) {
				choiceWagon.setValue(wagon.getNaam());
				choiceWagon.getItems().add(wagon.getNaam());
				laatsteWagon = wagon;
			}
			if(laatsteWagon != null) {
				choiceWagon.setValue(laatsteWagon.getNaam());
				getGeselecteerdeWagon(choiceWagon.getValue());
			}
		}

		selectWagon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					getGeselecteerdeWagon(choiceWagon.getValue());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		// wagon verwijderen
		deleteWagon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String wagonNaam = choiceWagon.getValue();
				choiceWagon.getItems().remove(wagonNaam);
				Factory factory = new WagonFactory();
				Wagon wagon = factory.createWagon(wagonNaam, 0, 0);
				try {
					wagon.remove();
					GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		// nieuwe wagon toevoegen
		addWagon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Factory factory = new WagonFactory();
				Wagon wagon = factory.createWagon(wagonNaam.getText(), Integer.parseInt(wagonStoel.getText()), Integer.parseInt(wagonBedden.getText()));
				wagon.setLocomotief(GUIlocomotief.geselecteerdeLocomotief);
				try {
					if(!wagon.update()) {
						wagon.save();
						choiceWagon.getItems().add(wagon.getNaam());
						choiceWagon.setValue(wagon.getNaam());
					}
					GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}				
			}
		});
	}

	protected static Pane createPane() {
		Pane pane = new Pane();
		pane.setMinWidth(450);
		pane.setStyle("  -fx-border-width: 1 1 1 1 ; -fx-font-weight: bold; -fx-background-color:white");
		return pane;
	}

	protected static Label SelectWagon() {
		Label SelectWagon = new Label("Kies een wagon op geselecteerde locomotief");
		SelectWagon.setLayoutX(5);
		SelectWagon.setLayoutY(40);
		return SelectWagon;
	}

	protected static HBox Wagon_HBox() {
		HBox Wagon_HBox = new HBox();
		Wagon_HBox.getChildren().addAll(choiceWagon = new ChoiceBox<>(), selectWagon = new Button("select"));

		Wagon_HBox.setLayoutX(5);
		Wagon_HBox.setLayoutY(60);
		return Wagon_HBox;
	}

	protected static Label losseWagons() {
		Label losseWagons = new Label("Kies een losse wagon");
		losseWagons.setLayoutX(5);
		losseWagons.setLayoutY(90);
		return losseWagons;
	}

	protected static HBox HBox() {
		HBox HBox = new HBox();
		HBox.getChildren().addAll(choiceLosseWagon = new ChoiceBox<>(), selectLosseWagon = new Button("select"));

		HBox.setLayoutX(5);
		HBox.setLayoutY(110);
		return HBox;
	}
	
	protected static VBox Wagon_VBox() {
		VBox Wagon_VBox = new VBox();
		Wagon_VBox.getChildren().addAll(new Label("Naam"), wagonNaam = new TextField(), new Label("Stoelen"),
				wagonStoel = new TextField(), new Label("Bedden"), wagonBedden = new TextField());

		Wagon_VBox.setLayoutX(5);
		Wagon_VBox.setLayoutY(140);
		return Wagon_VBox;
	}
	
	protected static HBox hbox() {
		HBox hbox = new HBox();
		hbox.getChildren().addAll(deleteWagon = new Button("delete"), clone = new Button("clone"),
				addWagon = new Button("Toevoegen/wijzigen"));

		hbox.setLayoutX(5);
		hbox.setLayoutY(290);
		return hbox;
	}

	protected static Label wisselenLbl() {
		Label wisselen = new Label("Wissel van locomotief");
		wisselen.setLayoutX(5);
		wisselen.setLayoutY(320);
		return wisselen;
	}
	

	protected static HBox Wisselen_hbox() {
		HBox Wisselen_hbox = new HBox();
		Wisselen_hbox.getChildren().addAll(wisselVanLocomotief = new ChoiceBox<>(), wissel = new Button("wissel"));

		Wisselen_hbox.setLayoutX(5);
		Wisselen_hbox.setLayoutY(340);
		return Wisselen_hbox;
	}


	protected static Label loskoppelenLbl() {
		Label loskoppelenLbl = new Label("Wagon loskoppelen");
		loskoppelenLbl.setLayoutX(5);
		loskoppelenLbl.setLayoutY(370);
		return loskoppelenLbl;
	}

	protected static HBox Loskoppelen_hbox() {
		HBox Loskoppelen_hbox = new HBox();
		Loskoppelen_hbox.getChildren().addAll(
				loskoppelen = new Button("loskoppelen"));

		Loskoppelen_hbox.setLayoutX(5);
		Loskoppelen_hbox.setLayoutY(390);
		return Loskoppelen_hbox;
	}
	
	protected static Label wagon() {
		wagon = new Label ("wagon naam");
		wagon.setLayoutX(5);
		wagon.setLayoutY(0);
		wagon.setStyle("-fx-font-size: 20; -fx-padding: 5 60 5 60; ");
		return wagon;
	}
	
	public static void getGeselecteerdeWagon(String choiceWagon) throws FileNotFoundException {
		Factory factory = new WagonFactory();
		geselecteerdeWagon = Wagon.getWagonDromDatabase(factory.createWagon(choiceWagon, 0, 0));
		wagonNaam.setText(choiceWagon);
		wagonStoel.setText(Integer.toString(geselecteerdeWagon.getStoelen()));
		wagonBedden.setText(Integer.toString(geselecteerdeWagon.getBedden()));
		wagon.setText(choiceWagon);
	}
}

package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;
import hu.pafr.richrail.locomotief.Builder;
import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.locomotief.LocomotiefBuilder;
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

public class GUIlocomotief {
	protected static ChoiceBox<String> choiceLocomotief;
	protected static ChoiceBox<String> choiceLosseLocomotief;
	protected static ChoiceBox<String> wisselVanSpoor;
	protected static ChoiceBox<String> typeMotor;
	protected static Button selectLocomotief;
	protected static Button selectLosseLocomotief;
	protected static Button deleteLocomotief;
	protected static Button addLocomotief;
	protected static Button clone;
	protected static Button spoorWisselen;
	protected static Button loskoppelenVanSpoor;
	protected static TextField vertrekPunt;
	protected static TextField eindBestemming;

	protected static TextField gps;
	protected static TextField locomotiefStoelen;
	protected static TextField locomotiefLengte;
	protected static TextField locomotiefNaam;
	protected static Locomotief geselecteerdeLocomotief;
	protected static Label locomotief;

	protected static Pane createLocomotiefKeuzeMenu() throws FileNotFoundException {
		Pane paneLocomotief = createPane();

		HBox HBox = HBox();
		HBox Locomotief_HBox = Locomotief_HBox();
		VBox vbox = vbox();
		HBox Clone_hbox = Clone_hbox();
		HBox Wisselen_hbox = Wisselen_hbox();
		HBox Loskoppelen_hbox = Loskoppelen_hbox();
		Label kiesLocomotief = kiesLocomotief();
		Label kiesLosseLocomotief = kiesLosseLocomotief();
		Label WisselVanSpoor = WisselVanSpoor();
		Label Loskoppelen = Loskoppelen();
		Label locomotief = locomotief();

		paneLocomotief.getChildren().addAll(HBox, kiesLocomotief, kiesLosseLocomotief, Locomotief_HBox, vbox, Clone_hbox,
				WisselVanSpoor, Wisselen_hbox, Loskoppelen, Loskoppelen_hbox, locomotief);

		Spoor spoor = new Spoor(0, 0.0);
		LocomotiefEventHanler(spoor);
		loadSporenSwitch();
		loadLosseLocomotieven();
		return paneLocomotief;
	}

	protected static void loadLosseLocomotieven() {
		choiceLosseLocomotief.getItems().clear();
		for (Locomotief locomotief : Locomotief.getLosseLocomotieven()) {
			choiceLosseLocomotief.setValue(locomotief.getNaam());
			choiceLosseLocomotief.getItems().add(locomotief.getNaam());
		}

		selectLosseLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					getChoiceLocomotief(choiceLosseLocomotief.getValue());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	protected static void loadSporenSwitch() throws FileNotFoundException {
		for(Spoor spoor : Spoor.getSporenFromDatabase()){
			wisselVanSpoor.getItems().add(Integer.toString(spoor.getNummer()));
		}
		
		spoorWisselen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					Spoor spoor = new Spoor(Integer.parseInt(wisselVanSpoor.getValue()), 0.0);
					geselecteerdeLocomotief.moveLocomotief(spoor);
					GUItest.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		loskoppelenVanSpoor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					geselecteerdeLocomotief.moveLocomotief(null);
					GUItest.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		

	}

	protected static void LocomotiefEventHanler(Spoor spoor) throws FileNotFoundException {
		choiceLocomotief.getItems().clear();
		typeMotor.getItems().clear();
		// zet de locomotieven van het geselcteerde spoor in de lijst
		if (spoor.getNummer() != 0) {
			spoor.getLocomotiefenFromDatabase();
			Locomotief laatsteLocomotief = null;
			for (Locomotief locomotief : spoor.getLocomotiefen()) {
				laatsteLocomotief = locomotief;
				choiceLocomotief.getItems().add(locomotief.getNaam());
				typeMotor.getItems().add(locomotief.getType_moter());
			}
			if (laatsteLocomotief != null) {
				choiceLocomotief.setValue(laatsteLocomotief.getNaam());
				getChoiceLocomotief(choiceLocomotief.getValue());
				typeMotor.getItems().add(laatsteLocomotief.getType_moter());
			}
		}

		selectLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {				
					getChoiceLocomotief(choiceLocomotief.getValue());
					getGeselecteerdeLocomotief(locomotief, choiceLocomotief);
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

			}
		});

		deleteLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String locomotiefNaam = choiceLocomotief.getValue();
				choiceLocomotief.getItems().remove(locomotiefNaam);
				Builder builder = new LocomotiefBuilder();
				builder.setNaam(locomotiefNaam);
				Locomotief locomotief = builder.build();
				locomotief.remove();
				
				try {
					GUItest.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
				} catch (FileNotFoundException eeee) {
					eeee.printStackTrace();
				}
			}
		});
		
		clone.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					geselecteerdeLocomotief = (Locomotief) geselecteerdeLocomotief.clone();
					try {
						GUItest.createTrain((GUISpoor.geselecteerdeSpoor.getNummer()));
						choiceLocomotief.getItems().add(geselecteerdeLocomotief.getNaam());
						choiceLocomotief.setValue(geselecteerdeLocomotief.getNaam());
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		addLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Builder builder = new LocomotiefBuilder();
				builder.setNaam(locomotiefNaam.getText());
				builder.setVertrekPunt(vertrekPunt.getText());
				builder.setEindBestemming(eindBestemming.getText());
				builder.setType_moter(typeMotor.getValue());
				builder.setGps(Boolean.parseBoolean(gps.getText()));
				builder.setLengte(Double.parseDouble(locomotiefLengte.getText()));
				builder.setStoelen(Integer.parseInt(locomotiefStoelen.getText()));
				Locomotief locomotief = builder.build();
				locomotief.setSpoor(GUISpoor.geselecteerdeSpoor);

				try {
					if (!locomotief.update()) {
						locomotief.save();
						GUItest.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());

						choiceLocomotief.getItems().add(locomotief.getNaam());
					} else {
						GUItest.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
					}
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

	protected static HBox HBox() {
		HBox HBox = new HBox();
		HBox.getChildren().addAll(choiceLocomotief = new ChoiceBox<>(), selectLocomotief = new Button("select"));

		HBox.setLayoutX(5);
		HBox.setLayoutY(50);
		return HBox;
	}

	protected static HBox Locomotief_HBox() {
		HBox Locomotief_HBox = new HBox();
		Locomotief_HBox.getChildren().addAll(choiceLosseLocomotief = new ChoiceBox<>(),
				selectLosseLocomotief = new Button("select"));

		Locomotief_HBox.setLayoutX(5);
		Locomotief_HBox.setLayoutY(100);
		return Locomotief_HBox;
	}

	protected static VBox vbox() {
		VBox vbox = new VBox();
		vbox.getChildren().addAll(new Label("Naam"), locomotiefNaam = new TextField(), new Label("Vertrek punt"),
				vertrekPunt = new TextField(), new Label("Eind bestemming"), eindBestemming = new TextField(),
				new Label("Type motor"), typeMotor = new ChoiceBox<>(), new Label("GPS"), gps = new TextField(),
				new Label("Lengte"), locomotiefLengte = new TextField(), new Label("Stoelen"),
				locomotiefStoelen = new TextField());

		vbox.setLayoutX(5);
		vbox.setLayoutY(130);
		return vbox;
	}

	protected static HBox Clone_hbox() {
		HBox hbox = new HBox();
		hbox.getChildren().addAll(deleteLocomotief = new Button("delete"), 
				clone = new Button("clone"),
				addLocomotief = new Button("Toevoegen/wijzigen"));
		hbox.setLayoutX(5);
		hbox.setLayoutY(420);
		return hbox;
	}

	protected static HBox Wisselen_hbox() {
		HBox Wisselen_hbox = new HBox();
		Wisselen_hbox.getChildren().addAll(wisselVanSpoor = new ChoiceBox<>(), spoorWisselen = new Button("wisselen"));

		Wisselen_hbox.setLayoutX(5);
		Wisselen_hbox.setLayoutY(500);
		return Wisselen_hbox;
	}

	protected static HBox Loskoppelen_hbox() {
		HBox Loskoppelen_hbox = new HBox();
		Loskoppelen_hbox.getChildren().addAll(loskoppelenVanSpoor = new Button("loskoppelen"));

		Loskoppelen_hbox.setLayoutX(5);
		Loskoppelen_hbox.setLayoutY(530);
		return Loskoppelen_hbox;
	}

	protected static Label kiesLocomotief() {
		Label kiesLocomotief = new Label("Kies een locomotief op geselecteerde spoor");
		kiesLocomotief.setLayoutX(5);
		kiesLocomotief.setLayoutY(30);
		return kiesLocomotief;
	}

	protected static Label kiesLosseLocomotief() {
		Label kiesLosseLocomotief = new Label("Kies een losse locomotief");
		kiesLosseLocomotief.setLayoutX(5);
		kiesLosseLocomotief.setLayoutY(70);
		return kiesLosseLocomotief;
	}

	protected static Label WisselVanSpoor() {
		Label WisselVanSpoor = new Label("Wissel van spoor");
		WisselVanSpoor.setLayoutX(5);
		WisselVanSpoor.setLayoutY(450);
		return WisselVanSpoor;
	}

	protected static Label Loskoppelen() {
		Label Loskoppelen = new Label("Locomotief loskoppelen");
		Loskoppelen.setLayoutX(5);
		Loskoppelen.setLayoutY(530);
		return Loskoppelen;
	}

	protected static Label locomotief() {
		locomotief = new Label("locomotief naam");
		locomotief.setLayoutX(5);
		locomotief.setLayoutY(0);
		locomotief.setStyle("-fx-font-size: 20; -fx-padding: 5 60 5 60; ");
		return locomotief;
	}

	public static void getGeselecteerdeLocomotief(Label locomotief, ChoiceBox<String> choiceLocmotief) {
		String naam = choiceLocmotief.getValue();
		locomotief.setText(naam);
	}
	
	protected static void getChoiceLocomotief(String naamLocomotief) throws FileNotFoundException {
		Builder builder = new LocomotiefBuilder();
		builder.setNaam(naamLocomotief);
		geselecteerdeLocomotief = Locomotief.getLocomotiefFromDatabase(builder.build());
		vertrekPunt.setText(geselecteerdeLocomotief.getVertrekPunt());
		eindBestemming.setText(geselecteerdeLocomotief.getEindBestemming());
		//typeMotor.setText(geselecteerdeLocomotief.getType_moter());
		gps.setText(Boolean.toString(geselecteerdeLocomotief.isGps()));
		locomotiefStoelen.setText(Integer.toString(geselecteerdeLocomotief.getStoelen()));
		locomotiefLengte.setText(Double.toString(geselecteerdeLocomotief.getLengte()));
		locomotiefNaam.setText(geselecteerdeLocomotief.getNaam());
		locomotief.setText(naamLocomotief);

		GUIWagon.WagonEventHandler(geselecteerdeLocomotief);
	}

}

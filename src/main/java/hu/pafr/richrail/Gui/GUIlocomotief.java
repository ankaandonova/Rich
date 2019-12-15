package hu.pafr.richrail.Gui;

import java.io.FileNotFoundException;

import hu.pafr.richrail.domein.locomotief.Builder;
import hu.pafr.richrail.domein.locomotief.Locomotief;
import hu.pafr.richrail.domein.locomotief.LocomotiefBuilder;
import hu.pafr.richrail.domein.spoor.Spoor;
import hu.pafr.richrail.domein.wagon.Wagon;
import hu.pafr.richrail.domein.wagon.WagonFactory;
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
	protected static Button selectLocomotief;
	protected static Button selectLosseLocomotief;
	protected static Button deleteLocomotief;
	protected static Button addLocomotief;
	protected static Button clone;
	protected static Button spoorWisselen;
	protected static Button loskoppelenVanSpoor;
	protected static TextField vertrekPunt;
	protected static TextField eindBestemming;
	protected static TextField locomotiefStoelen;
	protected static TextField locomotiefLengte;
	protected static TextField locomotiefNaam;
	protected static TextField typeMotor;
	protected static TextField locomotiefHoogte;
	protected static TextField maxSnelheid;
	protected static Locomotief geselecteerdeLocomotief;
	protected static Label locomotiefLbl;

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

		paneLocomotief.getChildren().addAll(HBox, kiesLocomotief, kiesLosseLocomotief, Locomotief_HBox, vbox,
				Clone_hbox, WisselVanSpoor, Wisselen_hbox, Loskoppelen, Loskoppelen_hbox, locomotief);

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
		for (Spoor spoor : Spoor.getSporenFromDatabase()) {
			wisselVanSpoor.getItems().add(Integer.toString(spoor.getNummer()));
		}

		spoorWisselen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					Spoor spoor = new Spoor(Integer.parseInt(wisselVanSpoor.getValue()), 0.0);
					choiceLosseLocomotief.getItems().remove(Integer.parseInt(wisselVanSpoor.getValue()));
					geselecteerdeLocomotief.moveLocomotief(spoor);
					if(GUISpoor.geselecteerdeSpoor.getNummer() == Integer.parseInt(wisselVanSpoor.getValue())) {
						choiceLocomotief.getItems().add(geselecteerdeLocomotief.getNaam());
					}
					GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});

		loskoppelenVanSpoor.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					loadLosseLocomotieven();
					LocomotiefEventHanler(GUISpoor.geselecteerdeSpoor);
					geselecteerdeLocomotief.moveLocomotief(null);
					GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
					loadLosseLocomotieven();
					loadSporenSwitch();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

	protected static void LocomotiefEventHanler(Spoor spoor) throws FileNotFoundException {
		choiceLocomotief.getItems().clear();
		// zet de locomotieven van het geselcteerde spoor in de lijst
		if (spoor.getNummer() != 0) {
			spoor.getLocomotiefenFromDatabase();
			Locomotief laatsteLocomotief = null;
			for (Locomotief locomotief : spoor.getLocomotiefen()) {
				laatsteLocomotief = locomotief;
				choiceLocomotief.getItems().add(locomotief.getNaam());
			}
			if (laatsteLocomotief != null) {
				choiceLocomotief.setValue(laatsteLocomotief.getNaam());
				getChoiceLocomotief(choiceLocomotief.getValue());
			}
		}

		selectLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					getChoiceLocomotief(choiceLocomotief.getValue());
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}

			}
		});

		deleteLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					Locomotief locomotief = Locomotief.getLocomotiefFromDatabase(geselecteerdeLocomotief);
					if (locomotief.getSpoor() == null) {
						choiceLosseLocomotief.getItems().remove(locomotief.getNaam());
					} else {
						choiceLocomotief.getItems().remove(locomotief.getNaam());
					}
					locomotief.getWagonnenFromDatabase();
					for(Wagon wagon : locomotief.getWagons()) {
						wagon.moveWagon(null);
					}
					locomotief.remove();

					maxSnelheid.setText(null);
					vertrekPunt.setText(null);
					eindBestemming.setText(null);
					locomotiefStoelen.setText(null);
					locomotiefNaam.setText(null);
					typeMotor.setText(null);
					locomotiefLengte.setText(null);
					locomotiefHoogte.setText(null);
					maxSnelheid.setText(null);
					locomotiefLbl.setText(null);
					GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
					
					GUIWagon.loadLocomotievenSwitch();
				} catch (FileNotFoundException eeee) {
					eeee.printStackTrace();
				}
			}
		});

		clone.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					try {
						Locomotief cloner = Locomotief.getLocomotiefFromDatabase(geselecteerdeLocomotief);
						geselecteerdeLocomotief = (Locomotief) cloner.clone();

						choiceLocomotief.getItems().add(geselecteerdeLocomotief.getNaam());
						choiceLocomotief.setValue(geselecteerdeLocomotief.getNaam());
						getChoiceLocomotief(choiceLocomotief.getValue());
						GUI.createTrain((GUISpoor.geselecteerdeSpoor.getNummer()));
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
				builder.setType_moter(typeMotor.getText());
				if (locomotiefLengte.getText().length() != 0) {
					builder.setLengte(Double.parseDouble(locomotiefLengte.getText()));
				} else {
					builder.setLengte(Double.parseDouble("0.0"));
				}
				if (locomotiefStoelen.getText().length() != 0) {
					builder.setStoelen(Integer.parseInt(locomotiefStoelen.getText()));
				} else {
					builder.setStoelen(Integer.parseInt("0"));
				}
				if (maxSnelheid.getText().length() != 0) {
					builder.setMax_snelheid(Double.parseDouble(maxSnelheid.getText()));
				} else {
					builder.setMax_snelheid(Double.parseDouble("0.0"));
				}
				if (locomotiefHoogte.getText().length() != 0) {
					builder.setHoogte(Double.parseDouble(locomotiefHoogte.getText()));
				} else {
					builder.setHoogte(Double.parseDouble("0.0"));
				}

				Locomotief locomotief = builder.build();
				locomotief.setSpoor(GUISpoor.geselecteerdeSpoor);

				try {
					if (!locomotief.update()) {
						locomotief.save();
						choiceLocomotief.getItems().add(locomotief.getNaam());
						choiceLocomotief.setValue(locomotief.getNaam());
						getChoiceLocomotief(choiceLocomotief.getValue());
						GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
					} else {
						GUI.createTrain(GUISpoor.geselecteerdeSpoor.getNummer());
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

	protected static Label kiesLocomotief() {
		Label kiesLocomotief = new Label("Kies een locomotief op geselecteerde spoor");
		kiesLocomotief.setLayoutX(5);
		kiesLocomotief.setLayoutY(40);
		return kiesLocomotief;
	}

	protected static HBox HBox() {
		HBox HBox = new HBox();
		HBox.getChildren().addAll(choiceLocomotief = new ChoiceBox<>(), selectLocomotief = new Button("select"));

		HBox.setLayoutX(5);
		HBox.setLayoutY(60);
		return HBox;
	}

	protected static Label kiesLosseLocomotief() {
		Label kiesLosseLocomotief = new Label("Kies een losse locomotief");
		kiesLosseLocomotief.setLayoutX(5);
		kiesLosseLocomotief.setLayoutY(90);
		return kiesLosseLocomotief;
	}

	protected static HBox Locomotief_HBox() {
		HBox Locomotief_HBox = new HBox();
		Locomotief_HBox.getChildren().addAll(choiceLosseLocomotief = new ChoiceBox<>(),
				selectLosseLocomotief = new Button("select"));

		Locomotief_HBox.setLayoutX(5);
		Locomotief_HBox.setLayoutY(110);
		return Locomotief_HBox;
	}

	protected static VBox vbox() {
		VBox vbox = new VBox();
		vbox.getChildren().addAll(new Label("Naam"), locomotiefNaam = new TextField(), new Label("Vertrek punt"),
				vertrekPunt = new TextField(), new Label("Eind bestemming"), eindBestemming = new TextField(),
				new Label("Type motor"), typeMotor = new TextField(), new Label("Lengte"),
				locomotiefLengte = new TextField(), new Label("Hoogte"), locomotiefHoogte = new TextField(),
				new Label("Maximum snelheid"), maxSnelheid = new TextField(), new Label("Stoelen"),
				locomotiefStoelen = new TextField());

		vbox.setLayoutX(5);
		vbox.setLayoutY(140);
		return vbox;
	}

	protected static HBox Clone_hbox() {
		HBox hbox = new HBox();
		hbox.getChildren().addAll(deleteLocomotief = new Button("delete"), clone = new Button("clone"),
				addLocomotief = new Button("Toevoegen/wijzigen"));
		hbox.setLayoutX(5);
		hbox.setLayoutY(480);
		return hbox;
	}

	protected static Label WisselVanSpoor() {
		Label WisselVanSpoor = new Label("Wissel van spoor");
		WisselVanSpoor.setLayoutX(5);
		WisselVanSpoor.setLayoutY(520);
		return WisselVanSpoor;
	}

	protected static HBox Wisselen_hbox() {
		HBox Wisselen_hbox = new HBox();
		Wisselen_hbox.getChildren().addAll(wisselVanSpoor = new ChoiceBox<>(), spoorWisselen = new Button("wisselen"));

		Wisselen_hbox.setLayoutX(5);
		Wisselen_hbox.setLayoutY(540);
		return Wisselen_hbox;
	}

	protected static Label Loskoppelen() {
		Label Loskoppelen = new Label("Locomotief loskoppelen");
		Loskoppelen.setLayoutX(5);
		Loskoppelen.setLayoutY(570);
		return Loskoppelen;
	}

	protected static HBox Loskoppelen_hbox() {
		HBox Loskoppelen_hbox = new HBox();
		Loskoppelen_hbox.getChildren().addAll(loskoppelenVanSpoor = new Button("loskoppelen"));

		Loskoppelen_hbox.setLayoutX(5);
		Loskoppelen_hbox.setLayoutY(590);
		return Loskoppelen_hbox;
	}

	protected static Label locomotief() {
		locomotiefLbl = new Label("locomotief naam");
		locomotiefLbl.setLayoutX(5);
		locomotiefLbl.setLayoutY(0);
		locomotiefLbl.setStyle("-fx-font-size: 20; -fx-padding: 5 60 5 60; ");
		return locomotiefLbl;
	}

	protected static void getChoiceLocomotief(String naamLocomotief) throws FileNotFoundException {
		Builder builder = new LocomotiefBuilder();
		builder.setNaam(naamLocomotief);

		locomotiefLbl.setText(naamLocomotief);
		geselecteerdeLocomotief = Locomotief.getLocomotiefFromDatabase(builder.build());
		locomotiefNaam.setText(geselecteerdeLocomotief.getNaam());
		vertrekPunt.setText(geselecteerdeLocomotief.getVertrekPunt());
		eindBestemming.setText(geselecteerdeLocomotief.getEindBestemming());
		typeMotor.setText(geselecteerdeLocomotief.getType_moter());
		locomotiefStoelen.setText(Integer.toString(geselecteerdeLocomotief.getStoelen()));

		if (geselecteerdeLocomotief.getLengte() != null) {
			locomotiefLengte.setText(Double.toString(geselecteerdeLocomotief.getLengte()));
		}
		if (geselecteerdeLocomotief.getHoogte() != null) {
			locomotiefHoogte.setText(Double.toString(geselecteerdeLocomotief.getHoogte()));
		}
		if (geselecteerdeLocomotief.getMax_snelheid() != null) {
			maxSnelheid.setText(Double.toString(geselecteerdeLocomotief.getMax_snelheid()));
		}

		GUIWagon.WagonEventHandler(geselecteerdeLocomotief);
	}

}

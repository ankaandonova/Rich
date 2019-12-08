package hu.pafr.richrail.Gui;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.stage.Stage;


public class GUI extends Application{

	private Scene scene;
	private TabPane tabLayout;
	private ChoiceBox<String>  choiceWagon;
	private ChoiceBox<String>  choiceSpoor;
	private ChoiceBox<String>  choiceLocomotief;
	private Button selectSpoor;
	private Button deleteSpoor;
	private Button addSpoor;
	private Button selectLocomotief;
	private Button deleteLocomotief;
	private Button addLocomotief;
	private Button selectWagon;
	private Button deleteWagon;
	private Button addWagon;
	private TextField spoorNummer;
	private TextField lengteSpoor;
	private TextField locomotiefNaam;
	private TextField locomotiefStoelen;
	private TextField vertrekPunt;
	private TextField eindBestemming;
	private TextField typeMotor;
	private TextField gps;
	private TextField locomotiefLengte;
	private TextField wagonNaam;
	private TextField wagonStoel;
	private TextField wagonBedden;

	@Override
	public void start(Stage window) throws Exception {
	
		 tabLayout= new TabPane(); 
		  
		  //tabSpoor
		  Tab tabSpoor =new Tab("Spoor");
		  VBox Spoor_VBox = new VBox();
		  Spoor_VBox.getChildren().addAll(
				  new Label ("Kies een spoor"),
				  choiceSpoor = new ChoiceBox<>(),
				  selectSpoor =  new Button ("select"),
				  deleteSpoor =  new Button ("delete"),
				  new Label ("Spoor nummer"),
				  spoorNummer = new TextField (),
				  new Label ("Lengte"),
				  lengteSpoor = new TextField (),
				  addSpoor = new Button ("Add")
				  
				  );
		
		  choiceSpoor.getItems().addAll("6", "2", "3"); 
		  choiceSpoor.setValue("6");
		  selectSpoor.setOnAction(e -> getChoiceSpoor(choiceSpoor));
		  
		  addSpoor.setOnAction(new EventHandler<ActionEvent>() {
		  @Override
		      public void handle(ActionEvent e) {
			  	lengteSpoor.getText();
			  	spoorNummer.getText();
		        System.out.println(lengteSpoor.getText() + spoorNummer.getText());
		       }
		   });
		  deleteSpoor.setOnAction(e -> deleteChoiceSpoor(choiceSpoor)); 							
		  tabSpoor.setContent(Spoor_VBox);
		  tabLayout.getTabs().add(tabSpoor);
		  
	
		  
		  //tabLocomotief
		  Tab tabLocomotief =new Tab("Locomotief");
		  VBox Locomotief_VBox = new VBox();
		  Locomotief_VBox.getChildren().addAll(
				  new Label ("Kies een locomotief"),
				  choiceLocomotief = new ChoiceBox<>(),
				  selectLocomotief =  new Button ("select"),
				  deleteLocomotief =  new Button ("delete"),
				  new Label ("Naam"),
				  locomotiefNaam = new TextField (),
				  new Label ("Vertrek punt"),
				  vertrekPunt =new TextField (),
				  new Label ("Eind bestemming"),
				  eindBestemming =new TextField (),
				  new Label ("Type motor"),
				  typeMotor = new TextField (),
				  new Label ("GPS"),
				  gps =new TextField (),
				  new Label ("Lengte"),
				  locomotiefLengte =new TextField (),
				  new Label ("Stoelen"),
				  locomotiefStoelen=new TextField (),
				  addLocomotief= new Button ("Add")
				  );
		  choiceLocomotief.getItems().addAll("5", "2", "3"); 
		  choiceLocomotief.setValue("5");
		  selectLocomotief.setOnAction(e -> getChoiceLocomotief(choiceLocomotief));
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
					  System.out.println(locomotiefNaam.getText() 
							  + vertrekPunt.getText() 
							  + eindBestemming.getText()
							  + typeMotor.getText()
							  + gps.getText()
							  + locomotiefLengte.getText()
							  + locomotiefStoelen.getText());
				  	  }
				   });

		  tabLocomotief.setContent(Locomotief_VBox);
		  tabLayout.getTabs().add(tabLocomotief);
		  
		
		  //tabWagon
		  Tab tabWagon =new Tab("Wagon");
		  VBox Wagon_VBox = new VBox();
		  Wagon_VBox.getChildren().addAll(
				  new Label ("Kies een wagon"),
				  choiceWagon = new ChoiceBox<>(),
				  selectWagon =  new Button ("select"),
				  deleteWagon =  new Button ("delete"),
				  new Label ("Naam"),
				  wagonNaam = new TextField (),
				  new Label ("Stoelen"),
				  wagonStoel = new TextField (),
				  new Label ("Bedden"),
				  wagonBedden = new TextField (),
				  addWagon = new Button ("Add")
				  );
		  choiceWagon.getItems().addAll("1", "2", "3"); 
		  choiceWagon.setValue("1");
		  selectWagon.setOnAction(e -> getChoiceWagon(choiceWagon)); 
		  deleteWagon.setOnAction(e -> deleteChoiceWagon(choiceWagon));
		  addWagon.setOnAction(new EventHandler<ActionEvent>() {
			  @Override
		      public void handle(ActionEvent e) {
				  wagonNaam.getText();
				  wagonStoel.getText();
				  wagonBedden.getText();
				  System.out.println(wagonNaam.getText() 
						  + wagonStoel.getText() 
						  + wagonBedden.getText());
			  	  }
			   });
		  tabWagon.setContent(Wagon_VBox);
		  tabLayout.getTabs().add(tabWagon);
		 
		
		scene = new Scene(tabLayout,  900, 900);
		window.setScene(scene);
		window.show();
	}

	public void getChoiceLocomotief(ChoiceBox<String> choiceLocmotief) {
		String locomotief = choiceLocmotief.getValue();
		System.out.print(locomotief);
	}
	
	public void deleteChoiceLocomotief(ChoiceBox<String> choiceLocmotief) {
		String locomotief = choiceLocmotief.getValue();
		choiceLocmotief.getItems().remove(locomotief);
	}
	
	public void getChoiceWagon(ChoiceBox<String> choiceWagon) {
		String wagon = choiceWagon.getValue();
		System.out.print(wagon);
	}
	
	public void getChoiceSpoor (ChoiceBox<String> choiceSpoor) {
		String spoor = choiceSpoor.getValue();
		System.out.print(spoor);
	}
	
	public void deleteChoiceWagon(ChoiceBox<String> choiceWagon) {
		String wagon = choiceWagon.getValue();
		choiceWagon.getItems().remove(wagon);
	}

	public void deleteChoiceSpoor(ChoiceBox<String> choiceSpoor) {
		String spoor = choiceSpoor.getValue();
		choiceSpoor.getItems().remove(spoor);
	}
	
	public static void main (String[] args) {
		launch(args);
	}


}

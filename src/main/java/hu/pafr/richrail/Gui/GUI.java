package hu.pafr.richrail.Gui;



import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GUI extends Application{

	private Scene scene;
	private ChoiceBox<String>  choiceBox;
	private Button selectSpoor;
	private Button deleteSpoor;
	private Button addSpoor;
	private TextField nummer;
	private TextField lengte;
	private TabPane tabLayout;
	private TextField locomotiefNaam;
	private Button selectLocomotief;
	private Button deleteLocomotief;
	private Button addLocomotief;
	private Button selectWagon;
	private Button deleteWagon;
	private TextField wagonNaam;
	private TextField wagonStoel;
	private TextField wagonBedden;
	private Button addWagon;
	
	
	public static void main (String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) throws Exception {
		
		
		  tabLayout= new TabPane(); 
		  
		  //tabSpoor
		  Tab tabSpoor =new Tab("Spoor");
		  VBox Spoor_VBox = new VBox();
		  Spoor_VBox.getChildren().addAll(
				  new Label ("Kies een spoor"),
				  choiceBox = new ChoiceBox<>(),
				  selectSpoor =  new Button ("select"),
				  deleteSpoor =  new Button ("delete"),
				  new Label ("Spoor nummer"),
				  nummer = new TextField (),
				  new Label ("Lengte"),
				  lengte = new TextField (),
				  addSpoor = new Button ("Add")
				  );
		/*
		 * addSpoor.setLayoutX(250); 
		 * addSpoor.setLayoutY(250);
		 */
		  
		  choiceBox.getItems().addAll("1", "2", "3"); choiceBox.setValue("1");
		  selectSpoor.setOnAction(e -> getChoice(choiceBox)); 
		  addSpoor.setOnAction(e -> System.out.println(nummer.getText()) );
		  tabSpoor.setContent(Spoor_VBox);
		  tabLayout.getTabs().add(tabSpoor);
		  
		  
		  //tabLocomotief
		  Tab tabLocomotief =new Tab("Locomotief");
		  VBox Locomotief_VBox = new VBox();
		  Locomotief_VBox.getChildren().addAll(
				  new Label ("Kies een locomotief"),
				  choiceBox = new ChoiceBox<>(),
				  selectLocomotief =  new Button ("select"),
				  deleteLocomotief =  new Button ("delete"),
				  new Label ("Naam"),
				  locomotiefNaam = new TextField (),
				  new Label ("Vertrek punt"),
				  new TextField (),
				  new Label ("Eind bestemming"),
				  new TextField (),
				  new Label ("Type motor"),
				  new TextField (),
				  new Label ("GPS"),
				  new TextField (),
				  new Label ("Lengte"),
				  new TextField (),
				  new Label ("Stoelen"),
				  lengte = new TextField (),
				  
				  addLocomotief= new Button ("Add")
				  );
		  choiceBox.getItems().addAll("1", "2", "3"); choiceBox.setValue("1");
		  selectLocomotief.setOnAction(e -> getChoice(choiceBox)); 
		  addLocomotief.setOnAction(e -> System.out.println(nummer.getText()) );
		  tabLocomotief.setContent(Locomotief_VBox);
		  tabLayout.getTabs().add(tabLocomotief);
		  
		
		  //tabWagon
		  Tab tabWagon =new Tab("Wagon");
		  VBox Wagon_VBox = new VBox();
		  Wagon_VBox.getChildren().addAll(
				  new Label ("Kies een wagon"),
				  choiceBox = new ChoiceBox<>(),
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
		  choiceBox.getItems().addAll("1", "2", "3"); choiceBox.setValue("1");
		  selectWagon.setOnAction(e -> getChoice(choiceBox)); 
		  addWagon.setOnAction(e -> System.out.println(wagonNaam.getText()) );
		  tabWagon.setContent(Wagon_VBox);
		  tabLayout.getTabs().add(tabWagon);
		 
		
		scene = new Scene(tabLayout,  900, 900);
		window.setScene(scene);
		window.show();
	}

	public void getChoice(ChoiceBox<String> choiceBox) {
		String sporen = choiceBox.getValue();
		System.out.print(sporen);
	}
	


}

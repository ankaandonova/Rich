package hu.pafr.richrail.Gui;



import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUItest extends Application{
	
	private Scene scene;
	private BorderPane schermBorder;
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
	private HBox spoor;
	
	
	@Override
	public void start(Stage window) throws Exception {
		
		schermBorder = new BorderPane();
		
		//draw
		spoor = creatHBox();
		
		schermBorder.setTop(spoor);
		
		//spoor
		VBox Spoor_VBox = creatVBox();
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
		selectSpoor.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
				getChoiceSpoor(choiceSpoor);
				createTrain();
		    }
		});
		
		addSpoor.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
				lengteSpoor.getText();
				spoorNummer.getText();
				System.out.println(lengteSpoor.getText() + spoorNummer.getText());
		    }
		});
		deleteSpoor.setOnAction(e -> deleteChoiceSpoor(choiceSpoor)); 
		Spoor_VBox.setMaxSize(500,200);
		schermBorder.setLeft(Spoor_VBox);
		
		
		//trein
		VBox Locomotief_VBox = creatVBox();
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
		selectLocomotief.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
				getChoiceLocomotief(choiceLocomotief);
				createTrain();
		    }
		});
		deleteLocomotief.setOnAction(e -> deleteChoiceLocomotief(choiceLocomotief));
		
		addLocomotief.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				createTrain();
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
		schermBorder.setCenter(Locomotief_VBox);
		
		//wagon
		VBox Wagon_VBox =creatVBox();
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
		selectWagon.setOnAction(new EventHandler<ActionEvent>() {
		@Override
		    public void handle(ActionEvent e) {
				getChoiceWagon(choiceWagon);
				createTrain();
		    }
		});
		deleteWagon.setOnAction(e -> deleteChoiceWagon(choiceWagon));
		addWagon.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				createTrain();
				 wagonNaam.getText();
				 wagonStoel.getText();
				 wagonBedden.getText();
				 System.out.println(wagonNaam.getText() 
				 + wagonStoel.getText() 
				 + wagonBedden.getText());
			  }
		  });
		schermBorder.setRight(Wagon_VBox);
		  
		
		scene = new Scene(schermBorder,  1900, 900);
		
		window.setTitle("RichRail");
		window.setScene(scene);
		window.show();
		
	}
	
	private void createTrain() {
		Image img = new Image("img.jpg");
		spoor.getChildren().add(new ImageView(img));
	}
	
	@SuppressWarnings("static-access")
	private VBox  creatVBox(){
		VBox vbox = new VBox();
		schermBorder.setMargin(vbox, new Insets(5));
		vbox.setPrefWidth(450);
		vbox.setStyle(" -fx-border-style: dotted; -fx-border-width: 1 1 1 1 ; -fx-font-weight: bold;");
		vbox.setAlignment(Pos.BASELINE_LEFT);
		return vbox;
		
	}
	
	@SuppressWarnings("static-access")
	private HBox  creatHBox(){
		HBox hbox = new HBox();
		schermBorder.setMargin(hbox, new Insets(5));
		hbox.prefWidthProperty().bind(schermBorder.widthProperty());
		hbox.prefHeightProperty().bind(schermBorder.heightProperty().subtract(500));
		hbox.setStyle("-fx-border-style: dotted; -fx-border-width: 0 0 1 0 ; -fx-font-weight: bold;");
		hbox.setAlignment(Pos.BASELINE_CENTER);
		return hbox;
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

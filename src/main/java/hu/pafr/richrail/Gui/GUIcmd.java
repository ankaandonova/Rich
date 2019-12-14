package hu.pafr.richrail.Gui;


import java.lang.reflect.InvocationTargetException;

import hu.pafr.richrail.locomotief.Locomotief;
import hu.pafr.richrail.parser.RichRailCli;
import hu.pafr.richrail.parser.RichRailUitvoerListener;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIcmd extends Application {

	private Scene scene;
	private BorderPane schermBorder;
	private HBox hbox;
	private VBox vbox;
	private Button commit;
	private TextField text;
	private Label label;

	@Override
	public void start(Stage window) throws Exception, InvocationTargetException {
		schermBorder = new BorderPane();
		
		hbox = new HBox();
		vbox = new VBox();
		text = new TextField();
		commit = new Button("commit");
		label = new Label("messege");
		commit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				setText(label, text);
			}
		});
		
		hbox = createHBox();
		vbox = createVBox();
		vbox.getChildren().addAll(label);
		hbox.getChildren().addAll(text, commit);
		
		schermBorder.setTop(vbox);
		schermBorder.setCenter(hbox);

		scene = new Scene(schermBorder, 500, 300);

		window.setTitle("RichRail cmd");
		window.setScene(scene);
		window.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings({ "static-access" })
	private HBox createHBox() {
		HBox hbox = new HBox();
		schermBorder.setMargin(hbox, new Insets(5));
		hbox.prefWidthProperty().bind(schermBorder.widthProperty());
		hbox.prefHeightProperty().bind(schermBorder.heightProperty().subtract(500));
		hbox.setStyle(" -fx-border-width: 0 0 1 0 ; -fx-font-weight: bold;");
		hbox.setAlignment(Pos.BASELINE_CENTER);
		return hbox;
	}

	protected  VBox createVBox() {
		VBox vbox = new VBox();
		vbox.setPrefWidth(450);
		vbox.setPrefHeight(200);
		vbox.setStyle(" -fx-border-width: 1 1 1 1 ; -fx-font-weight: bold; -fx-background-color: black; -fx-text-inner-color: blue");
		vbox.setAlignment(Pos.BASELINE_LEFT);
		return vbox;
	}

	public static void setText(Label label, TextField text) {
		String invoer = text.getText();
		RichRailUitvoerListener richrail =RichRailCli.voerCommandUit(invoer);
		Object object = richrail.getObject();
		String responseMessage = richrail.getMessage();
		System.out.println("'"+responseMessage+"'");
		if(responseMessage == null) {
			label.setText("ongeldige syntax");
		} else {
			label.setText(responseMessage);
		}
	}
}

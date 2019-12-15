package hu.pafr.richrail.Gui;


import java.lang.reflect.InvocationTargetException;

import hu.pafr.richrail.domein.locomotief.Locomotief;
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


	private static BorderPane schermBorder;


	@Override
	public void start(Stage window) throws Exception, InvocationTargetException {
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings({ "static-access" })
	protected static HBox createHBox() {
		HBox hbox = new HBox();
		schermBorder.setMargin(hbox, new Insets(5));
		hbox.prefWidthProperty().bind(schermBorder.widthProperty());
		hbox.prefHeightProperty().bind(schermBorder.heightProperty().subtract(500));
		hbox.setStyle(" -fx-border-width: 0 0 1 0 ; -fx-font-weight: bold;");
		hbox.setAlignment(Pos.BASELINE_CENTER);
		return hbox;
	}

	protected static  VBox createVBox() {
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
		if(responseMessage == null) {
			label.setText("ongeldige syntax");
		} else {
			label.setText(responseMessage);
		}
	}
}

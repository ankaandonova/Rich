package hu.pafr.richrail.Gui;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUIcmd extends Application {
	// Adding a Label
	Label label1 = new Label("Name:");
	TextField textField = new TextField();
	HBox hb = new HBox();hb.getChildren().addAll(label1,textField);hb.setSpacing(10);

	// Setting an action for the Submit button
	submit.setOnAction(new EventHandler<ActionEvent>(){

	public void handle(ActionEvent e) {
	        if ((comment.getText() != null && !comment.getText().isEmpty())) {
	            label.setText(name.getText() + " " + lastName.getText() + ", "
	                + "thank you for your comment!");
	        } else {
	            label.setText("You have not left a comment.");
	        }
	     }
	 });
	 
	//Setting an action for the Clear button
	clear.setOnAction(new EventHandler<ActionEvent>() {

	@Override
	    public void handle(ActionEvent e) {
	        name.clear();
	        lastName.clear();
	        comment.clear();
	        label.setText(null);
	    }
	});
	@Override
	public void start(Stage arg0) throws Exception {
		
	}
}



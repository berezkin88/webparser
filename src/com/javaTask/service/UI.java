package com.javaTask.service;

import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.JAXBExc;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class UI extends Application {
	private static final Insets MARGIN_LEFT = new Insets(0, 0, 0, 20.0);
	private static final Insets MARGIN_TOP = new Insets(20.0, 0, 0, 0);
	private static Website web = null;
	private static Tab tab = null;
	private static VBox vbox = null;
	private static TextField input = null;

	@Override
	public void start(Stage primaryStage) throws Exception {

		TabPane tabPane = new TabPane();

		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.getTabs().addAll(searchTab(), signUpTab(), addToBasket());

		Scene scene = new Scene(tabPane, 400, 500);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Tab searchTab() {
		tab = new Tab("Search Tab");
		vbox = new VBox(20);

		// set title
		Label label = new Label("Search for a product");
		label.setAlignment(Pos.BASELINE_CENTER);
		label.setMaxWidth(2160);
		label.setFont(new Font("Times New Roman", 20));
		VBox.setMargin(label, MARGIN_TOP);

		// description text
		Text text = new Text("Paste the link to the product from Prom.ua here");
		VBox.setMargin(text, MARGIN_LEFT);

		// input field for an URL
		input = new TextField();
		input.setPrefWidth(300.0);

		// button
		Button btn = new Button("Search");
		btn.setMaxSize(60.0, 30.0);

		// pane for input and button
		FlowPane pane = new FlowPane(5, 5, input, btn);
		VBox.setMargin(pane, MARGIN_LEFT);

		vbox.getChildren().addAll(label, text, pane);

		tab.setContent(vbox);

		// customize button action
		btn.setOnAction(a -> {
			searchButtonFunc();
		});

		return tab;
	}

	private Tab signUpTab() {
		Tab tab = new Tab("Tab 2");

		Text text = new Text("Here will be a sign up functionality");
		tab.setContent(text);

		return tab;
	}

	private Tab addToBasket() {
		Tab tab = new Tab("Tab 3");

		Text text = new Text("Here will be a login and adding to basket functionality");
		tab.setContent(text);

		return tab;
	}

	private boolean validateUrl(String url) {
		if (url == null)
			return false;

		if (url == "")
			return false;

		if (checkSpaces(url))
			return false;

		if (!url.startsWith("https://prom.ua"))
			return false;

		return true;
	}

	private boolean checkSpaces(String url) {

		for (int i = 0, len = url.length(); i < len; i++) {
			if (url.charAt(i) == ' ')
				return true;
		}

		return false;
	}
	
	private void searchButtonFunc() {
		/**
		 * The method is adding functionality for the Search button hit.
		 * It returns error if there is no URL, Input field is empty, URL is not from Prom.ua or URL contains spaces.
		 * For success operation it returns the success message and adds the Save button
		 */
		
		String url = input.getText();

		if (!validateUrl(url)) {
			Text warning = new Text(
					"URL is incorrect or missing. Please make sure you used a link from Prom.ua and if a link doesn't content any whitespaces");
			warning.setTextAlignment(TextAlignment.LEFT);
			warning.setFill(Color.RED);
			warning.setWrappingWidth(300.0);
			VBox.setMargin(warning, MARGIN_LEFT);

			vbox.getChildren().add(warning);

			return;
		}

		//parsing the URL
		web = AppService.collectData(url);
		
		Text success = new Text("The product is found!");
		success.setTextAlignment(TextAlignment.CENTER);
		success.maxWidth(2160);
		VBox.setMargin(success, MARGIN_LEFT);
		
		Button save = new Button("Save");
		save.setMaxSize(80.0, 30.0);
		save.setAlignment(Pos.BASELINE_CENTER);
		VBox.setMargin(save, MARGIN_LEFT);
		
		vbox.getChildren().addAll(success, save);
		
		save.setOnAction(ac -> {
			saveBtnFunc();
		});
	}

	private void saveBtnFunc() {
		/**
		 * The method purpose is to handle hitting Save button.
		 * The button saves parsed URL results to JSON and XML files, named with a current System timestamp.
		 * If error occurs it displays the exception message.
		 */
		
		try {
			AppService.writeToJSON(web);
			AppService.writeToXML(web);
		} catch (IOExc | JAXBExc e) {
			Text error = new Text("The error occures while saving: " + e.getMessage());
			error.setFill(Color.RED);
			error.setTextAlignment(TextAlignment.CENTER);
			error.maxWidth(2160);
			VBox.setMargin(error, MARGIN_LEFT);

			vbox.getChildren().add(error);
		}
		
		Text saved = new Text("The data was successfully saved");
		saved.setTextAlignment(TextAlignment.CENTER);
		saved.maxWidth(2160);
		VBox.setMargin(saved, MARGIN_LEFT);
		
		vbox.getChildren().add(saved);
	}
}

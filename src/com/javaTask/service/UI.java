package com.javaTask.service;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import com.javaTask.entity.User;
import com.javaTask.entity.Website;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.JAXBExc;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.riojavino.entity.Wine;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class UI extends Application {
	private static final Logger LOG = Logger.getLogger(UI.class.getName());

	private static final Insets MARGIN_LEFT = new Insets(0, 0, 0, 20.0);
	private static final Insets MARGIN_TOP = new Insets(20.0, 0, 0, 0);
	private static final Insets MARGIN_TOP_LEFT = new Insets(20.0, 0, 0, 20.0);
	private static Website web = null;
	private static Tab tab = null;
	private static VBox vboxSearch, vboxSignup, vboxAdd, vboxSave = null;
	private static TextField input, name, email, password, inputUrl = null;

	@Override
	public void start(Stage primaryStage) throws Exception {

		TabPane tabPane = new TabPane();

		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabPane.getTabs().addAll(searchTab(), signUpTab(), addToBasket(), saveToCSVTab());

		Scene scene = new Scene(tabPane, 400, 500);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private Tab searchTab() {
		tab = new Tab("Search Tab");
		vboxSearch = new VBox(20);

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

		vboxSearch.getChildren().addAll(label, text, pane);

		tab.setContent(vboxSearch);

		// customize button action
		btn.setOnAction(a -> {
			searchButtonFunc();
		});

		return tab;
	}

	private Tab signUpTab() {
		Tab tab = new Tab("Create account");
		vboxSignup = new VBox(5);

		// set title
		Label label = new Label("Search for a product");
		label.setAlignment(Pos.BASELINE_CENTER);
		label.setMaxWidth(2160);
		label.setFont(new Font("Times New Roman", 20));
		VBox.setMargin(label, MARGIN_TOP);

		// description text
		Text nameDesc = new Text("Insert your name");
		VBox.setMargin(nameDesc, MARGIN_TOP_LEFT);

		// input field for a name
		name = new TextField();
		name.setMaxWidth(200.0);
		VBox.setMargin(name, MARGIN_LEFT);

		// description text
		Text emailDesc = new Text("Insert your email");
		VBox.setMargin(emailDesc, MARGIN_TOP_LEFT);

		// input field for an email
		email = new TextField();
		email.setMaxWidth(200.0);
		VBox.setMargin(email, MARGIN_LEFT);

		// description text
		Text passDesc = new Text("Insert your password");
		VBox.setMargin(passDesc, MARGIN_TOP_LEFT);

		// input field for a password
		password = new TextField();
		password.setMaxWidth(200.0);
		VBox.setMargin(password, MARGIN_LEFT);

		// button
		Button btn = new Button("Sign up");
		btn.setMaxSize(60.0, 30.0);
		VBox.setMargin(btn, MARGIN_TOP_LEFT);

		btn.setOnAction(a -> {
			signupBtnFunc(name.getText(), email.getText(), password.getText());
		});

		vboxSignup.getChildren().addAll(label, nameDesc, name, emailDesc, email, passDesc, password, btn);

		tab.setContent(vboxSignup);

		return tab;
	}

	private Tab addToBasket() {
		Tab tab = new Tab("Login & Cart");
		vboxAdd = new VBox(5);

		// set title
		Label label = new Label("Login and Add");
		label.setAlignment(Pos.BASELINE_CENTER);
		label.setMaxWidth(2160);
		label.setFont(new Font("Times New Roman", 20));
		VBox.setMargin(label, MARGIN_TOP);

		// description text
		Text emailDesc = new Text("Insert your email");
		VBox.setMargin(emailDesc, MARGIN_TOP_LEFT);

		// input field for an email
		email = new TextField();
		email.setMaxWidth(200.0);
		VBox.setMargin(email, MARGIN_LEFT);

		// description text
		Text passDesc = new Text("Insert your password");
		VBox.setMargin(passDesc, MARGIN_TOP_LEFT);

		// input field for a password
		password = new TextField();
		password.setMaxWidth(200.0);
		VBox.setMargin(password, MARGIN_LEFT);

		// description text
		Text url = new Text("Insert product URL");
		VBox.setMargin(url, MARGIN_TOP_LEFT);

		// input field for an URL
		inputUrl = new TextField();
		inputUrl.setMaxWidth(350.0);
		VBox.setMargin(inputUrl, MARGIN_LEFT);

		Button btn = new Button("Login & Add");
		btn.setMaxSize(120.0, 30.0);
		VBox.setMargin(btn, MARGIN_LEFT);

		btn.setOnAction(a -> {
			loginAndAdd(email.getText(), password.getText(), inputUrl.getText());
		});

		vboxAdd.getChildren().addAll(label, emailDesc, email, passDesc, password, url, inputUrl, btn);

		tab.setContent(vboxAdd);

		return tab;
	}

	private Tab saveToCSVTab() {
		Tab tab = new Tab("Find and Save products");
		vboxSave = new VBox(5);
		HBox hboxDateFrom = new HBox(10);
		HBox hboxDateTill = new HBox(10);

		// Calendar
		Calendar date = new GregorianCalendar();

		// set title
		Label label = new Label("Find and Save records to CSV");
		label.setAlignment(Pos.BASELINE_CENTER);
		label.setMaxWidth(2160);
		label.setFont(new Font("Times New Roman", 20));
		VBox.setMargin(label, MARGIN_TOP);

		// description text
		Text fromDesc = new Text("Set the beginnign date");
		VBox.setMargin(fromDesc, MARGIN_TOP_LEFT);

		// input fields for beginning date
		Text monthf = new Text("Month");
		TextField monthFrom = new TextField(String.valueOf(date.get(Calendar.MONTH) + 1));
		monthFrom.setMaxWidth(30.0);

		Text dayf = new Text("Day");
		TextField dayFrom = new TextField(String.valueOf(date.get(Calendar.DATE)));
		dayFrom.setMaxWidth(30.0);

		Text yearf = new Text("Year");
		TextField yearFrom = new TextField(String.valueOf(date.get(Calendar.YEAR)));
		yearFrom.setMaxWidth(50.0);

		hboxDateFrom.getChildren().addAll(monthf, monthFrom, dayf, dayFrom, yearf, yearFrom);
		VBox.setMargin(hboxDateFrom, MARGIN_LEFT);

		// description text
		Text tillDesc = new Text("Set the ending date");
		VBox.setMargin(tillDesc, MARGIN_TOP_LEFT);

		// input fields for ending date
		Text montht = new Text("Month");
		TextField monthTill = new TextField(String.valueOf(date.get(Calendar.MONTH) + 1));
		monthTill.setMaxWidth(30.0);

		Text dayt = new Text("Day");
		TextField dayTill = new TextField(String.valueOf(date.get(Calendar.DATE)));
		dayTill.setMaxWidth(30.0);

		Text yeart = new Text("Year");
		TextField yearTill = new TextField(String.valueOf(date.get(Calendar.YEAR)));
		yearTill.setMaxWidth(50.0);

		hboxDateTill.getChildren().addAll(montht, monthTill, dayt, dayTill, yeart, yearTill);
		VBox.setMargin(hboxDateTill, MARGIN_LEFT);

		Button btn = new Button("Find & Save");
		btn.setMaxSize(120.0, 30.0);
		VBox.setMargin(btn, MARGIN_TOP_LEFT);
		
		vboxSave.getChildren().addAll(label, fromDesc, hboxDateFrom, tillDesc, hboxDateTill, btn);
		tab.setContent(vboxSave);

		btn.setOnAction(e -> {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
			String dateFrom = monthFrom.getText() + "/" +  dayFrom.getText()  + "/" + yearFrom.getText() + " 00:00:00";
			LOG.info(dateFrom);
			String dateTill = monthTill.getText() + "/" + dayTill.getText() + "/" + yearTill.getText() + " 23:59:59";
			LOG.info(dateTill);
			long dateF = 0l;
			long dateT = 0l;

			try {
				dateF = sdf.parse(dateFrom).getTime();
				LOG.info("Date from: " + dateF);
				dateT = sdf.parse(dateTill).getTime();
				LOG.info("Date till: " + dateT);
			} catch (ParseException e1) {
				LOG.info("An exception occured when converting date from string to long timestamp");
				e1.printStackTrace();
			}

			if (findAndSaveFunc(dateF, dateT)) {
				Text success = new Text("The results are saved to csv.");
				success.setTextAlignment(TextAlignment.CENTER);
				success.maxWidth(2160);
				VBox.setMargin(success, MARGIN_LEFT);
				
				vboxSave.getChildren().add(success);
			} else {
				Text failure = new Text("Nothign was found.");
				failure.setTextAlignment(TextAlignment.CENTER);
				failure.maxWidth(2160);
				failure.setFill(Color.RED);
				VBox.setMargin(failure, MARGIN_LEFT);
				
				vboxSave.getChildren().add(failure);
			}
		});

		return tab;
	}

	/*
	 * The method is looking for the website data in the database, it saves results
	 * to a csv if the search is successes and return TRUE.
	 * It returns FALSE otherwise.
	 */
	private boolean findAndSaveFunc(long dateF, long dateT) {
		WebsiteService ws = new WebsiteService();
		List<Website> results = new ArrayList<>();

		try {
			results = ws.read(dateF, dateT);
		} catch (SQLException e) {
			LOG.info("Exception occured in findAndSaveFunc() method");
			e.printStackTrace();
		}

		if (!results.isEmpty()) {
			writeToCsv(results);
			return true;
		}
		
		return false;
	}

	/*
	 * The method is adding functionality for the Search button hit. It returns
	 * error if there is no URL, Input field is empty, URL is not from Prom.ua or
	 * URL contains spaces. For success operation it returns the success message and
	 * adds the Save button
	 */
	private void searchButtonFunc() {

		String url = input.getText();

		if (!validateUrl(url)) {
			Text warning = new Text(
					"URL is incorrect or missing. Please make sure you used a link from Prom.ua and if a link doesn't content any whitespaces");
			warning.setTextAlignment(TextAlignment.LEFT);
			warning.setFill(Color.RED);
			warning.setWrappingWidth(300.0);
			VBox.setMargin(warning, MARGIN_LEFT);

			vboxSearch.getChildren().add(warning);

			return;
		}

		// parsing the URL
		web = AppService.collectData(url);

		Text success = new Text("The product is found!");
		success.setTextAlignment(TextAlignment.CENTER);
		success.maxWidth(2160);
		VBox.setMargin(success, MARGIN_LEFT);

		Button save = new Button("Save");
		save.setMaxSize(80.0, 30.0);
		save.setAlignment(Pos.BASELINE_CENTER);
		VBox.setMargin(save, MARGIN_LEFT);

		vboxSearch.getChildren().addAll(success, save);

		save.setOnAction(ac -> {
			saveBtnFunc();
		});
	}

	/*
	 * The method purpose is to handle hitting Save button. The button saves parsed
	 * URL results to JSON and XML files, named with a current System timestamp. If
	 * error occurs it displays the exception message.
	 */
	private void saveBtnFunc() {

		try {
			AppService.writeToJSON(web);
			AppService.writeToXML(web);
		} catch (IOExc | JAXBExc e) {
			Text error = new Text("The error occures while saving: " + e.getMessage());
			error.setFill(Color.RED);
			error.setTextAlignment(TextAlignment.CENTER);
			error.maxWidth(2160);
			VBox.setMargin(error, MARGIN_LEFT);

			vboxSearch.getChildren().add(error);
		}

		Text saved = new Text("The data was successfully saved");
		saved.setTextAlignment(TextAlignment.CENTER);
		saved.maxWidth(2160);
		VBox.setMargin(saved, MARGIN_LEFT);

		vboxSearch.getChildren().add(saved);
	}

	/*
	 * The method purpose is the signup into Prom.ua service. It requires three
	 * fields to be fulfilled: name, email and password. If one of the inputs failed
	 * validation test the warning message would be shown. In the case of success a
	 * new account would be created. Selenium runs in the fullscreen mode.
	 */
	private void signupBtnFunc(String name, String email, String pass) {

		User user = new User();
		SignUpBot sub = new SignUpBot();

		if (validateStr(name) && validateEmail(email) && validateStr(pass)) {
			user.setName(name);
			user.setEmail(email);
			user.setPassword(pass);
		} else {
			Text warning = new Text(
					"Name, email or password is incorrect, please make sure all fields are filled out, do not contain whitespaces and email contains @ symbol");
			warning.setFill(Color.RED);
			warning.setTextAlignment(TextAlignment.LEFT);
			warning.setWrappingWidth(300.0);
			VBox.setMargin(warning, MARGIN_TOP_LEFT);

			vboxSignup.getChildren().add(warning);

			return;
		}

		WebDriver web = sub.registerUser(user);

		Text success = new Text("You successfully signed up!");
		success.setTextAlignment(TextAlignment.CENTER);
		success.maxWidth(2160);
		VBox.setMargin(success, MARGIN_LEFT);

		vboxSignup.getChildren().add(success);
	}

	/*
	 * This method make a user login and add a product to the cart. It accepts
	 * strings for email, password and product URL. Validate the input parameters,
	 * and show warning message if any of the fields is incorrect or missing. In the
	 * case of success it logs in the user and add a product to the basket.
	 */
	private void loginAndAdd(String email, String password, String url) {

		User user = new User();
		String link = null;
		LoginBot lb = new LoginBot();
		AddToBasket atb = new AddToBasket();

		if (validateEmail(email) && validateStr(password) && validateUrl(url)) {
			user.setEmail(email);
			user.setPassword(password);
			link = url;
		} else {
			Text warning = new Text(
					"Email ,password or product URL is incorrect, please make sure all fields are filled out, do not contain whitespaces, email contains @ symbol, the URL is from Prom.ua website");
			warning.setFill(Color.RED);
			warning.setTextAlignment(TextAlignment.LEFT);
			warning.setWrappingWidth(300.0);
			VBox.setMargin(warning, MARGIN_TOP_LEFT);

			vboxAdd.getChildren().add(warning);

			return;
		}

		WebDriver web = lb.logInUser(user);
		web = atb.addToCart(url, lb.getWebDriver());

		Text success = new Text("Operations were performed successfully");
		success.setTextAlignment(TextAlignment.CENTER);
		success.maxWidth(2160);
		VBox.setMargin(success, MARGIN_LEFT);

		vboxAdd.getChildren().add(success);

	}

	private void writeToCsv(List<Website> results) {
		LOG.info("In writeToCsv() method");

		Writer writer;
		try {
			writer = Files.newBufferedWriter(Paths.get(System.getProperty("user.dir") + File.separator + "data"
					+ File.separator + System.currentTimeMillis() + ".csv"));

			LOG.info("Writer started");
			StatefulBeanToCsv<Website> beanToCsv = new StatefulBeanToCsvBuilder<Website>(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();

			LOG.info("CSV builder completed");

			beanToCsv.write(results);
			LOG.info("CSV completed");

			writer.close();
		} catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
			LOG.info("Exception occured in writeToCsv() method");
			e.printStackTrace();
		}

	}

	private boolean validateEmail(String email) {
		if (email == null || email.isEmpty() || checkSpaces(email) || !email.contains("@"))
			return false;

		return true;
	}

	private boolean validateStr(String str) {
		if (str == null || str.isEmpty() || checkSpaces(str))
			return false;

		return true;
	}

	private boolean validateUrl(String url) {
		if (url == null || url.isEmpty() || checkSpaces(url) || !url.contains("https://prom.ua"))
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
}

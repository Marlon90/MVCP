package de.mvcp.view.impl;

import java.sql.SQLException;
import java.util.List;

import de.mvcp.controller.impl.ControllerImpl;
import de.mvcp.model.Article;
import de.mvcp.view.View;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ViewImpl extends Application implements View {

	private static ControllerImpl controller = new ControllerImpl();
	private int articleId;

	// If field is empty border will appear red else black
	private void checkFields(List<TextField> textFields) {
		for (TextField t : textFields) {
			if (t.getText().isEmpty()) {
				t.setStyle("-fx-border-color:red;");
			} else {
				t.setStyle("-fx-border-color:black;");
			}
		}
	}

	// Create and configure table list //
	private ObservableList<TableColumn<?, ?>> getTableColumnList() {
		ObservableList<TableColumn<?, ?>> list = FXCollections.observableArrayList();

		TableColumn<Article, Integer> itemNumberColumn = new TableColumn<Article, Integer>("Artikelnummer");
		itemNumberColumn.setCellValueFactory(new PropertyValueFactory<Article, Integer>("itemNumber"));
		itemNumberColumn.setMinWidth(100);
		TableColumn<Article, String> descriptionColumn = new TableColumn<Article, String>("Bezeichnung");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<Article, String>("description"));
		descriptionColumn.setMinWidth(100);
		TableColumn<Article, Integer> costColumn = new TableColumn<Article, Integer>("Einkaufspreis");
		costColumn.setCellValueFactory(new PropertyValueFactory<Article, Integer>("cost"));
		costColumn.setMinWidth(100);
		TableColumn<Article, Integer> priceColumn = new TableColumn<Article, Integer>("Verkaufspreis");
		priceColumn.setCellValueFactory(new PropertyValueFactory<Article, Integer>("price"));
		priceColumn.setMinWidth(100);

		list.add(itemNumberColumn);
		list.add(descriptionColumn);
		list.add(costColumn);
		list.add(priceColumn);

		return list;
	}

	// Create list
	private ObservableList<TextField> getTextFields() {
		TextField itemNumber = new TextField();
		itemNumber.setPromptText("Artikelnummer");
		TextField description = new TextField();
		description.setPromptText("Bezeichnung");
		TextField cost = new TextField();
		cost.setPromptText("Einkaufspreis");
		TextField price = new TextField();
		price.setPromptText("Verkaufspreis");

		ObservableList<TextField> list = FXCollections.observableArrayList();
		list.addAll(itemNumber, description, cost, price);

		return list;

	}

	// Set Style to all items in list
	private void setTextFieldStyle(ObservableList<TextField> list) {
		for (TextField item : list) {
			item.setStyle("-fx-border-color:black; -fx-background-color: white;");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		BorderPane root = new BorderPane();
		HBox labelNameBox = new HBox();
		
		TextArea logScreen = new TextArea();
		logScreen.setEditable(false);
		logScreen.setMinSize(400, 200);
		logScreen.setMaxSize(400, 200);
		
		Label labelDescription = new Label();
		labelDescription.setText("Article Manager");
		labelNameBox.getChildren().add(labelDescription);
		labelNameBox.setAlignment(Pos.TOP_LEFT);

		root.setLeft(labelNameBox);
		
		VBox middleRootBox = new VBox();
		Label article = new Label("Articles");
		middleRootBox.getChildren().add(article);

		TableView<Article> tv = new TableView<Article>();
		tv.setMinSize(400, 400);
		tv.setMaxSize(400, 400);
		tv.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		middleRootBox.getChildren().add(tv);

		for (TableColumn<?, ?> object : getTableColumnList()) {
			tv.getColumns().add((TableColumn<Article, ?>) object);
		}

		HBox middleHorizontalBox = new HBox();
		middleHorizontalBox.setAlignment(Pos.CENTER);

		Button showAll = new Button("Alle anzeigen");
		showAll.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					tv.setItems(controller.getAll());
				} catch (SQLException e) {

					e.printStackTrace();
				}
				tv.refresh();
			}
		});

		middleHorizontalBox.getChildren().add(showAll);

		Button delete = new Button("Löschen");
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {

					for (Article article : tv.getSelectionModel().getSelectedItems()) {
						logScreen.appendText("Artikel " + article.getDescription() + " mit Artikelnummer "
								+ article.getItemNumber() + " wurde erfolgreich entfernt. \n");
						controller.deleteArticle(article.getId());
					}

					tv.setItems(controller.getAll());
					tv.refresh();

				} catch (SQLException e) {
					e.printStackTrace();
					logScreen.appendText("Artikel ist nicht vorhanden oder wurde bereits entfernt! \n");
				}
			}
		});
		
		middleHorizontalBox.getChildren().add(delete);
		middleRootBox.getChildren().add(middleHorizontalBox);
		middleRootBox.getChildren().add(logScreen);

		Button refreshConsole = new Button("History Einträge entfernen");
		middleRootBox.getChildren().add(refreshConsole);
		middleRootBox.setAlignment(Pos.CENTER);
		
		refreshConsole.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				logScreen.setText("");
			}
		});

		root.setCenter(middleRootBox);

		// Horizontal Bottom Box //
		HBox bottomBox = new HBox();

		// Text fields //
		ObservableList<TextField> textFieldList = getTextFields();
		setTextFieldStyle(textFieldList);

		// List<TextField> textFields = new ArrayList<TextField>();
		tv.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if (tv.getSelectionModel().getSelectedItem() != null) {
					textFieldList.get(0)
							.setText(Integer.toString(tv.getSelectionModel().getSelectedItem().getItemNumber()));
					textFieldList.get(1).setText(tv.getSelectionModel().getSelectedItem().getDescription());
					textFieldList.get(2).setText(Integer.toString(tv.getSelectionModel().getSelectedItem().getCost()));
					textFieldList.get(3).setText(Integer.toString(tv.getSelectionModel().getSelectedItem().getPrice()));
					articleId = tv.getSelectionModel().getSelectedItem().getId();
				}
			}
		});

		// Buttons //
		Button add = new Button("Hinzufügen");
		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					controller.setArticle(Integer.parseInt(textFieldList.get(0).getText()),
							textFieldList.get(1).getText(), Integer.parseInt(textFieldList.get(2).getText()),
							Integer.parseInt(textFieldList.get(3).getText()));
					logScreen.appendText("Artikel " + textFieldList.get(1).getText() + " wurde hinzugefügt! \n");
					tv.setItems(controller.getAll());
					tv.refresh();
					checkFields(textFieldList);
				} catch (NumberFormatException | SQLException e) {

					e.printStackTrace();
					logScreen.appendText(e.getMessage() + "\n");
					checkFields(textFieldList);
				}
			}
		});
		Button change = new Button("Ändern");
		change.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					controller.updateArticle(articleId, Integer.parseInt(textFieldList.get(0).getText()),
							textFieldList.get(1).getText(), Integer.parseInt(textFieldList.get(2).getText()),
							Integer.parseInt(textFieldList.get(3).getText()));
					tv.setItems(controller.getAll());
					tv.refresh();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		
		middleHorizontalBox.setSpacing(20.0);

		Button clear = new Button("Leeren");
		clear.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				for (TextField item : textFieldList) {
					item.setText("");
				}
			}
		});

		// Adding components //

		// line "add"
		bottomBox.getChildren().add(add);
		bottomBox.getChildren().add(change);
		bottomBox.getChildren().add(clear);
		for (TextField item : textFieldList) {
			bottomBox.getChildren().add(item);
		}
		bottomBox.setAlignment(Pos.BOTTOM_CENTER);
		bottomBox.setTranslateY(-15);

		root.setBottom(bottomBox);
		root.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {

				if (tv.getSelectionModel().getSelectedItem() != null) {
					tv.getSelectionModel().clearSelection();
				}
				event.consume();
			}
		});

		// CSS Area //
		root.setStyle("-fx-background-color: linear-gradient(to bottom, #996622 95% , #f4a442 0%, #996633 100%);");
		tv.setStyle("-fx-background-radius: 5; -fx-focus-color: transparent;-fx-selection-bar: #f4a442;");
		article.setStyle("-fx-border-color:black; -fx-background-color: white;");
		logScreen.setStyle("-fx-background-radius: 50; -fx-focus-color: transparent;");
		labelDescription.setFont(Font.font("Verdana", 14));
		labelDescription.setStyle(
				" -fx-background-color: linear-gradient(to bottom, #996622 10% , #f4a442 0%, #996633 100%); -fx-background-radius: 5;");

		// Screen Settings //
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());

		// Adding Scene, show stage //
		primaryStage.setScene(new Scene(root, 500, 600));
		primaryStage.setMaximized(true);
		primaryStage.show();

	}
}

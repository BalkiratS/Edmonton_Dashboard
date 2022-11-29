package com.example.milestone3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.example.milestone3.Utils.readData;

public class HelloApplication extends Application {

    private Neighbourhoods neighbourhoods;
    private final ComboBox<Object> neighbourhoodInput = new ComboBox<>();
    private VBox valuesVBox;
    private VBox inputBox;
    private VBox dropdownsVBox;
    private String boxStyle = "-fx-padding: 10;" +
            "-fx-border-style: solid inside;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 3;" +
            "-fx-border-color: gainsboro;";

    private final String titleFont = "-fx-background-color: transparent;" +
            "-fx-font-family: Georgia;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: bold";

    private String cssStyle = "-fx-background-color: transparent;" +
            "-fx-font-family: Verdana;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 10;" +
            "-fx-border-style: solid inside;" +
            "-fx-border-width: 1;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 4;" +
            "-fx-border-color: grey;";

    private BarChart<String, Number> barChart;
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        stage.setTitle("Neighbourhood information");

        BorderPane fullLayout = new BorderPane();

        Scene scene = new Scene(fullLayout, 1300, 850);
        stage.setScene(scene);

        neighbourhoods = new Neighbourhoods(); // read data

        configureInputBox();

        configureGraph();

        fullLayout.setLeft(inputBox);
        fullLayout.setCenter(barChart);

        stage.show();
    }

    private void configureInputBox(){
        inputBox = new VBox(10);
        //inputBox.maxWidth(100);
        inputBox.setStyle(cssStyle);

        configureChoices();

        configureFilters();

        HBox plotResetBox = new HBox(10);
        plotResetBox.setPadding(new Insets(20, 0, 0, 0));

        Button plot = new Button("Plot");
        plot.setMaxWidth(Double.MAX_VALUE);

        Button reset = new Button("Reset");
        reset.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(plot, Priority.ALWAYS);
        HBox.setHgrow(reset, Priority.ALWAYS);
        plotResetBox.getChildren().addAll(plot, reset);

        inputBox.getChildren().addAll(plotResetBox);
    }

    private void configureChoices(){
        Label title1 = new Label("Select Criteria");
        title1.setPadding(new Insets(0, 0, 5, 0));
        title1.setStyle(titleFont);

        ToggleGroup choiceGroup = new ToggleGroup();

        RadioButton assessedValueButton = new RadioButton("Average Assessed value");
        assessedValueButton.setPadding(new Insets(10, 0, 10, 0));
        assessedValueButton.setToggleGroup(choiceGroup);

        RadioButton developmentButton = new RadioButton("Average Development (2000-2013)");
        developmentButton.setPadding(new Insets(0, 0, 10, 0));
        developmentButton.setToggleGroup(choiceGroup);

        RadioButton languageButton = new RadioButton("Top 10 Languages");
        languageButton.setPadding(new Insets(0, 0, 10, 0));
        languageButton.setToggleGroup(choiceGroup);

        Separator line = new Separator();

        inputBox.getChildren().addAll(title1, assessedValueButton, developmentButton, languageButton, line);
    }

    private void configureFilters(){
        Label title2 = new Label("Filters");
        title2.setStyle(titleFont);
        title2.setPadding(new Insets(0, 0, 5, 0));

        Label rangeLabel = new Label("Assessed Value Range:");
        rangeLabel.setPadding(new Insets(10, 0 , 0, 0));
        ComboBox<String> rangeBox = new ComboBox<>();
        rangeBox.setMaxWidth(250);

        String[] rangeList = {"$0 - $125,000", "$125,000 - $200,000", "$200,000 - $235,000",
                "$235,000 - $255,000", "$255,000 - $275,000","$275,000 - $285,000", "$285,000 - $295,000", "$295,000 - $315,000",
                "$315,000 - $340,000", "$340,000 - $380,000", "$380,000 - $410,000", "$410,000 - $445,000", "$445,000 - $575,000",
                "$575,000 - $750,000", "$750,000 - $1,000,000", "$1,000,000 - $50,000,000"};

        rangeBox.getItems().addAll(rangeList);

        Label neighbourhood1Label = new Label("Neighbourhood 1:");
        neighbourhood1Label.setPadding(new Insets(10, 0 , 0, 0));
        ComboBox<String> neighbourhood1Box = new ComboBox<>();
        neighbourhood1Box.setMaxWidth(250);
        neighbourhood1Box.getItems().addAll(neighbourhoods.getUniqueNeighbourhoodNames());


        Label neighbourhood2Label = new Label("Neighbourhood 2:");
        neighbourhood2Label.setPadding(new Insets(10, 0 , 0, 0));
        ComboBox<String> neighbourhood2Box = new ComboBox<>();
        neighbourhood2Box.setMaxWidth(250);
        neighbourhood2Box.getItems().addAll(neighbourhoods.getUniqueNeighbourhoodNames());


        Label neighbourhood3Label = new Label("Neighbourhood 3:");
        neighbourhood3Label.setPadding(new Insets(10, 0 , 0, 0));
        ComboBox<String> neighbourhood3Box = new ComboBox<>();
        neighbourhood3Box.setMaxWidth(250);
        neighbourhood3Box.getItems().addAll(neighbourhoods.getUniqueNeighbourhoodNames());

        Separator line2 = new Separator();

        inputBox.getChildren().addAll(title2, rangeLabel, rangeBox, neighbourhood1Label, neighbourhood1Box, neighbourhood2Label, neighbourhood2Box, neighbourhood3Label, neighbourhood3Box, line2);
    }

    private void configureGraph(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("yaxis");
        xAxis.setStyle("-fx-font-weight: bold;");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Graph");
        yAxis.setStyle("-fx-font-weight: bold;");
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("x axis");
        barChart.setAnimated(false);
        barChart.setLegendVisible(false);
    }

}
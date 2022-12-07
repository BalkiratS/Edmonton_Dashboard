package com.example.milestone3;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Objects;

public class HelloApplication extends Application {
    private Neighbourhoods neighbourhoods;
    private VBox inputBox;
    private RadioButton assessedValueButton;
    private RadioButton developmentButton;
    private RadioButton languageButton;
    private ComboBox<String> rangeBox;
    private ComboBox<String> neighbourhood1Box;
    private ComboBox<String> neighbourhood2Box;
    private ComboBox<String> neighbourhood3Box;
    private BorderPane mainLayout;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {

        configureInputBox();

        stage.setTitle("Neighbourhood information");
        mainLayout = new BorderPane();

        Scene main = new Scene(mainLayout, 1200, 850);
        main.getStylesheets().add(Objects.requireNonNull(getClass().getResource("GraphColours.css")).toExternalForm());

        mainLayout.setLeft(inputBox);

        stage.setScene(main);
        stage.show();

    }
    @Override
    public void init() throws Exception {
        neighbourhoods = new Neighbourhoods(); // read data
    }

    public static void main(String[] args) {
        System.setProperty("javafx.preloader", preloader.class.getCanonicalName());
        launch(args);
    }

    private void configureInputBox(){
        inputBox = new VBox(10);
        inputBox.getStyleClass().add("inputBox");

        configureChoices();

        configureFilters();

        HBox plotResetBox = new HBox(10);
        plotResetBox.setPadding(new Insets(20, 0, 0, 0));

        Button plot = new Button("Plot");
        plot.setMaxWidth(Double.MAX_VALUE);
        plot.setOnAction(actionEvent -> plotOnClick());

        Button reset = new Button("Reset");
        reset.setMaxWidth(Double.MAX_VALUE);
        reset.setOnAction(actionEvent -> resetOnClick());

        HBox.setHgrow(plot, Priority.ALWAYS);
        HBox.setHgrow(reset, Priority.ALWAYS);
        plotResetBox.getChildren().addAll(plot, reset);

        inputBox.getChildren().addAll(plotResetBox);
    }

    private void resetOnClick(){
        resetNeighbourhoodBoxes();
        rangeBox.setValue(null);
        rangeBox.setDisable(true);
        neighbourhood1Box.setDisable(true);
        assessedValueButton.setSelected(false);
        developmentButton.setSelected(false);
        languageButton.setSelected(false);
        mainLayout.setCenter(null);
    }

    private void configureChoices(){
        Label title1 = new Label("Select Criteria");
        title1.setPadding(new Insets(0, 0, 5, 0));
        title1.getStyleClass().add("title");

        ToggleGroup choiceGroup = new ToggleGroup();

        assessedValueButton = new RadioButton("Average Assessed value");
        assessedValueButton.setPadding(new Insets(10, 0, 10, 0));
        assessedValueButton.setToggleGroup(choiceGroup);
        assessedValueButton.setOnAction(actionEvent -> {choiceButtonOnClick();
                                                        neighbourhood1Box.setDisable(true);});

        developmentButton = new RadioButton("Average Development (2000-2013)");
        developmentButton.setPadding(new Insets(0, 0, 10, 0));
        developmentButton.setToggleGroup(choiceGroup);
        developmentButton.setOnAction(actionEvent -> choiceButtonOnClick());

        languageButton = new RadioButton("Top 10 Languages");
        languageButton.setPadding(new Insets(0, 0, 10, 0));
        languageButton.setToggleGroup(choiceGroup);
        languageButton.setOnAction(actionEvent -> choiceButtonOnClick());

        Separator line = new Separator();

        inputBox.getChildren().addAll(title1, assessedValueButton, developmentButton, languageButton, line);
    }

    private void choiceButtonOnClick(){
        rangeBox.setValue(null);
        rangeBox.setDisable(false);
        resetNeighbourhoodBoxes();
        fillNeighbourhoodBox();
        neighbourhood1Box.setDisable(false);
    }

    private void configureFilters(){
        Label title2 = new Label("Filters");
        title2.getStyleClass().add("title");
        title2.setPadding(new Insets(0, 0, 5, 0));

        Label rangeLabel = new Label("Assessed Value Range:");
        rangeLabel.setPadding(new Insets(10, 0 , 0, 0));
        rangeBox = new ComboBox<>();
        rangeBox.setDisable(true);
        rangeBox.setMaxWidth(250);

        String[] rangeList = {"$0 - $125,000", "$125,000 - $200,000", "$200,000 - $235,000",
                "$235,000 - $255,000", "$255,000 - $275,000","$275,000 - $285,000", "$285,000 - $295,000", "$295,000 - $315,000",
                "$315,000 - $340,000", "$340,000 - $380,000", "$380,000 - $410,000", "$410,000 - $445,000", "$445,000 - $575,000",
                "$575,000 - $750,000", "$750,000 - $1,000,000", "$1,000,000 - $50,000,000"};

        rangeBox.getItems().addAll(rangeList);

        Label neighbourhood1Label = new Label("Neighbourhood 1:");
        neighbourhood1Label.setPadding(new Insets(10, 0 , 0, 0));
        neighbourhood1Box = new ComboBox<>();
        neighbourhood1Box.setDisable(true);
        neighbourhood1Box.setMaxWidth(250);
        neighbourhood1Box.getItems().addAll(neighbourhoods.getUniqueNeighbourhoodNames());


        Label neighbourhood2Label = new Label("Neighbourhood 2:");
        neighbourhood2Label.setPadding(new Insets(10, 0 , 0, 0));
        neighbourhood2Box = new ComboBox<>();
        neighbourhood2Box.setDisable(true);
        neighbourhood2Box.setMaxWidth(250);


        Label neighbourhood3Label = new Label("Neighbourhood 3:");
        neighbourhood3Label.setPadding(new Insets(10, 0 , 0, 0));
        neighbourhood3Box = new ComboBox<>();
        neighbourhood3Box.setDisable(true);
        neighbourhood3Box.setMaxWidth(250);

        rangeBox.setOnAction(actionEvent -> {

            resetNeighbourhoodBoxes();
            fillNeighbourhoodBox();

        });

        neighbourhood1Box.setOnAction(actionEvent -> neighbourhoodBoxOnCLick(neighbourhood1Box, neighbourhood2Box));

        neighbourhood2Box.setOnAction(actionEvent -> neighbourhoodBoxOnCLick(neighbourhood2Box, neighbourhood3Box));

        Separator line2 = new Separator();

        inputBox.getChildren().addAll(title2, rangeLabel, rangeBox, neighbourhood1Label, neighbourhood1Box, neighbourhood2Label, neighbourhood2Box, neighbourhood3Label, neighbourhood3Box, line2);
    }

    private void neighbourhoodBoxOnCLick(ComboBox<String> boxClicked, ComboBox<String> nextBox){
        nextBox.setValue(null);
        nextBox.getItems().clear();
        nextBox.getItems().addAll(boxClicked.getItems());
        nextBox.getItems().remove(boxClicked.getValue());
        nextBox.setDisable(false);
    }

    private void resetNeighbourhoodBoxes(){

        neighbourhood1Box.getItems().clear();

        neighbourhood2Box.getItems().clear();
        neighbourhood2Box.setDisable(true);

        neighbourhood3Box.getItems().clear();
        neighbourhood3Box.setDisable(true);
    }

    private void fillNeighbourhoodBox(){
        if (rangeBox.getValue() != null){
            Double minRange = currencyToDouble(rangeBox.getValue().split("-")[0]);
            Double maxRange = currencyToDouble(rangeBox.getValue().split("-")[1]);
            for (Neighbourhood neighbourhood: neighbourhoods.getNeighbourhoodsInRange(minRange, maxRange)){
                neighbourhood1Box.getItems().add(neighbourhood.getNeighbourhoodName());
            }
        }
        else {
            neighbourhood1Box.getItems().addAll(neighbourhoods.getUniqueNeighbourhoodNames());
        }

        if (developmentButton.isSelected()){
            for (Neighbourhood neighbourhood: neighbourhoods.getEmptyDevelopmentNeighbourhoods()) {
                neighbourhood1Box.getItems().remove(neighbourhood.getNeighbourhoodName());
            }
        } else if (languageButton.isSelected()) {
            for (Neighbourhood neighbourhood: neighbourhoods.getEmptyLanguageNeighbourhoods()) {
                neighbourhood1Box.getItems().remove(neighbourhood.getNeighbourhoodName());
            }
        }
    }

    private Double currencyToDouble(String currency){
        return Double.parseDouble(currency.replaceAll("[$,]", ""));
    }

    private void plotOnClick(){
        if(validateInputs()){
            if (assessedValueButton.isSelected()){
                Double minRange = currencyToDouble(rangeBox.getValue().split("-")[0]);
                Double maxRange = currencyToDouble(rangeBox.getValue().split("-")[1]);
                createAssessedValueGraph(minRange, maxRange);
            } else if (developmentButton.isSelected()) {
                createDevelopmentGraph(neighbourhood1Box.getValue(), neighbourhood2Box.getValue(), neighbourhood3Box.getValue());
            } else if (languageButton.isSelected()) {
                createLanguageGraph(neighbourhood1Box.getValue(), neighbourhood2Box.getValue(), neighbourhood3Box.getValue());
            }
        }

    }

    private boolean validateInputs() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invalid Input");

        if (assessedValueButton.isSelected() & rangeBox.getValue() == null) {
            alert.setHeaderText(null);
            alert.setContentText("Please Select an Assessment Value Range From Filters");
            alert.showAndWait();
            return false;
        } else if (developmentButton.isSelected() &
                neighbourhood1Box.getValue() == null) {
            alert.setHeaderText(null);
            alert.setContentText("Please Select Minimum One Neighbourhood From Filters");
            alert.showAndWait();
            return false;
        } else if (languageButton.isSelected() &
                neighbourhood1Box.getValue() == null) {
            alert.setHeaderText(null);
            alert.setContentText("Please Select Minimum One Neighbourhood From Filters");
            alert.showAndWait();
            return false;
        } else if(!assessedValueButton.isSelected() & !developmentButton.isSelected() & !languageButton.isSelected()){
            alert.setHeaderText(null);
            alert.setContentText("Please Select One of the Criteria Buttons");
            alert.showAndWait();
            return false;
        }
        return true;
    }
    private void createAssessedValueGraph(double minRange, double maxRange){
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Neighbourhood");
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Average Assessed Value");

        BarChart<Number, String> assessedValueChart = new BarChart<>(xAxis, yAxis);
        assessedValueChart.setTitle("Average Assessed Value");
        assessedValueChart.setAnimated(false);
        assessedValueChart.setLegendVisible(false);
        assessedValueChart.setPadding(new Insets(10, 20, 10, 10));

        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (Neighbourhood neighbourhood : neighbourhoods.getNeighbourhoodsList()) { //create the series with list of AccountEntry
            if (neighbourhood.getAverageAssessedValue() >= minRange && neighbourhood.getAverageAssessedValue() <= maxRange){
                series.getData().add(new XYChart.Data<>(neighbourhood.getAverageAssessedValue(), neighbourhood.getNeighbourhoodName()));
            }

        }
        assessedValueChart.getData().add(series); //add series to bar chart

        for (XYChart.Series<Number, String> s : assessedValueChart.getData()) {
            for (XYChart.Data<Number, String> entry : s.getData()) {
                currencyFormat.setMaximumFractionDigits(0);
                Tooltip t = new Tooltip("Average Assessed Value: " + currencyFormat.format(entry.getXValue()));
                Tooltip.install(entry.getNode(), t);
            }
        }

        mainLayout.setCenter(assessedValueChart);

    }

    private void createDevelopmentGraph(String neighbourhood1, String neighbourhood2, String neighbourhood3){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Development Value");

        LineChart<String, Number> developmentChart = new LineChart<>(xAxis, yAxis);
        developmentChart.setTitle("Development over year");
        developmentChart.setAnimated(false);
        developmentChart.setPadding(new Insets(10, 10, 10, 10));

        if (neighbourhood1 != null){
            developmentChart.getData().add(addDevelopmentSeries(neighbourhood1));
        }
        if (neighbourhood2 != null){
            developmentChart.getData().add(addDevelopmentSeries(neighbourhood2));
        }
        if (neighbourhood3 != null){
            developmentChart.getData().add(addDevelopmentSeries(neighbourhood3));
        }

        for (XYChart.Series<String, Number> s : developmentChart.getData()) {
            for (XYChart.Data<String, Number> entry : s.getData()) {
                currencyFormat.setMaximumFractionDigits(0);
                Tooltip t = new Tooltip("Total Investment: " + currencyFormat.format(entry.getYValue()));
                Tooltip.install(entry.getNode(), t);
            }
        }

        mainLayout.setCenter(developmentChart);

    }
    private XYChart.Series<String, Number> addDevelopmentSeries(String neighbourhoodName){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(neighbourhoodName);

        for ( Integer key : neighbourhoods.getNeighbourhoodByName(neighbourhoodName).getNeighbourhoodDevelopment().keySet()) {
            series.getData().add(new XYChart.Data<>(key.toString(), neighbourhoods.getNeighbourhoodByName(neighbourhoodName).getNeighbourhoodDevelopment().get(key)));
        }
        return series;

    }

    private void createLanguageGraph(String neighbourhood1, String neighbourhood2, String neighbourhood3){
        NumberAxis  xAxis = new NumberAxis();
        xAxis.setLabel("Households");
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Languages");

        BarChart<Number, String> languageCharts = new BarChart<>(xAxis, yAxis);
        languageCharts.setTitle("Top 10 Languages Spoken");
        languageCharts.setAnimated(false);
        languageCharts.setPadding(new Insets(10, 10, 10, 10));

        if (neighbourhood1 != null){
            languageCharts.getData().add(addLanguageSeries(neighbourhood1));
        }
        if (neighbourhood2 != null){
            languageCharts.getData().add(addLanguageSeries(neighbourhood2));
        }
        if (neighbourhood3 != null){
            languageCharts.getData().add(addLanguageSeries(neighbourhood3));
        }

        for (XYChart.Series<Number, String> s : languageCharts.getData()) {
            for (XYChart.Data<Number, String> entry : s.getData()) {
                Tooltip t = new Tooltip("Number of speakers: " + entry.getXValue().toString());
                Tooltip.install(entry.getNode(), t);
            }
        }

        mainLayout.setCenter(languageCharts);
    }

    private XYChart.Series<Number, String> addLanguageSeries(String neighbourhoodName){
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        series.setName(neighbourhoodName);

        for ( String key : neighbourhoods.getNeighbourhoodByName(neighbourhoodName).getLanguages().keySet().stream().sorted(Comparator.reverseOrder()).toList()) {
            series.getData().add(new XYChart.Data<>(neighbourhoods.getNeighbourhoodByName(neighbourhoodName).getLanguages().get(key), key));
        }
        return series;
    }
}
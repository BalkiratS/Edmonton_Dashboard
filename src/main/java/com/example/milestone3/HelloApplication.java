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
import java.util.Comparator;

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
    private Button plot;
    private Button reset;
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
    private BarChart<Number, String> assessedValueChart;
    private LineChart<String, Number> developmentChart;
    private BarChart<Number, String> languageCharts;

    private BorderPane mainLayout;
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {

        configureInputBox();

        stage.setTitle("Neighbourhood information");
        mainLayout = new BorderPane();

        Scene main = new Scene(mainLayout, 1200, 850);
        main.getStylesheets().add(getClass().getResource("GraphColours.css").toExternalForm());

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
        inputBox.setStyle(cssStyle);

        configureChoices();

        configureFilters();

        HBox plotResetBox = new HBox(10);
        plotResetBox.setPadding(new Insets(20, 0, 0, 0));

        plot = new Button("Plot");
        plot.setMaxWidth(Double.MAX_VALUE);
        plot.setOnAction(actionEvent -> plotOnClick());

        reset = new Button("Reset");
        reset.setMaxWidth(Double.MAX_VALUE);
        reset.setOnAction(actionEvent -> {  rangeBox.setValue(null);
                                            rangeBox.setDisable(true);
                                            neighbourhood1Box.setDisable(true);
                                            assessedValueButton.setSelected(false);
                                            developmentButton.setSelected(false);
                                            languageButton.setSelected(false);});

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

        assessedValueButton = new RadioButton("Average Assessed value");
        assessedValueButton.setPadding(new Insets(10, 0, 10, 0));
        assessedValueButton.setToggleGroup(choiceGroup);
        assessedValueButton.setOnAction(actionEvent -> {rangeBox.setValue(null);
                                                        rangeBox.setDisable(false);
                                                        neighbourhood1Box.setDisable(true);});

        developmentButton = new RadioButton("Average Development (2000-2013)");
        developmentButton.setPadding(new Insets(0, 0, 10, 0));
        developmentButton.setToggleGroup(choiceGroup);
        developmentButton.setOnAction(actionEvent -> {
                                                    rangeBox.setValue(null);
                                                    rangeBox.setDisable(false);
                                                    neighbourhood1Box.setDisable(false);});

        languageButton = new RadioButton("Top 10 Languages");
        languageButton.setPadding(new Insets(0, 0, 10, 0));
        languageButton.setToggleGroup(choiceGroup);
        languageButton.setOnAction(actionEvent -> { rangeBox.setValue(null);
                                                    rangeBox.setDisable(false);
                                                    neighbourhood1Box.setDisable(false);});

        Separator line = new Separator();

        inputBox.getChildren().addAll(title1, assessedValueButton, developmentButton, languageButton, line);
    }

    private void configureFilters(){
        Label title2 = new Label("Filters");
        title2.setStyle(titleFont);
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


            neighbourhood1Box.getItems().clear();

            neighbourhood2Box.getItems().clear();
            neighbourhood2Box.setDisable(true);

            neighbourhood3Box.getItems().clear();
            neighbourhood3Box.setDisable(true);

            if (rangeBox.getValue() != null){
                Double minRange = currencyToDouble(rangeBox.getValue().split("-")[0]);
                Double maxRange = currencyToDouble(rangeBox.getValue().split("-")[1]);
                for (Neighbourhood neighbourhood: neighbourhoods.getNeighbourhoodsInRange(minRange, maxRange)){
                    neighbourhood1Box.getItems().add(neighbourhood.getNeighbourhoodName());
                }
            }

        });

        neighbourhood1Box.setOnAction(actionEvent -> {
                                                    neighbourhood2Box.setValue(null);
                                                    neighbourhood2Box.getItems().clear();
                                                    neighbourhood2Box.getItems().addAll(neighbourhood1Box.getItems());
                                                    neighbourhood2Box.getItems().remove(neighbourhood1Box.getValue());
                                                    neighbourhood2Box.setDisable(false);
                                                      });

        neighbourhood2Box.setOnAction(actionEvent -> {
                                                    neighbourhood3Box.setValue(null);
                                                    neighbourhood3Box.getItems().clear();
                                                    neighbourhood3Box.getItems().addAll(neighbourhood2Box.getItems());
                                                    neighbourhood3Box.getItems().remove(neighbourhood1Box.getValue());
                                                    neighbourhood3Box.getItems().remove(neighbourhood2Box.getValue());
                                                    neighbourhood3Box.setDisable(false);});

        Separator line2 = new Separator();

        inputBox.getChildren().addAll(title2, rangeLabel, rangeBox, neighbourhood1Label, neighbourhood1Box, neighbourhood2Label, neighbourhood2Box, neighbourhood3Label, neighbourhood3Box, line2);
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
                ((rangeBox.getValue() == null) || (neighbourhood1Box.getValue() == null))) {
            alert.setHeaderText(null);
            alert.setContentText("Please Select an Assessment Value Range and a Minimum of" +
                    " One Neighbourhood From Filters");
            alert.showAndWait();
            return false;
        } else if (languageButton.isSelected() &
                ((rangeBox.getValue() == null) || (neighbourhood1Box.getValue() == null))) {
            alert.setHeaderText(null);
            alert.setContentText("Please Select an Assessment Value Range and a Minimum of" +
                    " One Neighbourhood From Filters");
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
        yAxis.setStyle("-fx-font-weight: bold;");
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Average Assessed Value");
        xAxis.setStyle("-fx-font-weight: bold;");

        assessedValueChart = new BarChart<>(xAxis, yAxis);
        assessedValueChart.setTitle("Average Assessed Value");
        assessedValueChart.setAnimated(false);
        assessedValueChart.setLegendVisible(false);
        assessedValueChart.setPadding(new Insets(0, 20, 0, 0));

        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (Neighbourhood neighbourhood : neighbourhoods.getNeighbourhoodsList()) { //create the series with list of AccountEntry
            if (neighbourhood.getAverageAssessedValue() >= minRange && neighbourhood.getAverageAssessedValue() <= maxRange){
                series.getData().add(new XYChart.Data<>(neighbourhood.getAverageAssessedValue(), neighbourhood.getNeighbourhoodName()));
            }

        }
        assessedValueChart.getData().add(series); //add series to bar chart

        mainLayout.setCenter(assessedValueChart);

    }

    private void createDevelopmentGraph(String neighbourhood1, String neighbourhood2, String neighbourhood3){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");
        xAxis.setStyle("-fx-font-weight: bold;");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Development Value");
        yAxis.setStyle("-fx-font-weight: bold;");

        developmentChart = new LineChart<>(xAxis, yAxis);
        developmentChart.setTitle("Development over year");
        developmentChart.setAnimated(false);
        developmentChart.setPadding(new Insets(0, 10, 0, 0));

        if (neighbourhood1 != null){
            developmentChart.getData().add(addDevelopmentSeries(neighbourhood1));
        }
        if (neighbourhood2 != null){
            developmentChart.getData().add(addDevelopmentSeries(neighbourhood2));
        }
        if (neighbourhood3 != null){
            developmentChart.getData().add(addDevelopmentSeries(neighbourhood3));
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
        xAxis.setStyle("-fx-font-weight: bold;");
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Languages");
        yAxis.setStyle("-fx-font-weight: bold;");
        languageCharts = new BarChart<>(xAxis, yAxis);
        languageCharts.setTitle("Top 10 Languages Spoken");
        languageCharts.setAnimated(false);
        languageCharts.setPadding(new Insets(0, 10, 0, 0));

        if (neighbourhood1 != null){
            languageCharts.getData().add(addLanguageSeries(neighbourhood1));
        }
        if (neighbourhood2 != null){
            languageCharts.getData().add(addLanguageSeries(neighbourhood2));
        }
        if (neighbourhood3 != null){
            languageCharts.getData().add(addLanguageSeries(neighbourhood3));
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
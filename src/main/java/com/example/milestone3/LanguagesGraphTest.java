package com.example.milestone3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LanguagesGraphTest extends Application {

    private BarChart<Number, String> languageCharts;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chart Testing");

        // Creates the main Scene Node with main container
        VBox mainBox = new VBox(10);
        Scene scene = new Scene(mainBox, 870, 700);
        primaryStage.setScene(scene);

        configureGraph();

        Button plotButton = new Button("Plot");
        plotButton.setOnAction(actionEvent -> plotData("ermineskin"));

        mainBox.getChildren().addAll(languageCharts, plotButton);

        primaryStage.show();
    }

    private void configureGraph() {
        // Configures barchart's elements
        NumberAxis  xAxis = new NumberAxis();
        xAxis.setLabel("Households");
        CategoryAxis yAxis = new CategoryAxis();
        yAxis.setLabel("Languages");
        languageCharts = new BarChart<>(xAxis, yAxis);
        languageCharts.setTitle("Top 10 Languages Spoken");
        languageCharts.setAnimated(false);
        languageCharts.setLegendVisible(false);
    }

    public void plotData(String neighbourhoodName) {
        Neighbourhoods neighbourhoods = new Neighbourhoods(); // create the list of neighbourhoods

        XYChart.Series<Number, String> series = new XYChart.Series<>();
        for (Neighbourhood neighbourhood : neighbourhoods.getNeighbourhoodsList()) {
            if (neighbourhoodName.equalsIgnoreCase(neighbourhood.getNeighbourhoodName())){

                for ( String key : neighbourhood.getLanguages().keySet() ) {
                    series.getData().add(new XYChart.Data<>(neighbourhood.getLanguages().get(key), key));
                }
            }

        }
        languageCharts.getData().add(series); //add series to bar chart
    }
}

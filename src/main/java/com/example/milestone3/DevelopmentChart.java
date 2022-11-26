package com.example.milestone3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class DevelopmentChart extends Application {
    private LineChart<String, Number> developmentChart;
    Button plotDevelopmentButton;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Development Chart Testing");

        VBox mainBox = new VBox(10);

        Scene scene = new Scene(mainBox, 700, 700);
        stage.setScene(scene);


        plotDevelopmentButton = new Button("Plot");
        configureDevelopmentGraph();

        List<String> neighbourhoodNames = new ArrayList<>();
        neighbourhoodNames.add("westwood");
        neighbourhoodNames.add("Balwin");
        neighbourhoodNames.add("Belgravia");
        plotDevelopmentButton.setOnAction(actionEvent -> plotDevelopmentGraph(neighbourhoodNames));

        developmentChart.prefHeightProperty().bind(scene.heightProperty());

        mainBox.getChildren().addAll(developmentChart, plotDevelopmentButton);

        stage.show();

    }
    private void plotDevelopmentGraph(List<String> neighbourhoodNameList){
        Neighbourhoods neighbourhoods = new Neighbourhoods(); // create the list of neighbourhoods

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();

        for (Neighbourhood neighbourhood : neighbourhoods.getNeighbourhoodsList()) { //create the series with list of AccountEntry
            for (String nName : neighbourhoodNameList){
                if (nName.equalsIgnoreCase(neighbourhood.getNeighbourhoodName())){
                    developmentChart.getData().add(addNeighbourhood(neighbourhood));
                    break;
                }
            }
        }
    }

    private XYChart.Series<String, Number> addNeighbourhood(Neighbourhood neighbourhood){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(neighbourhood.getNeighbourhoodName());
        for ( Integer key : neighbourhood.getNeighbourhoodDevelopment().keySet()) {
            series.getData().add(new XYChart.Data<>(key.toString(), neighbourhood.getNeighbourhoodDevelopment().get(key)));
        }
        return series;
    }

    private void configureDevelopmentGraph(){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Year");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Development Value");

        developmentChart = new LineChart<>(xAxis, yAxis);
        developmentChart.setTitle("Development over year");
        developmentChart.setAnimated(false);
    }

}

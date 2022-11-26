package com.example.milestone3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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

    private final ComboBox<Object> neighbourhoodInput = new ComboBox<>();

    private BarChart<String, Number> barChart;
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {


        //Vbox for Neighbourhood range check boxes
        VBox valuesVBox = new VBox();
        valuesVBox.setSpacing(10);
        //range menu style
        valuesVBox.setMaxSize(300, 100);
        valuesVBox.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 3;" +
                "-fx-border-color: gainsboro;");
        Label assessmentTitle = new Label("Neighbourhood Assessments Range");
        valuesVBox.getChildren().add(assessmentTitle);

        //labels array for checkboxes
        String labels[] = {"$0 - $200,000", "$200,000 - $300,000", "$300,000 - $400,000",
                            "$400,000 - $500,000", "$500,000 - $600,000","$600,000 - $700,000"};

        // checkboxes for the range of assessments
        for (int i=0; i< labels.length; i++){
            CheckBox box = new CheckBox(labels[i]);
            valuesVBox.getChildren().add(box);
//            box.setIndeterminate(true);

            //create event handler for each checkbox with a label to display it
            Label rangeSelected = new Label();
            String range = labels[i];
            EventHandler<ActionEvent> select = e -> {
                if (box.isSelected()){
                    rangeSelected.setText(range + " is selected");
                }
            };
            box.setOnAction(select);

            valuesVBox.getChildren().add(rangeSelected);
        }

        //bottom left VBOX which includes dropdowns
        VBox dropdownsVBox = new VBox();
        dropdownsVBox.setSpacing(10);
        dropdownsVBox.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 3;" +
                "-fx-border-color: gainsboro;");

        //get unique list of neighbourhoods
        List<String[]> data;
        data = readData("/Datasets/Property_Assessment_Data__Current_Calendar_Year_ (1).csv");
        Set<String> uniqueNeighbourhoods = new HashSet<>();

        for (String[] row : data){
            if (!row[6].equals(""))
                uniqueNeighbourhoods.add(row[6]);
        }

        //populate dropdown
        neighbourhoodInput.setMaxWidth(200);
        neighbourhoodInput.getItems().clear();
        neighbourhoodInput.getItems().addAll(uniqueNeighbourhoods);

        //add dropdown to VBOX
        dropdownsVBox.getChildren().add(neighbourhoodInput);


        //this VBOX will have 2 VBOX menus (the entire left side menu) - parent VBOX, 2 VBOX children
        VBox parentVBox = new VBox();
        valuesVBox.setSpacing(10);

        parentVBox.getChildren().addAll(valuesVBox, dropdownsVBox);

        //create barchart for the right side of the window (placeholder)
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





        //full layout
        BorderPane fullLayout = new BorderPane();
        fullLayout.setLeft(parentVBox);
        fullLayout.setRight(barChart);

        stage.setTitle("Neighbourhood information");
        Scene scene = new Scene(fullLayout);
        stage.setMinWidth(1000);
        stage.setMinHeight(800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
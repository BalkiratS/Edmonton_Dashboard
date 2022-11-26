package com.example.milestone3;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
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

    private Neighbourhoods neighbourhoods;
    private final ComboBox<Object> neighbourhoodInput = new ComboBox<>();
    private VBox valuesVBox;
    private VBox dropdownsVBox;
    private String boxStyle = "-fx-padding: 10;" +
            "-fx-border-style: solid inside;" +
            "-fx-border-width: 2;" +
            "-fx-border-insets: 5;" +
            "-fx-border-radius: 3;" +
            "-fx-border-color: gainsboro;";
    private BarChart<String, Number> barChart;
    @Override
    public void start(Stage stage) throws IOException, URISyntaxException {
        stage.setTitle("Neighbourhood information");

        BorderPane fullLayout = new BorderPane();

        Scene scene = new Scene(fullLayout, 1000, 800);
        stage.setScene(scene);

        neighbourhoods = new Neighbourhoods(); // read data

        //Vbox for Neighbourhood range check boxes
        setValuesBox();

        //bottom left VBOX which includes dropdowns
        setDropdownsVBox();


        //this VBOX will have 2 VBOX menus (the entire left side menu) - parent VBOX, 2 VBOX children
        VBox parentVBox = new VBox();
        valuesVBox.setSpacing(10);

        parentVBox.getChildren().addAll(valuesVBox, dropdownsVBox);

        //create barchart for the right side of the window (placeholder)
        configureGraph();

        fullLayout.setLeft(parentVBox);
        fullLayout.setCenter(barChart);

        stage.show();
    }

    private void setValuesBox(){

        valuesVBox = new VBox();
        valuesVBox.setSpacing(10);

        //range menu style
        valuesVBox.setMaxSize(300, 100);
        valuesVBox.setStyle(boxStyle);

        Label assessmentTitle = new Label("Neighbourhood Assessments Range");
        valuesVBox.getChildren().add(assessmentTitle);

        //labels array for checkboxes
        String[] labels = {"$0 - $200,000", "$200,000 - $300,000", "$300,000 - $400,000",
                "$400,000 - $500,000", "$500,000 - $600,000","$600,000 - $700,000"};

        ToggleGroup tg = new ToggleGroup();

        // radio buttons for the range of assessments
        for (String label : labels) {
            RadioButton box = new RadioButton(label);
            box.setToggleGroup(tg);
            valuesVBox.getChildren().add(box);

            //create event handler for each checkbox with a label to display it
            Label rangeSelected = new Label();
            String range = label;
            EventHandler<ActionEvent> select = e -> {
                if (box.isSelected()) {
                    rangeSelected.setText(range + " is selected");
                }
            };
            box.setOnAction(select);

            valuesVBox.getChildren().add(rangeSelected);
        }
    }

    private void setDropdownsVBox(){
        dropdownsVBox = new VBox();
        dropdownsVBox.setSpacing(10);
        dropdownsVBox.setStyle(boxStyle);

        //populate dropdown
        neighbourhoodInput.setMaxWidth(200);
        neighbourhoodInput.getItems().clear();
        neighbourhoodInput.getItems().addAll(neighbourhoods.getUniqueNeighbourhoodNames());

        //add dropdown to VBOX
        dropdownsVBox.getChildren().add(neighbourhoodInput);
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
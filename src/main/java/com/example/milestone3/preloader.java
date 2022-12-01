package com.example.milestone3;

import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class preloader extends Preloader {
    private Stage preloaderStage;

    @Override
    public void start(Stage stage) throws Exception {
        // add loading screen
        this.preloaderStage = stage;
        BorderPane borderPane = new BorderPane();

        ProgressIndicator pg = new ProgressIndicator();
        Label loadingLabel = new Label("Loading data...");

        borderPane.setLeft(pg);
        borderPane.setCenter(loadingLabel);

        BorderPane.setAlignment(pg, Pos.CENTER_LEFT);
        BorderPane.setAlignment(loadingLabel, Pos.CENTER_LEFT);
        BorderPane.setMargin(pg, new Insets(10,20,10,40));

        Scene loading = new Scene(borderPane, 300, 90);
        preloaderStage.setTitle("Loading...");
        preloaderStage.setScene(loading);
        preloaderStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        switch (type){
            case BEFORE_START:
                preloaderStage.hide();
                break;
        }
    }
}

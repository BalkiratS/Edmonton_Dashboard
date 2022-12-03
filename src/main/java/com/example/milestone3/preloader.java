package com.example.milestone3;

import javafx.application.Preloader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.Objects;

public class preloader extends Preloader {
    private Stage preloaderStage;

    @Override
    public void start(Stage stage) throws Exception {
        // add loading screen
        this.preloaderStage = stage;
        BorderPane borderPane = new BorderPane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("edmonton.png"))),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true))));

        HBox loadingBox = new HBox(10);

        ProgressIndicator pg = new ProgressIndicator();
        pg.setStyle(" -fx-progress-color: white;");
        pg.setMaxWidth(35);
        pg.setMaxHeight(35);

        Label loadingLabel = new Label("Loading data...");
        loadingLabel.setStyle("-fx-text-fill: white;" +
                                "-fx-font-size: 16px;" );


        loadingBox.getChildren().addAll(pg, loadingLabel);
        loadingBox.setPadding(new Insets(0, 0, 15, 0));

        borderPane.setBottom(loadingBox);

        BorderPane.setAlignment(pg, Pos.CENTER);
        loadingBox.setAlignment(Pos.CENTER);

        Scene loading = new Scene(borderPane, 270, 132);
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

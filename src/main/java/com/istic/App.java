package com.istic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private Scene scene;
    private Parent root;
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../../sample.fxml"));
        scene = new Scene(root);
        stage.setResizable(false);
        //change skin
        scene.getStylesheets().add("/skins/default.css");
        stage.setTitle("SynthLabC");
        stage.setScene(scene);
        stage.show();


            /* change skin
            scene.getStylesheets().remove(theme1Url);
            if(!scene.getStylesheets().contains(theme2Url)) scene.getStylesheets().add(theme2Url);
        */
    }

    public static void main(String[] args) {
        launch(args);

    }
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        System.exit(0);
    }

}

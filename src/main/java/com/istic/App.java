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
        System.out.println();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("SynthLabC");
        stage.setScene(scene);
        stage.show();
        


    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        System.out.println("Stage is closing");
        System.exit(0);
    }

}

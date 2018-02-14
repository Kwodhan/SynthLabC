package com.istic;

import com.istic.keyboard.KBListener;
import com.istic.keyboard.ReglageKB;

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
        root = FXMLLoader.load(getClass().getResource("../../main.fxml"));
        scene = new Scene(root);
        stage.setResizable(false);
        stage.setTitle("SynthLabC");
        stage.setScene(scene);
        stage.show();
        
		ReglageKB rkb =  new ReglageKB();
KBListener kbl = new KBListener(rkb);
kbl.add_listener(scene);

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

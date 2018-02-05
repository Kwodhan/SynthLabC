package com.istic.modulesController;

import com.istic.cable.Cable;
import com.istic.modulesController.ModuleController;
import com.istic.modulesController.OUTPUTModuleController;
import com.istic.modulesController.VCOModuleController;
import com.istic.port.Port;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	public static final int SQUAREWAVE = 0;
	public static final int TRIANGLEWAVE = 1;
	public static final int SAWTOOTHWAV = 2;


	@FXML
	HBox HBox1, Hbox;

    Cable cable1;
    Line line;

    final ToggleGroup group = new ToggleGroup();
    @FXML
    AnchorPane pane;
    @FXML
    MenuItem vcoMenuItem;
    @FXML
    HBox hBox1, hBox2,hBox3;
    @FXML
    Button startVCOButton,stopVCOButton,muteButton;
    @FXML
    Slider frequencySlider;
    @FXML
    Slider frequencyFineSlider;
    @FXML
    RadioButton sawRadio, triangleRadio,squareRadio;

    VCOModuleController vcoModuleController;
    OUTPUTModuleController outputModuleController;

    private Synthesizer synth;

	Port port;

    private boolean isPlugged = false;

	public void initialize(URL location, ResourceBundle resources) {
		this.synth = JSyn.createSynthesizer();
		this.synth.start();

		try {
			addOutput();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }


	public void addVCO() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vco.fxml"));
		addMod(root);
		vcoModuleController = (VCOModuleController) root.getUserData();
		vcoModuleController.init(this,synth);
	}

	public void addOutput() throws IOException {

		// outputModuleController=new OUTPUTModuleController();
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/output.fxml"));

		addMod(root);

		outputModuleController = (OUTPUTModuleController) root.getUserData();
		outputModuleController.init(this,synth);

	}

	public void addMixer() throws IOException {

		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/mixer.fxml"));
		addMod(root);

	}

	public void addEG() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/eg.fxml"));
		addMod(root);

	}

	public void addOscilloscope() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/oscilloscope.fxml"));
		addMod(root);

	}

	public void addReplicator() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/replicator.fxml"));
		addMod(root);

	}

	public void addSequencer() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/sequencer.fxml"));
		addMod(root);

	}

	public void addVca() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vca.fxml"));
		addMod(root);

	}

	public void addVcfLp() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vcfLp.fxml"));
		addMod(root);

	}

	public void addVcfHp() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vcfHp.fxml"));
		addMod(root);

	}

	public void addWhiteNoise() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/whiteNoise.fxml"));
		addMod(root);

	}

	public void drawCable() {
		if (line != null) {
			pane.getChildren().remove(line);
		}

		line = new Line(vcoModuleController.getX(),
				vcoModuleController.getY(), outputModuleController.getX(),
				outputModuleController.getY());
		line.setStrokeWidth(5);
		line.setStroke(Color.BLUEVIOLET);
		line.setId("cable1");
		pane.getChildren().add(line);
		line.setOnMouseClicked(event -> {
			this.disconnect();
		});
	}

	public void deleteCable() {
	    if (line!=null) {
	        pane.getChildren().remove(line);
        }
    }

	public void connect() {

		if(vcoModuleController != null && outputModuleController != null && outputModuleController.getX() != 0 && vcoModuleController.getX() != 0) {
			System.out.println("connect in main");
			cable1 = new Cable(vcoModuleController.getCurrentPort(),
					outputModuleController.getCurrentPort());
			System.out.println("connect in main2");

			if (cable1.connect() && outputModuleController.getX() != 0 && vcoModuleController.getX() != 0) {
				drawCable();
			}

		}
    }

    public void disconnect() {
	    if (cable1 != null) {
	        cable1.disconnect();
	        deleteCable();
        }
    }

    public void addMod(Node root){



        if(hBox1.getChildren().size()<3){
            hBox1.getChildren().add(root);

        }else{

            if(hBox2.getChildren().size()<3){
                hBox2.getChildren().add(root);
            }else
            {

                if(hBox3.getChildren().size()<3)
                {
                    hBox3.getChildren().add(root);
                }else{
                    if(hBox1.getChildren().size()==3 && hBox2.getChildren().size()==3&&hBox3.getChildren().size()==3){

                    }
                }
            }
        }

    }

	public AnchorPane getPane() {
		return pane;
	}

	public boolean isPlugged() {
		return isPlugged;
	}

	public void setPlugged(boolean plugged) {
		isPlugged = plugged;
	}

	public Port getPort() {
		return port;
	}

	public void setPort(Port port) {
		this.port = port;
	}
}

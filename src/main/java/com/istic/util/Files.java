package com.istic.util;

import com.istic.modulesController.OUTPUTModuleController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.File;
import java.io.IOException;

public class Files {


    public void openOut(File file,String[] strings,OUTPUTModuleController outputModuleController) throws IOException {



        double d =Double.parseDouble(strings[1]);
        int i =Integer.parseInt(strings[2]);
        System.out.println(i);
        outputModuleController.getLineOut().setAttenuation(d);
        outputModuleController.getLineOut().setMute(i);
        outputModuleController.getAttenuationSlider().setValue(d);
        if(i==0)
            outputModuleController.getMute().setSelected(true);
    }
}

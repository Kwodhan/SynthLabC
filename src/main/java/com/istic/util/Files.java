package com.istic.util;

import com.istic.cable.Cable;
import com.istic.cable.CableController;
import com.istic.modulesController.Controller;
import com.istic.modulesController.ModuleController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Files {
    File file;
    Controller controller;

    public Files(File file, Controller controller) {
        this.file = file;
        this.controller = controller;
    }


    public void open() throws IOException, ParseException {
        FileReader fileReader = new FileReader(file);
        ModuleController moduleController;
        JSONParser parser = new JSONParser();
        Object object = parser.parse(fileReader);
        //convert Object to JSONObject
        JSONObject jsonObject = (JSONObject) object;

        //Reading the array for the modules
        JSONArray jsonArrayModules = (JSONArray) jsonObject.get("modules");
        //Reading the array for the modules
        JSONArray jsonArrayCables = (JSONArray) jsonObject.get("cables");


        //Adding the modules
        for (Object module : jsonArrayModules) {
            System.out.println("\n" + module.toString());
            JSONObject jsonObjectModule = (JSONObject) module;
            switch (jsonObjectModule.get("type").toString()) {
                case "OUTPUTModuleController":
                    this.controller.addOutput().restore(jsonObjectModule);
                    break;
                case "EGModuleController":
                    this.controller.addEG().restore(jsonObjectModule);
                    break;
                case "MIXERModuleController":
                    this.controller.addMixer().restore(jsonObjectModule);
                    break;
                case "OSCILLOSCOPEModuleController":
                    this.controller.addOscilloscope().restore(jsonObjectModule);
                    break;
                case "REPLICATORModuleController":
                    this.controller.addReplicator().restore(jsonObjectModule);
                    break;
                case "SEQUENCERModuleController":
                    this.controller.addSequencer().restore(jsonObjectModule);
                    break;
                case "VCAModuleController":
                    this.controller.addVca().restore(jsonObjectModule);
                    break;
                case "VCFHPModuleController":
                    this.controller.addVcfHp().restore(jsonObjectModule);
                    break;
                case "VCFLPModuleController":
                    this.controller.addVcfLp().restore(jsonObjectModule);
                    break;
                case "VCOModuleController":
                    this.controller.addVCO().restore(jsonObjectModule);
                    break;
                case "WHITENOISEModuleController":
                    this.controller.addWhiteNoise().restore(jsonObjectModule);
                    break;


            }
            //Adding the cables
            for (Object cable : jsonArrayModules) {
                System.out.println("\n" + cable.toString());
                JSONObject jsonObjectCable = (JSONObject) cable;
                //attaching the modules for the connexion
                moduleController=this.controller
                        .getModuleControllers()
                        .get((Integer) jsonObjectCable.get("positionM1"));
                this.controller.setTemporaryCableModuleController(this.controller
                        .getModuleControllers()
                        .get((Integer) jsonObjectCable.get("positionM2")));
                //attaching the modules ports
                moduleController.setCurrentPort((Integer) jsonObjectCable.get("portM1"));

                this.controller.getTemporaryCableModuleController()
                        .setCurrentPort((Integer) jsonObjectCable.get("portM2"));

                this.controller.connect(moduleController).restore(jsonObjectModule);


            }
        }
    }


    public void save() throws IOException {

        FileWriter fileWriter = new FileWriter(file);

        //the JSON final for the modules and cables
        JSONObject finalJsonObject = new JSONObject();

        //Getting the modules
        JSONArray objectsModules = new JSONArray();
        for (ModuleController moduleController : this.controller.getModuleControllers()) {
            moduleController.serialize();
            objectsModules.add(moduleController.getJsonCableObject());
        }
        //puttin the modules in the final file configuration
        finalJsonObject.put("modules", objectsModules);

        //Getting the modules
        JSONArray objectsCables = new JSONArray();
        for (CableController cableController : this.controller.getCables()) {
            cableController.serialize();
            objectsCables.add(cableController.getJsonModuleObject());
        }
        //puttin the modules in the final file configuration
        finalJsonObject.put("cables", objectsCables);

        finalJsonObject.writeJSONString(fileWriter);
        fileWriter.close();


    }
}

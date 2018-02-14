package com.istic.util;

import com.istic.cable.CableController;
import com.istic.modulesController.Controller;
import com.istic.modulesController.ModuleController;
import com.istic.port.Port;
import javafx.scene.image.ImageView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Files {
    File file;
    Controller controller;

    public Files(File file, Controller controller) {
        this.file = file;
        this.controller = controller;
    }

    /**
     * Open a file and get the modules and cables and draw them
     * @throws IOException
     * @throws ParseException
     */
    public void open() throws IOException, ParseException {
        FileReader fileReader = new FileReader(file);
        JSONParser parser = new JSONParser();
        Object object = parser.parse(fileReader);
        //convert Object to JSONObject
        JSONObject jsonObject = (JSONObject) object;

        //Reading the array for the modules
        JSONArray jsonArrayModules = (JSONArray) jsonObject.get("modules");

        openModules(jsonArrayModules);


        //Reading the array for the modules
        JSONArray jsonArrayCables = (JSONArray) jsonObject.get("cables");
        //Adding the cables

        openCables(jsonArrayCables);

    }

    /**
     * open and add the modules
     * @param jsonArrayModules
     * @throws IOException
     */
    public void openModules(JSONArray jsonArrayModules) throws IOException {
        //Adding the modules
        for (Object module : jsonArrayModules) {
            //System.out.println("\n" + module.toString());
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
        }
    }

    public void openCables(JSONArray jsonArrayCables) {
        ModuleController mc1;
        ModuleController mc2;

        for (Object cable : jsonArrayCables) {
            JSONObject jsonObjectCable = (JSONObject) cable;

            //attaching the modules for the connexion

            mc1 = (ModuleController) this.controller
                    .getStacks()[((Long) jsonObjectCable.get("positionM1")).intValue()]
                    .getChildren()
                    .get(0)
                    .getUserData();

            System.out.println("m1 :"+mc1.getClass().getSimpleName());
            mc2 = (ModuleController) this.controller
                    .getStacks()[((Long) jsonObjectCable.get("positionM2")).intValue()]
                    .getChildren()
                    .get(0)
                    .getUserData();

            System.out.println("m2 :"+mc2.getClass().getSimpleName());

            //attaching the modules ports
            String portOne = (String) jsonObjectCable.get("portM1");
            String portTwo = (String) jsonObjectCable.get("portM2");

            mc1.getLayout(mc1.updateXandY(portOne));
            mc2.getLayout(mc2.updateXandY(portTwo));
            System.out.println("Result():" + mc1.getX()
                    + " y :" + mc1.getY());
            mc1.launchConnection(portOne);
            mc2.launchConnection(portTwo);


        }

        for (ModuleController moduleController:this.controller.getModuleControllers()){
            for (Map.Entry<ImageView, Port> entry : moduleController.getAllPorts().entrySet()) {


                moduleController.updateCablesPositionFromPort(entry.getValue());
            }
        }
    }

    /**
     * save the cureent configuration into a JSON file
     * @throws IOException
     */
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
        //System.out.println(finalJsonObject);
        finalJsonObject.writeJSONString(fileWriter);
        fileWriter.close();


    }
}

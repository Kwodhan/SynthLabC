package com.istic.util;

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
import java.util.ArrayList;

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
        ArrayList<ArrayList<Double>> linesData;
        int i=0;
        linesData=new ArrayList<>();
        for (Object cable : jsonArrayCables) {
            JSONObject jsonObjectCable = (JSONObject) cable;
            linesData.add(new ArrayList<>());
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

            mc1.launchConnection(portOne);
            mc2.launchConnection(portTwo);


            linesData.get(i).add((Double) jsonObjectCable.get("StartX"));
            linesData.get(i).add((Double) jsonObjectCable.get("EndX"));
            linesData.get(i).add((Double) jsonObjectCable.get("StartY"));
            linesData.get(i).add((Double) jsonObjectCable.get("EndY"));
            System.out.println("data at :"+linesData.get(i));
            /*linesData.get(i).add((Double) jsonObjectCable.get("ControlX1"));
            linesData.get(i).add((Double) jsonObjectCable.get("ControlX2"));
            linesData.get(i).add((Double) jsonObjectCable.get("ControlY1"));
            linesData.get(i).add((Double) jsonObjectCable.get("ControlY2"));*/
            i++;

        }
        i=0;
        for (CableController cableController:this.controller.getCables()){
            cableController.restoreLine(linesData.get(i));
            //System.out.println(linesData.get(i).get(2));
            i++;
        }


    }

    /**
     * save the cureent configuration into a JSON file
     * @throws IOException
     */
    @SuppressWarnings({"unchecked", "JavaDoc"})
    public void save() throws IOException {

        FileWriter fileWriter = new FileWriter(file);

        //the JSON final for the modules and cables
        JSONObject finalJsonObject = new JSONObject();

        //Getting the modules
        JSONArray objectsModules = new JSONArray();
        for (ModuleController moduleController : this.controller.getModuleControllers()) {
            moduleController.serialize();
            objectsModules.add(moduleController.getJsonModuleObject());
        }
        //puttin the modules in the final file configuration
        finalJsonObject.put("modules", objectsModules);

        //Getting the modules
        JSONArray objectsCables = new JSONArray();
        JSONObject objectCable ;
        for (CableController cableController : this.controller.getCables()) {
            if(cableController.serialize()){
                System.out.println("ok one");
                objectCable=cableController.getJsonCableObject();
                objectsCables.add(objectCable);
                //System.out.println(objectsCables);
                cableController.setJsonCableObject(objectCable);
            }

        }
        //puttin the modules in the final file configuration
        finalJsonObject.put("cables", objectsCables);
        System.out.println(finalJsonObject);
        //System.out.println(finalJsonObject);
        finalJsonObject.writeJSONString(fileWriter);
        fileWriter.close();


    }
}

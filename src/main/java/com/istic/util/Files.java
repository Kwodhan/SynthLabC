package com.istic.util;

import com.istic.cable.CableController;
import com.istic.modulesController.Controller;
import com.istic.modulesController.ModuleController;
import com.istic.port.Port;
import com.istic.port.PortController;
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
    private File file;
    private Controller controller;

    public Files(File file, Controller controller) {
        this.file = file;
        this.controller = controller;
    }

    /**
     * Open a file and get the modules and cables and draw them
     *
     * @throws IOException    In and out exceptions
     * @throws ParseException parsing of hte object exceptions
     */
    public void open() throws IOException, ParseException {
        FileReader fileReader = new FileReader(file);
        JSONParser parser = new JSONParser();
        Object object = parser.parse(fileReader);


        //convert Object to JSONObject
        JSONObject jsonObject = (JSONObject) object;

        //Reading the array for the modules
        JSONArray jsonArrayModules = (JSONArray) jsonObject.get("modules");

        //Adding the modules
        openModules(jsonArrayModules);


        //Reading the array for the cables
        JSONArray jsonArrayCables = (JSONArray) jsonObject.get("cables");

        //Adding the cables
        openCables(jsonArrayCables);

    }

    /**
     * open and add the modules
     *
     * @param jsonArrayModules the modules in json format
     * @throws IOException In and out exceptions
     */
    private void openModules(JSONArray jsonArrayModules) throws IOException {
        ArrayList<ModuleController>moduleControllers=new ArrayList<>();
        int[] positions=new int[12];
        int i=0;
        //Adding the modules to the controller and UI
        for (Object module : jsonArrayModules) {
            JSONObject jsonObjectModule = (JSONObject) module;
            switch (jsonObjectModule.get("type").toString()) {
                case "OUTPUTModuleController":
                    moduleControllers.add(this.controller.addOutput());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;
                    break;
                case "EGModuleController":
                    moduleControllers.add(this.controller.addEG());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;
                case "MIXERModuleController":
                    moduleControllers.add(this.controller.addMixer());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;
                case "OSCILLOSCOPEModuleController":
                    moduleControllers.add(this.controller.addOscilloscope());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;
                case "REPLICATORModuleController":
                    moduleControllers.add(this.controller.addReplicator());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;
                case "SEQUENCERModuleController":
                    moduleControllers.add(this.controller.addSequencer());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;
                case "VCAModuleController":
                    moduleControllers.add(this.controller.addVca());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;
                case "VCFHPModuleController":
                    moduleControllers.add(this.controller.addVcfHp());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;
                case "VCFLPModuleController":
                    moduleControllers.add(this.controller.addVcfLp());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;
                case "VCOModuleController":
                    moduleControllers.add(this.controller.addVco());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;
                case "WHITENOISEModuleController":
                    moduleControllers.add(this.controller.addWhiteNoise());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;

                case "KBModuleController":
                    moduleControllers.add(this.controller.addKeyBoard());
                    moduleControllers.get(i).restore(jsonObjectModule);
                    positions[i]=( (Long)jsonObjectModule.get("position")).intValue();
                    i++;                    break;


            }
        }
        //
         this.controller.addMod(positions,moduleControllers);
    }

    /**
     * Manage the cables from the json file
     *
     * @param jsonArrayCables the cables in json format
     */
    private void openCables(JSONArray jsonArrayCables) {
        ModuleController mc1;
        ModuleController mc2;
        ArrayList<ArrayList<Double>> linesData;
        ArrayList<String> colors=new ArrayList<>() ;
        int i = 0;
        linesData = new ArrayList<>();

        //Adding the cables to the controller and the UI

        for (Object cable : jsonArrayCables) {
            JSONObject jsonObjectCable = (JSONObject) cable;
            linesData.add(new ArrayList<>());
            colors.add((String) ((JSONObject) cable).get("color"));

            //attaching the modules for the connexion

            mc1 = (ModuleController) this.controller
                    .getStacks()[((Long) jsonObjectCable.get("positionM1")).intValue()]
                    .getChildren()
                    .get(0)
                    .getUserData();

            mc2 = (ModuleController) this.controller
                    .getStacks()[((Long) jsonObjectCable.get("positionM2")).intValue()]
                    .getChildren()
                    .get(0)
                    .getUserData();


            //attaching the modules ports

            String portOne = (String) jsonObjectCable.get("portM1");
            String portTwo = (String) jsonObjectCable.get("portM2");

            Port port1 = null;
            Port port2 = null;
            for(PortController portController : mc1.getAllPorts()){
                if(portController.getView().getId().equals(portOne)){
                    port1 = portController.getPort();
                }
            }
            for(PortController portController : mc2.getAllPorts()){
                if(portController.getView().getId().equals(portTwo)){
                    port2 = portController.getPort();
                }
            }
            this.controller.connect(port1,port2,mc1,mc2);


            //getting the lines data to draw on the UI

            linesData.get(i).add((Double) jsonObjectCable.get("StartX"));
            linesData.get(i).add((Double) jsonObjectCable.get("EndX"));
            linesData.get(i).add((Double) jsonObjectCable.get("StartY"));
            linesData.get(i).add((Double) jsonObjectCable.get("EndY"));

            i++;



        }
        //  Redrawing the new lines with the correct coordination
        i = 0;
        for (CableController cableController : this.controller.getCables()) {
            cableController.restoreLine(linesData.get(i),colors.get(i));
            i++;
        }



    }

    /**
     * save the cureent configuration into a JSON file
     *
     * @throws IOException In and out exceptions
     */
    @SuppressWarnings("unchecked")
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

        //Getting the cables
        JSONArray objectsCables = new JSONArray();
        JSONObject objectCable;
        for (CableController cableController : this.controller.getCables()) {
            if (cableController.serialize()) {

                objectCable = cableController.getJsonCableObject();
                objectsCables.add(objectCable);
                //System.out.println(objectsCables);
                cableController.setJsonCableObject(objectCable);
            }

        }
        //puttin the cables in the final file configuration
        finalJsonObject.put("cables", objectsCables);


        //writing the json file
        finalJsonObject.writeJSONString(fileWriter);
        fileWriter.close();


    }
}

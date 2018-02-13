package com.istic.util;

import com.istic.modulesController.Controller;
import com.istic.modulesController.EGModuleController;
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
import java.util.List;

public class Files {
    File file;
    Controller controller;

    public Files(File file, Controller controller) {
        this.file = file;
        this.controller = controller;
    }


    public void open() throws IOException, ParseException {
        //Module,0,OUTPUT,0,0.0
        FileReader fileReader;
        List<String> records = new ArrayList<String>();
        fileReader = new FileReader(file);
        //System.out.println(jsonObject);
        ModuleController moduleController;

        JSONParser parser = new JSONParser();
        Object object = parser.parse(fileReader);
        //convert Object to JSONObject
        JSONObject jsonObject = (JSONObject)object;

        //Reading the array
        JSONArray jsonArrayModules = (JSONArray)jsonObject.get("modules");
        //System.out.println("modules :"+jsonArrayModules);

        for(Object module : jsonArrayModules)
        {
            System.out.println("\n"+module.toString());
            JSONObject jsonObjectModule = (JSONObject)module;
            switch (jsonObjectModule.get("type").toString()){
                case "OUTPUTModuleController" : this.controller.addOutput().restore(jsonObjectModule);break;
                case "EGModuleController" : this.controller.addEG().restore(jsonObjectModule);break;
                case "MIXERModuleController" : this.controller.addMixer().restore(jsonObjectModule);break;
                case "OSCILLOSCOPEModuleController" : this.controller.addOscilloscope().restore(jsonObjectModule);break;
                case "REPLICATORModuleController" : this.controller.addReplicator().restore(jsonObjectModule);break;
                case "SEQUENCERModuleController" : this.controller.addSequencer().restore(jsonObjectModule);break;
                case "VCAModuleController" : this.controller.addVca().restore(jsonObjectModule);break;
                case "VCFHPModuleController" : this.controller.addVcfHp().restore(jsonObjectModule);break;
                case "VCFLPModuleController" : this.controller.addVcfLp().restore(jsonObjectModule);break;
                case "VCOModuleController" : this.controller.addVCO().restore(jsonObjectModule);break;
                case "WHITENOISEModuleController" : this.controller.addWhiteNoise().restore(jsonObjectModule);break;


            }


        }
    }



    public void openCables(String[] strings){

    }




    public void save() throws IOException {

        FileWriter fileWriter;
        JSONObject finalJsonObject = new JSONObject();
        fileWriter = new FileWriter(file);
        JSONArray objects = new JSONArray();
        for (ModuleController moduleController : this.controller.getModuleControllers()) {
            moduleController.serialize();
            objects.add(moduleController.getJsonModuleObject());
        }
        finalJsonObject.put("modules", objects);
        System.out.println(finalJsonObject);

        finalJsonObject.writeJSONString(fileWriter);
        fileWriter.close();


    }
}

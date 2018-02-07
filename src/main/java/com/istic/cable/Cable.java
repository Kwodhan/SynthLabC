package com.istic.cable;

import com.istic.port.Port;

public class Cable {

    Port portOne;
    Port portTwo;


    public Cable(Port portOne, Port portTwo) {
        this.portOne = portOne;
        this.portTwo = portTwo;

    }

    public Port getPortOne() {
        return portOne;
    }

    public Port getPortTwo() {
        return portTwo;
    }

    public boolean  connect() {
        System.out.println(this.getPortTwo());
        System.out.println(this.getPortOne());
        if(this.getPortTwo() == null){
            return false;
        }
        if(this.getPortOne() == null){
            return false;
        }
        if(this.portTwo.accept(this.portOne.getVisitorConnectPort())){
            this.portOne.setConnected(true);
            this.portTwo.setConnected(true);
            return true;
        }
        return false;


    }
    public void disconnect() {
        this.portOne.setConnected(false);
        this.portTwo.setConnected(false);
        this.portOne.disconnect();
        this.portTwo.disconnect();
    }
}

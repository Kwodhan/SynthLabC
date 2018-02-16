package com.istic.cable;

import com.istic.port.Port;

/**
 * représente un cable entre deux ports
 *
 */
public class Cable {

    private Port portOne;

    private Port portTwo;


    public Cable(Port portOne, Port portTwo) {
        this.portOne = portOne;
        this.portTwo = portTwo;

    }

    /**
     * réalise la connection entre deux cable
     * @return true si les ports sont branchés | false si les ports sont incompatibles
     */
    public boolean connect() {

        if(this.getPortTwo() == null){
            return false;
        }
        if(this.getPortOne() == null){
            return false;
        }
        // si les ports peuvent être cablés
        if(this.portTwo.accept(this.portOne.getVisitorPort())){
            this.portOne.setConnected(true);
            this.portTwo.setConnected(true);
            return true;
        }
        return false;
    }

    /**
     * Deconnexion du cable
     */
    public void disconnect() {
        this.portOne.setConnected(false);
        this.portTwo.setConnected(false);
        this.portOne.disconnect();
        this.portTwo.disconnect();
    }

    //Getters & Setters

    public Port getPortOne() {
        return portOne;
    }

    public Port getPortTwo() {
        return portTwo;
    }

}

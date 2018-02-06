






package com.istic.cable;

import java.util.ArrayList;

import com.istic.port.Port;

public class MultiCable {
	   Port portOne;
	    Port portTwo;
ArrayList<Port> ports_out= new ArrayList<>();

	    public MultiCable(Port portOne, Port portTwo) {
	        this.portOne = portOne;
	        this.portTwo = portTwo;
	        this.ports_out.add(portTwo);

	    }
public void add_output_port ( Port p ) {
    this.ports_out.add( p );

}
	    public Port getPortOne() {
	        return portOne;
	    }

	    public Port getPortTwo() {
	        return portTwo;
	    }
	    public Port getPortOut(int i) {
	        return this.ports_out.get(i);
	    }

	    public boolean  connect() {
	        if(this.portTwo.accept(this.portOne.getVisitorConnectPort())){
	            this.portOne.setConnected(true);
	            for (int i =0; i< this.ports_out.size(); i++) {
	            this.ports_out.get(i).setConnected(true);
	            }
	            return true;
	        }
	        return false;


	    }
	    public void disconnect() {
	        this.portOne.setConnected(false);
	        for (int i =0; i< this.ports_out.size(); i++) {
	            this.ports_out.get(i).setConnected(false);
	            }
 	        this.portOne.disconnect();
	        for (int i =0; i< this.ports_out.size(); i++) {
	            this.ports_out.get(i).disconnect( );
	            }
 	    }
}

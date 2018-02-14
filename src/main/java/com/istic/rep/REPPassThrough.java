package com.istic.rep;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Transfere le signal d'entrée dans 3 sorties
 */
public class REPPassThrough extends UnitGenerator {

	/**
	 * Entrée du signal
	 */
	private UnitInputPort in;

	/**
	 * Première sortie du signal
	 */
	private UnitOutputPort out1;

	/**
	 * Deuxième sortie du signal
	 */
	private UnitOutputPort out2;

	/**
	 * Troisième sortie du signal
	 */
	private UnitOutputPort out3;

	public REPPassThrough() {


		addPort(this.in  = new UnitInputPort("in"));
		addPort(this.out1 = new UnitOutputPort("out1"));
		addPort(this.out2 = new UnitOutputPort("out2"));
		addPort(this.out3 = new UnitOutputPort("out3"));


	}

	@Override
	public void generate(int start, int limit) {
		double[] inputs = in.getValues();
		double[] outputs1= out1.getValues();
		double[] outputs2= out2.getValues();
		double[] outputs3= out3.getValues();

		for (int i = start; i < limit; i++) {
			outputs1[i] =inputs[i];
			outputs2[i] =inputs[i];
			outputs3[i] =inputs[i];
		}
	}

 
	public UnitInputPort getInput() {
		return in;
	}

	public UnitOutputPort getOut1() {
		return out1;
	}
	public UnitOutputPort getOut2() {
		return out2;
	}
	public UnitOutputPort getOut3() {
		return out3;
	}
 
}

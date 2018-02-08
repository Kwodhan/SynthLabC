package com.istic;

import com.istic.cable.Cable;
import com.istic.eg.EG;
import com.istic.oscillo.Oscilloscope;
import com.istic.port.Port;
import com.istic.rep.REP;
import com.istic.vca.VCA;
import com.istic.vco.VCO;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import com.istic.out.OutMod;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Enclosed.class)
public class CableTest {

    @RunWith(Parameterized.class)
    public static class ComponentParamTests {

        // {
        // port1,
        // port2,
        // isConnectedAfterConnect,
        // cableOneIsConnectedAfterConnect,
        // cableTwoIsConnectedAfterConnect,
        // cableOneIsConnectedAfterDisconnect,
        // cableTwoIsConnectedAfterDisconnect
        // }
        @Parameterized.Parameters
        public static Collection<Object[]> data() {
            Synthesizer synth;
            OutMod lineOut;
            VCO vco1;
            VCO vco2;
            VCA vca;
            EG eg1;
            EG eg2;
            REP rep;
            Oscilloscope oscilloscope;

            synth = JSyn.createSynthesizer();
            lineOut = new OutMod();
            vco1 = new VCO();
            vco2 = new VCO();
            vca = new VCA();
            eg1 = new EG();
            eg2 = new EG();
            rep = new REP();
            oscilloscope = new Oscilloscope(synth);

            synth.add(lineOut);
            synth.add(vco1);
            synth.add(vco2);
            synth.add(vca);
            synth.add(eg1);
            synth.add(eg2);
            synth.add(rep);

            return Arrays.asList(new Object[][] {

                { vco1.getOutput(), vco1.getOutput(), false, false, false, false, false },
                { vco2.getFm(), vco1.getFm(),  false, false, false, false, false },
                { lineOut.getPortInput(), lineOut.getPortInput(), false, false, false, false, false },
                { vca.getAm(), vca.getAm(), false, false, false, false, false },
                { eg1.getGateInput(), eg1.getGateInput(), false, false, false, false, false },
                { eg1.getGateInput(), eg2.getGateInput(), false, false, false, false, false },

                { vco2.getFm(), vco1.getOutput(),  true, true, true, false, false },
                { vco1.getOutput(), vco2.getFm(),  true, true, true, false, false },

                { vco1.getOutput(), lineOut.getPortInput(), true, true, true, false, false },
                { lineOut.getPortInput(), vco1.getOutput(),  true, true, true, false, false },

                { vco2.getFm(), lineOut.getPortInput(),  false, false, false, false, false },
                { lineOut.getPortInput(), vco2.getFm(),  false, false, false, false, false },

                { vca.getOutput(), lineOut.getPortInput(),  true, true, true, false, false },
                { lineOut.getPortInput(), vca.getOutput(),  true, true, true, false, false },

                { vco2.getOutput(), vca.getInput(),  true, true, true, false, false },
                { vca.getInput(), vco2.getOutput(),  true, true, true, false, false },

                { vca.getAm(), eg1.getOutputPort(),  true, true, true, false, false },
                { eg1.getOutputPort(), vca.getAm(),  true, true, true, false, false },

                { eg1.getOutputPort(), vco1.getFm(),  true, true, true, false, false },
                { vco1.getFm(), eg1.getOutputPort(),  true, true, true, false, false },

                { vco1.getFm(), vca.getAm(),  false, false, false, false, false },
                { vca.getAm(), vco1.getFm(),  false, false, false, false, false },

                { vca.getAm(), rep.getInput(),  false, false, false, false, false },
                { rep.getInput(),vca.getAm(),  false, false, false, false, false },

                { vca.getInput(),rep.getOutput1(),  true, true, true, false, false },
                { rep.getOutput2(),vca.getInput(),  true, true, true, false, false },

                { eg1.getGateInput(),vca.getOutput(),  true, true, true, false, false },
                { vca.getOutput(),eg1.getGateInput(),  true, true, true, false, false },

                { vco1.getFm(),eg1.getGateInput(),  false, false, false, false, false },
                { eg1.getGateInput(),vco1.getFm(),  false, false, false, false, false },

                { vca.getAm(),eg1.getGateInput(),  false, false, false, false, false },
                { eg1.getGateInput(),vca.getAm(),  false, false, false, false, false },

            });
        }

        @Parameterized.Parameter
        public Port p1;

        @Parameterized.Parameter(1)
        public Port p2;

        @Parameterized.Parameter(2)
        public boolean isConnectedAfterConnect;

        @Parameterized.Parameter(3)
        public boolean cableOneIsConnectedAfterConnect;

        @Parameterized.Parameter(4)
        public boolean cableTwoIsConnectedAfterConnect;

        @Parameterized.Parameter(5)
        public boolean cableOneIsConnectedAfterDisconnect;

        @Parameterized.Parameter(6)
        public boolean cableTwoIsConnectedAfterDisconnect;

        @Test
        public void testMutate() {
            Cable cable = new Cable(p1, p2);
            assertNotNull(cable);

            boolean isConnected = cable.connect();
            assertEquals(isConnected, isConnectedAfterConnect);

            assertEquals(cable.getPortOne().isConnected(), cableOneIsConnectedAfterConnect);
            assertEquals(cable.getPortTwo().isConnected(), cableTwoIsConnectedAfterConnect);

            // disconnect

            cable.disconnect();

            assertEquals(cable.getPortOne().isConnected(), cableOneIsConnectedAfterDisconnect);
            assertEquals(cable.getPortTwo().isConnected(), cableTwoIsConnectedAfterDisconnect);
        }


    }
}

package com.istic;

import com.istic.cable.Cable;
import com.istic.port.Port;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SquareOscillatorBL;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

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
            VCO vco;

            synth = JSyn.createSynthesizer();
            lineOut = new OutMod(synth);
            vco = new VCO(synth);

            return Arrays.asList(new Object[][] {
                { vco.getPortOutput(), lineOut.getPortInput(), true, true, true, false, false },
                { vco.getPortOutput(), vco.getPortOutput(), false, false, false, false, false },
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

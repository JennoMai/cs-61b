package enigma;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the MachineTest class.
 *  @author Jenny Mei
 */
public class MachineTest {
    private ArrayList<Rotor> cNAVALA = new ArrayList<>();

    private void setUp() {
        cNAVALA.add(new MovingRotor("I", new Permutation(NAVALA.get("I"), UPPER), ""));
        cNAVALA.add(new MovingRotor("IV", new Permutation(NAVALA.get("IV"), UPPER), "J"));
        cNAVALA.add(new MovingRotor("III", new Permutation(NAVALA.get("III"), UPPER), "V"));
        cNAVALA.add(new MovingRotor("Beta", new Permutation(NAVALA.get("Beta"), UPPER), ""));
        cNAVALA.add(new MovingRotor("B", new Permutation(NAVALA.get("B"), UPPER), ""));
    }

    @Test
    public void machineTest() {
        setUp();

        Machine machine = new Machine(UPPER, 5, 3, cNAVALA);
        machine.setPlugboard(new Permutation("(YF) (ZH)", UPPER));
        assertEquals(5, machine.numRotors());
        assertEquals(3, machine.numPawls());

        machine.insertRotors(new String[]{"B", "Beta", "III", "IV", "I"});
        machine.setRotors("AXLE");
        assertEquals(25, machine.convert(24));

        assertNotEquals("YYYY", machine.convert("YYYY"));
    }
}

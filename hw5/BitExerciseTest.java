import org.junit.Test;
import static org.junit.Assert.*;

/** Tests of BitExercise
 *  @author Zoe Plaxco
 */
public class BitExerciseTest {

    @Test
    public void testLastBit() {
        int four = BitExercise.lastBit(100);
        assertEquals(4, four);

        int sixteen = BitExercise.lastBit(28);
        assertEquals(4, sixteen);

        int twozerofoureight = BitExercise.lastBit(3208);
        assertEquals(8, twozerofoureight);
    }

    @Test
    public void testPowerOfTwo() {
        boolean powOfTwo = BitExercise.powerOfTwo(32);
        assertTrue(powOfTwo);
        boolean notPower = BitExercise.powerOfTwo(7);
        assertFalse(notPower);
    }

    @Test
    public void testAbsolute() {
        int hundred = BitExercise.absolute(100);
        assertEquals(100, hundred);
        int negative = BitExercise.absolute(-100);
        assertEquals(100, negative);
        int zero = BitExercise.absolute(0);
        assertEquals(0,zero);
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(BitExerciseTest.class));
    }
}


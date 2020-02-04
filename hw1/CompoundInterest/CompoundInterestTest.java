import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.
        assertEquals(0, 0); */
        assertEquals(11, CompoundInterest.numYears(2031));
        assertEquals(981, CompoundInterest.numYears(3001));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(12.544, CompoundInterest.futureValue(10,12,2022), tolerance);
        assertEquals(56.880, CompoundInterest.futureValue(100, -5,2031), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(11.803, CompoundInterest.futureValueReal(10, 12, 2022, 3), tolerance);
        assertEquals(17.850, CompoundInterest.futureValueReal(100, -5, 2031, 10), tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550.0, CompoundInterest.totalSavings(5000, 2022, 10), tolerance);
        assertEquals(919.19, CompoundInterest.totalSavings(243, 2024, -14), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(15722.5, CompoundInterest.totalSavingsReal(5000, 2022, 10, 5), tolerance);
        assertEquals(735.35, CompoundInterest.totalSavingsReal(243, 2024, -14, 20), tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}

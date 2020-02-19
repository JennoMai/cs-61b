/** Functions to increment and sum the elements of a WeirdList. */
class WeirdListClient {

    /** Return the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        return L.map(new IntAdder(n));
    }

    /** Return the sum of all the elements in L. */
    static int sum(WeirdList L) {
        IntSum sum = new IntSum();
        L.map(sum);
        return sum.returnSum();
    }

    private static class IntAdder implements IntUnaryFunction {
        private int _n;

        public IntAdder(int start) {
            _n = start;
        }

        @Override
        public int apply(int x) {
            x += _n;
            return x;
        }
    }

    private static class IntSum implements IntUnaryFunction {
        private int _sum = 0;

        public IntSum() {}

        @Override
        public int apply(int x) {
            _sum += x;
            return x;
        }

        public int returnSum() {
            return _sum;
        }
    }

    /* IMPORTANT: YOU ARE NOT ALLOWED TO USE RECURSION IN ADD AND SUM
     *
     * As with WeirdList, you'll need to add an additional class or
     * perhaps more for WeirdListClient to work. Again, you may put
     * those classes either inside WeirdListClient as private static
     * classes, or in their own separate files.

     * You are still forbidden to use any of the following:
     *       if, switch, while, for, do, try, or the ?: operator.
     *
     * HINT: Try checking out the IntUnaryFunction interface.
     *       Can we use it somehow?
     */
}

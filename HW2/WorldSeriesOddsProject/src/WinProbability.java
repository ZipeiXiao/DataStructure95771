public class WinProbability {
    /**
     * P(2,3)    = _____    _______
     * P(4,7)    = _____    _______
     * P(7,6)    = _____    _______
     * P(10, 12) = _____    _______
     * P(20, 23) = _____    _______
     * P(30, 15) = _____    _______
     * P(50, 40) = _____    _______
     * @param args no
     */
    public static void main(String[] args) {
        int[] a = new int[] {2, 4, 7, 10, 20, 30, 50};
        int[] b = new int[] {3, 7, 6, 12, 23, 15, 40};

        for (int i = 0; i < a.length; i++) {
            final long startTime = System.currentTimeMillis();
            System.out.println("P(" + a[i] + ", " + b[i] + ") Value: " + dynamicProgramming(a[i], b[i]));
            final long endTime = System.currentTimeMillis();
            System.out.println("Dynamic Programming takes "+ (endTime - startTime) +  " milliseconds");

            final long startTime2 = System.currentTimeMillis();
            System.out.println("P(" + a[i] + ", " + b[i] + ") Value: " + recursive(a[i], b[i]));
            final long endTime2 = System.currentTimeMillis();
            System.out.println("Recursion takes "+ (endTime2 - startTime2) / 1.0  + " milliseconds");
            System.out.println("########################################################################");
        }
    }

    /**
     * P(i,j) = 1  if i = 0 and j > 0
     *        = 0  if i > 0 and j = 0
     *        = (P(i-1,j) + P(i,j-1))/2  if i > 0 and j > 0
     * @param i A needs i games to win
     * @param j B needs j games to win
     * @return the probability P(i, j) that A will eventually win the series.
     */
    private static double recursive(int i, int j) {
        if (i == 0 && j > 0) {
            return 1.0;
        } else if (j == 0 && i > 0) {
            return 0.0;
        }
        return (recursive(i - 1, j) + recursive(i, j - 1)) / 2.0;
    }

    /**
     * Using the values in the table, one
     * can compute the remaining probabilities in a bottom-up fashion.
     * @param i A needs i games to win
     * @param j B needs j games to win
     * @return the probability P(i, j) that A will eventually win the series.
     */
    private static double dynamicProgramming(int i, int j) {
        double[][] result = new double[i + 1][j + 1];

        for (int row = 0; row <= i; row++) {
            result[row][0] = 0.0;
        }

        for (int col = 0; col <= j; col++) {
            result[0][col] = 1.0;
        }

        for (int r = 1; r <= i; r++) {
            for (int c = 1; c <= j; c++) {
                result[r][c] = (result[r - 1][c] + result[r][c - 1]) / 2.0;
            }
        }

        return result[i][j];
    }
}

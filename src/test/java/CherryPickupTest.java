import org.junit.Test;


import static org.junit.jupiter.api.Assertions.*;


public class CherryPickupTest {

    class Cherry {
        int[][] dp;
        int[][] grid;
        int total_col;
        int total_row;

        public Cherry(int[][] grid) {
            this.grid = grid;
            total_col = grid[0].length;
            total_row = grid.length;
            dp = new int[total_col - 1][total_col];


            for (int row = total_row - 1; row >= 0; row--) {
                int[][] dpTemp = new int[total_col][total_col];
                for (int col1 = 0; col1 < total_col - 1; col1++) {
                    for (int col2 = col1 + 1; col2 < total_col; col2++) {
                        int toAdd = row < total_row - 1 ? getToAdd(col1, col2) : 0;
                        dpTemp[col1][col2] = grid[row][col1] + grid[row][col2] + toAdd;
                    }
                }
                dp = dpTemp;
            }


        }

        private int getToAdd(int col1, int col2) {
            int max = 0;
            for (int newCol1 = col1 - 1; newCol1 <= col1 + 1; newCol1++) {
                for (int newCol2 = col2 - 1; newCol2 <= col2 + 1; newCol2++) {
                    if (newCol1 >= 0 && newCol1 < total_col && newCol2 >= 0 && newCol2 < total_col) {
                        max = Math.max(max, dp[newCol1][newCol2]);
                    }
                }
            }
            return max;
        }
    }


    public int cherryPickup(int[][] grid) {
        Cherry cherry = new Cherry(grid);
        return cherry.dp[0][grid[0].length - 1];
    }

    @Test
    public void testCherry() {
        assertEquals(24, cherryPickup(new int[][]{{3, 1, 1}, {2, 5, 1}, {1, 5, 5}, {2, 1, 1}}));

        assertEquals(2, cherryPickup(new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {1, 0, 0, 1}}));
        assertEquals(1, cherryPickup(new int[][]{{0, 0}, {1, 0}}));
        assertEquals(4, cherryPickup(new int[][]{{3, 1, 1}}));
        assertEquals(10, cherryPickup(new int[][]{{3, 1, 1, 7}}));
        assertEquals(4, cherryPickup(new int[][]{{3, 1}}));
        assertEquals(6, cherryPickup(new int[][]{{3, 1}, {1, 1}}));
        assertEquals(9, cherryPickup(new int[][]{{3, 1}, {2, 3}}));
        assertEquals(1, cherryPickup(new int[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {1, 0, 0, 0}}));


    }
}

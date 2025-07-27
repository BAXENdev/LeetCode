
package problem2322.test;

import org.junit.Test;
import static org.junit.Assert.*;

import problem2322.Solution;

public class SolutionTest {
    Solution solution = new Solution();
    @Test
    public void test1() {
        int[] inputNums = {1,5,5,4,11};
        int[][] inputEdges = {{0,1},{1,2},{1,3},{3,4}};
        int expectedOutput = 9;
        assertEquals(expectedOutput, solution.minimumScore(inputNums, inputEdges));
    }
    @Test
    public void test2() {
        int[] inputNums = {5,5,2,4,4,2};
        int[][] inputEdges = {{0,1},{1,2},{5,2},{4,3},{1,3}};
        int expectedOutput = 0;
        assertEquals(expectedOutput, solution.minimumScore(inputNums, inputEdges));
    }
}

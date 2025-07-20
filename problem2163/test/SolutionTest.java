
package problem2163.test;

import org.junit.Test;
import static org.junit.Assert.*;
import problem2163.Solution;

public class SolutionTest {
    Solution solution = new Solution();
    
    @Test
    public void test1() {
        int[] nums = {3,1,2};
        long expectedValue = -1;
        assertEquals(solution.minimumDifference(nums), expectedValue);
    }

    @Test
    public void test2() {
        
    }
}


package problem1233.test;

import org.junit.Test;
import static org.junit.Assert.*;

import problem1233.Solution;

public class SolutionTest {
    Solution solution = new Solution();

    @Test
    public void test1() {
        String[] folders = {"/a","/a/b","/c/d","/c/d/e","/c/f"};
        String[] expectedOutput = {"/a","/c/d","/c/f"};
        assertArrayEquals(solution.removeSubfolders(folders).toArray(), expectedOutput);
    }

    @Test
    public void test2() {
        String[] folders = {"/a","/a/b/c","/a/b/d"};
        String[] expectedOutput = {"/a"};
        assertArrayEquals(solution.removeSubfolders(folders).toArray(), expectedOutput);
    }

    @Test
    public void test3() {
        String[] folders = {"/a/b/c","/a/b/ca","/a/b/d"};
        String[] expectedOutput = {"/a/b/c","/a/b/ca","/a/b/d"};
        assertArrayEquals(solution.removeSubfolders(folders).toArray(), expectedOutput);
    }
}

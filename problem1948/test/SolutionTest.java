
package problem1948.test; // TODO

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import problem1948.Solution; // TODO

public class SolutionTest {
    Solution solution = new Solution();

    private List<List<String>> toList(String[][] input) {
        List<List<String>> output = new ArrayList<>();
        for (String[] subArray : input) {
            output.add(Arrays.asList(subArray));
        }
        return output;
    }

    private void assertListEquals(List<List<String>> expected, List<List<String>> output) {
        List<String> expectedList = new ArrayList<>();
        List<String> outputList = new ArrayList<>();
        for (List<String> folderPath : expected) {
            String path = "";
            for (String folderName : folderPath) {
                path += String.format("/%s", folderName);
            }
            expectedList.add(path);
        }
        for (List<String> folderPath : output) {
            String path = "";
            for (String folderName : folderPath) {
                path += String.format("/%s", folderName);
            }
            outputList.add(path);
        }
        Collections.sort(expectedList);
        Collections.sort(outputList);
        assertEquals(expectedList, outputList);
    }

    @Test
    public void testPrint() {
        String[][] input = {{"b"},{"f"},{"f","r"},{"f","r","g"},{"f","r","g","c"},{"f","r","g","c","r"},{"f","o"},{"f","o","x"},{"f","o","x","t"},{"f","o","x","d"},{"f","o","l"},{"l"},{"l","q"},{"c"},{"h"},{"h","t"},{"h","o"},{"h","o","d"},{"h","o","t"}};
        List<List<String>> testInput = toList(input);
        solution.testPrint(testInput);
    }

    @Test
    public void testPrint2() {
        String[][] input = {{"a","b"},{"a","c"}};
        List<List<String>> testInput = toList(input);
        solution.testPrint(testInput);
    }

    @Test
    public void testSerial1() {
        String[][] input = {{"a","b"}, {"a","c"}};
        List<List<String>> testInput = toList(input);
        assertEquals("(a(b,c))", solution.testSerial(testInput));
    }

    @Test
    public void test1() {
        String[][] input = {{"a"},{"c"},{"d"},{"a","b"},{"c","b"},{"d","a"}};
        List<List<String>> testInput = toList(input);
        String[][] output = {{"d"},{"d","a"}};
        List<List<String>> testOutput = toList(output);

        List<List<String>> solutionOutput = solution.deleteDuplicateFolder(testInput);
        assertListEquals(testOutput, solutionOutput);
    }
    @Test
    public void test2() {
        String[][] input = {{"a"},{"c"},{"a","b"},{"c","b"},{"a","b","x"},{"a","b","x","y"},{"w"},{"w","y"}};
        List<List<String>> testInput = toList(input);
        String[][] output = {{"a"},{"a","b"},{"c"},{"c","b"}};
        List<List<String>> testOutput = toList(output);

        List<List<String>> solutionOutput = solution.deleteDuplicateFolder(testInput);
        assertListEquals(testOutput, solutionOutput);
    }
    @Test
    public void test3() {
        // What makes this test interesting is the parent folders c and a are declared after, but I know my Trie mapping method does not care
        String[][] input = {{"a","b"},{"c","d"},{"c"},{"a"}};
        List<List<String>> testInput = toList(input);
        String[][] output = {{"a"},{"a","b"},{"c"},{"c","d"}};
        List<List<String>> testOutput = toList(output);

        List<List<String>> solutionOutput = solution.deleteDuplicateFolder(testInput);
        assertListEquals(testOutput, solutionOutput);
    }
    @Test
    public void test4() {
        // What makes this test interesting is the parent folders c and a are declared after, but I know my Trie mapping method does not care
        String[][] input = {{"b"},{"f"},{"f","r"},{"f","r","g"},{"f","r","g","c"},{"f","r","g","c","r"},{"f","o"},{"f","o","x"},{"f","o","x","t"},{"f","o","x","d"},{"f","o","l"},{"l"},{"l","q"},{"c"},{"h"},{"h","t"},{"h","o"},{"h","o","d"},{"h","o","t"}};
        List<List<String>> testInput = toList(input);
        String[][] output = {{"b"},{"f"},{"l"},{"c"},{"h"},{"f","r"},{"f","o"},{"l","q"},{"h","t"},{"f","r","g"},{"f","o","l"},{"f","r","g","c"},{"f","r","g","c","r"}};
        List<List<String>> testOutput = toList(output);

        List<List<String>> solutionOutput = solution.deleteDuplicateFolder(testInput);
        assertListEquals(testOutput, solutionOutput);
    }


}

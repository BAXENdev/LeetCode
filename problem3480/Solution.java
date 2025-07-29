
package problem3480;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {
    Map<Integer, List<int[]>> rules = new HashMap<>();
    public long maxSubarrays(int n, int[][] conflictingPairs) {
       long totalArrays = summation(n);
        if (conflictingPairs.length <= 1) return totalArrays;
        for (int[] pair : conflictingPairs) {
            if (pair[0] > pair[1]) {
                int t = pair[0];
                pair[0] = pair[1];
                pair[1] = t;
            }
            if (rules.containsKey(pair[1])) {
                List<int[]> rightRules = rules.get(pair[1]);
                rightRules.add(pair);
                rightRules.sort((a, b) -> Integer.compare(a[0], b[0]));
            } else {
                List<int[]> rightRules = new ArrayList<>();
                rightRules.add(pair);
                rules.put(pair[1], rightRules);
            }
        }
        long highestValue = Long.MIN_VALUE;
        long secondHighestValue = Long.MIN_VALUE;
        for (int i = 1; i < n + 1; i++) {
            if (!rules.containsKey(i)) continue;
            List<int[]> rightRules = rules.get(i);
            
        }
        return totalArrays - secondHighestValue;
    }

    public long summation(int x) {
        return (x*(x+1))/2;
    }
}
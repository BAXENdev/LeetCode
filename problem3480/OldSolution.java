
package problem3480;

import java.util.HashSet;

//
public class OldSolution {
    public long maxSubarrays(int n, int[][] conflictingPairs) {
        // 4 = count([1],[2],[3],[4],[1,2],[2,3],[3,4],[1,2,3],[2,3,4],[1,2,3,4])
        // pair=[1,2] -> removes [1,2],[1,2,3],[1,2,3,4]
        // pair=[3,4] -> removes [3,4],[2,3,4],[1,2,3,4]
        
        // 5 = [1],[2],[3],[4],[5],[1,2],[2,3],[3,4],[4,5],[1,2,3],[2,3,4],[3,4,5],[1,2,3,4],[2,3,4,5],[1,2,3,4,5]
        // pair=[1,2] = 4 -> removes [1,2],[1,2,3],[1,2,3,4],[1,2,3,4,5]                                                                1*(5-2+1)=4
        // pair=[1,3] = 3 -> removes [1,2,3],[1,2,3,4],[1,2,3,4,5]
        // pair=[4,5] = 4 -> removes [4,5],[3,4,5],[2,3,4,5],[1,2,3,4,5]
        // pair=[3,5] = 4 -> removes [3,4,5],[2,3,4,5],[1,2,3,4,5]

        // pair=[2,3] = 6 -> removes [2,3],[1,2,3],[2,3,4],[1,2,3,4],[2,3,4,5],[1,2,3,4,5]                                              2*(5-3+1)=6
        // pair=[2,4] = 4 -> removes [2,3,4],[1,2,3,4],[2,3,4,5],[1,2,3,4,5]                                                            2*(5-4+1)=4
        // pair=[2,5] = ? -> removes [2,3,4,5],[1,2,3,4,5]                                                                              2*(5-5+1)=2

        // 6 = [1],[2],[3],[4],[5],[6],[1,2],[2,3],[3,4],[4,5],[5,6],[1,2,3],[2,3,4],[4,5,6],[1,2,3,4],[2,3,4,5],[3,4,5,6],[1,2,3,4,5],[2,3,4,5,6],[1,2,3,4,5,6]
        // pair=[1,2] = 5 -> removes [1,2],[1,2,3],[1,2,3,4],[1,2,3,4,5],[1,2,3,4,5,6]                                                  n - diff
        // pair=[1,4] = 3 -> removes [1,2,3,4],[1,2,3,4,5],[1,2,3,4,5,6]                                                                n - diff

        // pair=[2,3] = 8 -> removes [2,3],[1,2,3],[2,3,4],[1,2,3,4],[2,3,4,5],[1,2,3,4,5],[2,3,4,5,6],[1,2,3,4,5,6]                    2*(6-3+1)=8
        // pair=[3,4] = 9 -> removes [3,4],[2,3,4],[3,4,5],[1,2,3,4],[2,3,4,5],[3,4,5,6],[1,2,3,4,5],[2,3,4,5,6],[1,2,3,4,5,6]          3*(6-4+1)=9
        // pair=[4,5] = 8 -> about same as pair [2,3]                                                                                   4*(6-5+1)=8
        // pair=[2,4] = 6 -> removes [2,3,4],[1,2,3,4],[2,3,4,5],[1,2,3,4,5],[2,3,4,5,6],[1,2,3,4,5,6]                                  2*(6-4+1)=6
        // pair=[2,5] = 4 -> removes [2,3,4,5],[1,2,3,4,5],[2,3,4,5,6],[1,2,3,4,5,6]                                                    2*(6-5+1)=4

        // finalCount = n + 
        // if the diff in pair is 1, the smallest affected arrays are size 2
        // diff=1 -> !(diff+1) - 1 | (2+1) - 1
        // for larger sizes... if one of the pair values is edge, it will only remove one array per size arrays
        // How do I turn this into a formula? I feel like i can


        // remove the pair that has the largest impact
        // subtract the removed amount of all other pairs
        
        // formulas
        // edge case = n - diff     !!! THIS IS ABSOLUTELY TRUE
        // middle case /=           !!! THIS IS COMPLICATED
        //   a*(n-b+1)              // middle case also works for edge case

        // Side note: a*(n-b+1) == n-(b-a)? (when a = 1)
        // 1*(5-3+1)=5-(3-1); 

        // valuaing pairs for excluding
        // 1: the largest difference produces smallest removed subarrays
        // 2: a pair on the edge will always remove (n - pairDiff) subarrays
        // 3: middle pair (idk how to calculate that yet) 
        
        // What makes this complicated... n=5, pair[3,5] also includes pair [2,5]. Removing [2,5] does not change the value
        // pair[1,2] and pair[3,5], remove pair[1,2]



        long totalArrays = summation(n);
        if (conflictingPairs.length <= 1) return totalArrays;
        for (int[] pair : conflictingPairs) {
            if (pair[0] > pair[1]) {
                int t = pair[0];
                pair[0] = pair[1];
                pair[1] = t;
            }
        }
        Set<List<int[]>> rules = new HashSet<>();
        for (int[] pair : conflictingPairs) {
            if (rules)
        }
        long highestValue = Long.MIN_VALUE;
        long secondHighestValue = Long.MIN_VALUE;
        for (int[] pair : conflictingPairs) {
            long pairValue = 0;
            pairValue = pair[0]*((n-pair[1])+1);
            if (pairValue > highestValue) {
                if (highestValue > secondHighestValue) {
                    secondHighestValue = highestValue;
                }
                highestValue = pairValue;
            } else if (pairValue > secondHighestValue) {
                secondHighestValue = pairValue;
            }
        }
        return totalArrays - secondHighestValue;
    }

    public long summation(int x) {
        return (x*(x+1))/2;
    }
}
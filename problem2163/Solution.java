package problem2163;

import java.util.PriorityQueue;
import java.util.Queue;

public class Solution {
    public long minimumDifference(int[] nums) {
        int partitionSize = nums.length / 3;
        long[] sums = new long[partitionSize + 1];
        
        Queue<Integer> leftQueue = new PriorityQueue<>((a, b) -> b - a);
        long sum = 0;
        for (int i = 0; i < partitionSize; i++) {
            sum += nums[i];
            leftQueue.add(nums[i]);
        }
        sums[0] = sum;
        for (int i = partitionSize; i < partitionSize * 2; i++) {
            sum += nums[i];
            leftQueue.add(nums[i]);
            sum -= leftQueue.poll();
            sums[i - (partitionSize - 1)] = sum;
        }

        long sum2 = 0;
        Queue<Integer> rightQueue = new PriorityQueue<>((a, b) -> a - b);
        for (int i = partitionSize * 3 - 1; i >= partitionSize * 2; i--) {
            sum2 += nums[i];
            rightQueue.add(nums[i]);
        }
        long answer = sums[partitionSize] - sum2;
        for (int i = partitionSize * 2 - 1; i >= partitionSize; i--) {
            sum2 += nums[i];
            rightQueue.add(nums[i]);
            sum2 -= rightQueue.poll();
            answer = Math.min(answer, sums[i - partitionSize] - sum2);
        }

        return answer;
    }
}

/*
3n elements
n-removed
n-first sum-first
n-second sum-second
dif = sum-first - sum-second

Sweep partition 1 and 2 to compute maximum sum and store each sum.
sums[n+1] i = 0: is sum of partition 1; i > 0 is the cumputed maximum sum going from the start of partition 2 to the end.
Then we cumpute the sum of partition 3.
Finally, we walk back down partition 2 and the sums calculated from partition 1 and 2, to find the min value.
Because we walk back down partition 2 and the sums, we would never have the same value calculated into both sides.
*/


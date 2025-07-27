
package problem2322;

import java.lang.reflect.Array;
import java.util.*;

public class OldSolution {
    public int minimumScore(int[] nums, int[][] edges) {
        // XOR is communative. (its addition without overflow) 1 + 1 = 0 | 1 - 1 = 0 | 1 + 1 == 1 - 1
        // We can precalculate XOR values and do a walking line pattern where we check for maximum dif from the parts
        // But because of two edges removed, a two walking line problem here would be O(n^2)... not good
        
        // an advanced analysis would target numbers by their highest/lowest bits. A number that has the only highest Nth bit will never be negated... 
        // At worst, if other numbers have a highest Nth - 1 bit, the worst that can happen would be the number is halved - 1 (111... to 100...)
        // For the lowest number... we would want to obtain 0. So 5 ^ 4 ^ 1 = 0... maybe lowest bit isnt what matters for the difference
        // Also, do I consider the 32th/64th sign bit? or do we have unsigned ints/only positive ints? (For this problem, only positive numbers)

        // From a strategic view, You would want the left side to have the maximum value (which could probably be acheived with minimal xor of the highest values)
        // and the right side with a minimum vlaue, which could probably be achieved with a minimal xors of equivalent-ish values
        // The middle would contain our junk...

        // looking at tips for this, common solutions are O(n^2), which means some bruteforce required. We can use clever tricks to reduce number of operations.

        // Calculate XOR sums
        // Can I do a walking XOR?
        // To optimize for a walking XOR, how would I sort the edges? Do I have to fully re-evaluate a xor everytime I shift edges?

        // Store Edges in a map
        Map<Integer, List<Integer>> edgeMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) edgeMap.put(i, new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            edgeMap.get(edge[0]).add(edge[1]);
            edgeMap.get(edge[1]).add(edge[0]);
        }
        
        // Get total XOR.
        // Center XOR = XOR total of first removed edge node
        // Right XOR = XOR total of second removed edge node
        // Left XOR = Total XOR ^ Center XOR ^ Right XOR
        // A repeated XOR operation nullifies the original XOR operation. 1+2+3+4+5 = 15 -> 15-(5+2)-(4+3) = 1;
        int lowestScore = Integer.MAX_VALUE;
        int totalXor = calculateXor(0, new int[] {-1, -1}, new int[] {-1, -1}, nums, edgeMap, new HashSet<>());
        
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = i + 1; j < edges.length; j++) {
                int[] removedEdge1 = edges[i];
                int[] removedEdge2 = edges[j];
                // How do I know which edges is untraveled?
                // Travel one way using one node from the first edge.
                // With the second edge, check which value has not been traveled yet and get the XOR.
                // Both sides of second removed edge can not be traveled, because other there would be a loop and violate a definition of a tree,
                // Use both XORs to calculate the third XOR.
                Set<Integer> traveledNodes = new HashSet<>();
                int centerXOR = calculateXor(removedEdge1[0], removedEdge1, removedEdge2, nums, edgeMap, traveledNodes);
                int rightXORindex = !traveledNodes.contains(removedEdge2[0]) ? removedEdge2[0] : removedEdge2[1];
                int rightXOR = calculateXor(rightXORindex, removedEdge1, removedEdge2, nums, edgeMap, traveledNodes);
                int leftXOR = totalXor ^ centerXOR ^ rightXOR;

                int minXor = Math.min(leftXOR, Math.min(centerXOR, rightXOR));
                int maxXor = Math.max(leftXOR, Math.max(centerXOR, rightXOR));
                int scoreXor = maxXor - minXor;
                if (scoreXor < lowestScore) lowestScore = scoreXor;
            }
        }

        return lowestScore;
    }

    // Instead of a recursive solution, we could store queued traversals in a list
    // Everytime we traverse, XOR, then get any more connected nodes
    public int calculateXor(int rootNodeIndex, int[] removedEdge1, int[] removedEdge2, int[] nodeValues, Map<Integer, List<Integer>> edgeMap, Set<Integer> traveledNodes) {
        // Set up traversal information. Root, NextNodes, totalXOR
        // Set<Integer> traveledNodes = new HashSet<>();
        traveledNodes.add(rootNodeIndex);
        int totalXor = nodeValues[rootNodeIndex];
        Set<Integer> travelToNodes = new HashSet<>();
        List<Integer> connectedNodes = edgeMap.get(rootNodeIndex);
        for (Integer connectedNodeIndex : connectedNodes) {
            if (traveledNodes.contains(connectedNodeIndex)) continue; // should only occur with the previous node
            int[] currentEdge = {rootNodeIndex, connectedNodeIndex};
            if (edgeEquals(currentEdge, removedEdge1) || edgeEquals(currentEdge, removedEdge2)) continue;
            travelToNodes.add(connectedNodeIndex);
        }

        // Keep traversing and totaling the XOR
        while (!travelToNodes.isEmpty()) {
            // Integer[] nextNodes = (Integer[]) travelToNodes.toArray();
            List<Integer> nextNodes = new ArrayList<>(travelToNodes);
            for (int i = 0; i < nextNodes.size(); i++) {
                // Get XOR and add to total
                int currentNodeIndex = nextNodes.get(i);
                totalXor = totalXor ^ nodeValues[currentNodeIndex];

                // Add the next connected nodes
                traveledNodes.add(currentNodeIndex);
                travelToNodes.remove(currentNodeIndex);
                connectedNodes = edgeMap.get(currentNodeIndex);
                for (Integer connectedNodeIndex : connectedNodes) {
                    if (traveledNodes.contains(connectedNodeIndex)) continue; // should only occur with the previous node
                    int[] currentEdge = {currentNodeIndex, connectedNodeIndex};
                    if (edgeEquals(currentEdge, removedEdge1) || edgeEquals(currentEdge, removedEdge2)) continue;
                    travelToNodes.add(connectedNodeIndex);
                }
            }
        }

        return totalXor;
    }

    public boolean edgeEquals(int[] edge1, int[] edge2) {
        // if ({1,2} == {1,2}) || ({1,2} == {2,1})
        return (edge1[0] == edge2[0] && edge1[1] == edge2[1]) || (edge1[0] == edge2[1] && edge1[1] == edge2[0]);
    }
}
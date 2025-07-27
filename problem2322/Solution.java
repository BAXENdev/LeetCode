
package problem2322;

import java.lang.reflect.Array;
import java.util.*;

// Fast enough but only beats 16%
public class Solution {
    public int minimumScore(int[] nums, int[][] edges) {
        // Old solution is (unsurprisingly) too slow
        // So lets optimize edge mapping so that from node 0 to node N, we can have a one-way direct traversal from node 0 to every leaf irregardless of numeric sorting.
        // By doing this, we can precalculate XOR values of a subtrees.
        // And then using XOR's communative property, we can subtract removed subtree's from from root value.
        Map<Integer, Set<Integer>> edgeMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) edgeMap.put(i, new HashSet<>());
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            edgeMap.get(edge[0]).add(edge[1]);
            edgeMap.get(edge[1]).add(edge[0]);
        }
        
        // Remove upward mappings
        Stack<Integer> nextNodes = new Stack<>();
        int edgesModifierIndex = 0;
        nextNodes.add(0);
        while (!nextNodes.isEmpty()) {
            int currentNode = nextNodes.pop();
            Set<Integer> connectedNodes = edgeMap.get(currentNode);
            for (Integer connectedNodeIndex : connectedNodes) {
                Set<Integer> connectedNodesSquared = edgeMap.get(connectedNodeIndex);
                connectedNodesSquared.remove(currentNode);
                
                // We do this to ensure second node is always the subroot
                int[] edge = edges[edgesModifierIndex++];
                edge[0] = currentNode;
                edge[1] = connectedNodeIndex;
            }
            nextNodes.addAll(connectedNodes);
        }

        // Calculate descendants
        Map<Integer, Set<Integer>> nodeDescendants = new HashMap<>();
        for (int i = 0; i < nums.length; i++) nodeDescendants.put(i, new HashSet<>());
        calculateDescendants(0, edgeMap, nodeDescendants);

        // Precalculate values
        Map<Integer, Integer> nodeXorValues = new HashMap<>();
        calculateXors(0, nums, edgeMap, nodeXorValues);
        
        int minimumScore = Integer.MAX_VALUE;
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = i + 1; j < edges.length; j++) {
                int[] removedEdge1 = edges[i];
                int[] removedEdge2 = edges[j];
                int subNodeIndex1 = removedEdge1[1];
                int subNodeIndex2 = removedEdge2[1];
                Set<Integer> descendants1 = nodeDescendants.get(subNodeIndex1);
                Set<Integer> descendants2 = nodeDescendants.get(subNodeIndex2);

                int leftXorValue = nodeXorValues.get(0);
                int centerXorValue = nodeXorValues.get(subNodeIndex1);
                int rightXorValue = nodeXorValues.get(subNodeIndex2);

                if (descendants1.contains(subNodeIndex2)) {
                    leftXorValue = leftXorValue ^ centerXorValue;
                    centerXorValue = centerXorValue ^ rightXorValue;
                } else if (descendants2.contains(subNodeIndex1)) {
                    leftXorValue = leftXorValue ^ rightXorValue;
                    rightXorValue = rightXorValue ^ centerXorValue;
                } else { // neither subNode are descendants of each other, so they both need XORed out of left
                    leftXorValue = leftXorValue ^ centerXorValue ^ rightXorValue;
                }

                int highestValue = Math.max(leftXorValue, Math.max(centerXorValue, rightXorValue));
                int lowestValue = Math.min(leftXorValue, Math.min(centerXorValue, rightXorValue));
                if (highestValue - lowestValue < minimumScore) minimumScore = highestValue - lowestValue;
            }
        }

        return minimumScore;
    }

    
    public int calculateXors(int currentNodeIndex, int[] nodeValues, Map<Integer, Set<Integer>> edgeMap, Map<Integer, Integer> nodeXorValues) {
        // Set up traversal information. Root, NextNodes, totalXOR
        int nodeXor = nodeValues[currentNodeIndex];
        Set<Integer> connectedNodes = edgeMap.get(currentNodeIndex);
        for (Integer connectedNodeIndex : connectedNodes) {
            nodeXor = nodeXor ^ calculateXors(connectedNodeIndex, nodeValues, edgeMap, nodeXorValues);
        }
        nodeXorValues.put(currentNodeIndex, nodeXor);
        return nodeXor;
    }

    public Set calculateDescendants(int currentNodeIndex, Map<Integer, Set<Integer>> edgeMap, Map<Integer, Set<Integer>> nodeDescendants) {
        Set<Integer> descendants = nodeDescendants.get(currentNodeIndex);
        Set<Integer> connectedNodes = edgeMap.get(currentNodeIndex);
        descendants.addAll(connectedNodes);
        for (Integer connectedNodeIndex : connectedNodes) {
            descendants.addAll(calculateDescendants(connectedNodeIndex, edgeMap, nodeDescendants));
        }
        return descendants;
    }

    public boolean edgeEquals(int[] edge1, int[] edge2) {
        // if ({1,2} == {1,2}) || ({1,2} == {2,1})
        return (edge1[0] == edge2[0] && edge1[1] == edge2[1]) || (edge1[0] == edge2[1] && edge1[1] == edge2[0]);
    }
}
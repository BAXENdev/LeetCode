
package problem1948; // TODO

import java.util.*;

public class Solution {
    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        // Hint suggests a Trie... Tree structure typically used for mapping words.
        TrieNode root = new TrieNode();
        for (List<String> directPath : paths) {
            TrieNode currentNode = root;
            for (String folderName : directPath) {
                if (currentNode.children.containsKey(folderName)) {
                    currentNode = currentNode.children.get(folderName);
                } else {
                    currentNode.children.put(folderName, new TrieNode());
                    currentNode = currentNode.children.get(folderName);
                }
            }
        }

        // From here... we hash the folders based on children....
        // Then reiterate over the paths (similar to test print below) and only print paths that dont have duplicate hashes
        // This isnt listed in the examples, but what about a duplicate folder that has two children folders? only delete if both children are duplicated... right?
        
        // How would I hash multiple children? Add their string hashes together? No... Hash(a) + Hash(b) == Hash(b) + Hash(a)
        // A child's hash would also need to include their childrens hashes
        // What about just two empty folders /a and /b? They both have no children and are thus duplicate? unless no children is not considered duplicate
        Map<String, Integer> serials = new HashMap<>();
        root.computeSerialsAndRegister(serials);
        root.pruneDuplicates(serials);
        List<List<String>> output = new ArrayList<>();
        root.getPaths(output, new ArrayList<>());
        return output;
    }

    public String testSerial(List<List<String>> paths) {
        TrieNode root = new TrieNode();
        for (List<String> directPath : paths) {
            TrieNode currentNode = root;
            for (String folderName : directPath) {
                if (currentNode.children.containsKey(folderName)) {
                    currentNode = currentNode.children.get(folderName);
                } else {
                    currentNode.children.put(folderName, new TrieNode());
                    currentNode = currentNode.children.get(folderName);
                }
            }
        }
        Map<String, Integer> serials = new HashMap();
        root.computeSerialsAndRegister(serials);
        return root.serial;
    }

    public void testPrint(List<List<String>> paths) {
        TrieNode root = new TrieNode();
        for (List<String> directPath : paths) {
            TrieNode currentNode = root;
            for (String folderName : directPath) {
                if (currentNode.children.containsKey(folderName)) {
                    currentNode = currentNode.children.get(folderName);
                } else {
                    currentNode.children.put(folderName, new TrieNode());
                    currentNode = currentNode.children.get(folderName);
                }
            }
        }
        Map<String, Integer> serials = new HashMap();
        root.computeSerialsAndRegister(serials);
        System.out.println(root.get("a").serial);
        System.out.println(root.get("a").children.size());

    }

    public class TrieNode {
        Map<String, TrieNode> children = new HashMap<>();
        Integer hash = 0;
        String serial = "";
        
        public void print(String parent) {
            for (String folderName : this.children.keySet()) {
                TrieNode child = this.get(folderName);
                // System.out.printf("%10d | %s/%s\n", child.hash, parent, folderName);
                System.out.printf("%s/%s\n", parent, folderName);
                child.print(String.format("%s/%s", parent, folderName));
            }
        }

        public TrieNode get(String folder) {
            return this.children.get(folder);
        }

        public void getPaths(List<List<String>> output, List<String> parent) {
            for (String childFolder : this.children.keySet()) {
                List<String> childPath = new ArrayList<>(parent);
                childPath.add(childFolder);
                output.add(childPath);
                TrieNode child = this.get(childFolder);
                child.getPaths(output, childPath);
            }
        }

        public String computeSerialsAndRegister(Map<String, Integer> register) {
            if (this.children.isEmpty()) {
                this.serial = "";
            } else {
                List<String> childrenSerials = new ArrayList<>();
                // A SORTED HASH SET IS IMPORTANT!!!!
                List<String> childFolders = new ArrayList<>(this.children.keySet());
                Collections.sort(childFolders);
                for (String childFolder : childFolders) {
                    TrieNode child = this.get(childFolder);
                    childrenSerials.add(childFolder + child.computeSerialsAndRegister(register));
                }
                this.serial = String.format("(%s)", String.join(",", childrenSerials));
                register.put(this.serial, register.getOrDefault(this.serial, 0) + 1);
            }
            return this.serial;
        }

        public void pruneDuplicates(Map<String, Integer> register) {
            List<String> keysToRemove = new ArrayList<>();
            for (String key : this.children.keySet()) {
                TrieNode child = this.get(key);
                int serialCount = register.getOrDefault(child.serial, 0);
                if (!child.serial.equals("") && serialCount > 1) {
                    // this.children.remove(key);
                    keysToRemove.add(key);
                } else {
                    child.pruneDuplicates(register);
                }
            }
            for (String key : keysToRemove) {
                this.children.remove(key);
            }
        }
    }
}

package problem1233;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Solution {
    public List<String> removeSubfolders(String[] folder) {
        Set<String> folderSet = new HashSet<>(Arrays.asList(folder));
        List<String> rootFolders = new ArrayList<>();
        // List<String> rootFolders = new ArrayList<>(Arrays.asList(folder));
        // Collections.sort(rootFolders);
        for (int i = 0; i < folder.length; i++) {
            String subFolder = folder[i];
            String prefix = subFolder;
            boolean isRootFolder = true;
            while (!prefix.isEmpty()) {
                int lastSlashPos = prefix.lastIndexOf((char) '/');
                if (lastSlashPos == 0) break;
                prefix = prefix.substring(0, lastSlashPos);
                if (folderSet.contains(prefix)) {
                    isRootFolder = false;
                    break;
                }
            }
            if (isRootFolder) {
                rootFolders.add(subFolder);
            }
        }

        return rootFolders;
    }



    // FIRST SOLUTION
    public List<String> removeSubfoldersOld(String[] folder) {
        Set<String> folderSet = new HashSet<>(Arrays.asList(folder));
        List<String> rootFolders = new ArrayList<>(Arrays.asList(folder));
        Collections.sort(rootFolders);
        for (int i = 0; i < rootFolders.size(); i++) {
            String subFolder = rootFolders.get(i);
            String[] subFolderArr = subFolder.substring(1).split("/");
            List<Integer> toRemoveSubfolders = new ArrayList<>();
            for (int j = i + 1; j < rootFolders.size(); j++) {
                String subSubFolder = rootFolders.get(j);
                String[] subSubFolderArr = subSubFolder.substring(1).split("/");
                if (arrayStartsWith(subSubFolderArr, subFolderArr)) {
                    toRemoveSubfolders.add(j);
                } else {
                    break;
                }
            }
            int indexBuffer = 0;
            for (int removeIndex : toRemoveSubfolders) {
                rootFolders.remove(removeIndex - indexBuffer++);
            }
        }

        return rootFolders;
    }

    public static boolean arrayStartsWith(String[] arr, String[] prefix) {
        if (prefix.length >= arr.length) return false;
        for (int i = 0; i < prefix.length; i++) {
            if (!prefix[i].equals(arr[i])) {
                return false;
            }
        }
        return true;
    }
}
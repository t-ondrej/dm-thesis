package sk.upjs.ics.helpers;

import weka.classifiers.trees.J48;

import java.util.ArrayList;
import java.util.List;

public final class ClassifierTreeUtils {

    private ClassifierTreeUtils() {
        // Placeholder
    }

    public static void string(J48 j48) {

        String tree = j48.toString();
        String[] lines = tree.split("\n");

        List<List<String>> lists = new ArrayList<>();
        // Break lines into parts.
        for(String line : lines){
            List<String> temp = new ArrayList<>();
            while(line.indexOf("|") != -1){
                temp.add("|");
                line = line.replaceFirst("\\|", "");
            }
            temp.add(line.trim());
            lists.add(temp);
        }

        // remove first 3 lines of the output.
        for(int i = 0; i < 3; i++){
            lists.remove(0);
        }
        // remove last 4 lines of the output.
        for(int i = 0; i < 4; i++){
            lists.remove(lists.size()-1);
        }

        // This is a ordered list of parents for any given node while traversing the tree.
        List<String> parentClauses = new ArrayList<>();
        // this describes the depth
        //int depth = -1;

        // all the paths in the tree.
        List<List<String>> paths = new ArrayList<>();


        for (List<String> list : lists) {
            int currDepth = 0;
            for(int i = 0; i < list.size(); i++){
                String token = list.get(i);
                // find how deep is this node in the tree.
                if (token.equals("|")) {
                    currDepth++;
                }
                else {    // now we get to the string token for the node.
                    // if leaf, so we get one path..
                    if (token.contains(":")) {
                        List<String> path = new ArrayList<>();
                        for (int index = 0; index < currDepth; index++) {
                            path.add(parentClauses.get(index));
                        }
                        path.add(token);
                        paths.add(path);
                    }
                    else {
                        // add this to the current parents list
                        parentClauses.add(currDepth, token);
                    }
                }
            }
        }

        // print each of the paths.
        for (List<String> path : paths) {
            String str = "";
            for (String token : path) {
                str += token + " AND ";
            }
            System.out.println(str + "\n");
            // LOG.info(str + "\n");
        }

    }
}

import java.util.*;

public class AllSubSet {
    public List<Set<Integer>> allsubset(int n) {
        int N = (int)Math.pow(2,n);
        List<Set<Integer>> allSubSets = new  ArrayList<Set<Integer>>();
        // Run a loop for printing all 2^n
        // subsets one by one
        for (int i = 0; i < (1<<n); i++)
        {
            //System.out.print("{ ");
            Set<Integer> set = new TreeSet<Integer>();

            // Print current subset
            for (int j = 0; j < n; j++)

                // (1<<j) is a number with jth bit 1
                // so when we 'and' them with the
                // subset number we get which numbers
                // are present in the subset and which
                // are not
                if ((i & (1 << j)) > 0) {
                    //System.out.print(j + 1 + " ");
                    set.add(j+1);
                }

            //System.out.println("}");
            allSubSets.add(set);
        }
        System.out.println(Arrays.toString(allSubSets.toArray())+ "\n" + allSubSets.toArray().length);
        return allSubSets;
    }
}

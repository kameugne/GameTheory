import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;

import java.util.List;
import java.util.Set;

public class GenerateSimpleGame {
    public static void main(String[] args) {
        // Number of players
        int n = 3;
        // Number of non empty subsets of {1,2,...,n}
        int N = (int)Math.pow(2.0, n);
        List<Set<Integer>> subSet = new AllSubSet().allsubset(n);
        // Create the model
        Model model = new Model();
        IntVar[] value = new IntVar[N];
        for (int i = 0; i < N; i++) {
            value[i] = model.intVar(0,1);
        }
        model.arithm(value[0], "=", 0).post();
        model.arithm(value[N-1], "=", 1).post();
        for (int i = 0; i < N-1; i++) {
            for (int j = i+1; j < N; j++) {
                if (i != j) {
                    new Constraint("monotony", new MonotoneConstraint(subSet.get(i), subSet.get(j), value[i], value[j])).post();
                    new Constraint("property", new PropertyConstraint(subSet.get(i), subSet.get(j), value[i], value[j], n)).post();
                }
            }
        }


        int compter = 0;

        while(model.getSolver().solve()){
            compter += 1;
            System.out.println("Solution found!");
            for (int i = 0; i < N; i++)
                System.out.println("subset("+i+") := " + subSet.get(i) + " ---------------- " + "value("+i+") := " + value[i].getValue());
        }
        System.out.println("Number of Solutions found! : " + compter);


    }
}

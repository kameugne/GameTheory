import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.constraints.PropagatorPriority;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.util.ESat;
import org.chocosolver.util.tools.ArrayUtils;

import java.util.Set;

public class MonotoneConstraint extends Propagator<IntVar> {
    private Set<Integer> X;
    private Set<Integer> Y;
    private IntVar v;
    private IntVar w;

    public MonotoneConstraint(Set<Integer> X, Set<Integer> Y, IntVar v, IntVar w) {
        super(new IntVar[]{v,w});
        this.v = v;
        this.w = w;
        this.X = X;
        this.Y = Y;
    }
    @Override
    public void propagate(int evtmask) throws ContradictionException {
        if (!X.isEmpty() && isInclude(X,Y) && v.isInstantiatedTo(1) && !w.isInstantiated()) {
            w.isInstantiatedTo(1);
            return;
        }
        if (!X.isEmpty() && isInclude(X,Y) && v.isInstantiatedTo(1) && w.isInstantiatedTo(0)) {
            fails();
        }
    }

    @Override
    public ESat isEntailed() {
        if (!X.isEmpty() && isInclude(X,Y) && v.isInstantiatedTo(1) && w.isInstantiatedTo(1)){
            return ESat.TRUE;
        }
        else {
            if (!X.isEmpty() && isInclude(X,Y) && v.isInstantiatedTo(1) && w.isInstantiatedTo(0)) {
                return ESat.FALSE;
            }
        }
        return ESat.UNDEFINED;
    }
     public boolean isInclude(Set<Integer> X, Set<Integer> Y) {
         for (int x : X) {
             if (!Y.contains(x)) {
                 return false;
             }
         }
         return true;
     }
}

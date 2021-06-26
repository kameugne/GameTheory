import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;

import java.util.Set;

public class PropertyConstraint extends Propagator<IntVar> {
    private Set<Integer> X;
    private Set<Integer> Y;
    private IntVar v;
    private IntVar w;
    private int n;

    public PropertyConstraint(Set<Integer> X, Set<Integer> Y, IntVar v, IntVar w, int n) {
        super(new IntVar[]{v,w});
        this.v = v;
        this.w = w;
        this.X = X;
        this.Y = Y;
        this.n = n;
    }
    @Override
    public void propagate(int evtmask) throws ContradictionException {
        if (areComplementary(X,Y) && v.isInstantiatedTo(1) && !w.isInstantiated()) {
            w.isInstantiatedTo(0);
            return;
        }
        if (areComplementary(X,Y) && w.isInstantiatedTo(1) && !v.isInstantiated()) {
            v.isInstantiatedTo(0);
            return;
        }
        if (areComplementary(X,Y) && v.isInstantiatedTo(1) && w.isInstantiatedTo(1)) {
            fails();
        }
    }

    @Override
    public ESat isEntailed() {
        if ((areComplementary(X,Y) && v.isInstantiatedTo(1) && w.isInstantiatedTo(0)) ||
            (areComplementary(X,Y) && w.isInstantiatedTo(1) && v.isInstantiatedTo(0))){
            return ESat.TRUE;
        }
        else {
            if (areComplementary(X,Y) && v.isInstantiatedTo(1) && w.isInstantiatedTo(1)) {
                return ESat.FALSE;
            }
        }
        return ESat.UNDEFINED;
    }
    public boolean areComplementary(Set<Integer> X, Set<Integer> Y) {
        for (int x : X) {
            if (Y.contains(x)) {
                return false;
            }
        }
        for(int i = 1; i <= n; i++) {
            if (!X.contains(i) && !Y.contains(i))
                return false;
        }
        return true;
    }
}

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.ESat;
import org.jgrapht.alg.spanning.EsauWilliamsCapacitatedMinimumSpanningTree;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class CompletudeConstraint extends Propagator<IntVar> {
    private List<Set<Integer>> set;
    private IntVar[] v;
    private int n;
    public CompletudeConstraint(List<Set<Integer>> set, IntVar[] v, int n) {
        super(v);
        this.set = set;
        this.v = v;
        this.n = n;
    }
    @Override
    public void propagate(int evtmask) throws ContradictionException {
        for (int k = 0; k < set.size(); k++) {
            if (v[k].isInstantiatedTo(1)) {
                for (int i : set.get(k)) {
                    for (int j = 1; j < n; j++) {
                        if (j > i && !set.get(k).contains(j)) {
                            int l = indexOfSMinusIPlusJ(i, j, set.get(k));
                            if (!v[l].isInstantiated()) {
                                v[l].isInstantiatedTo(0);
                            }else {
                                if (v[l].isInstantiatedTo(1)) {
                                    fails();
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public ESat isEntailed() {
        for (int k = 0; k < set.size(); k++) {
            if (v[k].isInstantiatedTo(1)) {
                for (int i : set.get(k)) {
                    for (int j = 1; j < n; j++) {
                        if (j > i && !set.get(k).contains(j)) {
                            int l = indexOfSMinusIPlusJ(i, j, set.get(k));
                            if (v[l].isInstantiatedTo(0)) {
                                return ESat.TRUE;
                            }else {
                                if (v[l].isInstantiatedTo(1)) {
                                    return ESat.FALSE;
                                }
                            }
                        }
                    }
                }
            }
        }
        return ESat.UNDEFINED;
    }
    public int indexOfSMinusIPlusJ(int i, int j, Set<Integer> S) {
        Set<Integer> SS = new TreeSet<Integer>();
        SS.add(j);
        for (int h : S)
            if (h != i) SS.add(h);
        for (int t = 0; t < set.size(); t++) {
            if (isInclude(set.get(t), SS) && isInclude(SS, set.get(t)))
                return t;
        }
        return -1;
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

import org.chocosolver.solver.constraints.Propagator;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.util.ESat;

public class UnicityOfSets extends Propagator<SetVar> {
    private SetVar X;
    private SetVar Y;
    public UnicityOfSets(SetVar X, SetVar Y) {
        super(new SetVar[]{X,Y});
        this.X = (SetVar)this.vars[0];
        this.Y = (SetVar)this.vars[1];
        //System.out.println("bbbbbbbbbbbbbbbbbbb" + X + Y);
    }
    @Override
    public void propagate(int evtmask) throws ContradictionException {
        if (X.isInstantiated() && Y.isInstantiated()  && LBSomme(X) > LBSomme(Y) && UBSomme(X) > UBSomme(Y)) {
            //System.out.println("bbbbbbbbbbbbbbbbbbb" + X + Y);
            //setPassive();
            fails();
        }
    }

    @Override
    public ESat isEntailed() {
        if (!X.getLB().isEmpty() &&  X.isInstantiated() && Y.isInstantiated()  && LBSomme(X) < LBSomme(Y) && UBSomme(X) < UBSomme(Y)) {
            //System.out.println("bbbbbbbbbbbbbbbbbbb" + X + Y);
            return ESat.TRUE;
        }
        else
            if (X.isInstantiated() && Y.isInstantiated() && LBSomme(X) > LBSomme(Y)) {
                //System.out.println("bbbbbbbbbbbbbbbbbbb" + X + Y);
                return ESat.FALSE;
            }
        return ESat.UNDEFINED;
    }
    public int LBSomme(SetVar s) {
        int somme = 0;
        for (int x : s.getLB()) {
            somme += x;
        }
        //System.out.println("bbbbbbbbbbbbbbbbbbb");
        return somme;
    }
    public int UBSomme(SetVar s) {
        int somme = 0;
        for (int x : s.getUB()) {
            somme += x;
        }
        return somme;
    }
}

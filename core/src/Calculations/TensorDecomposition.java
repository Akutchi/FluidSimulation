package Calculations;

public class TensorDecomposition {

    private final Tensor _S;
    private final Tensor _A;

    public TensorDecomposition(Tensor t) {
        _S = (t.add(t.transpose())).mult(0.5);
        _A = (t.soustract(t.transpose())).mult(0.5);
    }

    public TensorDecomposition(Tensor s, Tensor a) {
        _S = s;
        _A = a;
    }

    public TensorDecomposition add(TensorDecomposition t) {
        return new TensorDecomposition(t._S.add(_S), t._A.add(_A));
    }

    public TensorDecomposition mult(TensorDecomposition t) {
        return new TensorDecomposition(t._S.mult(_S), t._A.mult(_A));
    }

    public void print() {
        System.out.println("Decompostion of the tensor");
        _S.print();
        _A.print();
    }
}

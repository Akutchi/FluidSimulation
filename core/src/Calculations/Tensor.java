package Calculations;

public class Tensor {

    private double[][] _coefficients = new double[2][2];

    public Tensor() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                _coefficients[i][j] = 0;
            }
        }
    }

    public Tensor(Vector[] coefficients) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                _coefficients[i][j] = coefficients[i].extract(j);
            }
        }
    }

    public Tensor(double[][] coefficients) {
        _coefficients = coefficients;
    }

    private double product(int row, int col, Tensor t) {
        double value = 0;
        for (int i = 0; i < 2; i++) {
            value += t._coefficients[i][row] * _coefficients[col][i];
        }

        return value;
    }

    public Tensor add(Tensor t) {
        double[][] coeff = new double[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                coeff[i][j] = _coefficients[i][j] + t._coefficients[i][j];
            }
        }
        return new Tensor(coeff);
    }

    public Tensor soustract(Tensor t) {
        double[][] coeff = new double[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                coeff[i][j] = _coefficients[i][j] - t._coefficients[i][j];
            }
        }
        return new Tensor(coeff);
    }

    public Tensor mult(Tensor t) {
        double[][] coeff = new double[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                coeff[i][j] = product(j, i, t);
            }
        }
        return new Tensor(coeff);
    }

    public Tensor mult(double a) {
        double[][] coeff = new double[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                coeff[i][j] = _coefficients[i][j] * a;
            }
        }
        return new Tensor(coeff);
    }

    public Tensor transpose() {
        double[][] coeff = new double[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                coeff[i][j] = _coefficients[j][i];
            }
        }
        return new Tensor(coeff);
    }

    public double trace() {
        return _coefficients[0][0] + _coefficients[1][1];
    }

    public double det() {
        return _coefficients[0][0] * _coefficients[1][1] - _coefficients[0][1] * _coefficients[1][0];
    }

    public static Tensor identity() {
        return new Tensor(new double[][]{{1.0, 0.0}, {0.0, 1.0}});
    }

    public void print() {
        System.out.println("/ " + _coefficients[0][0] + "\t" + _coefficients[0][1] + "\t\\");
        System.out.println("\\ " + _coefficients[1][0] + "\t" + _coefficients[1][1] + "\t/");
    }
}

package Calculations;

import particule.Vector;

import java.util.ArrayList;

public class Area {

    private final ArrayList<Vector> _vectors = new ArrayList<>();

    public Area(Vector[] vectors) {
        for (int i = 0; i < vectors.length - 1; i++) {
            _vectors.add(vectors[i + 1].soustract(vectors[i]));
        }
    }

    private double calculateElementOfArea(Vector u, Vector v) {
        System.out.println(u.det(v) / 2.0);
        return u.det(v) / 2.0;
    }

    public double calculate() {
        double area = 0;
        int index = 0;
        Vector vi, vi_1;

        if (_vectors.size() <= 1) {
            return 0;
        }

        do {
            vi = _vectors.get(index);
            vi_1 = _vectors.get(index + 1);

            if (vi.mult(vi_1) >= 0) {
                Vector dv = vi_1.soustract(vi);
                area += calculateElementOfArea(vi, vi_1);

                _vectors.set(index, dv);
                _vectors.remove(index + 1);
            }
            index++;
        } while (Math.abs(vi.mult(vi_1) - Math.pow(vi.magnitude(), 2)) > 0.01 && index < _vectors.size() - 1);

        return area;
    }
}

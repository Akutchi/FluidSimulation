package Calculations;

import particule.Vector;

import java.util.ArrayList;

public class Area {

    private double _area = 0;

    private final ArrayList<Vector> _vectors = new ArrayList<>();

    public Area(Vector[] vectors) {
        for (int i = 0; i < vectors.length - 1; i++) {
            Vector dv = vectors[i + 1].soustract(vectors[i]);
            _vectors.add(dv);
        }
        _vectors.add(vectors[vectors.length - 1].soustract(vectors[0]));
    }

    private double calculateElementOfArea(Vector u, Vector v) {
        return u.det(v) / 2.0;
    }

    private boolean areInSameQuarter(Vector u, Vector v) {
        return u.compareCoordinatesSign(v);
    }

    private boolean areParallel(Vector u, Vector v) {
        return Math.abs(u.mult(v) - Math.pow(u.magnitude(), 2)) < 0.01;
    }

    /**
     * Calculate the area of the particule with the following alogirthm
     * 1. take a vector u and one of its adjacent one v
     * 2. if they curve inwards, calculate the area of the triangle formed by u, v and u+v
     * 3. remove u and v from the list and insert u+v
     * 4. if not, repeat the process for v
     *
     * @return the area in meter
     */
    public double calculate() {
        int index = 0;
        Vector vi, vi_1;

        if (_vectors.size() <= 1) {
            return 0;
        }

        do {
            vi = _vectors.get(index);
            vi_1 = _vectors.get(index + 1);

            if (!areInSameQuarter(vi, vi_1)) {
                Vector w = vi.add(vi_1);
                _area += calculateElementOfArea(vi, vi_1);

                _vectors.set(index, w);
                _vectors.remove(index + 1);
            } else {
                index++;
            }
        } while (_vectors.size() > 2 && !areParallel(vi, vi_1));

        return _area;
    }

    public void printState(int stateNumber) {
        System.out.println("Sate " + stateNumber + " ------------");
        for (Vector v : _vectors) {
            v.print();
        }
        System.out.println("Current area : " + _area);
    }
}

package Calculations;

import particule.Vector;

import java.util.ArrayList;

public class Area {

    private double _area = 0;

    private final ArrayList<Vector> _vectors = new ArrayList<>();

    /**
     * Transform an array of points into an array of "real" vectors
     *
     * @param vectors the result
     */
    public Area(Vector[] vectors) {
        for (int i = 0; i < vectors.length - 1; i++) {
            Vector dv = vectors[i + 1].soustract(vectors[i]);
            _vectors.add(dv);
        }
        _vectors.add(vectors[vectors.length - 1].soustract(vectors[0]));
    }

    /**
     * calculate the area of a small elemental triangle
     *
     * @param u first vector
     * @param v adjacent vector to u
     * @return the area of the triangle u v u+v
     */
    private double calculateElementOfArea(Vector u, Vector v) {
        return u.det(v) / 2.0;
    }

    /**
     * Verify if two vectors are in the same quarter in the trigonometric circle
     *
     * @param u first vector
     * @param v second vector
     * @return true if in the same quarter, false otherwise
     */
    private boolean areInSameQuarter(Vector u, Vector v) {
        return u.compareCoordinatesSign(v);
    }

    /**
     * Verify if two vectors are parralel
     *
     * @param u first vector
     * @param v second vector
     * @return true if parrallel, false otherwise
     */
    private boolean areParallel(Vector u, Vector v) {
        return Math.abs(u.mult(v) - Math.pow(u.magnitude(), 2)) < 0.01;
    }

    /**
     * Calculate the area of the particule with the following algorithm
     * 1. take a vector u and one of its adjacent one v
     * 2. if they curve inwards (a.k.a not in the same quarter in the trigonometric circle),
     * calculate the area of the triangle formed by u, v and u+v
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

    /**
     * Print the current area at operation n
     *
     * @param stateNumber nth operation
     */
    public void printState(int stateNumber) {
        System.out.println("Sate " + stateNumber + " ------------");
        for (Vector v : _vectors) {
            v.print();
        }
        System.out.println("Current area : " + _area);
    }
}

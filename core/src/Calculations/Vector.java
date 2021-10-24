package Calculations;

import Draw.objectRenderer;

public class Vector {

    private final double _x;
    private final double _y;

    public Vector(double x, double y) {
        _x = x;
        _y = y;
    }

    public Vector add(Vector p) {
        return new Vector(_x + p._x, _y + p._y);
    }

    public Vector soustract(Vector p) {
        return new Vector(_x - p._x, _y - p._y);
    }

    public Vector mult(double n) {
        return new Vector(_x * n, _y * n);
    }

    public double mult(Vector v) {
        return v._x * _x + v._y * _y;
    }

    public double det(Vector v) {
        return _x * v._y - _y * v._x;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(_x, 2) + Math.pow(_y, 2));
    }

    public boolean compareCoordinatesSign(Vector v) {
        return (_x * v._y > 0) && (_y * v._y > 0);
    }

    public double extract(int index) {
        if (index < 2) {
            return index == 0 ? _x : _y;
        }
        return 0;
    }

    public void print() {
        System.out.println("(" + _x + "; " + _y + ")");
    }

    public void render(objectRenderer rd, Vector p) {
        rd.render(_x, _y, p._x, p._y);
    }
}

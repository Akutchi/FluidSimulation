package particule;

import Draw.objectRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Vector {

    private final double _xCenter;
    private final double _yCenter;

    public Vector(double x, double y) {
        _xCenter = x;
        _yCenter = y;
    }

    public Vector add(Vector p) {
        return new Vector(_xCenter + p._xCenter, _yCenter + p._yCenter);
    }

    public Vector soustract(Vector p) {
        return new Vector(_xCenter - p._xCenter, _yCenter - p._yCenter);
    }

    public Vector mult(double n) {
        return new Vector(_xCenter * n, _yCenter * n);
    }

    public double mult(Vector v) {
        return v._xCenter * _xCenter + v._yCenter * _yCenter;
    }

    public double det(Vector v) {
        return _xCenter * v._yCenter - _yCenter * v._xCenter;
    }

    public double magnitude() {
        return Math.sqrt(Math.pow(_xCenter, 2) + Math.pow(_yCenter, 2));
    }

    public void print() {
        System.out.println("(" + _xCenter + "; " + _yCenter + ")");
    }

    public void render(ShapeRenderer sr, Vector p) {
        objectRenderer rd = new objectRenderer();
        rd.render(sr, _xCenter, _yCenter, p._xCenter, p._yCenter);
    }
}

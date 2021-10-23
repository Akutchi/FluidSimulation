package particule;

import Calculations.Area;
import Draw.objectRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Particule {

    private static final int STARTX = 100;
    private static final int STARTY = 900;
    private static final int RANGEX = 50;
    private static final int RANGEY = 50;
    private static final double scale = 1; // m.pixels-1

    // all of the units are in standart units.
    private static final double dt = 0.01;
    private static final double g = 9.81;
    private static final Vector gravity = new Vector(0.0, -g);

    private final int[][] pointInitializer = {{1, 1}, {0, 1}, {-1, 1}, {-1, -1}, {1, -1}};

    private final double _drho;
    private double _area;
    private double _m;

    private Vector _barycenter = new Vector(0.0, 0.0);

    private final Vector[] _vectors; // in m
    private final Vector[] _velocity = {new Vector(0, 0), new Vector(0, 0)}; // m.s-1
    private final Vector[] _acceleration = {new Vector(0, 0), new Vector(0, 0)}; // m.s-2


    private void initializeForm(int numberOfFaces) {

        Vector previousVector = new Vector((STARTX + Math.random() * RANGEX) * scale, STARTY * scale);
        Vector currentVector;

        for (int i = 0; i < numberOfFaces; i++) {

            double dx = pointInitializer[i][0] * Math.random() * RANGEX * scale;
            double dy = pointInitializer[i][1] * Math.random() * RANGEY * scale;
            currentVector = previousVector.add(new Vector(dx, dy));
            _vectors[i] = currentVector;
            previousVector = currentVector;
        }
    }

    private void calculateArea(Vector[] vList) {
        Area area = new Area(vList);
        _area = area.calculate();
        _m = _area * _drho * scale;
    }

    private Vector computeGravity() {
        return gravity.mult(_m);
    }

    private void shift(Vector[] quantity, Vector q) {
        quantity[0] = quantity[1];
        quantity[1] = q;
    }

    private void computePosition() {
        for (int i = 0; i < _vectors.length; i++) {
            Vector p = _velocity[1].mult(dt).add(_vectors[i]);
            _vectors[i] = p;
        }
    }

    private void computeVelocity() {
        Vector v = _acceleration[1].mult(dt).add(_velocity[1]);
        shift(_velocity, v);
    }

    private void computeAcceleration(Vector forces) {
        shift(_acceleration, forces.add(computeGravity()).mult(1.0 / _m));
    }

    private void computeState(Vector forces) {
        computeAcceleration(forces);
        computeVelocity();
        computePosition();
    }

    public Particule(Vector[] listOfVertex, double drho) {
        _drho = drho;
        _vectors = listOfVertex;
        _barycenter = updateBarycenter(_vectors);
        calculateArea(_vectors);
    }

    public Particule(int numberOfFaces, double drho) {
        _drho = drho;
        _vectors = new Vector[numberOfFaces];
        initializeForm(numberOfFaces);
        calculateArea(_vectors);
        _barycenter = updateBarycenter(_vectors);
    }

    public Particule(int numberOfFaces, Vector[] initialConditions, double drho) {
        _drho = drho;
        _vectors = new Vector[numberOfFaces];
        initializeForm(numberOfFaces);
        calculateArea(_vectors);
        _barycenter = updateBarycenter(_vectors);

        _velocity[1] = initialConditions[1];
        _acceleration[1] = initialConditions[2];
    }

    public Vector updateBarycenter(Vector[] arrayOfVector) {

        Vector newBarycenter = new Vector(0.0, 0.0);

        for (Vector vector : arrayOfVector) {
            newBarycenter = newBarycenter.add(vector);
        }
        return newBarycenter.mult(1.0 / arrayOfVector.length);

    }

    public void print(boolean trace) {

        System.out.println(" ---------------- ");

        System.out.print("Barycenter : ");
        _barycenter.print();
        System.out.println("Rho = " + _drho + " kg.m-3");
        System.out.println("A = " + _area * scale + " m^2");
        System.out.println("m = " + _m + " kg");

        if (trace) {
            for (Vector v : _vectors) {
                v.print();
            }
        }
    }

    public void render(ShapeRenderer sr, Vector forces) {
        computeState(forces);
        objectRenderer rd = new objectRenderer(sr, scale);
        rd.render(rd, _vectors);
    }
}

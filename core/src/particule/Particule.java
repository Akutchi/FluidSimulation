package particule;

import Calculations.Area;
import Draw.objectRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Particule {

    private static final int RANGEX = 100;
    private static final int RANGEY = 100;
    private static final double scale = 1E-6 / 100; // m.pixels-1

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
    private final Vector[] _velocity = new Vector[2]; // m.s-1
    private final Vector[] _acceleration = new Vector[2]; // m.s-2


    private void initializeForm(int numberOfFaces) {

        Vector previousVector = new Vector(100 + Math.random() * RANGEX, 300);
        Vector currentVector;

        for (int i = 0; i < numberOfFaces; i++) {

            double dx = pointInitializer[i][0] * Math.random() * RANGEX;
            double dy = pointInitializer[i][1] * Math.random() * RANGEY;
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

    public Particule(Vector[] listOfVertex, double drho) {
        _drho = drho;
        _vectors = listOfVertex;
        _barycenter = updateBarycenter(_vectors);
        calculateArea(_vectors);
        _velocity[0] = new Vector(0.0, 0.0);
        _acceleration[0] = new Vector(0.0, 0.0);
    }

    public Particule(int numberOfFaces, double drho) {
        _drho = drho;
        _vectors = new Vector[numberOfFaces];
        initializeForm(numberOfFaces);
        calculateArea(_vectors);
        _barycenter = updateBarycenter(_vectors);
        _velocity[0] = new Vector(0.0, 0.0);
        _acceleration[0] = new Vector(0.0, 0.0);
    }

    public Particule(int numberOfFaces, Vector[] initialConditions, double drho) {
        _drho = drho;
        _vectors = new Vector[numberOfFaces];
        initializeForm(numberOfFaces);
        calculateArea(_vectors);
        _barycenter = updateBarycenter(_vectors);

        _velocity[0] = initialConditions[1];
        _acceleration[0] = initialConditions[2];
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

    public void render(ShapeRenderer sr) {
        objectRenderer rd = new objectRenderer();
        rd.render(sr, _vectors);
    }
}

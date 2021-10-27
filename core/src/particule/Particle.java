package particule;

import Calculations.Area;
import Calculations.Vector;
import Draw.objectRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Particle {

    private static final int STARTX = 200;
    private static final int STARTY = 500;
    private static final int RANGEX = 20;
    private static final int RANGEY = 20;
    private static final double scale = 0.08; // m.pixels-1
    private static final int distance = 1;

    // all of the units are in standart units.
    private static final double dt = 0.01;
    private static final double g = 9.81;
    private static final Vector gravity = new Vector(0.0, -g);

    private double _drho;
    private double _area;
    private double _m;

    private Vector _oldBarycenter;
    private Vector _barycenter;

    private Vector[] _vectors; // in m  - the last point is the barycenter
    private final Vector[] _velocity = {new Vector(0, 0), new Vector(0, 0)}; // m.s-1
    private final Vector[] _acceleration = {new Vector(0, 0), new Vector(0, 0)}; // m.s-2


    private void calculateArea(Vector[] vList) {
        Area area = new Area(vList);
        _area = area.calculate();
        _m = _area * _drho * scale;
    }

    private Vector computeGravity() {
        return gravity.mult(_m);
    }

    private void shiftValue(Vector[] quantity, Vector q) {
        quantity[0] = quantity[1];
        quantity[1] = q;
    }

    private void computePosition() {
        for (int i = 0; i < _vectors.length; i++) {
            _vectors[i] = _velocity[1].mult(dt).add(_vectors[i]);
        }
        _oldBarycenter = _barycenter;
        _barycenter = _barycenter.add(_velocity[1].mult(dt));
    }

    private void computeVelocity() {
        Vector v = _acceleration[1].mult(dt).add(_velocity[1]);
        shiftValue(_velocity, v);
    }

    private void computeAcceleration(Vector forces) {
        Vector sumForces = forces.add(computeGravity()).mult(1.0 / _m);
        shiftValue(_acceleration, sumForces);
        if (sumForces.magnitude() < 1E-5) {
            _acceleration[1] = new Vector(0, 0);
            _velocity[1] = new Vector(0, 0);
        }
    }

    private void computeState(Vector forces) {
        computeAcceleration(forces);
        computeVelocity();
        computePosition();
    }

    private Vector[] getMinMaxCoords(final int typeOfCoord) {
        Vector[] sortCoord = new Vector[_vectors.length];
        System.arraycopy(_vectors, 0, sortCoord, 0, _vectors.length);
        Arrays.sort(sortCoord, new Comparator<Vector>() {
            @Override
            public int compare(Vector u, Vector v) {
                return Double.compare(u.extract(typeOfCoord), v.extract(typeOfCoord));
            }
        });

        return new Vector[]{sortCoord[0], sortCoord[sortCoord.length - 1]};
    }

    private boolean collisionBorders(int width) {
        Vector[] minMaxVectorSortedX = getMinMaxCoords(0);
        Vector minVectorSortedY = getMinMaxCoords(1)[0];

        boolean collisionUnder = minVectorSortedY.extract(1) < distance;
        boolean collisionLeft = minMaxVectorSortedX[0].extract(0) < distance;
        boolean collisionRight = minMaxVectorSortedX[1].extract(0) > width - distance;

        return collisionLeft || collisionRight || collisionUnder;
    }

    private void initializeRandomForm(int numberOfFaces) {

        int[][] pointInitializer = {{1, 1}, {0, 1}, {-1, 1}, {-1, -1}, {1, -1}};
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

    private void initializeCircle(int numberOfFaces) {

        Vector currentVector;
        double dangle = 2 * Math.PI / numberOfFaces;
        double Xorigin = (STARTX + Math.random() * RANGEX) * scale;
        double Yorigin = STARTY * scale;

        for (int i = 0; i < numberOfFaces; i++) {

            double dx = Math.cos(dangle * i) * RANGEX * scale + Xorigin;
            double dy = Math.sin(dangle * i) * RANGEY * scale + Yorigin;

            currentVector = new Vector(dx, dy);
            _vectors[i] = currentVector;
        }
    }

    private void initializeForm(int numberOfFaces, boolean randomForm) {
        if (randomForm) {
            initializeRandomForm(numberOfFaces);
        } else {
            initializeCircle(numberOfFaces);
        }
    }

    private void initialization(int numberOfFaces, double drho, boolean randomForm) {
        _onFloor = false;
        _drho = drho;
        _vectors = new Vector[numberOfFaces];
        initializeForm(numberOfFaces, randomForm);
        calculateArea(_vectors);
        _barycenter = updateBarycenter(_vectors);
        _oldBarycenter = _barycenter;
    }

    public Particle(Vector[] listOfVertex, double drho) {
        _drho = drho;
        _vectors = listOfVertex;
        calculateArea(_vectors);
        _barycenter = updateBarycenter(_vectors);
        _oldBarycenter = _barycenter;
    }

    public Particle(int numberOfFaces, double drho, boolean randomForm) {
        initialization(numberOfFaces, drho, randomForm);
    }

    public Particle(int numberOfFaces, Vector[] initialConditions, double drho, boolean randomForm) {
        initialization(numberOfFaces, drho, randomForm);
        _velocity[1] = initialConditions[0];
        _acceleration[1] = initialConditions[1];
    }

    public Vector updateBarycenter(Vector[] arrayOfVector) {

        Vector newBarycenter = new Vector(0.0, 0.0);

        for (Vector vector : arrayOfVector) {
            newBarycenter = newBarycenter.add(vector);
        }
        return newBarycenter.mult(1.0 / arrayOfVector.length);
    }

    public Vector computeReaction(int width) {

        if (collisionBorders(width)) {
            Vector dv = _barycenter.soustract(_oldBarycenter);
            double lambda = -g / dv.magnitude();
            return dv.mult(lambda * _m);
        }

        return new Vector(0, 0);
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

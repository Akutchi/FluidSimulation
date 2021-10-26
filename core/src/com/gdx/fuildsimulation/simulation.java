package com.gdx.fuildsimulation;

import Calculations.Vector;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import particule.Particle;

public class simulation extends ApplicationAdapter {

    int WIDTH;
    int HEIGHT;
    SpriteBatch batch;
    ShapeRenderer sr;
    Particle p;
    Vector externalForces;
    Vector[] initialConditions;

    private void setDimensions() {
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
    }

    @Override
    public void create() {

        Gdx.graphics.setWindowedMode(1000, 900);
        setDimensions();

        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        externalForces = new Vector(0, 0);
        initialConditions = new Vector[]{new Vector(-0, 0), new Vector(0, 0)};
        p = new Particle(15, initialConditions, 1000, false);
    }

    @Override
    public void render() {

        ScreenUtils.clear(1, 255, 255, 255);
        batch.begin();
        batch.end();
        externalForces.add(p.computeReaction(WIDTH));
        p.render(sr, externalForces);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

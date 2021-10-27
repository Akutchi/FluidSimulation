package com.gdx.fuildsimulation;

import Calculations.Vector;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import particule.Particle;

public class simulation extends ApplicationAdapter {

    private static final double SHOCK_ABSORPTION = 8E6; // in N

    private int WIDTH;
    private int HEIGHT;
    private SpriteBatch batch;
    private ShapeRenderer sr;
    private Particle p;
    private Vector reactionForce;

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
        reactionForce = new Vector(0, 0);
        Vector[] initialConditions = new Vector[]{new Vector(20, 0), new Vector(0, 0)};
        p = new Particle(15, initialConditions, 1000, false);
    }

    @Override
    public void render() {

        ScreenUtils.clear(1, 255, 255, 255);
        batch.begin();
        batch.end();
        reactionForce = p.computeReaction(WIDTH, SHOCK_ABSORPTION);
        p.render(sr, reactionForce);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

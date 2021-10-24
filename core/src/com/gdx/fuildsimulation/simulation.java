package com.gdx.fuildsimulation;

import Calculations.Tensor;
import Calculations.TensorDecomposition;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import particule.Particle;

public class simulation extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer sr;
    Particle p;
    Tensor t;
    TensorDecomposition td;

    @Override
    public void create() {

        Gdx.graphics.setWindowedMode(1000, 900);
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        t = new Tensor(new double[][]{{10.0, -4.0}, {-1.0, 3.0}});
        Tensor Id = Tensor.identity();
        td = new TensorDecomposition(t);
        td.print();
    }

    @Override
    public void render() {

        ScreenUtils.clear(1, 255, 255, 255);
        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

package com.gdx.fuildsimulation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import particule.Particule;
import particule.Vector;

public class simulation extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer sr;
    Particule p;

    Vector[] vList = {new Vector(100, 100),
            new Vector(200, 100),
            new Vector(200, 200),
            new Vector(100, 200)};

    @Override
    public void create() {

        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        p = new Particule(5, 1000);
        p.print(false);

    }

    @Override
    public void render() {

        ScreenUtils.clear(1, 255, 255, 255);
        batch.begin();
        batch.end();
        p.render(sr);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

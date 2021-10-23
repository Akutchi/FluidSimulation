package com.gdx.fuildsimulation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import particule.Particule;
import particule.Vector;

public class simulation extends ApplicationAdapter {
    SpriteBatch batch;
    ShapeRenderer sr;
    Particule p;

    @Override
    public void create() {

        Gdx.graphics.setWindowedMode(1000, 900);

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
        p.render(sr, new Vector(0, 0));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

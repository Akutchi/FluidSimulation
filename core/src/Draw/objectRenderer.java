package Draw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import particule.Vector;

public class objectRenderer {

    public void render(ShapeRenderer sr, Vector[] vectors) {
        for (int i = 0; i < vectors.length - 1; i++) {
            vectors[i].render(sr, vectors[i + 1]);
        }
        vectors[vectors.length - 1].render(sr, vectors[0]);
    }

    public void render(ShapeRenderer sr, double xP1, double yP1, double xP2, double yP2) {
        sr.setColor(Color.BLACK);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.line((float) xP1, (float) yP1, (float) xP2, (float) yP2);
        sr.end();
    }
}

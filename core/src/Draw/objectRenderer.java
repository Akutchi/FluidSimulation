package Draw;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import Calculations.Vector;

public class objectRenderer {

    double _scale;
    ShapeRenderer _sr;

    public objectRenderer(ShapeRenderer sr, double scale) {
        _sr = sr;
        _scale = scale;
    }

    private void renderScaled(objectRenderer rd, Vector u, Vector v) {
        Vector uScaled = u.mult(1 / _scale);
        Vector vScaled = v.mult(1 / _scale);
        uScaled.render(rd, vScaled);
    }

    public void render(objectRenderer rd, Vector[] vectors) {
        for (int i = 0; i < vectors.length - 1; i++) {
            renderScaled(rd, vectors[i], vectors[i + 1]);
        }
        renderScaled(rd, vectors[vectors.length - 1], vectors[0]);
    }

    public void render(double xP1, double yP1, double xP2, double yP2) {
        _sr.setColor(Color.BLACK);
        _sr.begin(ShapeRenderer.ShapeType.Line);
        _sr.line((float) xP1, (float) yP1, (float) xP2, (float) yP2);
        _sr.end();
    }
}

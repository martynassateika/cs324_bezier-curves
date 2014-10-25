import com.sun.istack.internal.NotNull;

import java.awt.geom.Point2D;
import java.util.List;

/**
 * User: Martynas Sateika
 * Date: 14.2.17
 * Time: 11.46
 */
public class BezierCurve {

    @NotNull private final List<Point2D> points;

    public BezierCurve(List<Point2D> points) {
        if (points == null) {
            throw new IllegalArgumentException("Points cannot be null");
        }
        this.points = points;
    }

    public int xValueAt(double u) {
        double sum = 0;

        for (int i = 0, n = points.size(); i < n; i++) {
            sum += points.get(i).getX() * BezierFunctions.functions[i].of(u);
        }

        return (int)sum;
    }

    public int yValueAt(double u) {
        double sum = 0;

        for (int i = 0, n = points.size(); i < n; i++) {
            sum += points.get(i).getY() * BezierFunctions.functions[i].of(u);
        }

        return (int)sum;
    }

    @NotNull
    public List<Point2D> getPoints() {
        return points;
    }

}
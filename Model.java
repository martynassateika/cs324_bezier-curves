import com.sun.istack.internal.NotNull;

/**
 * User: Martynas Sateika
 * Date: 14.2.16
 * Time: 23.14
 */
public class Model {

    @NotNull private final BezierCurve curve;
    private final int iterations;

    public Model(BezierCurve curve, int iterations) {
        if (curve == null) {
            throw new IllegalArgumentException("Curve cannot be null");
        }

        if (iterations < 2 || iterations > 100) {
            throw new IllegalArgumentException("Iterations must be 2-100");
        }

        this.curve = curve;
        this.iterations = iterations;
    }

    @NotNull
    public BezierCurve getCurve() {
        return curve;
    }

    public int getIterations() {
        return iterations;
    }
}

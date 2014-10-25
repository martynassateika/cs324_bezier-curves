/**
 * User: Martynas Sateika
 * Date: 14.2.17
 * Time: 13.09
 */
public class BezierFunctions {

    public static final Function[] functions = new Function[] {
            u -> Math.pow(1-u, 3),
            u -> 3 * u * Math.pow(1-u, 2),
            u -> 3 * Math.pow(u, 2) * (1-u),
            u -> Math.pow(u, 3)
    };

}

interface Function {
    double of(double x);
}

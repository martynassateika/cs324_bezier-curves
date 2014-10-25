import java.awt.*;
import java.util.Arrays;

/**
 * User: Martynas Sateika
 * Date: 14.2.16
 * Time: 23.18
 */
public class App {

    public static void main(String[] args) {
        new View(
            new Model(
                new BezierCurve(
                    Arrays.asList(
                        new Point(10, 10),
                        new Point(600, 400),
                        new Point(630, 150),
                        new Point(100, 50)
                    )
                ), 50
            ), "CS324 Computer Graphics: Bezier Curve test"
        );
    }

}

import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * User: Martynas Sateika
 * Date: 14.2.16
 * Time: 23.14
 */
public class View {

    @NotNull private final Model model;

    public View(Model model, String title) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null!");
        }

        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null!");
        }

        this.model = model;

        JFrame frame = new JFrame(title);
        frame.add(new GraphPanel());
        frame.pack();
        frame.setSize(640, 480);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private class GraphPanel extends JPanel {

        private final Color POLY_LINE_COLOR = new Color(50, 192, 192) ;
        private final Color CURVE_COLOR = new Color(0, 0, 0);
        private final Color POINT_COLOR = new Color(192, 50, 192);

        boolean isDragged = false;
        Point2D selectedPoint = null;

        public GraphPanel() {
            MouseAdapter adapter = new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);

                    final int MIN_DISTANCE = 5;
                    double distanceOfClosestPoint = Double.MAX_VALUE;

                    Point2D closestPoint = null;
                    for (Point2D point : model.getCurve().getPoints()) {
                        double distance = point.distance(e.getPoint().x, e.getPoint().y);
                        if (distance < MIN_DISTANCE) {
                            if (closestPoint != null) {
                                if (distance < distanceOfClosestPoint) {
                                    closestPoint = point;
                                    distanceOfClosestPoint = distance;
                                }
                            } else {
                                closestPoint = point;
                                distanceOfClosestPoint = distance;
                            }
                        }
                    }

                    selectedPoint = closestPoint;
                    isDragged = (selectedPoint != null);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    super.mouseReleased(e);
                    selectedPoint = null;
                    isDragged = false;
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    super.mouseDragged(e);
                    if (isDragged) {
                        selectedPoint.setLocation(e.getX(), e.getY());
                        repaint();
                    }
                }

            };

            this.addMouseListener(adapter);
            this.addMouseMotionListener(adapter);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics2D = (Graphics2D)g;

            drawBezierCurve(graphics2D);
            drawControlPoints(graphics2D);
        }

        private void drawBezierCurve(Graphics2D graphics2D) {
            int iterations = model.getIterations();
            int[] xPoints = new int[iterations + 1];
            int[] yPoints = new int[iterations + 1];

            double u = 0.0d;
            double m = 1 / (double) iterations;

            for (int i = 0; i <= iterations; i++) {
                xPoints[i] = model.getCurve().xValueAt(u);
                yPoints[i] = model.getCurve().yValueAt(u);
                u += m;
            }

            // Draw curve
            graphics2D.setColor(CURVE_COLOR);
            graphics2D.drawPolyline(xPoints, yPoints, iterations + 1);
        }

        public void drawControlPoints(Graphics2D graphics2D) {
            graphics2D.setColor(POLY_LINE_COLOR);
            List<Point2D> points = model.getCurve().getPoints();
            int numPoints = points.size();

            // Draw line connecting control points
            int[] xPoints = new int[numPoints];
            int[] yPoints = new int[numPoints];

            for (int i = 0; i < numPoints; i++) {
                xPoints[i] = (int) points.get(i).getX();
                yPoints[i] = (int) points.get(i).getY();
            }
            graphics2D.drawPolyline(xPoints, yPoints, numPoints);

            // Draw control points
            int circleDiameter = 10;
            graphics2D.setColor(POINT_COLOR);
            for (Point2D point : model.getCurve().getPoints()) {
                int xPos = (int) (point.getX() - circleDiameter / 2);
                int yPos = (int) (point.getY() - circleDiameter / 2);
                graphics2D.fillOval(xPos, yPos, circleDiameter, circleDiameter);
            }
        }

    }
}

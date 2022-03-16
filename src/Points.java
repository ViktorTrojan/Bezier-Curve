
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class point {

    static int SIZE = 20; // SIZE of the red dot
    int x, y;

    public point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // returns Point with number multiplication
    public static point multiply(point p, float s) {
        point ret = new point((int) (p.x * s), (int) (p.y * s));
        return ret;
    }
}

public class Points {

    static int ALPHAVALUE = 32;
    static float ITSTEPS = 1f / 100f; // 100 iteration steps
    ArrayList<point> points;

    public Points() {
        points = new ArrayList<>();
        points.add(new point(100, 100));
        points.add(new point(200, 150));
        points.add(new point(200, 500));
        points.add(new point(250, 400));
        points.add(new point(300, 300));
        points.add(new point(300, 400));
        points.add(new point(500, 200));
        points.add(new point(500, 500));
    }

    public static float lerp(int i0, int i1, float t) {
        return i0 + t * (i1 - i0);
    }

    public static point getCubicBezierPoint(float t, point p0, point p1, point p2, point p3) {
        point pp0 = point.multiply(p0, (-t * t * t + 3 * t * t - 3 * t + 1));
        point pp1 = point.multiply(p1, (3 * t * t * t - 6 * t * t + 3 * t));
        point pp2 = point.multiply(p2, (-3 * t * t * t + 3 * t * t));
        point pp3 = point.multiply(p3, (t * t * t));
        return new point(pp0.x + pp1.x + pp2.x + pp3.x, pp0.y + pp1.y + pp2.y + pp3.y);
    }

//    public void draw(Graphics2D g2) {
//        for (int i = 0; i < points.size(); i++) { // iterate through the array
//
//            // draw straight line
//            if (i < points.size() - 1) {
//                g2.setColor(new Color(255, 0, 0, ALPHAVALUE));
//                g2.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
//            }
//
//            // draw Point
//            g2.setColor(new Color(255,0,0,ALPHAVALUE));
//            g2.fillOval(points.get(i).x - point.SIZE / 4, points.get(i).y - point.SIZE / 4, point.SIZE / 2, point.SIZE / 2);
//
//            // draw Border around Point
//            g2.setColor(new Color(0,0,0,ALPHAVALUE));
//            g2.drawRect(points.get(i).x - point.SIZE / 2, points.get(i).y - point.SIZE / 2, point.SIZE, point.SIZE);
//
//            // draw Number of Point
//            g2.setColor(new Color(0, 0, 0, ALPHAVALUE));
//            Draw.drawString(g2, String.valueOf(i), points.get(i).x, points.get(i).y, 20);
//        }
//
//        if (points.size() > 2) { // if more than 2 points exist
//            for (int i = -1; i < points.size() - 2; i++) {
//                for (float t = 0; t <= 1; t += ITSTEPS) {
//
//                    g2.setColor(new Color(0, 0, 255));
//                    point p;
//                    if (i == -1) { // if it's the first point
//                        p = GetSplinePoint(t, points.get(i + 1), points.get(i + 1), points.get(i + 2), points.get(i + 3));
//                    } else if (i == points.size() - 3) { // if it's the last point
//                        p = GetSplinePoint(t, points.get(i), points.get(i + 1), points.get(i + 2), points.get(i + 2));
//                    } else { // any other point
//                        p = GetSplinePoint(t, points.get(i), points.get(i + 1), points.get(i + 2), points.get(i + 3));
//                    }
//                    g2.fillRect(p.x, p.y, 2, 2);
//                }
//            }
//        }
//    }
    
    public void draw(Graphics2D g2) {
        for (int i = 0; i < points.size(); i++) { // iterate through the array

            // draw straight line
            if (i < points.size() - 1) {
                g2.setColor(new Color(255, 0, 0, ALPHAVALUE));
                g2.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
            }

            // draw Point
            g2.setColor(new Color(255,0,0,ALPHAVALUE));
            g2.fillOval(points.get(i).x - point.SIZE / 4, points.get(i).y - point.SIZE / 4, point.SIZE / 2, point.SIZE / 2);

            // draw Border around Point
            g2.setColor(new Color(0,0,0,ALPHAVALUE));
            g2.drawRect(points.get(i).x - point.SIZE / 2, points.get(i).y - point.SIZE / 2, point.SIZE, point.SIZE);

            // draw Number of Point
            g2.setColor(new Color(0, 0, 0, ALPHAVALUE));
            Draw.drawString(g2, String.valueOf(i), points.get(i).x, points.get(i).y, 20);
        }

        for (int i = 0; i < points.size() - 2; i += 2) {
            if (i < points.size() - 3) {
                for (float t = 0; t <= 1; t += ITSTEPS) {

                    point calc = new point(0, 0);
                    if (points.get(i + 3).x >= points.get(i + 2).x) {
                        calc.x = points.get(i + 3).x - Math.abs(points.get(i + 3).x - points.get(i + 2).x) * 2;
                    } else {
                        calc.x = points.get(i + 3).x + Math.abs(points.get(i + 3).x - points.get(i + 2).x) * 2;
                    }

                    if (points.get(i + 3).y >= points.get(i + 2).y) {
                        calc.y = points.get(i + 3).y - Math.abs(points.get(i + 3).y - points.get(i + 2).y) * 2;
                    } else {
                        calc.y = points.get(i + 3).y + Math.abs(points.get(i + 3).y - points.get(i + 2).y) * 2;
                    }

                    point p = getCubicBezierPoint(t, points.get(i), points.get(i + 1), calc, points.get(i + 2));

                    g2.setColor(new Color(0, 0, 255));
                    g2.fillRect(p.x, p.y, 2, 2);
                }
            }
        }
    }

    public point onMousePressed(MouseEvent e) {

        Point pp = e.getPoint();
        System.out.println("X: " + pp.getX() + " Y: " + pp.getY());

        switch (e.getButton()) {
            case 1: { // Left Click to drag a Point
                for (point p : points) {
                    int x = p.x - point.SIZE / 2;
                    int y = p.y - point.SIZE / 2;
                    if (x <= pp.x && y <= pp.y && x + point.SIZE >= pp.x && y + point.SIZE >= pp.y) {
                        return p;
                    }
                }
                break;
            }

            case 2: { // Middle Click to add a Point 
                points.add(new point(pp.x, pp.y));
                break;
            }

            case 3: { // Right Click to remove a Point
                point pToRemove = null;
                for (point p : points) {
                    int x = p.x - point.SIZE / 2;
                    int y = p.y - point.SIZE / 2;
                    if (x <= pp.x && y <= pp.y && x + point.SIZE >= pp.x && y + point.SIZE >= pp.y) {
                        pToRemove = p;
                    }
                }
                if (pToRemove != null) {
                    points.remove(pToRemove);
                }

                break;
            }
        }
        return null;
    }
    
//    public point onMousePressed(MouseEvent e) {
//
//        Point pp = e.getPoint();
//        System.out.println("X: " + pp.getX() + " Y: " + pp.getY());
//
//        switch (e.getButton()) {
//            case 1: { // Left Click to drag a Point
//                for (point p : points) {
//                    int x = p.x - point.SIZE / 2;
//                    int y = p.y - point.SIZE / 2;
//                    if (x <= pp.x && y <= pp.y && x + point.SIZE >= pp.x && y + point.SIZE >= pp.y) {
//                        return p;
//                    }
//                }
//                break;
//            }
//
//            case 2: { // Middle Click to add a Point 
//                points.add(new point(pp.x, pp.y));
//                break;
//            }
//
//            case 3: { // Right Click to remove a Point
//                point pToRemove = null;
//                for (point p : points) {
//                    int x = p.x - point.SIZE / 2;
//                    int y = p.y - point.SIZE / 2;
//                    if (x <= pp.x && y <= pp.y && x + point.SIZE >= pp.x && y + point.SIZE >= pp.y) {
//                        pToRemove = p;
//                    }
//                }
//                if (pToRemove != null) {
//                    points.remove(pToRemove);
//                }
//
//                break;
//            }
//        }
//        return null;
//    }
}

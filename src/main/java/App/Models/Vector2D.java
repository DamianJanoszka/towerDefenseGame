package App.Models;

import java.util.Comparator;

public class Vector2D implements Comparator<Vector2D> {

    public double x;
    public double y;

    @Override
    public String toString() {
        return "Vector2D "  +
                "x=" + x +
                ", y=" + y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double magnitude() {
        return (double) Math.sqrt(x * x + y * y);
    }

    public void add(Vector2D v) {
        x += v.x;
        y += v.y;
    }

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void multiply(double n) {
        x *= n;
        y *= n;
    }

    public void div(double n) {
        x /= n;
        y /= n;
    }

    public void normalize() {
        double m = magnitude();
        if (m != 0 && m != 1) {
            div(m);
        }
    }

    public void limit(double max) {
        if (magnitude() > max) {
            normalize();
            multiply(max);
        }
    }

    static public Vector2D subtract(Vector2D v1, Vector2D v2) {
        return new Vector2D(v1.x - v2.x, v1.y - v2.y);
    }

    public double heading2D() {
        return Math.atan2(y, x);
    }

    public boolean isGreaterThan(Vector2D v) {
        if(x>v.getX()&& y>v.getY()) {
            return true;
        }
        else
            return false;
    }
    public boolean isLessThan(Vector2D v) {
        if(x<v.getX()&& y<v.getY()) {
            return true;
        }
        else
            return false;
    }

    @Override
    public int compare(Vector2D o1, Vector2D o2) {
        return 0;
    }
}
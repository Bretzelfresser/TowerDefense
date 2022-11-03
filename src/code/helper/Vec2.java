package code.helper;

import java.awt.*;

public class Vec2 {

    private final double dx, dy;

    public Vec2(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Vec2(Point p){
        this(p.x, p.y);
    }

    public double lengthSq() {
        return dx * dx + dy * dy;
    }

    public double length(){
        return Math.sqrt(lengthSq());
    }

    public Vec2 add(Vec2 toAdd){
        return new Vec2(dx + toAdd.dx, dy + toAdd.dy);
    }

    public Vec2 add(double toAdd){
        return new Vec2(dx + toAdd, dy + toAdd);
    }

    public Vec2 mult(double toMul){
        return new Vec2(dx * toMul, dy * toMul);
    }

    public double dotProduct(Vec2 other){
        return dx * other.dx + dy * other.dy;
    }

    public double getX() {
        return dx;
    }

    public double getY() {
        return dy;
    }

    public Vec2 setX(double x){
        return new Vec2(x, dy);
    }

    public Vec2 setY(double y){
        return new Vec2(dx, y);
    }
}

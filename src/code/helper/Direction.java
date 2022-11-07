package code.helper;

public enum Direction {
    UP(0, -1),
    DOWN(0,1),
    LEFT(-1, 0),
    RIGHT(1,0);
    private final Vec2 direction;

    Direction(double dx, double dy) {
        this.direction = new Vec2(dx, dy);
    }

    public Vec2 getDirection() {
        return direction.copy();
    }
}

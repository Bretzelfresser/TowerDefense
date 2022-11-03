package code.helper;

import com.sun.jdi.PrimitiveValue;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Path {

    public static Builder builder(){return new Builder();}

    private Map<Point, Vec2> path = new HashMap<>();
    private int reachedPoint = 0;

    private Path(Builder builder){
        for (int i = 0;i<builder.points.size();i++){
            path.put(builder.points.get(i), builder.directions.get(i));
        }
    }
    private Path(Path path, boolean saveData){
        this.path = path.path;
        if (saveData)
            this.reachedPoint = path.reachedPoint;
    }

    public Path copyPath(){
        return new Path(this, false);
    }

    public Path copy(){
        return new Path(this, true);
    }

    public static class Builder{
        private List<Point> points = new ArrayList<>();
        private List<Vec2> directions = new ArrayList<>();

        /**
         * they stay in the same direction as u put it
         * both parameters must not be null
         */
        public Builder addWaypoint(Point p , Vec2 direction){
            if (p != null && direction != null){
                this.points.add(p);
                this.directions.add(direction);
            }
            return this;
        }

        public Path build(){
            if (points.size() != directions.size())
                throw new IllegalStateException("points and directions in builder must have the same size");
            return new Path(this);
        }
    }
}

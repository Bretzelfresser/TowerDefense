package code.map;


import code.helper.FileHelper;
import code.helper.SquareGameObject;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile extends SquareGameObject {

    private Type type;
    private boolean hovered = false;

    public Tile(double x, double y, double width, double height, Type type) {
        super(x, y, width, height);
        this.type = type;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public boolean isInside(int x, int y){
        return x >= this.getX() && y >= this.getY() && this.getX() + this.getWidth() >= x && this.getY() + this.getHeight() >= y;
    }

    public Type getType() {
        return type;
    }
    public int ordinal(){
        return this.type.ordinal();
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public void draw(Graphics g) {
        Graphics copy = g.create();
        if (type.getColor() != null) {
            copy.setColor(type.getColor());
            fillRect(copy);
        }else if(type.img != null){
            drawImage(copy, type.img);
        }
        if (hovered){
            copy.setColor(new Color(255, 255, 255, 100));
            fillRect(copy);
        }
    }

    public enum Type{
        GRASS(FileHelper.getImage("grass.png"), false, true),
        PATH(FileHelper.getImage("path.jpg"), true, false),
        WATER(FileHelper.getImage("water.jpg"), false, false),
        SPAWN_TILE(FileHelper.getImage("path.jpg"), true, false);

        private Color c;
        private BufferedImage img;
        private final boolean canWalk, canBuild;

        Type(Color c, boolean canWalk, boolean canBuild) {
            this.c = c;
            this.canWalk = canWalk;
            this.canBuild = canBuild;
        }
        Type(BufferedImage img, boolean canWalk, boolean canBuild) {
            this.img = img;
            this.canWalk = canWalk;
            this.canBuild = canBuild;
        }

        public Color getColor() {
            return c;
        }

        public boolean canBuild() {
            return canBuild;
        }

        public boolean canWalk() {
            return canWalk;
        }

        public BufferedImage getImg() {
            return img;
        }
    }
}

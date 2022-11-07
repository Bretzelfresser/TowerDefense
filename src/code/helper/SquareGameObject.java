package code.helper;

import java.awt.*;
import java.awt.image.ImageObserver;

public abstract class SquareGameObject implements DrawableObject {
    private double x, y, width, height;
    private Rectangle boundingBox;


    public SquareGameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        updateBounds();
    }

    protected void updateBounds() {
        if (this.boundingBox != null)
            this.boundingBox.setBounds((int) x, (int) y, (int) width, (int) height);
        else
            this.boundingBox = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    public void addX(double toAdd) {
        setX(this.x + toAdd);
    }

    public void addY(double toAdd) {
        setY(this.y + toAdd);
    }

    public void setY(double y) {
        this.y = y;
        updateBounds();
    }

    public void setX(double x) {
        this.x = x;
        updateBounds();
    }

    public void setWidth(double width) {
        this.width = width;
        updateBounds();
    }

    public void setHeight(double height) {
        this.height = height;
        updateBounds();
    }

    /**
     * note that this will return a Vector 2d which contains the point coordinates
     */
    public Vec2 getMiddle(){
        return new Vec2(x + this.width / 2d, this.y + this.height / 2d);
    }

    public double getY() {
        return y;
    }

    public double getX() {
        return x;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBounds(Rectangle rect){
        setBounds(rect.x, rect.y, rect.width, rect.height);
    }

    public void setBounds(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        updateBounds();
    }

    protected void drawRect(Graphics g){
        g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    protected void fillRect(Graphics g){
        g.fillRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }

    protected void drawImage(Graphics g, Image img, ImageObserver observer){
        g.drawImage(img, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height, observer);
    }

    protected void drawImage(Graphics g, Image img){
       drawImage(g, img, null);
    }

    public boolean isInside(int x, int y){
        return this.boundingBox.contains(x, y);
    }
}

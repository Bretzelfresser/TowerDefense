package code.helper;

import java.awt.*;

public class BaseButton extends SquareGameObject {
    protected String text;
    protected Color bgColor, textColor;
    protected boolean isHovered = false, isSelected = false;
    protected IBaseButtonPressable pressable;

    public BaseButton(double x, double y, double width, double height, Color bgColor, Color textColor, String text, IBaseButtonPressable pressable) {
        super(x, y, width, height);
        this.text = text;
        this.bgColor = bgColor;
        this.textColor = textColor;
        this.pressable = pressable;
    }

    public BaseButton(double x, double y, double width, double height, Color bgColor, IBaseButtonPressable pressable) {
        this(x, y, width, height, bgColor, null, "", pressable);
    }

    @Override
    public void draw(Graphics g) {
        Graphics copy = g.create();
        copy.setColor(this.bgColor);
        fillRect(copy);
        if (this.text.length() != 0) {
            adjustTextSize(g, 5, 5);
        }
        if (isSelected) {
            copy.setColor(new Color(255, 255, 255, 100));
            fillRect(copy);
        } else if (isHovered()) {
            copy.setColor(new Color(255, 255, 255, 100));
            fillRect(copy);
        }
    }

    protected void adjustTextSize(Graphics g, int marginX, int marginY) {
        Graphics copy = g.create();
        copy.setColor(this.textColor);
        while (copy.getFontMetrics(copy.getFont()).stringWidth(this.text) < this.getWidth() + 2 * marginX &&
                copy.getFontMetrics(copy.getFont()).getHeight() < this.getHeight() + 2 * marginY) {
            copy.setFont(new Font(copy.getFont().getName(), copy.getFont().getStyle(), copy.getFont().getSize() + 1));
        }
        g.setFont(new Font(copy.getFont().getName(), copy.getFont().getStyle(), copy.getFont().getSize() - 1));
        TextUtils.drawCenteredString(copy, this.text, this.getBoundingBox(), copy.getFont());
    }

    public void setHovered(boolean hovered) {
        isHovered = hovered;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public void onPressed(){
        this.pressable.onPressed(this);
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public interface IBaseButtonPressable {
        /**
         * called when the button is pressed
         *
         * @param b the button that was pressed
         */
        void onPressed(BaseButton b);
    }
}

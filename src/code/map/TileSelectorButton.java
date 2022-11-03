package code.map;

import code.helper.BaseButton;
import code.helper.SquareGameObject;
import code.helper.TextUtils;

import java.awt.*;

public class TileSelectorButton extends BaseButton {
    private final Tile.Type type;
    private boolean isSelected;
    private IPressable pressable;

    public TileSelectorButton(double x, double y, double width, double height, Tile.Type type, IPressable pressable) {
        super(x, y, width, height, Color.WHITE, null);
        this.type = type;
        this.pressable = pressable;
    }

    @Override
    public void draw(Graphics g) {
        Graphics graphics = g.create();
        if (type.getColor() != null) {
            graphics.setColor(type.getColor());
            fillRect(graphics);
        } else if (type.getImg() != null) {
            drawImage(graphics, type.getImg());
        }
        if(type == Tile.Type.SPAWN_TILE){
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
            TextUtils.drawCenteredString(graphics, "SPAWN", this.getBoundingBox(), graphics.getFont());
        }
        if (isSelected) {
            graphics.setColor(new Color(255, 255, 255, 100));
            fillRect(graphics);
        } else if (isHovered()) {
            graphics.setColor(new Color(255, 255, 255, 100));
            fillRect(graphics);
        }
    }

    @Override
    public void onPressed() {
        if (isSelected) {
            setSelected(false);
        } else {
            setSelected(true);
            this.pressable.pressed(this);
        }
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Tile.Type getType() {
        return type;
    }

    public static interface IPressable {
        void pressed(TileSelectorButton button);
    }
}

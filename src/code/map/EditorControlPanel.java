package code.map;

import code.helper.BaseButton;
import code.helper.FileHelper;
import code.helper.SquareGameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class EditorControlPanel extends SquareGameObject implements MouseMotionListener, MouseListener, KeyListener {
    private List<BaseButton> buttons = new ArrayList<>();
    private BaseButton selectedButton;
    private int buttonsX, buttonsY;
    private int buttonsMarginY = 10, buttonsMarginX = 5;
    private Tile.Type selectedType = Tile.Type.GRASS;

    public EditorControlPanel(double x, double y, double width, double height) {
        super(x, y, width, height);
        for (Tile.Type t : Tile.Type.values()) {
            addButton(new TileSelectorButton(0, 0, 0, 0, t, this::onPress));
        }
    }

    private void onPress(TileSelectorButton button) {
        this.selectedType = button.getType();
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        fillRect(g);
        for (BaseButton b : this.buttons) {
            b.draw(g);
        }
    }

    public Tile.Type getSelectedType() {
        return selectedType;
    }

    protected void addButton(BaseButton button) {
        if (buttons.size() == 0) {
            buttonsX = buttonsMarginX;
            buttonsY = buttonsMarginY;
        } else {
            double tempButtonsX = buttonsX + buttonsMarginX + 2 * Editor.TILE_SIZE_DEFAULT;
            if (tempButtonsX > getWidth()) {
                buttonsX = buttonsMarginX;
                buttonsY += 2 * Editor.TILE_SIZE_DEFAULT + buttonsMarginY;
            } else {
                buttonsX += buttonsMarginX + 2 * Editor.TILE_SIZE_DEFAULT;
            }
        }
        button.setBounds(getBoundingBox().x + buttonsX, buttonsY, 2 * Editor.TILE_SIZE_DEFAULT, 2 * Editor.TILE_SIZE_DEFAULT);
        buttons.add(button);
    }

    /**
     * @param b the button must contain the height the button should have later on
     */
    public void addButtonToBottom(BaseButton b) {
        double height = b.getHeight();
        double y = this.getY() + this.getHeight() - height;
        for (int i = this.buttons.size() - 1; i >= 0; i--) {
            if (this.buttons.get(i).getY() == y || this.buttons.get(i).isInside(10, (int) y)) {
                y -= b.getHeight();
            }
        }
        b.setBounds(this.getX(), y, this.getWidth(), b.getHeight());
        this.buttons.add(b);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (BaseButton button : this.buttons) {
            if (button.isInside(e.getX(), e.getY())) {
                button.onPressed();
                if (selectedButton != null) {
                    selectedButton.setSelected(false);
                }
                this.selectedButton = button;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (BaseButton button : this.buttons) {
            if (button.isInside(e.getX(), e.getY())) {
                button.setHovered(true);
            } else
                button.setHovered(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

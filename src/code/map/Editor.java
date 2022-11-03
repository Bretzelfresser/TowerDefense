package code.map;

import code.core.TowerDefense;
import code.helper.AbstractGameState;

import java.awt.*;
import java.awt.event.*;

public class Editor extends AbstractGameState implements MouseMotionListener, MouseListener, KeyListener, MouseWheelListener {

    public static final int TILE_SIZE_DEFAULT = 32;
    private int tileSize = TILE_SIZE_DEFAULT;
    private TileMap map;
    private EditorControlPanel panel;

    public Editor(){
        setPreferredSize(new Dimension(TowerDefense.BASE_WIDTH, TowerDefense.BASE_HEIGHT));
        map = new TileMap(0,0, tileSize);
        panel = new EditorControlPanel(map.getWidth(), 0, TowerDefense.BASE_WIDTH - map.getWidth(), TowerDefense.BASE_HEIGHT);
        setName("Editor");
        addMouseMotionListener(this.map);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        addMouseMotionListener(this.panel);
        addMouseListener(this.panel);
        MouseDragger d = new MouseDragger();
        addMouseListener(d);
        addMouseMotionListener(d);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        map.draw(g);
        panel.draw(g);
    }

    @Override
    public void setToGameState(TowerDefense mainGame) {
        mainGame.getMainFrame().getContentPane().add(this);
    }

    @Override
    public void removeGameState(TowerDefense mainGame) {
        mainGame.getMainFrame().getContentPane().remove(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.map.isInside(e.getX(), e.getY())){
            this.map.getTile(e.getX(), e.getY()).setType(this.panel.getSelectedType());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        /*
        if (this.tileSize + e.getWheelRotation() > 0){
            this.tileSize += e.getWheelRotation();
            this.map.setTileSize(tileSize);
        }

         */
    }

    private class MouseDragger extends MouseAdapter implements MouseMotionListener {
        private Point start;

        @Override
        public void mouseReleased(MouseEvent e) {
            if (map.isInside(e.getX(), e.getY())) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    map.doInArea(start, map.getChunkCoordinates(e.getX(), e.getY()), t -> t.setType(panel.getSelectedType()));
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    map.forAll(t -> t.setHovered(false));
                }
                super.mouseReleased(e);
            }else {
                map.forAll(t -> t.setHovered(false));
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (map.isInside(e.getX(), e.getY())) {
                start = map.getChunkCoordinates(e.getX(), e.getY());
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (map.isInside(e.getX(), e.getY())) {
                map.forAll(t -> t.setHovered(false));
                map.doInArea(start, map.getChunkCoordinates(e.getX(), e.getY()), t -> t.setHovered(true));
            }else {
                map.forAll(t -> t.setHovered(false));
            }
        }
    }
}

package code.core;

import code.helper.AbstractGameState;
import code.helper.FileHelper;
import code.helper.IGameState;
import code.map.TileMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static code.core.TowerDefense.*;

public class Game extends AbstractGameState implements KeyListener {

    private int tileSize = 32;
    private TileMap map;

    public Game() {
        this.setPreferredSize(new Dimension(BASE_WIDTH, BASE_HEIGHT));
        setBackground(Color.RED);
        this.map = new TileMap(0,0, FileHelper.serialize("level1"), tileSize);
        addMouseMotionListener(this.map);
        setName("Game");

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.map != null) {
            this.map.draw(g);
        }
    }

    public int getTileSize() {
        return tileSize;
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

    public TileMap getMap() {
        return map;
    }

    @Override
    public void setToGameState(TowerDefense mainGame) {
        mainGame.getMainFrame().getContentPane().add(this);
        mainGame.getMainFrame().addKeyListener(this);
    }

    @Override
    public void removeGameState(TowerDefense mainGame) {
        mainGame.getMainFrame().getContentPane().remove(this);
        mainGame.getMainFrame().removeKeyListener(this);
    }
}

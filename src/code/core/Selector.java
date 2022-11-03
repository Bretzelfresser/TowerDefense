package code.core;

import code.helper.AbstractGameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Selector extends AbstractGameState {

    private final TowerDefense defense;

    public Selector(TowerDefense defense) {
        setPreferredSize(new Dimension(TowerDefense.BASE_WIDTH, TowerDefense.BASE_HEIGHT));
        this.defense = defense;
        setLayout(new BorderLayout(8, 8));
        JButton play = new JButton("PLAY");
        play.addActionListener(this::setGame);
        this.add(play, BorderLayout.NORTH);
        JButton editor = new JButton("EDITOR");
        editor.addActionListener(this::setEditor);
        this.add(editor, BorderLayout.CENTER);
        JButton exit = new JButton("EXIT");
        exit.addActionListener(this::exit);
        this.add(exit, BorderLayout.SOUTH);

        setName("Selector");

    }

    private void setGame(ActionEvent e){
        this.defense.setState(TowerDefense.GameState.GAME);
    }

    private void setEditor(ActionEvent e){
        this.defense.setState(TowerDefense.GameState.EDITOR);
    }

    private void exit(ActionEvent e){
        System.exit(0);
    }

    @Override
    public void setToGameState(TowerDefense mainGame) {
        mainGame.getMainFrame().getContentPane().add(this);
    }

    @Override
    public void removeGameState(TowerDefense mainGame) {
        mainGame.getMainFrame().getContentPane().remove(this);
    }
}

package code.core;

import code.helper.AbstractGameState;
import code.helper.IGameState;
import code.map.Editor;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TowerDefense {
    public static final int BASE_WIDTH = 1000, BASE_HEIGHT = 640;
    public static final String NAME = "Tower Defense";
    private long prevTimeFps = System.currentTimeMillis();
    private int fpsCounter, fps;
    private JFrame mainFrame;
    private Game game = new Game();
    private Thread mainThread;
    private Editor editor = new Editor();
    private Selector selector = new Selector(this);
    private GameState state = GameState.SELECTOR;
    private Map<GameState, AbstractGameState> states = Map.of(GameState.GAME, game, GameState.EDITOR, editor, GameState.SELECTOR, selector);

    public TowerDefense(){
        mainFrame = new JFrame(NAME);
        mainFrame.getContentPane().setPreferredSize(new Dimension(BASE_WIDTH, BASE_HEIGHT));
        mainFrame.getContentPane().add(selector);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainThread = new Thread(this::run);
        mainThread.start();
    }

    public void run() {
        while (true) {
           mainFrame.repaint();
            fpsCounter++;
            if (prevTimeFps + 1000 <= System.currentTimeMillis()){
                prevTimeFps = System.currentTimeMillis();
                fps = fpsCounter;
                fpsCounter = 0;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setState(GameState state) {
        System.out.println(this.state.name());
        states.get(this.state).removeGameState(this);
        this.state = state;
        states.get(state).setToGameState(this);
        mainFrame.getContentPane().revalidate();
        System.out.println(Arrays.toString(Arrays.stream(this.mainFrame.getContentPane().getComponents()).map(Component::getName).collect(Collectors.toList()).toArray()));
        mainFrame.repaint();

    }

    public int getFps() {
        return fps;
    }

    public double getDeltaTime(){
        return 1d/ (double)this.fps;
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public enum GameState{
        GAME,
        EDITOR,
        SELECTOR;
    }
}

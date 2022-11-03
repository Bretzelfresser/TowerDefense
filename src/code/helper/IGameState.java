package code.helper;

import code.core.TowerDefense;

public interface IGameState {

    /**
     * this is called every time the game switches to this game State
     */
    void setToGameState(TowerDefense mainGame);

    /**
     * called every time when the game state is switched from this to another
     */
    void removeGameState(TowerDefense mainGame);
}

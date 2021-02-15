package com.bsherwin.state;

import com.bsherwin.Game;

import java.awt.Graphics;

public abstract class State {
    //SateManager
    private static State currentState = null;



    public static void setState(State state){
        currentState = state;
    }
    public static State getState(){
        return currentState;
    }

    //Classes
    protected Game game;

    public State(Game game){
        this.game = game;
    }
    public abstract void tick();

    public abstract void render(Graphics g);
}

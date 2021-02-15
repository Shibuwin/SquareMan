package com.bsherwin;

import com.bsherwin.input.KeyManager;
import com.bsherwin.state.GameState;
import com.bsherwin.state.MenuState;
import com.bsherwin.state.SettingsState;
import com.bsherwin.state.State;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game implements Runnable {

    private Display display;

    public int width, height;
    public String title;
    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

    //States
    private State gameState;
    private State menuState;
    private State settingsState;

    //Input Keys
    private KeyManager keyManager;


    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        keyManager = new KeyManager();
    }

    private void init(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();

        gameState = new GameState(this);
        menuState = new MenuState(this);
        settingsState = new SettingsState(this);
        State.setState(gameState);
    }

    private void tick(){
        keyManager.tick();
        if(State.getState() !=null)
            State.getState().tick();
    }
    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){ //Check if theres a buffer set
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();
        //clear the screen
        g.clearRect(0,0, width, height);

        //Drawing begins here

        if(State.getState() !=null)
            State.getState().render(g);

        //drawing ends here
        bs.show();
        g.dispose();

    }
    public void run(){
        //initalize everything
        init();
        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;


        //run game loop
        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >=1) {


                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >=1000000000){
                System.out.println("Ticks and Frames: " + ticks);//outputs FPS in console
                ticks = 0;
                timer = 0;
            }
        }
        stop();


    }
    public KeyManager getKeyManager(){
        return keyManager;
    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

package com.bsherwin.state;

import com.bsherwin.Assets;
import com.bsherwin.Game;
import com.bsherwin.entities.creatures.Player;
import com.bsherwin.tiles.Tile;

import java.awt.*;

public class GameState extends State {

    private Player player;

    public GameState(Game game){
        super(game);
        player = new Player(game, 100,100);
    }

    @Override
    public void tick() {
    player.tick();
    }

    @Override
    public void render(Graphics g) {
        player.render(g);

    }
}


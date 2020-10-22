package com.gsc.bm.server.stomp;

import com.gsc.bm.server.model.Game;
import com.gsc.bm.server.model.Player;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    // TODO for now a single queue will do
    private List<Player> playersQueue = new ArrayList<>();

    @MessageMapping("/game/join")
    @SendTo("/topic/game/ready")
    public Game joinGame(Player player) throws InterruptedException {

        System.out.println("connection is coming");

        playersQueue.add(player);
        while (true) {
            if (playersQueue.size() >= 2) {
                Game game = new Game(playersQueue);
                playersQueue = new ArrayList<>();
                return game;
            }
            Thread.sleep(1000);
        }
    }
}

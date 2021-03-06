package Duelyst.model.Battle;


import Duelyst.model.Player;
import Duelyst.model.Cell;
import Duelyst.model.Deck;
import Duelyst.model.Hand;
import Duelyst.model.Item.CollectibleItem.CollectibleItem;

import java.util.ArrayList;
import java.util.Random;

public class HeroBattle extends Battle {
    private boolean playWithAI;
    private Player secondPlayer;
    private Hand secondPlayerHand = new Hand();

    {
        Random random = new Random();
        getMap().add(new ArrayList<>());
        getMap().add(new ArrayList<>());
        getMap().add(new ArrayList<>());
        getMap().add(new ArrayList<>());
        getMap().add(new ArrayList<>());
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (random.nextInt(100) < 25)
                    getMap().get(i).add(new Cell(i, j, 0, random.nextInt(CollectibleItem.getCollectibleItems().size())));
                else
                    getMap().get(i).add(new Cell(i, j, 0, -1));

            }
        }

    }

    public String getType() {
        return "HeroBattle";
    }

    public HeroBattle(Deck opponentDeck, Deck myDeck, Player player, int reward) {
        super(player, myDeck, opponentDeck);
        this.playWithAI = true;
        Player account = new Player(1);
        account.setMainDeck(opponentDeck);
        this.secondPlayer = account;
        this.setReward(reward);
            if (this.getMap().get(2).get(8).getCollectibleItem() != null) {
            this.getSecondPlayerCollectibleItem().add(this.getMap().get(2).get(8).getCollectibleItem());
            this.increamentTurn();
            this.getSecondPlayerCollectibleItem().get(this.getSecondPlayerCollectibleItem().size() - 1).cardIdGenerator(this);
            this.decreamentTurn();
            this.getMap().get(2).get(8).setCollectibleItem(null);
        } else if (this.getMap().get(2).get(0).getCollectibleItem() != null) {
            this.getFirstPlayerCollectibleItem().add(this.getMap().get(2).get(0).getCollectibleItem());
            this.getFirstPlayerCollectibleItem().get(this.getFirstPlayerCollectibleItem().size() - 1).cardIdGenerator(this);


            this.getMap().get(2).get(0).setCollectibleItem(null);
        }
    }

    public HeroBattle(Deck opponentDeck, Deck myDeck, Player player, Player player2, int reward) {
        super(player, myDeck, opponentDeck);
        this.secondPlayer = player2;
        this.playWithAI = false;
        this.setReward(reward);
        this.getMap().get(2).get(0).getHero().setCardId(player.getUserName() + "_" + this.getMap().get(2).get(0).getHero().getName() + "_" + (1));
        this.getMap().get(2).get(8).getHero().setCardId(player2.getUserName() + "_" + this.getMap().get(2).get(8).getHero().getName() + "_" + (1));

        this.getSecondPlayerInGameCards().add(this.getMap().get(2).get(8).getHero());
        this.getFirstPlayerInGameCards().add(this.getMap().get(2).get(0).getHero());
        if (this.getMap().get(2).get(8).getCollectibleItem() != null) {
            this.getSecondPlayerCollectibleItem().add(this.getMap().get(2).get(8).getCollectibleItem());
            this.increamentTurn();
            this.getSecondPlayerCollectibleItem().get(this.getSecondPlayerCollectibleItem().size() - 1).cardIdGenerator(this);
            this.decreamentTurn();
            this.getMap().get(2).get(8).setCollectibleItem(null);
        } else if (this.getMap().get(2).get(0).getCollectibleItem() != null) {
            this.getFirstPlayerCollectibleItem().add(this.getMap().get(2).get(0).getCollectibleItem());
            this.getFirstPlayerCollectibleItem().get(this.getFirstPlayerCollectibleItem().size() - 1).cardIdGenerator(this);


            this.getMap().get(2).get(0).setCollectibleItem(null);
        }
    }

    public void showDetailedInfo() {

    }

    @Override
    public boolean isPlayWithAI() {
        return playWithAI;
    }

    @Override
    public Hand getSecondPlayerHand() {
        return secondPlayerHand;
    }


    @Override
    public Player getSecondPlayer() {
        return secondPlayer;
    }
}

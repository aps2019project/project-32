package com.company.models;

import com.company.controller.Controller;
import com.company.controller.Exceptions.CantMove;
import com.company.controller.Exceptions.InvalidTargetException;
import com.company.controller.Menus.ShopMenu;
import com.company.controller.Menus.battlemenus.BattleMenu;
import com.company.controller.Menus.battlemenus.WarriorSelectMenu;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.Passive;
import com.company.models.widget.cards.spells.Spell;
import com.company.view.View;

import java.security.SecureRandom;
import java.util.ArrayList;

public class AIPlayer
{
    private final static AIPlayer instance = new AIPlayer();
    private ArrayList<String> orders = new ArrayList<>();
    private Player AIPlayer;
    private SecureRandom random = new SecureRandom();

    private AIPlayer()
    {
    }

    public Player getAIPlayer()
    {
        return AIPlayer;
    }

    public static AIPlayer getInstance()
    {
        return instance;
    }

    //

    public void doOrder()
    {
        boolean valid = false;
        int order;
        while (!valid)
        {
            order = Math.abs(random.nextInt() % 3);
            try
            {
                switch (order % 3)
                {
                    case 0:
                        randomInsertCard();
                        break;
                    case 1:
                        randomMoveWarrior();
                        break;
                    case 2:
                        randomAttack();
                        break;
                }
                valid = true;
                View.getInstance().show("AI Turn Has Ended!\nNow Your Turn!\n");
                Controller.getInstance().changeCurrentMenuTo(BattleMenu.getInstance());
            }
            catch (Exception ex)
            {
                valid = false;
            }
        }
    }

    private void randomInsertCard() throws Exception
    {
        String cardName = AIPlayer.getPlayerHand().getRandomCardNameFromHand();
        Position randomPosition = Position.getRandomFreePosition(Warrior.class);
        if (AIPlayer.getPlayerHand().getCardByName(cardName) instanceof Minion)
            BattleMenu.getInstance().insertCard
                    (String.format("Insert %s in %d %d", cardName, randomPosition.col, randomPosition.row));
        else
            throw new Exception();
    }

    private void randomMoveWarrior() throws InvalidTargetException, CantMove
    {
        int randomNumberI;
        int randomNumberJ;
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 5; j++)
                if (BattleMenu.getInstance().getCurrentBattle().getBattleMap().getWarriorsOnMap()[j][i].getOwnerPlayer().equals(AIPlayer))
                {
                    randomNumberI = random.nextInt() % 2;
                    randomNumberJ = random.nextInt() % 2;
                    WarriorSelectMenu.getInstance().moveCard
                            (String.format("Move to %d %d", i + randomNumberI, j + randomNumberJ));
                }
    }

    private void randomAttack() throws Exception
    {
//        Position randomPosition = Position.getRandomCaptured();
//        Warrior warrior = BattleMenu.getInstance().getCurrentBattle().
//                getBattleMap().getWarriorsOnMap()[randomPosition.row][randomPosition.col];
//
//        int warriorID = warrior.getID();
//
//        BattleMenu.getInstance().selectActions(warriorID);
//
//        WarriorSelectMenu.getInstance().attack
//                (String.format("Attack %d %d", randomPosition.col, randomPosition.row));

    }

    public void setAI() throws CloneNotSupportedException
    {
        ArrayList<Spell> spells = new ArrayList<>();
        spells.add(((Spell) ((Spell) ShopMenu.getInstance().getShopCards().get(0)).clone()));
        spells.add(((Spell) ((Spell) ShopMenu.getInstance().getShopCards().get(4)).clone()));
        spells.add(((Spell) ((Spell) ShopMenu.getInstance().getShopCards().get(6)).clone()));
        spells.add(((Spell) ((Spell) ShopMenu.getInstance().getShopCards().get(8)).clone()));
        spells.add(((Spell) ((Spell) ShopMenu.getInstance().getShopCards().get(9)).clone()));
        spells.add(((Spell) ((Spell) ShopMenu.getInstance().getShopCards().get(14)).clone()));
        spells.add(((Spell) ((Spell) ShopMenu.getInstance().getShopCards().get(17)).clone()));
        spells.add(((Spell) ((Spell) ShopMenu.getInstance().getShopCards().get(18)).clone()));
        spells.add(((Spell) ((Spell) ShopMenu.getInstance().getShopCards().get(21)).clone()));

        ArrayList<Minion> minions = new ArrayList<>();
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(22)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(23)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(24)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(25)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(26)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(27)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(29)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(30)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(31)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(32)).clone()));
        minions.add(((Minion) ((Minion) ShopMenu.getInstance().getShopCards().get(33)).clone()));

        Hero hero = ((Hero) ((Hero) ShopMenu.getInstance().searchCard("DiveSepid")).clone());

        Passive passive = ((Passive) ((Passive) ShopMenu.getInstance().searchCard("KamaneDamool")).clone());


        Player player = new Player();
        Player.Deck deck = player.new Deck(hero, spells, minions, passive);
        player.setMainDeck(deck);

        for (Card card : player.getMainDeck().getCards())
            card.setOwnerPlayer(player);

        player.getMainDeck().getHero().setOwnerPlayer(player);
        player.getMainDeck().getPassiveItem().setOwnerPlayer(player);

        AIPlayer = player;
    }
}

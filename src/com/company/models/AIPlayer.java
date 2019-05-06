package com.company.models;

import com.company.controller.Exceptions.CardNotFound;
import com.company.controller.Exceptions.GameIsNotOver;
import com.company.controller.Exceptions.InvalidPosition;
import com.company.controller.Menus.ShopMenu;
import com.company.controller.Menus.battlemenus.BattleMenu;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.spells.Passive;
import com.company.models.widget.cards.spells.Spell;

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
        int order = random.nextInt(20);
        boolean valid = false;
        while (!valid)
        {
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
                BattleMenu.getInstance().getCurrentBattle().getTurnHandler().changeTurn();
            } catch (Exception e)
            {
                valid = false;
                order++;
            }
        }
    }

    private void randomInsertCard() throws CardNotFound, InvalidPosition, GameIsNotOver
    {
        String cardName = AIPlayer.getPlayerHand().getRandomCardNameFromHand();
        Position randomPosition = Position.getRandomFreePosition();
        BattleMenu.getInstance().insertCard(String.format("Insert %s + in %d %d", cardName, randomPosition.row, randomPosition.col));

    }

    private void randomMoveWarrior()
    {

    }

    private void randomAttack()
    {

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

        Hero hero = ((Hero) ShopMenu.getInstance().searchCard("DiveSepid"));

        Passive passive = ((Passive) ShopMenu.getInstance().searchCard("KamaneDamool"));

        Player player = new Player();
        Player.Deck deck = player.new Deck(hero, spells, minions, passive);
        player.setMainDeck(deck);
        AIPlayer = player;
    }
}

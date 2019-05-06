package com.company.models;

import com.company.controller.Exceptions.CardNotFound;
import com.company.controller.Exceptions.GameIsNotOver;
import com.company.controller.Exceptions.InvalidPosition;
import com.company.controller.Menus.battlemenus.BattleMenu;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.spells.Spell;

import java.security.SecureRandom;
import java.util.ArrayList;

public class AIPlayer
{
    private final static AIPlayer aiPlayer = new AIPlayer();
    private ArrayList<String> orders = new ArrayList<>();
    private static Player AIPlayer;
    private SecureRandom random = new SecureRandom();

    private AIPlayer(){}


    public static AIPlayer getInstance(){
        return aiPlayer;
    }

    public static void setAIPlayerWith(Hero hero, ArrayList<Spell> spells, ArrayList<Minion> minions, Spell passiveSpell)
    {
        Player player = new Player();
        Player.Deck deck = player.new Deck(hero,spells,minions,passiveSpell);
        player.setMainDeck(deck);
        AIPlayer = player;
    }



    public static Player getAIPlayer()
    {
        return AIPlayer;
    }

    public void doOrder(){
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
            }catch (Exception e){
                valid = false;
                order++;
            }
        }
    }

    private static   void randomInsertCard() throws CardNotFound, InvalidPosition, GameIsNotOver
    {
        String cardName = AIPlayer.getPlayerHand().getRandomCardNameFromHand();
        Position randomPosition = Position.getRandomFreePosition();
        BattleMenu.getInstance().insertCard(String.format("Insert %s + in %d %d",cardName,randomPosition.row,randomPosition.col));

    }
    private void randomMoveWarrior()
    {

    }
    private void randomAttack()
    {

    }
}

package com.company.models.battle;

import com.company.models.Position;
import com.company.models.TimeHandler;
import com.company.models.Buff;
import com.company.models.Player;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Spell;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.items.Collectible;
import com.company.models.widget.items.Item;

import java.security.SecureRandom;
import java.util.ArrayList;

public abstract class Battle
{
    public Battle(Player firstPlayer, Player secondPlayer)
    {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }

    protected BattleMode battleMode;
    protected Map battleMap = new Map();
    protected TurnHandler battleTurnHandler = new TurnHandler();
    protected GameResault gameResault = GameResault.UnCertain;
    protected Player firstPlayer;
    protected Player secondPlayer;
    private SecureRandom randomMaker = new SecureRandom();

    public class Map
    {
        Map()
        {
            cellEffects.add(new Poison());
            cellEffects.add(new Fiery());
            cellEffects.add(new Holy());
            for (Position cellEffectsPosition : cellEffectsPositions)
                cellEffectsPosition = getRandomPosition();

            addCollectibleItemOnMapRandomise();
        }

        protected Widget[][] map = new Widget[5][9];

        private ArrayList<Buff> cellEffects = new ArrayList<>();
        private ArrayList<Position> cellEffectsPositions = new ArrayList<>();

        public class Poison extends Buff
        {
            Poison()
            {
                super();
            }

            @Override
            public void doEffect(Position... positions)
            {
                Widget cardInCellPosition = map[positions[0].row][positions[0].col];
                if (cardInCellPosition instanceof Warrior)
                {
                    ((Warrior) cardInCellPosition).decreaseHealth(1);
                    checkDeadActions(((Warrior) cardInCellPosition));
                }
            }
        }

        public class Fiery extends Buff
        {
            Fiery()
            {
                super();
            }

            @Override
            public void doEffect(Position... positions)
            {
                Widget cardInCellPosition = map[positions[0].row][positions[0].col];
                if (cardInCellPosition instanceof Warrior)
                {
                    ((Warrior) cardInCellPosition).decreaseHealth(2);
                    checkDeadActions(((Warrior) cardInCellPosition));
                }
            }
        }

        public class Holy extends Buff
        {
            public Holy()
            {
                super();
            }

            @Override
            public void doEffect(Position... positions)
            {
                Widget cardInCellPosition = map[positions[0].row][positions[0].col];
                if (cardInCellPosition instanceof Warrior)
                {

                }
            }
        }

        public Position getRandomPosition()
        {
            int row;
            int col;
            while (true)
            {
                row = randomMaker.nextInt(5);
                col = randomMaker.nextInt(9);
                if (map[row][col] == null)
                    return new Position(row, col);
            }
        }

        public void addCollectibleItemOnMapRandomise()
        {
            ArrayList<Collectible> collectibles = new ArrayList<>(5);
            // make and add 5 collectable item to collectibles
            ArrayList<Position> randomPositions = new ArrayList<>(5);
            for (int i = 0; i < 5; i++)
                randomPositions.add(getRandomPosition());

            // add to map 5 collectibles
        }

        public void removeDeadCardFromMap(Card intendedDeadCard)
        {
            for (Widget[] cards : map)
                for (Widget card : cards)
                    if (intendedDeadCard.equals(card))
                        card = null;
        }

        public Widget selectCard(int cardID)
        {
            for (Widget[] widgets : map)
                for (Widget widget : widgets)
                    if(widget.getID()==cardID)
                        return widget;

            return null;
        }

        public String toShowMinionInMap()
        {
            return null; // for in map and show all cards
        }
    }

    public class TurnHandler
    {
        protected Player playerHasTurn;
        protected Turn turn;
        protected int turnNumber;

        public void changeCoolDownRemaining()
        {
            firstPlayer.getMainDeck().getHero().getSpecialSpell().decreaseCoolDownRemaining();
            secondPlayer.getMainDeck().getHero().getSpecialSpell().decreaseCoolDownRemaining();
        }

        public void changeTurn()
        {
            if (turn == Turn.fristPlayerTurn)
            {
                turn = Turn.secondPlayerTurn;
                playerHasTurn = secondPlayer;
                secondPlayer.setPlayerManaSpace(secondPlayer.getPlayerManaSpace() + 1);
                secondPlayer.setPlayerCurrentMana(secondPlayer.getPlayerManaSpace());
            }
            else
            {
                turn = Turn.fristPlayerTurn;
                playerHasTurn = firstPlayer;
                firstPlayer.setPlayerManaSpace(firstPlayer.getPlayerManaSpace() + 1);
                firstPlayer.setPlayerCurrentMana(firstPlayer.getPlayerManaSpace());
            }

            changeCoolDownRemaining();
        }

        public Player getPlayerHasTurn()
        {
            return playerHasTurn;
        }
    }

    public void beginningBattleActions()
    {
        if (!checkBothPlayersDeckValidity())
        {
            //do exception
        }
        firstPlayer.setCopiedMainDeck(firstPlayer.getMainDeck().getCards());
        secondPlayer.setCopiedMainDeck(secondPlayer.getMainDeck().getCards());
        battleTurnHandler.turn = Turn.fristPlayerTurn;
        battleTurnHandler.playerHasTurn = firstPlayer;
        setHeroPositionInBeginning();
        setBothPlayersHandInBeginning();
    }

    private boolean checkBothPlayersDeckValidity()
    {
        return firstPlayer.getMainDeck().isValidDeck() && secondPlayer.getMainDeck().isValidDeck();
    }

    private void setTurnInBeginning()
    {
        battleTurnHandler.turn = Turn.fristPlayerTurn;
        battleTurnHandler.playerHasTurn = firstPlayer;
    }

    private void setHeroPositionInBeginning()
    {
        battleMap.map[2][0] = firstPlayer.getMainDeck().getHero();
        battleMap.map[2][9] = secondPlayer.getMainDeck().getHero();
    }

    private void setBothPlayersHandInBeginning()
    {
        firstPlayer.getPlayerHand().makeRandomiseHand();
        secondPlayer.getPlayerHand().makeRandomiseHand();
    }

    protected void addBattleToBattleHistories(GameResault gameResault)
    {
        if (gameResault == GameResault.FristPlayerWin)
        {
            firstPlayer.addGameResultToBattleHistories
                    (secondPlayer, true, TimeHandler.getInstance().getCurrentDate());
            secondPlayer.addGameResultToBattleHistories
                    (firstPlayer, false, TimeHandler.getInstance().getCurrentDate());
        }
        else if (gameResault == GameResault.SecondPlayerWin)
        {
            secondPlayer.addGameResultToBattleHistories
                    (firstPlayer, true, TimeHandler.getInstance().getCurrentDate());
            firstPlayer.addGameResultToBattleHistories
                    (secondPlayer, false, TimeHandler.getInstance().getCurrentDate());
        }
    }


    public void currentPlayerActions()
    {

    }

    public String toShowEndGameDetails()
    {
        return new String(String.format("a"));
    }

    public abstract void checkBattleResult();

    public void putSpellCardOnMap(Spell spell, Position... positions)
    {
        if (spell.getOwnerPlayer().getPlayerCurrentMana() >= spell.getManaCost())
        {
            spell.doEffect(positions);
            spell.getOwnerPlayer().decreaseMana(spell.getManaCost());
        }
        else
        {
        }
        // do exception no mana
    }

    public void doWarriorSpell(Warrior warrior, Position... positions)
    {
        if (warrior.getOwnerPlayer().getPlayerCurrentMana() >= warrior.getSpecialSpell().getManaCost()
                && warrior.getSpecialSpell().getCoolDownRemaining() == 0)
        {
            warrior.getSpecialSpell().doEffect(positions);
            warrior.getOwnerPlayer().decreaseMana(warrior.getSpecialSpell().getManaCost());
        }
        else if (true) // if no mana
        {
        }
        // do exception no mana
        else if (true) // if in cooldown
        {
        }
        // do exception no cooldown
    }

    public void moveWarriorOptions(Warrior intendedWarrior, Position sourcePosition, Position destinationPosition)
    {
        if (intendedWarrior.canMove())
        {
            intendedWarrior.moveTiredAffect();
            battleMap.map[sourcePosition.row][sourcePosition.col] = null;

            Widget widget = battleMap.map[destinationPosition.row][destinationPosition.col];

            if (widget instanceof Item)
            {
                if (widget != null)
                    collect(intendedWarrior, destinationPosition);
                widget = intendedWarrior;
            }

            if (widget instanceof Warrior)
            {
                if (!widget.getOwnerPlayer().equals(intendedWarrior.getOwnerPlayer()))
                    attackActions(intendedWarrior, ((Warrior) widget));
                else
                {
                    //dare be yar hamle mikone exception
                }
            }
        }
        else
        {
        }//do Exception its tired
    }

    public void collect(Card intendedCard, Position destinationPosition)
    {
        Widget widget = battleMap.map[destinationPosition.row][destinationPosition.col];
        if (widget != null && widget instanceof Collectible)
        {
            intendedCard.getOwnerPlayer().getPlayerHand().getCollectedItems().add(((Collectible) widget));
            widget.setOwnerPlayer(intendedCard.getOwnerPlayer());
            battleMap.map[destinationPosition.row][destinationPosition.col] = null;
        }
        else
        {
        }
        //do exception cant collect

    }

    private void attackActions(Warrior attacker, Warrior defender)
    {
        if (attacker.canAttack())
        {
            attacker.attack(defender);
            attacker.attackTiredAffect();
            defender.defend(attacker);
            checkDeadActions(attacker, defender);
        }
        else
        {
        } // doException
    }

    public void checkDeadActions(Warrior... warriors)
    {
        for (Warrior warrior : warriors)
            if (warrior.isDead())
            {
                warrior.getOwnerPlayer().getGraveYard().getGraveYardList().add(warrior);
                battleMap.removeDeadCardFromMap(warrior);
            }
    }

    public BattleMode getBattleMode()
    {
        return battleMode;
    }

    public void setBattleMode(BattleMode battleMode)
    {
        this.battleMode = battleMode;
    }

    public Map getBattleMap()
    {
        return battleMap;
    }

    public void setBattleMap(Map battleMap)
    {
        this.battleMap = battleMap;
    }

    public TurnHandler getBattleTurnHandler()
    {
        return battleTurnHandler;
    }

    public void setBattleTurnHandler(TurnHandler battleTurnHandler)
    {
        this.battleTurnHandler = battleTurnHandler;
    }

    public GameResault getGameResault()
    {
        return gameResault;
    }

    public void setGameResault(GameResault gameResault)
    {
        this.gameResault = gameResault;
    }

    public Player getFirstPlayer()
    {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer)
    {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer()
    {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer)
    {
        this.secondPlayer = secondPlayer;
    }

    public abstract String toShowGameInfo();
}

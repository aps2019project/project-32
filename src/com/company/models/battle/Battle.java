package com.company.models.battle;

import com.company.controller.Controller;
import com.company.controller.Exceptions.*;
import com.company.controller.Menus.ShopMenu;
import com.company.controller.Menus.battlemenus.EndGameMenu;
import com.company.models.AIPlayer;
import com.company.models.Player;
import com.company.models.Position;
import com.company.models.TimeHandler;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.ActiveTime;
import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.Type;
import com.company.models.widget.cards.spells.effects.Effectable;

import java.security.SecureRandom;

public abstract class Battle
{
    protected Battle(Player firstPlayer, Player secondPlayer, int winnerPrize) throws
            InvalidDeck, CloneNotSupportedException
    {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winnerPrize = winnerPrize;
        initialiseBattle();
        firstPlayer.addPassiveOnPlayerCards();
        secondPlayer.addPassiveOnPlayerCards();
    }

    protected BattleMode battleMode;
    protected Map battleMap = new Map();
    protected TurnHandler turnHandler = new TurnHandler();
    protected GameResault gameResault = GameResault.UnCertain;
    protected Player firstPlayer;
    protected Player secondPlayer;
    private SecureRandom randomMaker = new SecureRandom();
    protected int winnerPrize;

    public class Map
    {
        Map()
        {
        }

        protected Warrior[][] warriorsOnMap = new Warrior[5][9];
        protected Spell[][] spellsAndCollectibleOnMap = new Spell[5][9];

        public Warrior[][] getWarriorsOnMap()
        {
            return warriorsOnMap;
        }

        public Spell[][] getSpellsAndCollectibleOnMap()
        {
            return spellsAndCollectibleOnMap;
        }

        public void addCollectibleItemOnMapRandomise(int counter)
        {
            Position randomPosition = Position.getRandomFreePosition(Spell.class);
            spellsAndCollectibleOnMap[randomPosition.row][randomPosition.col]
                    = ShopMenu.getInstance().getCollectibleCards().get(counter);
        }

        public void removeDeadCardFromMap(Card intendedDeadCard)
        {
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 9; j++)
                    if (intendedDeadCard.equals(warriorsOnMap[i][j]))
                        warriorsOnMap[i][j] = null;
        }

        public Warrior selectCard(int cardID)
        {
            for (Warrior[] warriors : warriorsOnMap)
                for (Warrior warrior : warriors)
                    if (warrior != null && warrior.getID() == cardID)
                        return warrior;

            return null;
        }

        public Position getPosition(Widget widget)
        {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 5; j++)
                    if (warriorsOnMap[j][i] != null && warriorsOnMap[j][i].equals(widget))
                        return new Position(j, i);

            return null;
        }

        public String toShowMinionInMap(Player intendedPlayer)
        {
            String widgetsString = "";
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 5; j++)
                {
                    Warrior warrior = warriorsOnMap[j][i];
                    if (warrior != null && warrior.getOwnerPlayer().equals(intendedPlayer))
                        widgetsString = widgetsString.concat(String.format("(Warrior) %s - Location (%d,%d)\n\n",
                                warrior.toShow(), i, j));

                    Widget item = spellsAndCollectibleOnMap[j][i];
                    if (item != null)
                        widgetsString = widgetsString.concat(String.format
                                ("(Collectible) CardName : %s - Location (%d,%d)\n\n", item.getName(), i, j));
                }

            return widgetsString;
        }

        public Position getHeroPosition(Player player)
        {
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 9; j++)
                    if (warriorsOnMap[i][j] instanceof Hero && warriorsOnMap[i][j].getOwnerPlayer() == player)
                        return new Position(i, j);

            return null;
        }
    }

    public void insertCard(Card intendedCard, Position position) throws Exception
    {
        if (intendedCard instanceof Spell)
        {
            Spell spell = ((Spell) intendedCard);
            if (spell.getOwnerPlayer().getPlayerCurrentMana() < spell.getManaCost())
                throw new NotEnoughMana();

            spell.generalDo(battleMap, position);
            spell.getOwnerPlayer().decreaseMana(spell.getManaCost());

        }
        else if (intendedCard instanceof Minion)
        {
            Minion minion = ((Minion) battleMap.warriorsOnMap[position.row][position.col]);
            if (minion != null)
                throw new InvalidPosition();

            if (intendedCard.getOwnerPlayer().getPlayerCurrentMana() < ((Minion) intendedCard).getManaCost())
                throw new NotEnoughMana();

            if ((((Minion) intendedCard).getSpecialSpell() != null && ((Minion) intendedCard).getSpecialSpell().getActiveTime() == ActiveTime.onInsert))
                ((Minion) intendedCard).getSpecialSpell().generalDo(battleMap, battleMap.getPosition(minion));

            battleMap.warriorsOnMap[position.row][position.col] = (Warrior) intendedCard;
            intendedCard.getOwnerPlayer().decreaseMana(((Minion) intendedCard).getManaCost());
        }

        checkDeadActions();
        checkBattleResult();
    }

    public class TurnHandler
    {
        int firstPlayerHasFlagTurnNumber;
        int secondPlayerHasFlagTurnNumber;
        Player playerHasTurn;
        int turnNumber;

        public int getTurnNumber()
        {
            return turnNumber;
        }

        public Player getPlayerInRest()
        {
            if (playerHasTurn.equals(firstPlayer))
                return secondPlayer;
            else
                return firstPlayer;
        }

        private void changeCoolDownRemaining()
        {
            for (Widget[] widgets : battleMap.warriorsOnMap)
                for (Widget widget : widgets)
                    if (widget instanceof Warrior)
                        if (((Warrior) widget).getSpecialSpell() != null)
                            ((Warrior) widget).getSpecialSpell().decreaseCoolDownRemaining();
        }

        public void changeTurn() throws Exception
        {
            turnNumber++;

            if (turnNumber % 2 == 0)
                battleMap.addCollectibleItemOnMapRandomise(turnNumber % 8);

            if (playerHasTurn.equals(firstPlayer))
            {
                playerHasTurn = secondPlayer;
                secondPlayer.setPlayerManaSpace(secondPlayer.getPlayerManaSpace() + 1);
                secondPlayer.setPlayerCurrentMana(secondPlayer.getPlayerManaSpace());
            }
            else
            {
                playerHasTurn = firstPlayer;
                firstPlayer.setPlayerManaSpace(firstPlayer.getPlayerManaSpace() + 1);
                firstPlayer.setPlayerCurrentMana(firstPlayer.getPlayerManaSpace());
            }

            changeCoolDownRemaining();

            doPerTurnActions();

            setCanMoveAndAttack();

            checkDeadActions();

            checkBattleResult();

            if (playerHasTurn == AIPlayer.getInstance().getAIPlayer())
            {
                AIPlayer.getInstance().doOrder();
                changeTurn();
            }
        }

        public void doPerTurnActions()
        {
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 9; j++)
                {
                    Warrior warriorInPosition = battleMap.warriorsOnMap[i][j];
                    if (warriorInPosition != null)
                    {
                        for (Effectable effectable : warriorInPosition.getEffectsOnWarrior())
                            if (effectable != null && effectable.getActiveTime() == ActiveTime.perTurn)
                                effectable.doEffect(warriorInPosition);

                        warriorInPosition.getEffectsOnWarrior().removeIf(effectable -> effectable.getTurnRemaining() == 0);
                    }
                }
            //
        }

        public Player getPlayerHasTurn()
        {
            return playerHasTurn;
        }
    }

    public void setCanMoveAndAttack()
    {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 5; j++)
                if (battleMap.warriorsOnMap[j][i] != null)
                {
                    battleMap.warriorsOnMap[j][i].setCanMove(true);
                    battleMap.warriorsOnMap[j][i].setCanAttack(true);
                }
    }


    protected void initialiseBattle() throws InvalidDeck, CloneNotSupportedException
    {
        checkBothPlayersDeckValidity();
        firstPlayer.setCopiedMainDeck(((Player.Deck) firstPlayer.getMainDeck().clone()));
        secondPlayer.setCopiedMainDeck(((Player.Deck) secondPlayer.getMainDeck().clone()));
        firstPlayer.setPlayerManaSpace(7);
        secondPlayer.setPlayerManaSpace(7);
        firstPlayer.setPlayerCurrentMana(firstPlayer.getPlayerManaSpace());
        secondPlayer.setPlayerCurrentMana(secondPlayer.getPlayerManaSpace());
        turnHandler.playerHasTurn = firstPlayer;
        setHeroPositionInBeginning();
        setBothPlayersHandInBeginning();
    }

    private void checkBothPlayersDeckValidity() throws InvalidDeck
    {
        if (!(firstPlayer.getMainDeck().isValidDeck() && secondPlayer.getMainDeck().isValidDeck()))
            throw new InvalidDeck();
    }

    private void setHeroPositionInBeginning()
    {
        battleMap.warriorsOnMap[2][0] = firstPlayer.getMainDeck().getHero();
        battleMap.warriorsOnMap[2][8] = secondPlayer.getMainDeck().getHero();
    }

    private void setBothPlayersHandInBeginning()
    {
        firstPlayer.getPlayerHand().makeRandomiseHand();
        secondPlayer.getPlayerHand().makeRandomiseHand();
    }

    protected void addBattleToBattleHistories(GameResault gameResault)
    {
        switch (gameResault)
        {
            case FristPlayerWin:
                firstPlayer.addGameResultToBattleHistories
                        (secondPlayer, true, TimeHandler.getInstance().getTime());
                secondPlayer.addGameResultToBattleHistories
                        (firstPlayer, false, TimeHandler.getInstance().getTime());

            case SecondPlayerWin:
                secondPlayer.addGameResultToBattleHistories
                        (firstPlayer, true, TimeHandler.getInstance().getTime());
                firstPlayer.addGameResultToBattleHistories
                        (secondPlayer, false, TimeHandler.getInstance().getTime());
        }
    }

    public abstract void checkBattleResult();

    public void doWarriorSpell(Warrior warrior, Position position) throws Exception
    {
        if (warrior.getOwnerPlayer().getPlayerCurrentMana() < warrior.getSpecialSpell().getManaCost())
            throw new NotEnoughMana();

        else if (warrior.getSpecialSpell().getCoolDownRemaining() >= 0)
            throw new CoolDownRemaining();

        warrior.getSpecialSpell().generalDo(battleMap, position);
        warrior.getOwnerPlayer().decreaseMana(warrior.getSpecialSpell().getManaCost());
        checkDeadActions();
    }

    public void winActions(Player player)
    {
        player.increaseCash(winnerPrize);
        player.increaseWinNumber();
        getOtherPlayer(player).increasLoseNumber();

        firstPlayer.getPlayerHand().getCollectedItems().clear();
        secondPlayer.getPlayerHand().getCollectedItems().clear();

        if (firstPlayer.equals(player))
            addBattleToBattleHistories(GameResault.FristPlayerWin);
        else
            addBattleToBattleHistories(GameResault.SecondPlayerWin);

        EndGameMenu.getInstance().setWinnerAndLosePlayer(player, getOtherPlayer(player));
        Controller.getInstance().changeCurrentMenuTo(EndGameMenu.getInstance());
    }

    public Player getOtherPlayer(Player player)
    {
        if (player.equals(firstPlayer))
            return secondPlayer;
        else
            return firstPlayer;
    }

    public void moveWarriorOptions(Warrior intendedWarrior, Position sourcePosition, Position destinationPosition) throws CantMove
    {
        if (!intendedWarrior.canMove())
            throw new CantMove();

        intendedWarrior.moveTiredAffect();
        battleMap.warriorsOnMap[sourcePosition.row][sourcePosition.col] = null;
        Spell widget = battleMap.spellsAndCollectibleOnMap[destinationPosition.row][destinationPosition.col];

        if (widget != null && widget.getType() == Type.Collectible)
            collect(intendedWarrior, widget);

        battleMap.warriorsOnMap[destinationPosition.row][destinationPosition.col] = intendedWarrior;
        battleMap.spellsAndCollectibleOnMap[destinationPosition.row][destinationPosition.col] = null;
    }

    public void collect(Card intendedCard, Widget widget)
    {
        if (widget instanceof Spell && ((Spell) widget).getType() == Type.Collectible)
        {
            intendedCard.getOwnerPlayer().getPlayerHand().getCollectedItems().add(((Spell) widget));
            widget.setOwnerPlayer(intendedCard.getOwnerPlayer());
        }
    }

    public void attackActions(Warrior attacker, Warrior defender) throws Exception
    {
        if (!attacker.canAttack())
            throw new CanNotAttack();

        attacker.attack(defender);
        attacker.attackTiredAffect();
        checkDeadActions();
        checkBattleResult();
    }

    private void checkDeadActions() throws Exception
    {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++)
            {
                Warrior warrior = battleMap.warriorsOnMap[i][j];
                if (warrior != null && warrior.isDead())
                {
                    doOnDeathSpells(warrior.getSpecialSpell(), warrior);
                    doOnDeathSpells(warrior.getPassiveSpell(), warrior);

                    warrior.getOwnerPlayer().getGraveYard().getGraveYardList().add(warrior);
                    battleMap.removeDeadCardFromMap(warrior);
                }

            }

    }

    public void doOnDeathSpells(Spell spell, Warrior warrior) throws Exception
    {
        if (spell.getActiveTime() == ActiveTime.onDeath)
        {
            switch (spell.getFoe())
            {
                case enemy:

                    switch (spell.getTargetType())
                    {
                        case onHero:
                            spell.generalDo
                                    (battleMap, battleMap.getHeroPosition(getOtherPlayer(warrior.getOwnerPlayer())));

                            break;

                        case onMinion:
                        case onPlayer:
                        case onMinionOrHero:
                            spell.generalDo
                                    (battleMap, battleMap.getPosition(warrior));

                            break;
                    }

                case friend:

                    switch (spell.getTargetType())
                    {
                        case onHero:
                            spell.generalDo(battleMap, battleMap.getHeroPosition(warrior.getOwnerPlayer()));

                            break;
                        case onMinion:
                        case onMinionOrHero:
                        case onPlayer:
                            spell.generalDo
                                    (battleMap, battleMap.getPosition(warrior));

                            break;
                    }


            }
        }
    }

    public Map getBattleMap()
    {
        return battleMap;
    }

    public TurnHandler getTurnHandler()
    {
        return turnHandler;
    }

    public String toShowGameInfo()
    {
        String gameInfoString = "";

        gameInfoString = gameInfoString.concat
                (firstPlayer.getName() + " Hero Health is " + firstPlayer.getMainDeck().getHero().getHealth() + "\n");
        gameInfoString = gameInfoString.concat
                (secondPlayer.getName() + " Hero Health is " + secondPlayer.getMainDeck().getHero().getHealth() + "\n");

        gameInfoString = gameInfoString.concat
                (turnHandler.playerHasTurn.getName() + " Mana Point is " + turnHandler.playerHasTurn.getPlayerCurrentMana());

        return gameInfoString;
    }

}

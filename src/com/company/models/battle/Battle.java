package com.company.models.battle;

import com.company.controller.Exceptions.*;
import com.company.controller.Menus.ShopMenu;
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

    public void addPassiveSpells() throws CloneNotSupportedException
    {
        firstPlayer.addPassiveOnPlayerCards();
        secondPlayer.addPassiveOnPlayerCards();
    }

    public class Map
    {
        Map()
        {
            addCollectibleItemOnMapRandomise(0);
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
            for (Widget[] cards : warriorsOnMap)
                for (Widget card : cards)
                    if (intendedDeadCard.equals(card))
                        card = null;
        }

        public Warrior selectCard(int cardID)
        {
            for (Warrior[] warriors : warriorsOnMap)
                for (Warrior warrior : warriors)
                    if (warrior != null && warrior.getID() == cardID)
                        return warrior;

            return null;
        }

        public void addBuffRandomise()
        {
            //TODO
        }

        public Position getPosition(Widget widget)
        {
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 5; j++)
                    if (warriorsOnMap[j][i].equals(widget))
                        return new Position(j, i);

            return null;
        }

        public String toShowMinionInMap(Player intendedPlayer)
        {
            String widgetsString = "";
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 5; j++)
                {
                    Warrior widget = warriorsOnMap[j][i];
                    if (widget.getOwnerPlayer().equals(intendedPlayer))
                        widgetsString = widgetsString.concat(String.format("(Warrior) %s - Location (%d,%d)\n",
                                widget.toShow(), i, j));

                    Widget item = spellsAndCollectibleOnMap[j][i];
                    widgetsString = widgetsString.concat(String.format
                            ("(Collectible) CardName : %s - Location (%d,%d)", item.getName(), i, j));
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

    public void insertCard(Card intendedCard, Position position) throws InvalidPosition, NotEnoughMana
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
            if (minion == null)
                throw new InvalidPosition();

            if (minion.getOwnerPlayer().getPlayerCurrentMana() < minion.getManaCost())
                throw new NotEnoughMana();

            if (minion.getSpecialSpell().getActiveTime() == ActiveTime.onInsert)
                minion.getSpecialSpell().generalDo(battleMap, battleMap.getPosition(minion));

            battleMap.warriorsOnMap[position.row][position.col] = (Warrior) intendedCard;
            minion.getOwnerPlayer().decreaseMana(minion.getManaCost());
        }
    }

    public class TurnHandler
    {
        Player playerHasTurn;
        int turnNumber;

        public int getTurnNumber()
        {
            return turnNumber;
        }

        public void setTurnNumber(int turnNumber)
        {
            this.turnNumber = turnNumber;
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
                        ((Warrior) widget).getSpecialSpell().decreaseCoolDownRemaining();
        }

        public void changeTurn()
        {
            turnNumber++;

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

            if (playerHasTurn == AIPlayer.getInstance().getAIPlayer())
                AIPlayer.getInstance().doOrder();
        }

        public Player getPlayerHasTurn()
        {
            return playerHasTurn;
        }
    }

    protected void initialiseBattle() throws InvalidDeck, CloneNotSupportedException
    {
        checkBothPlayersDeckValidity();
        firstPlayer.setCopiedMainDeck(((Player.Deck) firstPlayer.getMainDeck().clone()));
        secondPlayer.setCopiedMainDeck(((Player.Deck) secondPlayer.getMainDeck().clone()));
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
        battleMap.warriorsOnMap[2][9] = secondPlayer.getMainDeck().getHero();
    }

    private void setBothPlayersHandInBeginning()
    {
        firstPlayer.getPlayerHand().makeRandomiseHand();
        secondPlayer.getPlayerHand().makeRandomiseHand();
    }

    protected void addBattleToBattleHistories(GameResault gameResault) throws GameIsNotOver
    {
        if (gameResault == GameResault.UnCertain)
            throw new GameIsNotOver();

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

    public String toShowEndGameDetails()
    {
        return new String("a");
    }

    public abstract void checkBattleResult() throws GameIsNotOver;

    public void doWarriorSpell(Warrior warrior, Position position) throws NotEnoughMana, CoolDownRemaining
    {
        if (warrior.getOwnerPlayer().getPlayerCurrentMana() < warrior.getSpecialSpell().getManaCost())
            throw new NotEnoughMana();

        else if (warrior.getSpecialSpell().getCoolDownRemaining() >= 0)
            throw new CoolDownRemaining();

        //TODO
        //warrior.getSpecialSpell().doEffectAction(battleMap, position);
        warrior.getOwnerPlayer().decreaseMana(warrior.getSpecialSpell().getManaCost());
    }

    public void winActions(Player player) throws GameIsNotOver
    {
        player.increaseCash(winnerPrize);
        player.increaseWinNumber();
        getOtherPlayer(player).increasLoseNumber();
        if (firstPlayer.equals(player))
            addBattleToBattleHistories(GameResault.FristPlayerWin);
        else
            addBattleToBattleHistories(GameResault.SecondPlayerWin);
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
        battleMap.warriorsOnMap[destinationPosition.row][destinationPosition.col] = null;
    }

    public void collect(Card intendedCard, Widget widget)
    {
        if (widget instanceof Spell && ((Spell) widget).getType() == Type.Collectible)
        {
            intendedCard.getOwnerPlayer().getPlayerHand().getCollectedItems().add(((Spell) widget));
            widget.setOwnerPlayer(intendedCard.getOwnerPlayer());
        }
    }

    public void attackActions(Warrior attacker, Warrior defender) throws CanNotAttack
    {
        if (!attacker.canAttack())
            throw new CanNotAttack();

        attacker.attack(defender);
        attacker.attackTiredAffect();
        checkDeadActions(attacker, defender);
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

    public Warrior getRandomWarrior(Player player, SpellType spellType)
    {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 5; j++)
            {
                Warrior warrior = battleMap.warriorsOnMap[j][i];
                if ((spellType == SpellType.onFriend) && warrior.getOwnerPlayer().equals(player))
                    return warrior;

                if ((spellType == SpellType.onEnemy) && !warrior.getOwnerPlayer().equals(player))
                    return warrior;
            }

        return null;
    }

    public BattleMode getBattleMode()
    {
        return battleMode;
    }

    public Map getBattleMap()
    {
        return battleMap;
    }

    public Player getFirstPlayer()
    {
        return firstPlayer;
    }

    public Player getSecondPlayer()
    {
        return secondPlayer;
    }

    public TurnHandler getTurnHandler()
    {
        return turnHandler;
    }

    public abstract String toShowGameInfo();

}

package com.company.models.battle;

import com.company.controller.Exceptions.*;
import com.company.models.Player;
import com.company.models.Position;
import com.company.models.TimeHandler;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.Type;

import java.security.SecureRandom;
import java.util.ArrayList;

public abstract class Battle
{
    protected Battle(Player firstPlayer, Player secondPlayer, int winnerPrize)
    {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winnerPrize = winnerPrize;
    }

    protected static Battle currentBattle;

    public static Battle getInstance()
    {
        return currentBattle;
    }

    public abstract void makeBattle(Player firstPlayer, Player secondPlayer, int winnerPrize);

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
            addCollectibleItemOnMapRandomise();
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

        public Position getRandomPosition()
        {
            int row;
            int col;
            while (true)
            {
                row = randomMaker.nextInt(5);
                col = randomMaker.nextInt(9);
                if (warriorsOnMap[row][col] == null)
                    return new Position(row, col);
            }
        }

        public void addCollectibleItemOnMapRandomise()
        {
            ArrayList<Spell> collectibles = new ArrayList<>(5);
            // make and add 5 collectable item to collectibles
            ArrayList<Position> randomPositions = new ArrayList<>(5);
            for (int i = 0; i < 5; i++)
                randomPositions.add(getRandomPosition());

            // add to warriorsOnMap 5 collectibles
        }

        public void removeDeadCardFromMap(Card intendedDeadCard)
        {
            for (Widget[] cards : warriorsOnMap)
                for (Widget card : cards)
                    if (intendedDeadCard.equals(card))
                        card = null;
        }

        public Widget selectCard(int cardID)
        {
            for (Widget[] widgets : warriorsOnMap)
                for (Widget widget : widgets)
                    if (widget.getID() == cardID)
                        return widget;

            return null;
        }

        public void addBuffRandomise()
        {
            Position randomPosition = getRandomPosition();
            int randomNumber = randomMaker.nextInt() % 3;

            switch (randomNumber)
            {
                case 0:
//                    spellsAndCollectibleOnMap[randomPosition.row][randomPosition.col] = new Spell(Type.Buff, "PoisonBuff", 0, 0, 0, 3, 0, 0, 0, 0, -1, SpellType.HealthPoint, SpellType.onMinionOrHero, SpellType.enemyOrFriend);
                case 1:
//                    spellsAndCollectibleOnMap[randomPosition.row][randomPosition.col] = new Spell(Type.Buff, "FieryBuff", 0, 0, 0, 3, 0, 0, 0, 0, -2, SpellType.HealthPoint, SpellType.onMinionOrHero, SpellType.enemyOrFriend);
                case 2:
//                     spellsAndCollectibleOnMap[randomPosition.row][randomPosition.col];
            }
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
        }

        public Player getPlayerHasTurn()
        {
            return playerHasTurn;
        }
    }

    public void initialiseBattle(BattleMode battleMode) throws InvalidDeck
    {
        checkBothPlayersDeckValidity();

        firstPlayer.setCopiedMainDeck(firstPlayer.getMainDeck().getCards());
        secondPlayer.setCopiedMainDeck(secondPlayer.getMainDeck().getCards());
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

        if (gameResault == GameResault.FristPlayerWin)
        {
            firstPlayer.addGameResultToBattleHistories
                    (secondPlayer, true, TimeHandler.getInstance().getTime());
            secondPlayer.addGameResultToBattleHistories
                    (firstPlayer, false, TimeHandler.getInstance().getTime());
        }
        else if (gameResault == GameResault.SecondPlayerWin)
        {
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

    public void putSpellCardOnMap(Spell spell, Position position) throws NotEnoughMana, InvalidAttack
    {
        if (spell.getOwnerPlayer().getPlayerCurrentMana() >= spell.getManaCost())
            throw new NotEnoughMana();

        spell.initialSpell(battleMap, position);
    }

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

    public void winActions(Player player)
    {
        player.increaseCash(winnerPrize);
        player.increaseWinNumber();
        getOtherPlayer(player).decreaseWinNumber();
        player.addGameResultToBattleHistories(getOtherPlayer(player), true, TimeHandler.getInstance().getTime());
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

    public void attackActions(Warrior attacker, Warrior defender) throws CantAttack
    {
        if (!attacker.canAttack())
            throw new CantAttack();

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

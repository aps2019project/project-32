package Duelyst.model.Card;


import Duelyst.model.Battle.Battle;
import Duelyst.model.Buff.Buff;
import Duelyst.model.Card.Hero.Hero;
import Duelyst.model.Card.Minion.Minion;
import Duelyst.model.Card.Spell.Spell;
import Duelyst.model.Cell;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class Card {

    private String name;
    private boolean isHitting;
    private boolean isDying;
    private boolean isComingToMap;
    private int costOfBuy;
    private long id;
    private String cardId;
    private int remainedMoves = 2;
    private boolean canAttack = true;
    protected Image cardImage;

    public static Card getCard(String string) {
        for (Hero hero : Hero.getHeroes()) {
            if (hero.getName().equals(string))
                return hero;
        }
        for (Minion minion : Minion.getMinions()) {
            if (minion.getName().equals(string))
                return minion;
        }
        for (Spell spell : Spell.getSpells()) {
            if (spell.getName().equals(string))
                return spell;
        }
        return null;
    }

    public String getName() {
        return name.toLowerCase();
    }

    public boolean getCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public int getRemainedMoves() {
        return remainedMoves;
    }


    public void deadChecker(Battle battle) {
        if (this.getType().equals("Minion") && ((Minion) this).getHp() <= 0) {
            if (((Minion) this).getNumberOfFlag() != 0) {
                if (battle.getType().equals("OneFlagBattle")) {
                    if (this.getCardId().contains(battle.getFirstPlayer().getUserName())) {
                        battle.setFirstPlayerFlagCarryTurnCounter(0);
                    } else battle.setSecondPlayerFlagCarryTurnCounter(0);
                }
                Cell cell = battle.getMap().get(0).get(0).getCellOfCard(this, battle);
                cell.setNumberOfFlag(((Minion) this).getNumberOfFlag());
                ((Minion) this).setNumberOfFlag(0);
            }
            if (battle.getSelectedCard().cardId.equals(this.getCardId()))
                battle.setSelectedCard(null);
            if (battle.getFirstPlayerInGameCards().contains(this)) {
                battle.getFirstPlayerInGameCards().remove(this);
                battle.addToFirstGrave(this);
            } else if (battle.getSecondPlayerInGameCards().contains(this)) {
                battle.getSecondPlayerInGameCards().remove(this);
                battle.addToSecondGrave(this);
            }
            Cell cell = battle.getMap().get(0).get(0).getCellOfCard(this, battle);
            cell.setMinion(null, 2);
            if (battle.getTurn() % 2 == 1 && battle.getFirstPlayerDeck().getUsableItem() != null)
                battle.getFirstPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getFirstPlayer(), 3);
            if (battle.getTurn() % 2 == 0 && battle.getSecondPlayerDeck().getUsableItem() != null)
                battle.getSecondPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getSecondPlayer(), 4 - 1);
        } else if (this.getType().equals("Hero") && ((Hero) this).getHp() <= 0) {
            if (battle.getType().equals("HeroBattle")) {
                if (this.getCardId().contains(battle.getSecondPlayer().getUserName())) {
                }
                else {
                }
            } else {
                if (battle.getType().equals("OneFlagBattle")) {
                    if (((Hero) this).getNumberOfFlag() != 0) {
                        if (this.getCardId().contains(battle.getFirstPlayer().getUserName())) {
                            battle.setFirstPlayerFlagCarryTurnCounter(0);
                        } else battle.setSecondPlayerFlagCarryTurnCounter(0);
                    }
                }
                Cell cell = battle.getMap().get(0).get(0).getCellOfCard(this, battle);
                cell.setNumberOfFlag(((Hero) this).getNumberOfFlag());
                ((Hero) this).setNumberOfFlag(0);
                if (battle.getFirstPlayerInGameCards().contains(this)) {
                    battle.getFirstPlayerInGameCards().remove(this);
                    battle.addToFirstGrave(this);
                } else {
                    battle.getSecondPlayerInGameCards().remove(this);
                    battle.addToSecondGrave(this);
                }
                cell.setHero(null, 2);
            }
        }
    }

    public void doHarm(Card card, int y, Battle battle, boolean isCounter) {
        if (card.getType().equals("Hero") && this.getType().equals("Hero")) {
            if (((Hero) this).getAp() >= y && !(((Hero) this).getAp() < ((Hero) card).getAp() && card.getName().equals("Ashkbous")))
                ((Hero) card).setHp(((Hero) card).getHp() - ((Hero) this).getAp() + y);
            if (battle.getTurn() % 2 == 1 && battle.getFirstPlayerDeck().getUsableItem() != null)
                battle.getFirstPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getFirstPlayer(), 6);
            if (battle.getTurn() % 2 == 0 && battle.getSecondPlayerDeck().getUsableItem() != null)
                battle.getSecondPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getSecondPlayer(), 6);


            if (!((Hero) card).isStunning() && ((Hero) card).isCounterAttack() && !isCounter) {
                card.counterAttack(battle, this);
            }
        } else if (card.getType().equals("Hero") && this.getType().equals("Minion")) {

            if (((Minion) this).getAp() >= y && !(((Minion) this).getAp() < ((Hero) card).getAp() && card.getName().equals("Ashkbous")))
                ((Hero) card).setHp(((Hero) card).getHp() - ((Minion) this).getAp() + y);
            if (battle.getTurn() % 2 == 1 && battle.getFirstPlayerDeck().getUsableItem() != null)
                battle.getFirstPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getFirstPlayer(), 6);
            if (battle.getTurn() % 2 == 0 && battle.getSecondPlayerDeck().getUsableItem() != null)
                battle.getSecondPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getSecondPlayer(), 7 - 1);

            if (!((Hero) card).isStunning() && ((Hero) card).isCounterAttack() && !isCounter) {
                card.counterAttack(battle, this);
            }
        } else if (card.getType().equals("Minion") && this.getType().equals("Minion")) {
            if (((Minion) this).getAp() >= y && !(((Minion) this).getAp() < ((Minion) card).getAp() && card.getName().equals("Ashkbous")))
                ((Minion) card).setHp(((Minion) card).getHp() - ((Minion) this).getAp() + y);
            if (battle.getTurn() % 2 == 1 && battle.getFirstPlayerDeck().getUsableItem() != null)
                battle.getFirstPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getFirstPlayer(), 3);
            if (battle.getTurn() % 2 == 0 && battle.getSecondPlayerDeck().getUsableItem() != null)
                battle.getSecondPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getSecondPlayer(), 3);

            if (!((Minion) card).isStunning() && ((Minion) card).isCounterAttack()) {
                card.counterAttack(battle, this);
            }
        } else {
            if (((Hero) this).getAp() >= y && !(((Hero) this).getAp() < ((Minion) card).getAp() && card.getName().equals("Ashkbous")))
                ((Minion) card).setHp(((Minion) card).getHp() - ((Hero) this).getAp() + y);
            if (battle.getTurn() % 2 == 1 && battle.getFirstPlayerDeck().getUsableItem() != null)
                battle.getFirstPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getFirstPlayer(), 3);
            if (battle.getTurn() % 2 == 0 && battle.getSecondPlayerDeck().getUsableItem() != null)
                battle.getSecondPlayerDeck().getUsableItem().get(0).applyEffect(battle, null, battle.getSecondPlayer(), 2 + 1);

            if (!((Minion) card).isStunning() && ((Minion) card).isCounterAttack()) {
                card.counterAttack(battle, this);
            }
        }
    }

    public void counterAttack(Battle battle, Card card) {
        Cell cell = battle.getMap().get(0).get(0).getCellOfCard(card, battle);
        int x = 0, y = 0;
        if (this.getType().equals("Minion")) {
            x = ((Minion) this).getRange();
        } else if (this.getType().equals("Hero")) {
            x = ((Hero) this).getRange();
        }
        if (card.getType().equals("Minion")) {
            y = ((Minion) card).getHolyCounter();
        } else
            y = ((Hero) card).getHolyCounter();
        if ((this.getType().equals("Minion") && ((Minion) this).getTypeOfRange() == 0) ||
                (this.getType().equals("Hero") && ((Hero) this).getTypeOfRange() == 0) && cell.manhataniDistance(battle.getMap().get(0).get(0).getCellOfCard(this, battle).getX() + 1,
                        battle.getMap().get(0).get(0).getCellOfCard(this, battle).getY() + 1) <= 2) {
            this.doHarm(card, y, battle, true);


        } else if ((this.getType().equals("Minion") && ((Minion) this).getTypeOfRange() == 1) || (this.getType().equals("Hero") && ((Hero) this).getTypeOfRange() == 1)
                && cell.manhataniDistance(battle.getMap().get(0).get(0).getCellOfCard(this, battle).getX() + 1,
                battle.getMap().get(0).get(0).getCellOfCard(this, battle).getY() + 1) > 2 && cell.manhataniDistance(battle.getMap().get(0).get(0).getCellOfCard(this, battle).getX() + 1,
                battle.getMap().get(0).get(0).getCellOfCard(this, battle).getY() + 1) <= x) {
            this.doHarm(card, y, battle, true);
        } else if ((this.getType().equals("Minion") && ((Minion) this).getTypeOfRange() == 2) || (this.getType().equals("Hero") && ((Hero) this).getTypeOfRange() == 2)
                && cell.manhataniDistance(battle.getMap().get(0).get(0).getCellOfCard(this, battle).getX() + 1,
                battle.getMap().get(0).get(0).getCellOfCard(this, battle).getY() + 1) <= x) {
            this.doHarm(card, y, battle, true);
        }
    }

    public void setRemainedMoves(int remainedMoves) {
        this.remainedMoves = remainedMoves;
    }

    public void setName(String nameOfCard) {
        this.name = nameOfCard;
    }

    public String getCardId() {
        return cardId.toLowerCase();
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }



    public void setId(long id) {
        this.id = id;
    }

    public int getCostOfBuy() {
        return costOfBuy;
    }

    public void setCostOfBuy(int costOfBuy) {
        this.costOfBuy = costOfBuy;
    }

    public long getId() {
        return id;
    }

    abstract public String getType();

    public abstract String showDetails();

    public void cardIdGenerator(Battle battle) {
        ArrayList<Card> cards;
        String playerName;
        if (battle.getTurn() % 2 == 1) {
            playerName = battle.getFirstPlayer().getUserName();
            cards = battle.getFirstPlayerInGameCards();
        } else {
            cards = battle.getSecondPlayerInGameCards();
            playerName = battle.getSecondPlayer().getUserName();
        }

        int count = 0;
        for (Card card : cards) {
            if (card.getName().equals(this.name)) {
                count++;
            }
        }
        String str = playerName + "_" + this.getName() + "_" + (count);
        setCardId(str);
    }

    public boolean isHitting() {
        return isHitting;
    }

    public void setHitting(boolean hitting) {
        isHitting = hitting;
    }

    public boolean isDying() {
        return isDying;
    }

    public void setDying(boolean dying) {
        isDying = dying;
    }

    public boolean isComingToMap() {
        return isComingToMap;
    }

    public void setComingToMap(boolean comingToMap) {
        isComingToMap = comingToMap;
    }

    public abstract Image getImage();
}

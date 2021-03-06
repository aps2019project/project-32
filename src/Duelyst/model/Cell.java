package Duelyst.model;

import Duelyst.model.Battle.Battle;
import Duelyst.model.Buff.Buff;
import Duelyst.model.Card.Card;
import Duelyst.model.Card.Hero.Hero;
import Duelyst.model.Card.Minion.Minion;
import Duelyst.model.Item.CollectibleItem.CollectibleItem;

import java.util.ArrayList;

public class Cell {
    private int x, y;
    private Hero hero;
    private Minion minion;
    private CollectibleItem collectibleItem;
    private int whichPlayerIsInCell;//0 for first player  1 for second player
    private int numberOfFlag = 0;
    private ArrayList<Buff> cellEffect = new ArrayList<>();

    public int getWhichPlayerIsInCell() {
        return whichPlayerIsInCell;
    }

    public void setWhichPlayerIsInCell(int whichPlayerIsInCell) {
        this.whichPlayerIsInCell = whichPlayerIsInCell;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CollectibleItem getCollectibleItem() {
        return collectibleItem;
    }

    public void setCollectibleItem(CollectibleItem collectibleItem) {
        this.collectibleItem = collectibleItem;
    }

    public void addCellEffect(Buff buff) {
        this.cellEffect.add(buff);
    }

    public ArrayList<Buff> getCellEffect() {
        return cellEffect;
    }

    public Cell rightCell(ArrayList<ArrayList<Cell>> map) {

        if (this.getY() == 8)
            return null;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (map.get(i).get(j).equals(this)) {
                    return map.get(i).get(j + 1);
                }
            }
        }
        return null;
    }

    public Cell leftCell(ArrayList<ArrayList<Cell>> map) {
        if (this == null)
            return null;
        if (this.getY() == 0)
            return null;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (map.get(i).get(j).equals(this)) {
                    return map.get(i).get(j - 1);
                }
            }
        }
        return null;
    }

    public Cell downCell(ArrayList<ArrayList<Cell>> map) {

        if (this == null)
            return null;
        if (this.getX() == 4)
            return null;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (map.get(i).get(j).equals(this)) {
                    return map.get(i + 1).get(j);
                }
            }
        }
        return null;
    }

    public Cell upCell(ArrayList<ArrayList<Cell>> map) {
        if (this == null)
            return null;
        if (this.getX() == 0)
            return null;
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                if (map.get(i).get(j).equals(this)) {
                    return map.get(i - 1).get(j);
                }
            }
        }
        return null;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero, int whichPlayerIsInCell) {
        this.hero = hero;
        this.minion = null;//ezafe shod
        this.whichPlayerIsInCell = whichPlayerIsInCell;
    }

    public Minion getMinion() {
        return minion;
    }

    public void setMinion(Minion minion, int whichPlayerIsInCell) {
        this.minion = minion;
        this.hero = null;//ezafe shod
        this.whichPlayerIsInCell = whichPlayerIsInCell;
    }


    public Cell(int x, int y, int flag, int indexOfCollect) {
        this.x = x;
        this.y = y;
        this.numberOfFlag = flag;
        if (indexOfCollect != -1) {
            this.collectibleItem = CollectibleItem.getCollectibleItems().get(indexOfCollect).duplicate();

        }

    }

    public int manhataniDistance(int x, int y) {
        x--;
        y--;
        return abs(this.x - x) + abs(this.y - y);
    }
    public static void addCollectible(int x, int y,Battle battle) {
        battle.getMap().get(x - 1).get(y - 1).getCollectibleItem().cardIdGenerator(battle);
        if (battle.getTurn() % 2 == 1) {
            battle.addFirstPlayerCollectibleItem(battle.getMap().get(x - 1).get(y - 1).getCollectibleItem());
        } else {
            battle.addSecondPlayerCollectibleItem(battle.getMap().get(x - 1).get(y - 1).getCollectibleItem());
        }
        battle.getMap().get(x - 1).get(y - 1).setCollectibleItem(null);
    }
    public int abs(int x) {
        return Math.abs(x);
    }

    public void moveCardPos(int x, int y, Battle battle) {
        int who = battle.getMap().get(x - 1).get(y - 1).whichPlayerIsInCell;
        if (this.hero != null) {
            battle.getMap().get(x - 1).get(y - 1).setHero(this.hero, who);
            this.hero = null;
        } else {
            battle.getMap().get(x - 1).get(y - 1).setMinion(this.minion, who);
            this.minion = null;
        }

    }

    public Cell getCellOfCard(Card card, Battle battle) {
        for (int i = 0; i < 5; i++) {
            for (Cell cell : battle.getMap().get(i)) {
                if (cell.getMinion() != null && cell.getMinion().getCardId().equals(card.getCardId()))
                    return cell;
                else if (cell.getHero() != null && cell.getHero().getCardId().equals(card.getCardId()))
                {
                    return cell;
                }
            }
        }
        return null;

    }

    public void setNumberOfFlag(int numberOfFlag) {
        this.numberOfFlag = numberOfFlag;
    }

    public int getNumberOfFlag() {
        return numberOfFlag;
    }
}
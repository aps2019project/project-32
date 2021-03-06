package Duelyst.model.Item.CollectibleItem;


import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Cell;

import java.util.ArrayList;

public class ChineseSword extends CollectibleItem {

    public ChineseSword() {
        super("chineseSword");
    }

    public ChineseSword(ChineseSword chineseSword) {
        super(chineseSword);
    }


    @Override
    public void applyEffect(Battle battle, Cell cell, Player player) {
        ArrayList<Cell> meleeForces = getRandomForce(battle, player);
        for (Cell newCell : meleeForces) {
            if (newCell.getHero() != null) {
                newCell.getHero().incrementAp(5);
            }
            if (newCell.getMinion() != null) {
                newCell.getMinion().incrementAp(5);
            }
        }
    }

    private ArrayList<Cell> getRandomForce(Battle battle, Player player) {
        ArrayList<Cell> enemyCells = new ArrayList<>();
        if (battle.getSecondPlayer().getUserName().equals(player.getUserName())) { // Insider is secondPlayer
            addCellToList(battle, enemyCells, battle.getSecondPlayer());
        } else { // Insider is firstPlayer
            addCellToList(battle, enemyCells, battle.getFirstPlayer());
        }
        return enemyCells;
    }

    private void addCellToList(Battle battle, ArrayList<Cell> cells, Player player) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (battle.getMap().get(i).get(j).getHero() != null &&
                        player.getMainDeck().isContain(battle.getMap().get(i).get(j).getHero()) &&
                        battle.getMap().get(i).get(j).getHero().getTypeOfHit().equals("Melee"))
                    cells.add(battle.getMap().get(i).get(j));
                if (battle.getMap().get(i).get(j).getMinion() != null &&
                        player.getMainDeck().isContain(battle.getMap().get(i).get(j).getMinion()) &&
                        battle.getMap().get(i).get(j).getHero().getTypeOfHit().equals("Melee"))
                    cells.add(battle.getMap().get(i).get(j));
            }
        }
    }

    @Override
    public CollectibleItem duplicate() {
        ChineseSword chineseSword = new ChineseSword(this);
        return chineseSword;
    }

    @Override
    public String showDetails() {
        String details;
        details = "Name : " + this.getClass().getSimpleName() +
                " - Desc: " + CollectibleItemWork.CHINESE_SWORD.getMessage();
        return details;
    }
}

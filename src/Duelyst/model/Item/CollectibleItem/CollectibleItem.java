package Duelyst.model.Item.CollectibleItem;


import Duelyst.model.Player;
import Duelyst.model.Battle.Battle;
import Duelyst.model.Item.Item;

import java.util.ArrayList;
import Duelyst.model.Cell;

public abstract class CollectibleItem extends Item {
    private static ArrayList<CollectibleItem> collectibleItems = new ArrayList<>();
    private String cardId;

    static {
        new BladesOfAgility();
        new ChineseSword();
        new DeathCurse();
        new Devastation();
        new DoubleEntendreArrow();
        new Elexir();
        new ManaElectuary();
        new PerpetuityElectuary();
        new RandomDamage();
    }

    public abstract void applyEffect(Battle battle, Cell cell, Player player);

    public CollectibleItem(String name) {
        this.setName(name);
        collectibleItems.add(this);
    }

    public void cardIdGenerator(Battle battle) {
        ArrayList<CollectibleItem> collectibleItems;
        String playerName;
        if (battle.getTurn() % 2 == 1) {
            playerName = battle.getFirstPlayer().getUserName();
            collectibleItems = battle.getFirstPlayerCollectibleItem();
        } else {
            collectibleItems = battle.getSecondPlayerCollectibleItem();
            playerName = battle.getSecondPlayer().getUserName();
        }

        int count = 1;
        for (CollectibleItem collectibleItem : collectibleItems) {
            if (collectibleItem.getName().equals(this.getName())) {
                count++;
            }
        }
        String str = playerName + "_" + this.getName() + "_" + (count);
        setCardId(str);
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }


    public String getCardId() {
        return cardId;
    }

    public CollectibleItem(CollectibleItem collectibleItem) {
        this.setName(collectibleItem.getName());
    }

    public static ArrayList<CollectibleItem> getCollectibleItems() {
        return collectibleItems;
    }

    public CollectibleItem duplicate() {
        return null;
    }
}


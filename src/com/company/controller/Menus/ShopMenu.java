package com.company.controller.Menus;

import com.company.Position;
import com.company.controller.Controller;
import com.company.controller.Exceptions.CardIsExistInCollection;
import com.company.controller.Exceptions.CardNotFoundInShop;
import com.company.controller.Exceptions.NotEnoughCash;
import com.company.models.battle.Battle;
import com.company.models.widget.Widget;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Spell;
import com.company.models.widget.cards.Warriors.AttackType;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Warrior;
import com.company.models.widget.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopMenu implements AbstractMenu {
    private ArrayList<Card> cards = new ArrayList<>();
    private HashMap<String, String> fileToCard = new HashMap<>();

    private ShopMenu() {
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    private static ShopMenu shopMenuInstance = new ShopMenu();

    public static AbstractMenu getInstance() {
        return shopMenuInstance;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public void selectOptionByCommand(String command) throws CardIsExistInCollection, CardNotFoundInShop, NotEnoughCash {
        if (command.matches("exit")) {
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
        } else if (command.matches("search \\w+")) {

        } else if (command.matches("search collection \\w+")) {
            searchCollection(command);
        } else if (command.matches("buy \\w+")) {
            buyCard(command);
        } else if (command.matches("sell \\w+ \\w+")) {

        } else if (command.matches("show")) {

        } else if (command.matches("help")) {

        }
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public String toShowMenu() {
        return null;
    }

    public void buyCard(String command) throws CardNotFoundInShop, NotEnoughCash {
        String cardOrItemName = command.split(" ")[1];
        if (searchCard(cardOrItemName) == null) {
            throw new CardNotFoundInShop();
        } else {
            Card card = searchCard(cardOrItemName);
            if (Controller.getInstance().getCurrentPlayer().getCash() < card.getCash()) {
                throw new NotEnoughCash();
            } else {
                addToCollection(card);
            }
        }
    }

    public void sellCard() {

    }


    public Card searchCard(String cardName) {
        return null;
    }

    public Item searchItem(String itemName) {
        return null;
    }

    public int getID(String name) {
        return 0;
    }

    public void toShowShop() {

    }

    public void searchCollection(String command) throws CardIsExistInCollection {
        String cardOrItemName = command.split(" ")[2];
        if (Controller.getInstance().getCurrentPlayer().getCollection().findCardInCollection(cardOrItemName) == null) {
            throw new CardIsExistInCollection();
        }

    }

    public Card serchCard(String cardName) {
        for (Card card : getCards()) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    public void addToCollection(Card card) {
        if (card instanceof Warrior) {
            //Card newCard = new Warrior(card);
        }
    }

    public void addCardsToShop() {
        Battle battle = Controller.getInstance().getCurrentBattle();
        Widget[][] map = battle.getBattleMap().getMap();
        new Hero("WhiteGiant", 50, 4, 8000, 0, AttackType.Melee) {
            @Override
            public String toShowHero() {
                return null;
            }

            @Override
            public void attack(Card defender) {

            }

            @Override
            public void defend(Card attacker) {

            }

            public void addToShop() {
                cards.add(this);
            }
        }.addToShop();
        new Spell("TotalDisarm", 0, 0, 1000) {
            @Override
            public void doEffect(Position... positions) {
                Widget cardInPosition = map[positions[0].row][positions[0].col];
                if (cardInPosition != null && cardInPosition instanceof Warrior) {
                    if (!cardInPosition.getOwnerPlayer().equals(this.getOwnerPlayer())) {
                        ((Warrior) cardInPosition).setCanAttack(false);
                    } else {
                        //Exception
                    }
                } else {
                    //Exception
                }
            }

            public void addToShop() {
                cards.add(this);
            }
        }.addToShop();
        new Spell("Empower", 1, 0, 1500) {
            @Override
            public void doEffect(Position... positions) {
                Widget cardInPosition = map[positions[0].row][positions[0].col];
                if (cardInPosition != null && cardInPosition instanceof Warrior) {
                    if (cardInPosition.getOwnerPlayer().equals(this.getOwnerPlayer())) {
                        ((Warrior) cardInPosition).increasePower(2);
                    } else {
                        //Exception
                    }
                } else {
                    //Exception
                }
            }

            public void addToShop() {
                cards.add(this);
            }
        }.addToShop();
        new Spell("FireBall",1,0,400) {
            @Override
            public void doEffect(Position... positions) {
                Widget cardInPosition = map[positions[0].row][positions[0].col];
                if (cardInPosition != null && cardInPosition instanceof Warrior) {
                    if (!cardInPosition.getOwnerPlayer().equals(this.getOwnerPlayer())) {
                        ((Warrior) cardInPosition).decreaseHealth(4);
                    } else {
                        //Exception
                    }
                } else {
                    //Exception
                }
            }
            public void addToShop() {
                cards.add(this);
            }
        }.addToShop();
        new Spell("GodStrength",2,0,450) {
            @Override
            public void doEffect(Position... positions) {
                Widget cardInPosition = map[positions[0].row][positions[0].col];
                if (cardInPosition != null && cardInPosition instanceof Hero) {
                    if (cardInPosition.getOwnerPlayer().equals(this.getOwnerPlayer())) {
                        ((Warrior) cardInPosition).increasePower(4);
                    } else {
                        //Exception
                    }
                } else {
                    //Exception
                }
            }
            public void addToShop() {
                cards.add(this);
            }
        }.addToShop();
        new Spell("LightingBolt", 2, 0, 1250) {
            @Override
            public void doEffect(Position... positions) {
                Widget cardInPosition = map[positions[0].row][positions[0].col];
                if (cardInPosition != null && cardInPosition instanceof Hero) {
                    if (!cardInPosition.getOwnerPlayer().equals(this.getOwnerPlayer())) {
                        ((Warrior) cardInPosition).decreasePower(2);
                    } else {
                        //Exception
                    }
                } else {
                    //Exception
                }
            }
            public void addToShop(){
                cards.add(this);
            }
        }.addToShop();
        new Spell("") {
            @Override
            public void doEffect(Position... positions) {

            }
        }
    }
}

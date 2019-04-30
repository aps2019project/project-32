package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.models.Player;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.AttackType;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.SpellType;

import java.util.ArrayList;

public class ShopMenu implements AbstractMenu
{
    private ShopMenu()
    {
    }

    private static ShopMenu shopMenuInstance = new ShopMenu();

    public static AbstractMenu getInstance()
    {
        return shopMenuInstance;
    }

    private ArrayList<Card> cards = new ArrayList<>();

    public ArrayList<Card> getCards() {
        return cards;
    }

    private int IDCounter = 1;

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }

    @Override
    public void selectOptionByCommand(String command)
    {
        if (command.matches("exit"))
        {
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
        }
        else if (command.matches("search \\w+ ")) //search in shop
        {
            search(command);
        }
        else if (command.matches("search collection \\w+")) // search in collection
        {
            searchCollection(command);
        }
        else if (command.matches("buy \\w+"))
        {
            buy(command);
        }
        else if (command.matches("sell \\w+"))
        {

        }
        else if (command.matches("show collection"))
        {
            Controller.getInstance().getCurrentPlayer().getCollection().toShowCollection(); //send to view
        }
        else if (command.matches("help"))
        {

        }
    }

    private void buy(String command) {
        String cardName = command.split(" ")[1];
        if (searchCard(cardName) == null){
            // not exist
        }
        else {
            Card card = searchCard(cardName);
            Player player = Controller.getInstance().getCurrentPlayer();
            if (card.getPrice()>player.getCash()){
                // Exception
            }
            else {
                if(player.getCollection().getNumberOfUsable() == 3){
                    // Exeception
                }
                else {

                }
            }
        }
    }


    private void searchCollection(String command) {

        String cardName = command.split(" ")[2];
        if (Controller.getInstance().getCurrentPlayer().getCollection().findCardInCollection(cardName) != null){
            // card exsit
        }
        else {
            // exeption
        }
    }

    private String search(String command) {
        String cardOrUsableName = command.split(" ")[1];
        if (searchCard(cardOrUsableName) != null){
            Card card = searchCard(cardOrUsableName);
            return String.valueOf(card.getID());

        }
        else{
            // Exception
        }
        return null;
    }

    private Card searchCard(String cardOrUsableName) {
        for (Card card : getCards()) {
            if (card.getName().equals(cardOrUsableName)){
                return card;
            }
        }
        return null;
    }

    @Override
    public String help()
    {
        return null;
    }




    @Override
    public String toShowMenu()
    {
        return null;
    }

    public void buyCard(String command)
    {

    }

    public void sellCard()
    {

    }



    public int getID(String name)
    {
        return 0;
    }

    public void toShowShop()
    {

    }



    public void addCardToShop(){
        Card totalDisarm = new Spell("TotalDisarm",1000,0,0,0,1000,0,0,0,0, SpellType.Disarm,SpellType.onEnemy,SpellType.onMinionOrHero);
        add(totalDisarm);
        //Card areaSpell = new Spell("AreaSpell",1500,2,0,0,0,0,2,0,0,SpellType.inactiveBuffs,SpellType.cellEffect);
        //add(areaSpell);
        Card emPower = new Spell("Empower",250,1,0,0,0,0,0,2,0,SpellType.onFriend,SpellType.AttackPoint,SpellType.onMinionOrHero);
        add(emPower);
        Card fireBall = new Spell("FireBall",400,1,0,0,0,0,0,0,-4,SpellType.onEnemy,SpellType.HealthPoint,SpellType.onMinionOrHero);
        add(fireBall);
        Card godStrength = new Spell("GodStrength",450,2,0,0,0,0,0,0,4,SpellType.onFriend,SpellType.onHero,SpellType.HealthPoint);
        add(godStrength);
        Card hellFire = new Spell ("HellFire",600,3,0,2,0,0,2,0,-2,SpellType.cellEffect,SpellType.HealthPoint);
        add(hellFire);
        Card lightingBolt = new Spell ("LightingBolt",1250,2,0,0,0,0,0,0,-8,SpellType.onEnemy,SpellType.onHero,SpellType.HealthPoint);
        add(lightingBolt);
        Card poisonLake = new Spell("PoisonLake",900,5,0,1,0,0,3,0,-1,SpellType.cellEffect,SpellType.HealthPoint);
        add(poisonLake);
        Card madness = new Spell("Madness",650,0,0,0,3,0,0,4,0,SpellType.onFriend,SpellType.Disarm,SpellType.AttackPoint,SpellType.onMinionOrHero);
        add(madness);
        Card allDisarm = new Spell("AllDisarm",2000,9,0,0,1,0,0,0,0,SpellType.allEnemy,SpellType.Disarm);
        add(allDisarm);
        Card allPoison = new Spell("AllPoison",1500,8,0,4,0,0,0,0,-1,SpellType.allEnemy,SpellType.HealthPoint);
        add(allPoison);
        //Card dispel = new Spell("Dispel",2100,0,0,0,0,0,0,0,0,SpellType.onEnemyOrFriend,SpellType.inactiveBuffs);
        //add(dispel);
        //Card healthWithProfit = new Spell("HealthWithProfit",2250,0,0,0,)   *buff*
        //Card ghazaBokhorJoonBegiri = new Spell() *buff*
        //Card allPower = new Spell() *buff*
        Card allAttack = new Spell("AllAttack",1500,4,0,0,0,0,0,0,-6,SpellType.onEnemiesInCol,SpellType.onEnemy,SpellType.HealthPoint,SpellType.onMinionOrHero);
        add(allAttack);
        //Card weakening = new Spell() *buff*
        Card sacrifice = new Spell("Sacrifice",1600,3,0,0,0,0,0,0,0,SpellType.healHeroByMinion,SpellType.onFriend,SpellType.onMinion);
        add(sacrifice);
        Card kingsGard = new Spell("KingsGard",1750,3,0,0,0,0,0,0,-1000,SpellType.onMinionNearHero,SpellType.onEnemy,SpellType.HealthPoint);
        add(kingsGard);
        Card shock = new Spell("Shock",1200,1,0,0,0,2,0,0,0,SpellType.onEnemy,SpellType.Stun,SpellType.onMinionOrHero);
        add(shock);


        Card rakhsh = new Hero("rakhsh",4,50,10000, AttackType.Melee,0);
        ((Hero) rakhsh).setSpecialSpell(2,0,0,1,0,0,0,SpellType.onEnemy,SpellType.onMinionOrHero);

        Card dragon = new Hero("Dragon",4,50)



    }

    public void add(Card card){
        getCards().add(card);
    }

}

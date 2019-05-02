package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.CardNotFound;
import com.company.models.Player;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.AttackType;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.Warriors.MinionSpellType;
import com.company.models.widget.cards.spells.Spell;
import com.company.models.widget.cards.spells.SpellKind;
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
    public void selectOptionByCommand(String command) throws CardNotFound {
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
            sellCard(command);
        }
        else if (command.matches("show collection"))
        {
            Controller.getInstance().getCurrentPlayer().getCollection().toShowCollection(); //send to view
        }
        else if (command.matches("help"))
        {
            toShowMenu();
        }
        else if (command.matches("show")){
            showCards();
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
                    Card card1;
                    if (card instanceof Spell){

                        card1 = new Spell((Spell)card,true);
                    }
                    else if (card instanceof Minion){
                        card1 = new Minion((Minion) card);
                    }
                    else {
                        card1 = new Hero((Hero)card);
                    }
                    player.getCollection().addTOCollection(card1);
                    player.deCreaseCash(card1.getPrice());
                    //print done
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


    public String showCards()
    {
        String total = "";
        int counter = 1;
        for (Card card : getCards()) {
            total = total.concat(counter + " ");
            total = total.concat(card.toShow());
            counter++;
        }

        return total;

    }


    @Override
    public String toShowMenu()
    {
        return String.format("1. exit \n 2. search [item name | card name] \n 3. show collection \n" +
            "4. search collection [item name | card name] \n 5. buy [card name | item name]" +
            "6. sell [card id | card id] \n 7. show \n 8. help" );;
    }


    public void sellCard(String command) throws CardNotFound {
        Player player = Controller.getInstance().getCurrentPlayer();
        int cardID = Integer.parseInt(command.split(" ")[1]);
        if (player.getCollection().findCardInCollection(cardID)!=null) {
            player.getCollection().removeFromCollection(cardID);
            player.inCreaseCash(player.getCollection().findCardInCollection(cardID).getID() / 2);
        }else {
            // exception
        }
    }



    public int getID(String name)
    {
        return 0;
    }

    public void toShowShop()
    {

    }


    public void addCardToShop(){
        Card totalDisarm = new Spell(SpellKind.spellCard,"TotalDisarm",1000,0,0,0,1000,0,0,0,0, SpellType.Disarm,SpellType.onEnemy,SpellType.onMinionOrHero);
        add(totalDisarm);

        Card areaSpell = new Spell(SpellKind.spellCard,"AreaSpell",1500,2,0,0,0,0,2,0,0,SpellType.inactiveBuffs,SpellType.cellEffect);
        //add(areaSpell);

        Card emPower = new Spell(SpellKind.spellCard,"Empower",250,1,0,0,0,0,0,2,0,SpellType.onFriend,SpellType.AttackPoint,SpellType.onMinionOrHero);
        add(emPower);

        Card fireBall = new Spell(SpellKind.spellCard,"FireBall",400,1,0,0,0,0,0,0,-4,SpellType.onEnemy,SpellType.HealthPoint,SpellType.onMinionOrHero);
        add(fireBall);

        Card godStrength = new Spell(SpellKind.spellCard,"GodStrength",450,2,0,0,0,0,0,0,4,SpellType.onFriend,SpellType.onHero,SpellType.HealthPoint);
        add(godStrength);

        Card hellFire = new Spell (SpellKind.spellCard,"HellFire",600,3,0,2,0,0,2,0,-2,SpellType.cellEffect,SpellType.HealthPoint);
        add(hellFire);

        Card lightingBolt = new Spell (SpellKind.spellCard,"LightingBolt",1250,2,0,0,0,0,0,0,-8,SpellType.onEnemy,SpellType.onHero,SpellType.HealthPoint);
        add(lightingBolt);

        Card poisonLake = new Spell(SpellKind.spellCard,"PoisonLake",900,5,0,1,0,0,3,0,-1,SpellType.cellEffect,SpellType.HealthPoint);
        add(poisonLake);

        Card madness = new Spell(SpellKind.spellCard,"Madness",650,0,0,4,3,0,0,4,0,SpellType.onFriend,SpellType.Disarm,SpellType.AttackPoint,SpellType.onMinionOrHero);
        add(madness);

        Card allDisarm = new Spell(SpellKind.spellCard,"AllDisarm",2000,9,0,0,1,0,0,0,0,SpellType.allEnemy,SpellType.Disarm);
        add(allDisarm);

        Card allPoison = new Spell(SpellKind.spellCard,"AllPoison",1500,8,0,4,0,0,0,0,-1,SpellType.allEnemy,SpellType.HealthPoint);
        add(allPoison);

        //Card dispel = new Spell("Dispel",2100,0,0,0,0,0,0,0,0,SpellType.onEnemyOrFriend,SpellType.inactiveBuffs);
        //add(dispel);

        //Card healthWithProfit = new Spell("HealthWithProfit",2250,0,0,0,)   *buff*
        //Card ghazaBokhorJoonBegiri = new Spell() *buff*

        //Card allPower = new Spell() *buff*

        Card allAttack = new Spell(SpellKind.spellCard,"AllAttack",1500,4,0,0,0,0,0,0,-6,SpellType.onEnemiesInCol,SpellType.onEnemy,SpellType.HealthPoint,SpellType.onMinionOrHero);
        add(allAttack);

        //Card weakening = new Spell() *buff*
        Card sacrifice = new Spell(SpellKind.spellCard,"Sacrifice",1600,3,0,0,0,0,0,0,0,SpellType.healHeroByMinion,SpellType.onFriend,SpellType.onMinion);
        add(sacrifice);

        Card kingsGard = new Spell(SpellKind.spellCard,"KingsGard",1750,3,0,0,0,0,0,0,-1000,SpellType.onMinionNearHero,SpellType.onEnemy,SpellType.HealthPoint);
        add(kingsGard);

        Card shock = new Spell(SpellKind.spellCard,"Shock",1200,1,0,0,0,2,0,0,0,SpellType.onEnemy,SpellType.Stun,SpellType.onMinionOrHero);
        add(shock);

        Card rakhsh = new Hero("rakhsh",4,50,8000, AttackType.Melee,1);
        ((Hero) rakhsh).setSpecialSpell(SpellKind.spellCard,2,2,0,0,1,0,0,0,SpellType.onEnemy,SpellType.onMinionOrHero);
        cards.add(rakhsh);

        Card dragon = new Hero("Dragon",4,50,8000,AttackType.Melee,1);
        ((Hero) dragon).setSpecialSpell(SpellKind.spellCard,1,2,0,0,1,10,0,0,SpellType.onEnemy);
        cards.add(dragon);

        Card whiteGiant = new Hero("WhiteGiant",4,50,8000,AttackType.Melee,1);
        ((Hero) whiteGiant).setSpecialSpell(SpellKind.Buff,2,2,1000,0,0,0,4,0,SpellType.onFriend,SpellType.onHero);
        cards.add(whiteGiant);

        Card persianArcher = new Minion("PersianArcher",2,4,6,300,AttackType.Ranged,7, null);
        cards.add(persianArcher);

        Card persianSpeared = new Minion("PersianSpeared",1,3,5,500,AttackType.Hybrid,3,null);
        cards.add(persianSpeared);

        Card persianSword = new Minion("PersianSword",2,4,6,400,AttackType.Melee,1,MinionSpellType.OnAttack);
        ((Minion) persianSword).setSpecialSpell(SpellKind.spellCard,0,0,0,0,1,1,0,0,SpellType.onEnemy,SpellType.onMinionOrHero);
        cards.add(persianSword);

        Card eraj = new Minion("Eraj",4,20,6,500,AttackType.Ranged,3,null);
        cards.add(eraj);

        Card bigGiant = new Minion("BigGiant",9,8,30,600,AttackType.Hybrid,2,null);
        cards.add(bigGiant);

        Card fireDragon = new Minion("FireDragon",5,5,9,250,AttackType.Ranged,4,null);
        cards.add(fireDragon);

        Card hogRider = new Minion("HogRider",4,8,16,300,AttackType.Melee,1,null);
        cards.add(hogRider);

        Card stoneGiant = new Minion("StoneGiant",9,12,12,300,AttackType.Ranged,7,null);
        cards.add(stoneGiant);

        Card blackGiant = new Minion("BlackGiant",9,10,14,300,AttackType.Hybrid,7,null);
        cards.add(blackGiant);

        Card turanSwampy = new Minion("TuranSwampy",2,10,3,600,AttackType.Melee,1,null);
        cards.add(turanSwampy);

        Card turanSpear = new Minion("TuranSpear",1,4,4,600,AttackType.Hybrid,3,null);
        cards.add(turanSpear);

        Card turanStoneThrower = new Minion("TuranStoneThrower",1,2,4,600,AttackType.Ranged,7,null);
        cards.add(turanStoneThrower);

        Card turanArcher = new Minion("TuranArcher",1,4,3,500,AttackType.Hybrid,5,null);
        cards.add(turanArcher);

        Card persianHorseRider = new Minion("PersianHorseRider",4,6,10,200,AttackType.Melee,1,null);
        cards.add(persianHorseRider);

        Card turanSpy = new Minion("TurnSpy",4,6,6,700,AttackType.Melee,1,MinionSpellType.OnAttack);
        ((Minion) turanSpear).setSpecialSpell(SpellKind.spellCard,0,0,4,1,0,1,0,-1,SpellType.onEnemy,SpellType.onMinionOrHero,SpellType.HealthPoint);
        cards.add(turanSpy);

        Card eagle = new Minion("Eagle",2,1,2,200,AttackType.Ranged,3,MinionSpellType.Passive);
        ((Minion) eagle).setSpecialSpell(SpellKind.Buff,0,0,0,0,0,1,0,10,SpellType.onFriend,SpellType.onMinionOrHero,SpellType.HealthPoint);
        cards.add(eagle);

        Card poisonSnake = new Minion("PoisonSnake",4,6,5,300,AttackType.Ranged,4,MinionSpellType.OnAttack);
        ((Minion) poisonSnake).setSpecialSpell(SpellKind.spellCard,0,0,3,0,0,1,0,-1,SpellType.onEnemy,SpellType.onMinionOrHero,SpellType.HealthPoint);
        cards.add(poisonSnake);


    }

    public void add(Card card){
        getCards().add(card);
    }

}

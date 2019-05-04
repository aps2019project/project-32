package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.CantAddUsableItems;
import com.company.controller.Exceptions.CardNotFound;
import com.company.controller.Exceptions.NotEnoughCash;
import com.company.models.Player;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.AttackType;
import com.company.models.widget.cards.Warriors.Hero;
import com.company.models.widget.cards.Warriors.Minion;
import com.company.models.widget.cards.Warriors.MinionSpellType;
import com.company.models.widget.cards.spells.*;
import com.company.models.widget.cards.spells.effects.*;
import com.company.view.View;

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

    private ArrayList<Card> shopCards = new ArrayList<>();

    @Override
    public void selectOptionByCommand(String command) throws CardNotFound, NotEnoughCash, CantAddUsableItems
    {
        if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());

        else if (command.matches("Search \\w+")) //search in shop
            View.getInstance().show(search(command));

        else if (command.matches("Search Collection \\w+")) // search in collection
            View.getInstance().show(searchCollection(command));

        else if (command.matches("Buy \\w+"))
            View.getInstance().show(buy(command));

        else if (command.matches("Sell \\w+"))
            View.getInstance().show(sellCard(command));

        else if (command.matches("Show Collection"))
            View.getInstance().show(Controller.getInstance().getCurrentPlayer().getCollection().toShowCollection());

        else if (command.matches("Help"))
            View.getInstance().show(toShowMenu());

        else if (command.matches("Show ShopCards"))
            View.getInstance().show(showCards());
    }

    private String buy(String command) throws CardNotFound, NotEnoughCash, CantAddUsableItems
    {
        String cardName = command.split(" ")[1];
        Card card = searchCard(cardName);
        if (card == null)
            throw new CardNotFound();

        Player player = Controller.getInstance().getCurrentPlayer();
        if (card.getPrice() > player.getCash())
            throw new NotEnoughCash();

        if (player.getCollection().getNumberOfUsable() == 3)
            throw new CantAddUsableItems();

        Card theCard;
        if (card instanceof Spell)
            theCard = new Spell((Spell) card);
        else if (card instanceof Minion)
            theCard = new Minion((Minion) card);
        else
            theCard = new Hero((Hero) card);

        player.getCollection().addTOCollection(theCard);
        player.decreaseCash(theCard.getPrice());

        return "Successful Buy!";
    }


    private String searchCollection(String command) throws CardNotFound
    {
        String cardName = command.split(" ")[2];
        Card card = Controller.getInstance().getCurrentPlayer().getCollection().findCardInCollection(cardName);
        if (card == null)
            throw new CardNotFound();

        return String.valueOf(card.getID());
    }

    private String search(String command) throws CardNotFound
    {
        String cardOrUsableName = command.split(" ")[1];
        Card card = searchCard(cardOrUsableName);
        if (card == null)
            throw new CardNotFound();

        return String.valueOf(card.getID());
    }

    private Card searchCard(String cardOrUsableName)
    {
        for (Card card : shopCards)
            if (card.getName().equals(cardOrUsableName))
                return card;

        return null;
    }

    private String showCards()
    {
        String total = "";
        int counter = 1;
        for (Card card : shopCards)
        {
            total = total.concat(counter + " ");
            total = total.concat(card.toShow());
            counter++;
        }
        return total;
    }

    @Override
    public String toShowMenu()
    {
        return "1.Exit \n2.Search <CardName> \n3.Show Collection \n" +
                "4.Search Collection <CardName> \n5.Buy <CardName>\n" +
                "6.Sell <CardID> \n7.Show ShopCards \n8.Help";
    }


    private String sellCard(String command) throws CardNotFound
    {
        Player player = Controller.getInstance().getCurrentPlayer();
        int cardID = Integer.parseInt(command.split(" ")[1]);
        if (player.getCollection().findCardInCollection(cardID) == null)
            throw new CardNotFound();

        player.increaseCash(player.getCollection().findCardInCollection(cardID).getID() / 2);
        player.getCollection().removeFromCollection(cardID);

        return "Successful sell!";
    }

    public void addCardToShop()
    {
        Spell totalDisarm = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "TotalDisarm", 1000, 0, 0, 0,new DisarmEffect(1000, BuffType.Negative));
        totalDisarm.addSpellTypes(SpellType.onEnemy,SpellType.onTarget,SpellType.onMinionOrHero);
        shopCards.add(totalDisarm);

        Spell areaSpell = new Spell(SpellTarget.CellEffect,SpellActiveTime.OnRespawn, SpellKind.spellCard, "AreaSpell", 1500, 2, 0, 0,new DeBuff(1,BuffType.Positive));
        areaSpell.addSpellTypes(SpellType.onEnemy,SpellType.onFriend,SpellType.onMinionOrHero);
        shopCards.add(areaSpell);

        Spell emPower = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "Empower", 250, 1, 0, 0,new ChangeAttackPoint(2,1,BuffType.Positive));
        emPower.addSpellTypes(SpellType.onMinionOrHero,SpellType.onFriend,SpellType.onTarget);
        shopCards.add(emPower);

        Spell fireBall = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "FireBall", 400, 1, 0, 0,new ChangeHealthEffect(-4,1,BuffType.Negative));
        fireBall.addSpellTypes(SpellType.onTarget,SpellType.onEnemy,SpellType.onMinionOrHero);
        shopCards.add(fireBall);

        Spell godStrength = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "GodStrength", 450, 2, 0, 0,new ChangeAttackPoint(4,1,BuffType.Negative));
        godStrength.addSpellTypes(SpellType.onFriend,SpellType.onTarget,SpellType.onHero);
        shopCards.add(godStrength);

        Spell hellFire = new Spell(SpellTarget.CellEffect,SpellActiveTime.OnRespawn, SpellKind.Buff, "HellFire", 600, 3, 0, 2,new ChangeHealthEffect(-2,2,BuffType.Negative));
        hellFire.addSpellTypes(SpellType.onSquare,SpellType.onEnemy,SpellType.onFriend);
        shopCards.add(hellFire);


        Spell lightingBolt = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "LightingBolt", 1250, 2, 0, 0,new ChangeHealthEffect(-8,1,BuffType.Negative));
        lightingBolt.addSpellTypes(SpellType.onTarget,SpellType.onEnemy,SpellType.onHero);
        shopCards.add(lightingBolt);

        Spell poisonLake = new Spell(SpellTarget.CellEffect,SpellActiveTime.OnRespawn, SpellKind.spellCard, "PoisonLake", 900, 5, 0, 1,new ChangeHealthEffect(-1,1,BuffType.Negative));
        poisonLake.addSpellTypes(SpellType.onSquare,SpellType.onEnemy,SpellType.onFriend);
        shopCards.add(poisonLake);


        Spell madness = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "Madness", 650, 0, 0, 0,new ChangeAttackPoint(4,3,BuffType.Positive),new DisarmEffect(3,BuffType.Negative));
        madness.addSpellTypes(SpellType.onFriend,SpellType.onMinionOrHero,SpellType.onTarget);
        shopCards.add(madness);

        Spell allDisarm = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "AllDisarm", 2000, 9, 0, 0,new DisarmEffect(1,BuffType.Negative));
        allDisarm.addSpellTypes(SpellType.allWarrior,SpellType.onMinionOrHero,SpellType.onEnemy);
        shopCards.add(allDisarm);

        Spell allPoison = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "AllPoison", 1500, 8, 0, 4,new ChangeHealthEffect(-1,4,BuffType.Negative));
        allPoison.addSpellTypes(SpellType.allWarrior,SpellType.onMinionOrHero,SpellType.onEnemy);
        shopCards.add(allPoison);

        Spell dispel = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn,SpellKind.spellCard,"Dispel",2100,0,0,0,new DeBuff(1,BuffType.Positive));
        dispel.addSpellTypes(SpellType.onEnemy,SpellType.onFriend,SpellType.onMinionOrHero);
        shopCards.add(dispel);

        Spell healthWithProfit = new Spell(SpellTarget.OnWarrior, SpellActiveTime.OnRespawn, SpellKind.spellCard,"HealthWithProfit", 2250, 0, 0, 0,new HolyEffect(1,3,BuffType.Positive),new ChangeHealthEffect(-6,1,BuffType.Negative));
        healthWithProfit.addSpellTypes(SpellType.onMinionOrHero,SpellType.onFriend,SpellType.onTarget);
        shopCards.add(healthWithProfit);

        Spell allPower = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn,SpellKind.spellCard,"AllPower",2000,4,0,0,new ChangeAttackPoint(2,2,BuffType.Positive));
        allPower.addSpellTypes(SpellType.allWarrior,SpellType.onFriend,SpellType.onMinionOrHero,SpellType.onTarget);
        shopCards.add(allPower);

        Spell allAttack = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "AllAttack", 1500, 4, 0, 0,new ChangeHealthEffect(-6,1,BuffType.Negative));
        allAttack.addSpellTypes(SpellType.onTarget,SpellType.onMinionOrHero,SpellType.onEnemy,SpellType.onCol);
        shopCards.add(allAttack);

        Spell weakening = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn,SpellKind.spellCard,"Weakening",1000,1,0,0,new ChangeAttackPoint(-4,1,BuffType.Negative));
        weakening.addSpellTypes(SpellType.onTarget,SpellType.onEnemy,SpellType.onMinion);
        shopCards.add(weakening);

//        Card sacrifice = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnMap, SpellKind.spellCard, "Sacrifice", 1600, 3, 0, 0, 0, 0, 0, 0, 0, SpellType.healHeroByMinion, SpellType.onFriend, SpellType.onMinion);
//        shopCards.add(sacrifice);

        Spell kingsGard = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "KingsGard", 1750, 3, 0, 0,new ChangeHealthEffect(-1000,1,BuffType.Negative));
        kingsGard.addSpellTypes(SpellType.NearHero,SpellType.onEnemy,SpellType.onTarget,SpellType.onMinion);
        shopCards.add(kingsGard);

        Spell shock = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn, SpellKind.spellCard, "Shock", 1200, 1, 0, 0,new StunEffect(2,BuffType.Negative));
        shock.addSpellTypes(SpellType.onEnemy,SpellType.onTarget,SpellType.onMinionOrHero);
        shopCards.add(shock);

        Spell divSepidSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnUSe,SpellKind.Buff,"DivSepidSpell",0,1,2,0,new ChangeAttackPoint(4,1000,BuffType.Positive));
        divSepidSpell.addSpellTypes(SpellType.onHero,SpellType.onFriend,SpellType.onTarget);
        Hero divSepid = new Hero("DiveSepid", 4, 50, 8000, AttackType.Melee, 1,divSepidSpell );
        shopCards.add(divSepid);

        Spell simorghSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnUSe,SpellKind.spellCard,"SimorghSpell",0,5,8,0,new StunEffect(1,BuffType.Negative));
        simorghSpell.addSpellTypes(SpellType.onEnemy,SpellType.onTarget,SpellType.allWarrior,SpellType.onMinionOrHero);
        Hero siMorgh = new Hero("Simorgh", 4, 50, 9000, AttackType.Melee, 1,simorghSpell );
        shopCards.add(siMorgh);


        Spell ejdeha7SarSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnUSe,SpellKind.spellCard,"Ejdeha7SarSpell",0,0,1,0,new DisarmEffect(1,BuffType.Negative));
        ejdeha7SarSpell.addSpellTypes(SpellType.onMinionOrHero,SpellType.onEnemy,SpellType.onTarget,SpellType.onFriend);
        Hero ejdeha7Sar = new Hero("Ejdeha7Sar", 4, 50, 8000, AttackType.Melee, 1,ejdeha7SarSpell );
        shopCards.add(ejdeha7Sar);


        Spell rakhshSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnUSe,SpellKind.spellCard,"rakhshSpell",0,1,2,0,new StunEffect(1,BuffType.Negative));
        rakhshSpell.addSpellTypes(SpellType.onMinionOrHero,SpellType.onEnemy,SpellType.onTarget);
        Hero rakhsh = new Hero("Rakhsh", 4, 50, 8000, AttackType.Melee, 1,rakhshSpell );
        shopCards.add(rakhsh);

        Spell zahakSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnAttack,SpellKind.spellCard,"ZahakSpell",0,0,0,0,new ChangeHealthEffect(-1,3,BuffType.Negative));
        zahakSpell.addSpellTypes(SpellType.onEnemy,SpellType.onTarget,SpellType.onMinionOrHero);
        Hero zahhak = new Hero("Zahhak", 2, 50, 10000, AttackType.Melee, 1,zahakSpell );
        shopCards.add(zahhak);

        Spell kavehSpell = new Spell(SpellTarget.CellEffect,SpellActiveTime.OnUSe,SpellKind.spellCard,"KavehSpell",0,1,2,1,new HolyEffect(1,3,BuffType.Positive));
        kavehSpell.addSpellTypes(SpellType.onMinionOrHero,SpellType.onFriend,SpellType.onEnemy,SpellType.onSquare);
        Hero kaveh = new Hero("Kaveh", 4, 50, 8000, AttackType.Melee, 1,kavehSpell );
        shopCards.add(kaveh);

        Spell arashSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnUSe,SpellKind.spellCard,"ArashSpell",0,2,2,0,new ChangeHealthEffect(-4,1,BuffType.Negative)); //cellEffect Or Warrior!!
        arashSpell.addSpellTypes(SpellType.onMinionOrHero,SpellType.onEnemy,SpellType.onRow,SpellType.onTarget);
        Hero arash = new Hero("Arash", 2, 30, 10000, AttackType.Ranged, 6,arashSpell );
        shopCards.add(arash);

        Spell afsanehSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnUSe,SpellKind.spellCard,"AfsanehSpell",0,1,2,0,new DeBuff(1,BuffType.Negative));
        afsanehSpell.addSpellTypes(SpellType.onTarget,SpellType.onEnemy,SpellType.onMinionOrHero);
        Hero afsaneh = new Hero("Afsane", 3, 40, 11000, AttackType.Ranged, 3,afsanehSpell);
        shopCards.add(afsaneh);

        // Spell!
        Hero esfandiar = new Hero("Esfandiar", 3, 35, 12000,AttackType.Hybrid,3,  );
        shopCards.add(esfandiar);

        Hero rostam = new Hero("Rostam",7,55,8000,AttackType.Hybrid,4,null );
        shopCards.add(rostam);

        Minion persianArcher = new Minion("PersianArcher", 2, 4, 6, 300, AttackType.Ranged, 7, null);
        shopCards.add(persianArcher);

        Spell persianSwordSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnAttack,SpellKind.spellCard,"persinaSwordSpell",0,0,0,0,new StunEffect(1,BuffType.Negative));
        persianSwordSpell.addSpellTypes(SpellType.onMinionOrHero,SpellType.onEnemy,SpellType.onTarget);
        Minion persianSword = new Minion("PersianSword", 2, 4, 6, 400, AttackType.Melee, 1,persianSwordSpell,false);
        shopCards.add(persianSword);

        Minion persianSpeared = new Minion("PersianSpeared", 1, 3, 5, 500, AttackType.Hybrid, 3, null,false);
        shopCards.add(persianSpeared);

        Minion persianHorseRider = new Minion("PersianHorseRider", 4, 6, 10, 200, AttackType.Melee, 1, null,false);
        shopCards.add(persianHorseRider);

        // spell!
        Minion persianHero = new Minion("PersianHero",9,6,24,600,AttackType.Melee,1,null,false);
        shopCards.add(persianHero);

        Minion persianGard = new Minion("PersianGard",7,4,12,800,AttackType.Melee,1,null,true);
        shopCards.add(persianGard);

        Minion turanArcher = new Minion("TuranArcher", 1, 4, 3, 500, AttackType.Hybrid, 5, null,false);
        shopCards.add(turanArcher);

        Minion turanStoneThrower = new Minion("TuranStoneThrower", 1, 2, 4, 600, AttackType.Ranged, 7, null,false);
        shopCards.add(turanStoneThrower);

        Minion turanSpear = new Minion("TuranSpear", 1, 4, 4, 600, AttackType.Hybrid, 3, null,false);
        shopCards.add(turanSpear);

        Spell turanSpySpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnAttack,SpellKind.spellCard,"turnaSpySpell",0,0,0,0,new DisarmEffect(1,BuffType.Negative),new ChangeHealthEffect(-4,4,BuffType.Negative));
        turanSpySpell.addSpellTypes(SpellType.onTarget,SpellType.onEnemy,SpellType.onMinionOrHero);
        Minion turanSpy = new Minion("TurnSpy", 4, 6, 6, 700, AttackType.Melee, 1,turanSpySpell,false);
        shopCards.add(turanSpy);

        Minion turanSwampy = new Minion("TuranSwampy", 2, 10, 3, 600, AttackType.Melee, 1, null,false);
        shopCards.add(turanSwampy);

        Minion turanPrince = new Minion("TuranPrince",6,10,6,800,AttackType.Melee,1,null,true);
        shopCards.add(turanPrince);

        Minion blackGiant = new Minion("BlackGiant", 9, 10, 14, 300, AttackType.Hybrid, 7, null,false);
        shopCards.add(blackGiant);

        Minion stoneGiant = new Minion("StoneGiant", 9, 12, 12, 300, AttackType.Ranged, 7, null,false);
        shopCards.add(stoneGiant);

        Spell eagleSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.Passive,SpellKind.spellCard,"eagleSpell", 0, 0, 0, 0,new ChangeHealthEffect(10,1,BuffType.Positive));
        eagleSpell.addSpellTypes(SpellType.onFriend,SpellType.onMinionOrHero,SpellType.onTarget);
        Minion eagle = new Minion("Eagle", 2, 1, 2, 200, AttackType.Ranged, 3,eagleSpell,false);
        shopCards.add(eagle);

        Minion hogRider = new Minion("HogRider", 4, 8, 16, 300, AttackType.Melee, 1, null,false);
        shopCards.add(hogRider);


        Spell oneEyeGiantSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnDeath,SpellKind.spellCard,"oneEyeGiantSpell",0,0,0,0,new ChangeHealthEffect(2,1,BuffType.Negative));
        oneEyeGiantSpell.addSpellTypes(SpellType.onTarget,SpellType.onMinion,SpellType.minionAround,SpellType.onEnemy);
        Minion oneEyeGiant = new Minion("OneEyeGiant",7,11,12,500,AttackType.Hybrid,3,oneEyeGiantSpell,false);
        shopCards.add(oneEyeGiant); // dar mogheii ke mimirad position haye atraf dade shavad

        Spell poisonSnakeSpell = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnAttack,SpellKind.spellCard,"poisonSnakeSpell",0,0,0,0,new ChangeHealthEffect(-1,3,BuffType.Negative));
        poisonSnakeSpell.addSpellTypes(SpellType.onEnemy,SpellType.onMinionOrHero,SpellType.onTarget);
        Minion poisonSnake = new Minion("poisonSnake",4,6,5,0,AttackType.Ranged,4,poisonSnakeSpell,false);
        shopCards.add(poisonSnake);

        Minion fireDragon = new Minion("FireDragon", 5, 5, 9, 250, AttackType.Ranged, 4, null,false);
        shopCards.add(fireDragon);

        // spell!
        Minion lion = new Minion("Lion",2,8,1,600,AttackType.Melee,1,,false);

        Card eraj = new Minion("Eraj", 4, 20, 6, 500, AttackType.Ranged, 3, null);
        shopCards.add(eraj);



        Card bigGiant = new Minion("BigGiant", 9, 8, 30, 600, AttackType.Hybrid, 2, null);
        shopCards.add(bigGiant);





        Minion poisonSnake = new Minion("PoisonSnake", 4, 6, 5, 300, AttackType.Ranged, 4, MinionSpellType.OnAttack);
        poisonSnake.setSpecialSpell(SpellKind.spellCard, 0, 0, 3, 0, 0, 1, 0, -1, SpellType.onEnemy, SpellType.onMinionOrHero, SpellType.HealthPoint);
        shopCards.add(poisonSnake);

    }
}

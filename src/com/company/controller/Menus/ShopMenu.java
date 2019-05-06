package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.CantAddUsableItems;
import com.company.controller.Exceptions.CardNotFound;
import com.company.controller.Exceptions.NotEnoughCash;
import com.company.models.Player;
import com.company.models.battle.Battle;
import com.company.models.widget.cards.Card;
import com.company.models.widget.cards.Warriors.*;
import com.company.models.widget.cards.spells.*;
import com.company.models.widget.cards.spells.effects.*;
import com.company.view.View;

import java.util.ArrayList;
import java.util.Random;

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

    private String buy(String command) throws CardNotFound, NotEnoughCash, CantAddUsableItems, CloneNotSupportedException
    {
        String cardName = command.split(" ")[1];
        Card card = searchCard(cardName);
        if (card == null)
            throw new CardNotFound();

        Player currentPlayerInShop = Controller.getInstance().getCurrentPlayer();
        if (card.getPrice() > currentPlayerInShop.getCash())
            throw new NotEnoughCash();

        if (currentPlayerInShop.getCollection().getNumberOfUsable() == 3)
            throw new CantAddUsableItems();

        Object theCard = null;
        if (card instanceof Spell)
            theCard = ((Spell) card).clone();

        else if (card instanceof Minion)
            theCard = ((Minion) card).clone();
        else if (card instanceof Hero)
            theCard = ((Hero) card).clone();
        else if(card instanceof Passive)
            theCard = ((Passive) card).clone();

        ((Card) theCard).setOwnerPlayer(currentPlayerInShop);
        currentPlayerInShop.getCollection().getCards().add(((Card) theCard));
        currentPlayerInShop.decreaseCash(((Card) theCard).getPrice());

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

    public void initialiseShop()
    {
        Spell totalDisarm = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "TotalDisarm", 1000, 0, 0, 10,
                new Disarm(1000, PON.Negative, ActiveTime.onInsert));
        shopCards.add(totalDisarm);
        //
        Spell removeBadDispel = new Spell(Area.onSquare, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "RemoveBadAreaDispel", 750, 1, 0, 10,
                new DeBuff(1, PON.Positive, ActiveTime.perTurn, BuffType.deBuffNegatives));
        shopCards.add(removeBadDispel);
        //
        Spell removeGoodDispel = new Spell(Area.onSquare, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "RemoveGoodAreaSpell", 750, 1, 0, 10,
                new DeBuff(1, PON.Negative, ActiveTime.perTurn, BuffType.deBuffPositives));
        shopCards.add(removeGoodDispel);
        //
        Spell empower = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Empower", 250, 1, 0, 10,
                new AttackPoint(+2, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(empower);
        //
        Spell fireBall = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "FireBall", 400, 1, 0, 10,
                new HealthPoint(-4, 1, PON.Negative, ActiveTime.perTurn));
        shopCards.add(fireBall);
        //
        Spell godStrength = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onInsert, Type.NormalSpell, "GodStrength", 450, 2, 0, 10,
                new AttackPoint(+4, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(godStrength);
        //
        Spell hellFire = new Spell(Area.onSquare, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "HellFire", 600, 3, 0, 2,
                new HealthPoint(-2, 2, PON.Negative, ActiveTime.perTurn));
        shopCards.add(hellFire);
        //
        Spell lightingBolt = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onHero, ActiveTime.onInsert, Type.NormalSpell, "LightingBolt", 1250, 2, 0, 10,
                new HealthPoint(-8, 1, PON.Negative, ActiveTime.perTurn));
        shopCards.add(lightingBolt);
        //
        Spell poisonLake = new Spell(Area.onSquare, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "PoisonLake", 900, 5, 0, 10,
                new HealthPoint(-1, 1, PON.Negative, ActiveTime.perTurn));
        shopCards.add(poisonLake);
        //
        Spell madness = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Madness", 650, 0, 0, 10,
                new AttackPoint(+4, 3, PON.Positive, ActiveTime.perTurn),
                new Disarm(4, PON.Negative, ActiveTime.perTurn));
        shopCards.add(madness);
        //
        Spell allDisarm = new Spell(Area.allWarrior, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "AllDisarm", 2000, 9, 0, 10,
                new Disarm(2, PON.Negative, ActiveTime.perTurn));
        shopCards.add(allDisarm);
        //
        Spell allPoison = new Spell(Area.allWarrior, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "AllPoison", 1500, 8, 0, 10,
                new HealthPoint(-1, 4, PON.Negative, ActiveTime.perTurn));
        shopCards.add(allPoison);
        //
        Spell removeBadOneDispel = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "RemoveBadOneDispel", 2100, 0, 0, 10,
                new DeBuff(1, PON.Positive, ActiveTime.perTurn, BuffType.deBuffNegatives));
        shopCards.add(removeBadOneDispel);
        //
        Spell removeGoodOneDispel = new Spell(Area.onSquare, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "RemoveGoodOneDispel", 750, 1, 0, 10,
                new DeBuff(1, PON.Negative, ActiveTime.perTurn, BuffType.deBuffPositives));
        shopCards.add(removeGoodDispel);
        //
        Spell healthWithProfit = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "HealthWithProfit", 2250, 0, 0, 10,
                new HealthPoint(-6, 1, PON.Negative, ActiveTime.perTurn),
                new AttackPoint(+2, 3, PON.Positive, ActiveTime.onDefend));
        shopCards.add(healthWithProfit);
        //
        Spell powerUp = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "PowerUp", 2500, 2, 0, 10,
                new AttackPoint(+6, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(powerUp);
        //
        Spell allPower = new Spell(Area.allWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "AllPower", 2000, 4, 0, 10,
                new AttackPoint(+2, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(allPower);
        //
        Spell allAttack = new Spell(Area.onCol, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "AllAttack", 1500, 4, 0, 10,
                new HealthPoint(-6, 1, PON.Negative, ActiveTime.perTurn));
        shopCards.add(allAttack);
        //
        Spell weakening = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinion, ActiveTime.onInsert, Type.NormalSpell, "Weakening", 1000, 1, 0, 10,
                new AttackPoint(-4, 1, PON.Negative, ActiveTime.perTurn);
        shopCards.add(weakening);
        //
        Spell sacrifice = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onInsert, Type.NormalSpell, "Sacrifice", 1600, 2, 0, 10,
                new HealthPoint(-6, 1, PON.Negative, ActiveTime.perTurn),
                new AttackPoint(+8, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(sacrifice);
        //
        Spell kingsGuard = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinion, ActiveTime.onClick, Type.NormalSpell, "KingGuard", 1750, 9, 0, 10,
                new HealthPoint(-100, 1, PON.Negative, ActiveTime.perTurn));
        shopCards.add(kingsGuard);
        //
        Spell shock = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onInsert, Type.NormalSpell, "Shock", 1200, 1, 0, 100,
                new Stun(2, PON.Negative, ActiveTime.perTurn));
        shopCards.add(shock);
        //

        //Minions

        Minion kamandareFars = new Minion("KamandareFars", 2, 4, 6, 300, AttackType.Ranged, 7,
                null);
        shopCards.add(kamandareFars);
        //
        Minion shamshirZaneFars = new Minion("ShamshireZaneFars", 2, 4, 6, 400, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 400, 0, 0, 1,
                        new Stun(1, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(shamshirZaneFars);
        //
        Minion neyzeDareFars = new Minion("NeyzeDareFars", 1, 3, 5, 500, AttackType.Hybrid, 3,
                null);
        shopCards.add(neyzeDareFars);
        //
        Minion asbSavareFars = new Minion("AsbSavareFars", 4, 6, 10, 200, AttackType.Melee, 1,
                null);
        shopCards.add(asbSavareFars);
        //
        Minion pahlavaneFars = new Minion("PahlavaneFars", 9, 6, 24, 600, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                        new HealthPoint(Battle.getInstance().getTurnHandler().getTurnNumber() / 2, 1, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(pahlavaneFars);
        //
        Minion sepahSalar = new Minion("SepahSalarFars", 7, 4, 12, 800, AttackType.Melee, 1,
                null);
        shopCards.add(sepahSalar);
        //
        Minion kamandareToorani = new Minion("KamandareToorani", 1, 4, 3, 500, AttackType.Ranged, 5,
                null);
        shopCards.add(kamandareToorani);
        //
        Minion gholabeToorani = new Minion("GholabSangdareToorani", 1, 2, 4, 600, AttackType.Ranged, 7,
                null);
        shopCards.add(gholabeToorani);
        //
        Minion neyzedareToorani = new Minion("NeyzeDareToorani", 1, 4, 4, 600, AttackType.Hybrid, 3,
                null);
        shopCards.add(neyzedareToorani);
        //
        Minion jasoseToorani = new Minion("JasooseToorani", 4, 6, 6, 700, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                        new Disarm(1, PON.Negative, ActiveTime.perTurn), new HealthPoint(-1, 4, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(jasoseToorani);
        //
        Minion gorzDareToorani = new Minion("GorzDareToorani", 2, 10, 3, 450, AttackType.Melee, 1,
                null);
        shopCards.add(gorzDareToorani);
        //
        Minion ghooleTakCheshm = new Minion("GhooleTakCheshm", 7, 12, 11, 0, AttackType.Hybrid, 3,
                new Spell(Area.onAround, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onDeath, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                        new HealthPoint(-2, 1, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(ghooleTakCheshm);
        //
        Minion mareSammi = new Minion("MareSammi", 4, 6, 5, 300, AttackType.Ranged, 4,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 0, 0, 0, 0,
                        new HealthPoint(-1, 3, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(mareSammi);
        //
        Minion ejdehayeAtashAndaz = new Minion("EjdedhaeAtashAndaz", 5, 5, 9, 250, AttackType.Ranged, 4,
                null);
        shopCards.add(ejdehayeAtashAndaz);
        //
        Minion shireDarandeh = new Minion("ShireDarandeh", 2, 8, 1, 600, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                        new DeBuff(1, PON.Negative, ActiveTime.perTurn, BuffType.deBuffHoly)));
        shopCards.add(shireDarandeh);
        //
        Minion gorazeVahshi = new Minion("GorazeVahshi", 6, 14, 10, 500, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onDefend, Type.SpecialSpell, "SP", 500, 0, 0, 10,
                        new DeBuff(2, PON.Positive, ActiveTime.perTurn, BuffType.deBuffDisarm)));
        shopCards.add(gorazeVahshi);
        //
        Minion piran = new Minion("Piran", 8, 12, 8, 400, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onDefend, Type.SpecialSpell, "SP", 400, 0, 0, 1,
                        new DeBuff(1, PON.Positive, ActiveTime.perTurn, BuffType.deBuffPoison)));
        shopCards.add(piran);
        //
        Minion giv = new Minion("Giv", 4, 7, 5, 450, AttackType.Ranged, 5,
                new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onDefend, Type.SpecialSpell, "SP", 0, 0, 0, 10,
                        new DeBuff(1, PON.Positive, ActiveTime.perTurn, BuffType.deBuffNegatives)));
        shopCards.add(giv);
        //
        Minion bahman = new Minion("Bahman", 8, 16, 9, 450, AttackType.Melee, 1,
                new Spell(Area.randomWarrior, FOE.enemy, TargetType.onMinion, ActiveTime.onInsert, Type.SpecialSpell, "SP", 0, 0, 0, 10,
                        new HealthPoint(-16, 1, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(bahman);
        //
        Minion seyed = new Minion("Seyyed", 7, 8, 14, 400, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onDefend, Type.SpecialSpell, "SP", 0, 0, 0, 10,
                        new Stun(2, PON.Negative, ActiveTime.perTurn)));
        //kasi behesh attack bede stun mishe
        shopCards.add(seyed);
        //
        Minion ashkBoos = new Minion("AshkBoos", 7, 8, 14, 400, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onDefend, Type.SpecialSpell, "SP", 0, 0, 0, 1,
                        new HealthPoint(-2, 1, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(ashkBoos);

        //////////////////Heros

        Hero diveSepid = new Hero("DiveSepid", 4, 50, 8000, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onClick,
                        Type.SpecialSpell, "SP", 0, 1, 2, 10,
                        new AttackPoint(+4, 1, PON.Positive, ActiveTime.perTurn)));
        shopCards.add(diveSepid);

        //
        Hero simorgh = new Hero("Simorgh", 4, 50, 9000, AttackType.Melee, 1,
                new Spell(Area.allWarrior, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onClick,
                        Type.SpecialSpell, "SP", 0, 5, 8, 10,
                        new Stun(1, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(simorgh);

        //
        Hero ejdeha7Sar = new Hero("Ejdeha7sar", 4, 50, 8000, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                        Type.SpecialSpell, "SP", 0, 0, 2, 1,
                        new Disarm(2, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(ejdeha7Sar);

        //
        Hero rakhsh = new Hero("Rakhsh", 4, 50, 8000, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                        Type.SpecialSpell, "SP", 0, 1, 2, 1,
                        new Stun(2, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(rakhsh);

        //
        Hero zahhak = new Hero("Zahhak", 2, 50, 10000, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                        Type.SpecialSpell, "SP", 0, 0, 0, 1,
                        new HealthPoint(-1, 3, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(zahhak);

        //
        Hero kaveh = new Hero("kaveh", 4, 50, 8000, AttackType.Melee, 1,
                new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onInsert,
                        Type.SpecialSpell, "SP", 0, 1, 3, 10,
                        new HealthPoint(+1, 3, PON.Positive, ActiveTime.onAttack)));
        shopCards.add(kaveh);

        //
        Hero arash = new Hero("Arash", 2, 30, 10000, AttackType.Ranged, 6,
                new Spell(Area.onRow, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onClick,
                        Type.SpecialSpell, "SP", 0, 2, 2, 10,
                        new HealthPoint(-4, 1, PON.Negative, ActiveTime.perTurn)));
        shopCards.add(arash);

        //
        Hero afsaneh = new Hero("Afsane", 3, 40, 11000, AttackType.Ranged, 3,
                new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                        Type.SpecialSpell, "SP", 0, 1, 2, 1,
                        new DeBuff(1, PON.Negative, ActiveTime.perTurn, BuffType.deBuffPositives)));
        shopCards.add(afsaneh);

        //
        Hero esfandiar = new Hero("Esfandiar", 3, 35, 12000, AttackType.Hybrid, 3,
                new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onDefend,
                        Type.SpecialSpell, "SP", 0, 0, 6, 1,
                        new HealthPoint(+1, 3, PON.Positive, ActiveTime.onDefend, true)));
        shopCards.add(esfandiar);

        //
        Hero rostam = new Hero("Rostam", 7, 55, 8000, AttackType.Hybrid, 4, null);
        shopCards.add(rostam);

        // Passives And Collectibles

        Spell tajeDanaiSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.Passive,
                Type.Usable, "TajeDanai", 0, 0, 1000, 10,
                new Mana(+1, 3, PON.Positive, ActiveTime.perTurn));
        Passive tajeDanai = new Passive("TajeDanai", 300, tajeDanaiSpell, FOE.friend, TargetType.onPlayer);
        shopCards.add(tajeDanai);

        //
        Spell namooseSeparSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.Passive,
                Type.Usable, "NamooseSepar", 0, 0, 1000, 10,
                new HealthPoint(+2, 6, PON.Positive, ActiveTime.perTurn, true));
        Passive namooseSepar = new Passive("NamooseSepar", 4000, namooseSeparSpell, FOE.friend, TargetType.onHero);
        shopCards.add(namooseSepar);

        //
        Spell kamaneDamoolSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onAttack,
                Type.Usable, "KamaneDamool", 0, 0, 1000, 10,
                new Disarm(2, PON.Negative, ActiveTime.onAttack, true));
        Passive kamaneDamool = new Passive("KamaneDamool", 30000, kamaneDamoolSpell, FOE.friend, TargetType.onHero);
        shopCards.add(kamaneDamool);

        //
        Spell nooshDaroo = new Spell(Area.randomWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onClick,
                Type.Collectible, "NooshDaroo", 0, 0, 0, 10,
                new HealthPoint(+6, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(nooshDaroo);

        //
        Spell tire2Sakh = new Spell(Area.randomWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onClick,
                Type.Collectible, "TireDoSakh", 0, 0, 0, 10,
                new AttackPoint(+2, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(tire2Sakh);

        //
        Spell pareSimorghSpell = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onHero, ActiveTime.onAttack,
                Type.Usable, "PareSimorgh", 0, 0, 0, 10,
                new AttackPoint(-1, 1, PON.Negative, ActiveTime.perTurn));
        Passive pareSimorgh = new Passive("PareSimorgh", 3500, pareSimorghSpell, FOE.enemy, TargetType.onHero);
        shopCards.add(pareSimorgh);

        //
        Spell elksir = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onClick,
                Type.Collectible, "Elksir", 0, 0, 0, 10,
                new HealthPoint(+3, 1, PON.Positive, ActiveTime.perTurn),
                new AttackPoint(+3, 1, PON.Positive, ActiveTime.perTurn);
        shopCards.add(elksir);

        //
        Spell majooneMana = new Spell(Area.onOneTarget, FOE.friend, TargetType.onPlayer, ActiveTime.onClick,
                Type.Collectible, "MajooneMana", 0, 0, 0, 10,
                new Mana(+3, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(majooneMana);

        //
        Spell rooyinTan = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onClick,
                Type.Collectible, "MajooneRooyinTan", 0, 0, 0, 10,
                new HealthPoint(+5, 4, PON.Positive, ActiveTime.onDefend));
        shopCards.add(rooyinTan);

        //
        Spell nefrineMarg = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onHero, ActiveTime.onClick,
                Type.Collectible, "NefrineMarg", 0, 0, 0, 10,
                new HealthPoint(-8, 1, PON.Negative, ActiveTime.perTurn));
        shopCards.add(nefrineMarg);

        //
        Spell randomDamage = new Spell(Area.randomWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onClick,
                Type.Collectible, "RandomDamage", 0, 0, 0, 10,
                new AttackPoint(+2, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(randomDamage);

        //
        Spell terrorHoodSpell = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                Type.Usable, "TerrorHood", 0, 0, 0, 10,
                new AttackPoint(-1, 1, PON.Negative, ActiveTime.perTurn));
        Passive terrorHood = new Passive("TerrorHood", 5000, terrorHoodSpell, FOE.friend, TargetType.onMinionOrHero);
        shopCards.add(terrorHood);

        //
        Spell bladesOfAgility = new Spell(Area.randomWarrior, FOE.friend, TargetType.onMinionOrHero, ActiveTime.onClick,
                Type.Collectible, "BladesOfAgility", 0, 0, 0, 10,
                new AttackPoint(+6, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(bladesOfAgility);

        //
        Spell kingWisdomSpell = new Spell(Area.allWarrior, FOE.friend, TargetType.onHero, ActiveTime.Passive,
                Type.Usable, "KingWisdom", 0, 0, 0, 10,
                new Mana(+1, 100, PON.Positive, ActiveTime.perTurn, true));
        Passive kingWisdom = new Passive("KingWisdom", 9000, kingWisdomSpell, FOE.friend, TargetType.onPlayer);
        shopCards.add(kingWisdom);

        //
        Spell poisonousDaggerSpell = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                Type.Usable, "PoisonousDagger", 0, 0, 0, 0,
                new HealthPoint(-1, 1, PON.Negative, ActiveTime.perTurn));
        Passive poisonousDagger = new Passive("PoisonousDagger", 7000, poisonousDaggerSpell, FOE.friend, TargetType.onMinionOrHero);
        shopCards.add(poisonousDagger);

        //
        Spell shockHammerSpell = new Spell(Area.onOneTarget, FOE.enemy, TargetType.onMinionOrHero, ActiveTime.onAttack,
                Type.Usable, "ShockHammer", 0, 0, 0, 1,
                new Disarm(2, PON.Negative, ActiveTime.perTurn));
        Passive shockHammer = new Passive("ShockHammer", 15000, shockHammerSpell, FOE.friend, TargetType.onHero);
        shopCards.add(shockHammer);

        //
        Spell soulEaterSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onDeath,
                Type.Usable, "SoulEater", 0, 0, 0, 10,
                new HealthPoint(+1, 1, PON.Positive, ActiveTime.perTurn));
        Passive soulEater = new Passive("SoulEater", 25000, soulEaterSpell, FOE.friend, TargetType.onMinionOrHero);
        shopCards.add(soulEater);

        //
        Spell ghosleTamidSpell = new Spell(Area.onOneTarget, FOE.friend, TargetType.onMinion, ActiveTime.onInsert,
                Type.Usable, "GhosleTamid", 0, 0, 0, 10,
                new HealthPoint(+1, 2, PON.Positive, ActiveTime.onDefend));
        Passive ghosleTamid = new Passive("GhosleTamid", 20000, ghosleTamidSpell, FOE.friend, TargetType.onMinion);
        shopCards.add(ghosleTamid);

        //
        Spell shamshireChini = new Spell(Area.onOneTarget, FOE.friend, TargetType.onHero, ActiveTime.onClick,
                Type.Collectible, "ShamshireChini", 0, 0, 0, 10,
                new AttackPoint(+5, 1, PON.Positive, ActiveTime.perTurn));
        shopCards.add(shamshireChini);

        //
    }
}

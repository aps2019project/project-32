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
import com.company.models.widget.cards.spells.effects.ChangeHealthEffect;
import com.company.models.widget.cards.spells.effects.HolyEffect;
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
            theCard = new Spell((Spell) card, true);
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


        Card totalDisarm = new Spell(SpellActiveTime.OnRespawn,SpellKind.spellCard, "TotalDisarm", 1000, 0, 0, 0, 1000, 0, 0, 0, 0, SpellType.Disarm, SpellType.onEnemy, SpellType.onMinionOrHero);
        shopCards.add(totalDisarm);

        Card areaSpell = new Spell(SpellActiveTime.OnRespawn,SpellKind.spellCard, "AreaSpell", 1500, 2, 0, 0, 0, 0, 2, 0, 0, SpellType.inactiveBuffs, SpellType.cellEffect);
        shopCards.add(areaSpell);

        Card emPower = new Spell(SpellActiveTime.OnRespawn,SpellKind.spellCard, "Empower", 250, 1, 0, 0, 0, 0, 0, 2, 0, SpellType.onFriend, SpellType.AttackPoint, SpellType.onMinionOrHero);
        shopCards.add(emPower);

        Card fireBall = new Spell(SpellActiveTime.OnRespawn,SpellKind.spellCard, "FireBall", 400, 1, 0, 0, 0, 0, 0, 0, -4, SpellType.onEnemy, SpellType.HealthPoint, SpellType.onMinionOrHero);
        shopCards.add(fireBall);

        Card godStrength = new Spell(SpellActiveTime.OnRespawn,SpellKind.spellCard, "GodStrength", 450, 2, 0, 0, 0, 0, 0, 4, 0, SpellType.onFriend, SpellType.onHero, SpellType.AttackPoint);
        shopCards.add(godStrength);

        Card hellFire = new Spell(SpellActiveTime.OnRespawn,SpellKind.Buff, "HellFire", 600, 3, 0, 2, 0, 0, 2, 0, -2, SpellType.cellEffect, SpellType.HealthPoint);
        shopCards.add(hellFire);


        Card lightingBolt = new Spell(SpellActiveTime.OnMap,SpellKind.spellCard, "LightingBolt", 1250, 2, 0, 0, 0, 0, 0, 0, -8, SpellType.onEnemy, SpellType.onHero, SpellType.HealthPoint);
        shopCards.add(lightingBolt);

        Card poisonLake = new Spell(SpellActiveTime.OnMap,SpellKind.spellCard, "PoisonLake", 900, 5, 0, 1, 0, 0, 3, 0, -1, SpellType.cellEffect, SpellType.HealthPoint);
        shopCards.add(poisonLake);

        Card madness = new Spell(SpellActiveTime.OnMap,SpellKind.spellCard, "Madness", 650, 0, 0, 4, 3, 0, 0, 4, 0, SpellType.onFriend, SpellType.Disarm, SpellType.AttackPoint, SpellType.onMinionOrHero);
        shopCards.add(madness);

        Card allDisarm = new Spell(SpellActiveTime.OnMap,SpellKind.spellCard, "AllDisarm", 2000, 9, 0, 0, 1, 0, 0, 0, 0, SpellType.allEnemy, SpellType.Disarm);
        shopCards.add(allDisarm);

        Card allPoison = new Spell(SpellActiveTime.OnMap,SpellKind.spellCard, "AllPoison", 1500, 8, 0, 4, 0, 0, 0, 0, -1, SpellType.allEnemy, SpellType.HealthPoint);
        shopCards.add(allPoison);

        //Card dispel = new Spell("Dispel",2100,0,0,0,0,0,0,0,0,SpellType.onEnemyOrFriend,SpellType.inactiveBuffs);
        //add(dispel);

        Spell healthWithProfit = new Spell(SpellTarget.OnWarrior,SpellActiveTime.OnRespawn,"HealthWithProfit",2250,0,0,0,new HolyEffect(+2,3),new ChangeHealthEffect(-6,1));
        healthWithProfit.addSpellTypes(SpellType.onFriend,SpellType.onMinionOrHero);
        //Card ghazaBokhorJoonBegiri = new Spell() *buff*

        //Card allPower = new Spell() *buff*

        Card allAttack = new Spell(SpellActiveTime.OnMap,SpellKind.spellCard, "AllAttack", 1500, 4, 0, 0, 0, 0, 0, 0, -6, SpellType.onCol, SpellType.onEnemy, SpellType.HealthPoint, SpellType.onMinionOrHero);
        shopCards.add(allAttack);

        //Card weakening = new Spell() *buff*
        Card sacrifice = new Spell(SpellActiveTime.OnMap,SpellKind.spellCard, "Sacrifice", 1600, 3, 0, 0, 0, 0, 0, 0, 0, SpellType.healHeroByMinion, SpellType.onFriend, SpellType.onMinion);
        shopCards.add(sacrifice);

        Card kingsGard = new Spell(SpellActiveTime.OnMap,SpellKind.spellCard, "KingsGard", 1750, 3, 0, 0, 0, 0, 0, 0, -1000, SpellType.onMinionNearHero, SpellType.onEnemy, SpellType.HealthPoint);
        shopCards.add(kingsGard);

        Card shock = new Spell(SpellActiveTime.OnMap,SpellKind.spellCard, "Shock", 1200, 1, 0, 0, 0, 2, 0, 0, 0, SpellType.onEnemy, SpellType.Stun, SpellType.onMinionOrHero);
        shopCards.add(shock);

        Hero rakhsh = new Hero("rakhsh", 4, 50, 8000, AttackType.Melee, 1);
        rakhsh.setSpecialSpell(SpellKind.spellCard, 2, 2, 0, 0, 1, 0, 0, 0, SpellType.onEnemy, SpellType.onMinionOrHero);
        shopCards.add(rakhsh);

        Hero dragon = new Hero("Dragon", 4, 50, 8000, AttackType.Melee, 1);
        dragon.setSpecialSpell(SpellKind.spellCard, 1, 2, 0, 0, 1, 10, 0, 0, SpellType.onEnemy);
        shopCards.add(dragon);

        Hero whiteGiant = new Hero("WhiteGiant", 4, 50, 8000, AttackType.Melee, 1);
        whiteGiant.setSpecialSpell(SpellKind.Buff, 2, 2, 1000, 0, 0, 0, 4, 0, SpellType.onFriend, SpellType.onHero);
        shopCards.add(whiteGiant);

        Card persianArcher = new Minion("PersianArcher", 2, 4, 6, 300, AttackType.Ranged, 7, null);
        shopCards.add(persianArcher);

        Card persianSpeared = new Minion("PersianSpeared", 1, 3, 5, 500, AttackType.Hybrid, 3, null);
        shopCards.add(persianSpeared);

        Minion persianSword = new Minion("PersianSword", 2, 4, 6, 400, AttackType.Melee, 1, MinionSpellType.OnAttack);
        persianSword.setSpecialSpell(SpellKind.spellCard, 0, 0, 0, 0, 1, 1, 0, 0, SpellType.onEnemy, SpellType.onMinionOrHero);
        shopCards.add(persianSword);

        Card eraj = new Minion("Eraj", 4, 20, 6, 500, AttackType.Ranged, 3, null);
        shopCards.add(eraj);

        Card bigGiant = new Minion("BigGiant", 9, 8, 30, 600, AttackType.Hybrid, 2, null);
        shopCards.add(bigGiant);

        Card fireDragon = new Minion("FireDragon", 5, 5, 9, 250, AttackType.Ranged, 4, null);
        shopCards.add(fireDragon);

        Card hogRider = new Minion("HogRider", 4, 8, 16, 300, AttackType.Melee, 1, null);
        shopCards.add(hogRider);

        Card stoneGiant = new Minion("StoneGiant", 9, 12, 12, 300, AttackType.Ranged, 7, null);
        shopCards.add(stoneGiant);

        Card blackGiant = new Minion("BlackGiant", 9, 10, 14, 300, AttackType.Hybrid, 7, null);
        shopCards.add(blackGiant);

        Card turanSwampy = new Minion("TuranSwampy", 2, 10, 3, 600, AttackType.Melee, 1, null);
        shopCards.add(turanSwampy);

        Minion turanSpear = new Minion("TuranSpear", 1, 4, 4, 600, AttackType.Hybrid, 3, null);
        shopCards.add(turanSpear);

        Card turanStoneThrower = new Minion("TuranStoneThrower", 1, 2, 4, 600, AttackType.Ranged, 7, null);
        shopCards.add(turanStoneThrower);

        Card turanArcher = new Minion("TuranArcher", 1, 4, 3, 500, AttackType.Hybrid, 5, null);
        shopCards.add(turanArcher);

        Card persianHorseRider = new Minion("PersianHorseRider", 4, 6, 10, 200, AttackType.Melee, 1, null);
        shopCards.add(persianHorseRider);

        Card turanSpy = new Minion("TurnSpy", 4, 6, 6, 700, AttackType.Melee, 1, MinionSpellType.OnAttack);
        turanSpear.setSpecialSpell(SpellKind.spellCard, 0, 0, 4, 1, 0, 1, 0, -1, SpellType.onEnemy, SpellType.onMinionOrHero, SpellType.HealthPoint);
        shopCards.add(turanSpy);

        Minion eagle = new Minion("Eagle", 2, 1, 2, 200, AttackType.Ranged, 3, MinionSpellType.Passive);
        eagle.setSpecialSpell(SpellKind.Buff, 0, 0, 0, 0, 0, 1, 0, 10, SpellType.onFriend, SpellType.onMinionOrHero, SpellType.HealthPoint);
        shopCards.add(eagle);

        Minion poisonSnake = new Minion("PoisonSnake", 4, 6, 5, 300, AttackType.Ranged, 4, MinionSpellType.OnAttack);
        poisonSnake.setSpecialSpell(SpellKind.spellCard, 0, 0, 3, 0, 0, 1, 0, -1, SpellType.onEnemy, SpellType.onMinionOrHero, SpellType.HealthPoint);
        shopCards.add(poisonSnake);

    }
}

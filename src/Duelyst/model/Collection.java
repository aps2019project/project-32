package Duelyst.model;

import Duelyst.model.Card.Hero.Arash;
import Duelyst.model.Card.Hero.Hero;
import Duelyst.model.Card.Hero.WhiteBogey;
import Duelyst.model.Card.Hero.Zahhak;
import Duelyst.model.Card.Minion.*;
import Duelyst.model.Card.Spell.*;
import Duelyst.model.Item.UsableItem.CrownOfWisdom;
import Duelyst.model.Item.UsableItem.SoulEater;
import Duelyst.model.Item.UsableItem.TerrorHood;
import Duelyst.model.Item.UsableItem.UsableItem;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Hero> heroes = new ArrayList<>();
    private ArrayList<Spell> spells = new ArrayList<>();
    private ArrayList<Minion> minions = new ArrayList<>();
    private ArrayList<UsableItem> usableItems = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private ArrayList<Deck> storyModeDeck = new ArrayList<>();//should be added

    {
        Deck theDeck = new Deck();
        theDeck.getMinions().add(new PersianArcher());
        theDeck.getMinions().add(new TuranianSpear());
        theDeck.getMinions().add(new TuranianSwampy());
        theDeck.getMinions().add(new TuranianSwampy());
        theDeck.getMinions().add(new BlackBogey());
        theDeck.getMinions().add(new OneEyeGiant());
        theDeck.getMinions().add(new PoisonSnake());
        theDeck.getMinions().add(new GiantSnake());
        theDeck.getMinions().add(new WhiteWolf());
        theDeck.getMinions().add(new GiantMagician());
        theDeck.getMinions().add(new ColdGrandma());
        theDeck.getMinions().add(new Siavash());
        theDeck.getMinions().add(new ArzhangBogey());
        theDeck.getSpells().add(new TotalDisarm());
        theDeck.getSpells().add(new LightingBolt());
        theDeck.getSpells().add(new AllDisarm());
        theDeck.getSpells().add(new AllPoison());
        theDeck.getSpells().add(new Dispel());
        theDeck.getSpells().add(new Sacrifice());
        theDeck.getSpells().add(new Shock());
        theDeck.getHero().add(new WhiteBogey());
        theDeck.getUsableItem().add(new CrownOfWisdom());
        Deck deck2 = new Deck();
        deck2.getMinions().add(new PersianSwordsman());
        deck2.getMinions().add(new PersianSpear());
        deck2.getMinions().add(new PersianGladiator());
        deck2.getMinions().add(new TuranianStoneHook());
        deck2.getMinions().add(new TuranianPrince());
        deck2.getMinions().add(new Eagle());
        deck2.getMinions().add(new FieryDragon());
        deck2.getMinions().add(new Panther());
        deck2.getMinions().add(new Elf());
        deck2.getMinions().add(new Giv());
        deck2.getMinions().add(new Iraj());
        deck2.getMinions().add(new GiantKing());
        deck2.getMinions().add(new Eagle());
        deck2.getMinions().add(new FieryDragon());
        deck2.getMinions().add(new Panther());
        deck2.getMinions().add(new Elf());
        deck2.getMinions().add(new Giv());
        deck2.getMinions().add(new Iraj());
        deck2.getMinions().add(new GiantKing());

        deck2.getHero().add(new Zahhak());
        deck2.getUsableItem().add(new SoulEater());
        Deck deck3 = new Deck();
        deck3.getMinions().add(new PersianGeneralissimo());
        deck3.getMinions().add(new TuranianArcher());
        deck3.getMinions().add(new TuranianSpy());
        deck3.getMinions().add(new CatapultGiant());
        deck3.getMinions().add(new HogRiderBogey());
        deck3.getMinions().add(new HogRiderBogey());
        deck3.getMinions().add(new LupinLion());
        deck3.getMinions().add(new Wolf());
        deck3.getMinions().add(new Elf());
        deck3.getMinions().add(new WildHog());
        deck3.getMinions().add(new Piran());
        deck3.getMinions().add(new Bahman());
        deck3.getMinions().add(new GiantColossus());
        deck3.getSpells().add(new HellFire());
        deck3.getSpells().add(new AllDisarm());
        deck3.getSpells().add(new Dispel());
        deck3.getSpells().add(new PowerUp());
        deck3.getSpells().add(new AllPower());
        deck3.getSpells().add(new AllAttack());
        deck3.getSpells().add(new Weakening());
        deck3.getHero().add(new Arash());
        deck3.getUsableItem().add(new TerrorHood());
        getStoryModeDeck().add(theDeck);
        getStoryModeDeck().add(deck2);
        getStoryModeDeck().add(deck3);
    }

    public ArrayList<Deck> getStoryModeDeck() {
        return storyModeDeck;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }


    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Spell> spells) {
        this.spells = spells;
    }

    public ArrayList<Minion> getMinions() {
        return minions;
    }

    public void setMinions(ArrayList<Minion> minions) {
        this.minions = minions;
    }

    public ArrayList<UsableItem> getUsableItems() {
        return usableItems;
    }

    public void setUsableItems(ArrayList<UsableItem> usableItems) {
        this.usableItems = usableItems;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    public void addDeck(Deck deck) {
        this.decks.add(deck);
    }

    public void addToCollection(String cardName) {
        for (Hero hero : Hero.getHeroes()) {
            if (hero.getName().equals(cardName)) {
                Hero hero1 = hero.duplicate();
                this.heroes.add(hero1);
                return;
            }
        }
        for (Spell spell : Spell.getSpells()) {
            if (spell.getName().equals(cardName)) {
                Spell spell1 = spell.duplicate();
                this.spells.add(spell1);
                return;
            }
        }
        for (Minion minion : Minion.getMinions()) {
            if (minion.getName().equals(cardName)) {
                Minion minion1 = minion.duplicate();
                this.minions.add(minion1);
                return;
            }
        }
        for (UsableItem usableItem : UsableItem.getUsableItems()) {
            if (usableItem.getName().equals(cardName)) {
                UsableItem usableItem1 = usableItem.duplicate();

                this.usableItems.add(usableItem1);
                return;
            }
        }
    }

    public ArrayList<Long> search(String objectName) {
        ArrayList<Long> ids = new ArrayList<>();
        for (Hero hero : getHeroes()) {
            if (hero.getName().equals(objectName)) {
                ids.add(hero.getId());
            }
        }
        for (Spell spell : getSpells()) {
            if (spell.getName().equals(objectName)) {
                ids.add(spell.getId());
            }
        }
        for (Minion minion : getMinions()) {
            if (minion.getName().equals(objectName)) {
                ids.add(minion.getId());
            }
        }
        for (UsableItem usableItem : getUsableItems()) {
            if (usableItem.getName().equals(objectName)) {
                ids.add(usableItem.getId());
            }
        }
        return ids;
    }

    public void createDeck(String deckName) {
        Deck newDeck = new Deck();
        newDeck.setName(deckName);
        getDecks().add(newDeck);

    }
    public void removeCardFromCollection(String cardName) {
        this.getHeroes().removeIf(hero -> hero.getName().equals(cardName) );
        this.getSpells().removeIf(spell -> spell.getName().equals(cardName) );
        this.getMinions().removeIf(minion -> minion.getName().equals(cardName) );
        this.getUsableItems().removeIf(item -> item.getName().equals(cardName) );
        for (Deck deck : this.getDecks()) {
            removeFromDeck(cardName, deck.getName());
        }
    }

    public void removeFromDeck(String cardName, String deckName) {
        Deck deck = findDeck(deckName);

        deck.getHero().removeIf(hero -> hero.getName().equals(cardName));
        deck.getUsableItem().removeIf(item -> item.getName().equals(cardName));
        deck.getSpells().removeIf(spell -> spell.getName().equals(cardName));
        deck.getMinions().removeIf(minion -> minion.getName().equals(cardName));
    }

    public void deleteDeck(String deckName) {
        getDecks().removeIf(deck -> deck.getName().equals(deckName));
    }

    public Deck findDeck(String name) {
        for (Deck deck : getDecks()) {
            if (deck.getName().equals(name)) {
                return deck;
            }
        }
        return null;
    }

    public boolean hasThisCard(int objectID) {
        for (Hero hero : this.getHeroes()) {
            if (hero.getId() == objectID) {
                return true;
            }
        }
        for (Spell spell : this.getSpells()) {
            if (spell.getId() == objectID) {
                return true;
            }
        }
        for (Minion minion : this.getMinions()) {
            if (minion.getId() == objectID) {
                return true;
            }
        }
        for (UsableItem usableItem : this.getUsableItems()) {
            if (usableItem.getId() == objectID) {
                return true;
            }
        }
        return false;
    }

    public boolean hasThisCard(String objectName) {
        for (Hero hero : getHeroes()) {
            if (hero.getName().equals(objectName)) {
                return true;
            }
        }
        for (Spell spell : getSpells()) {
            if (spell.getName().equals(objectName)) {
                return true;
            }
        }
        for (Minion minion : getMinions()) {
            if (minion.getName().equals(objectName)) {
                return true;
            }
        }
        for (UsableItem usableItem : getUsableItems()) {
            if (usableItem.getName().equals(objectName)) {
                return true;
            }
        }
        return false;
    }

    public void addToDeck(String cardName, String deckName) {
        Deck deck = findDeck(deckName);
        for (Hero hero : getHeroes()) {
            if (hero.getName().equals(cardName)) {
                deck.getHero().add(hero);
                return;
            }
        }
        for (Spell spell : getSpells()) {
            if (spell.getName().equals(cardName)) {
                deck.getSpells().add(spell);
                return;
            }
        }
        for (Minion minion : getMinions()) {
            if (minion.getName().equals(cardName)) {
                deck.getMinions().add(minion);
                return;
            }
        }
        for (UsableItem usableItem : getUsableItems()) {
            if (usableItem.getName().equals(cardName)) {
                deck.getUsableItem().add(usableItem);
                return;
            }
        }
    }


    public boolean checkDeckValidation(String deckName) {
        Deck deck = findDeck(deckName);
        if (deck.isValid())
            return true;
        else
            return false;
    }

    public void selectDeck(String deckName) {
        Deck deck = findDeck(deckName);
        Player.getLoginAccount().setMainDeck(deck);
    }



}

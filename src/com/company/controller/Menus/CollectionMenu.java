package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.*;
import com.company.models.Player;
import com.company.models.widget.cards.Card;

public class CollectionMenu implements AbstractMenu
{
    private CollectionMenu()
    {
    }

    private static CollectionMenu collectionMenuInstance = new CollectionMenu();

    public static AbstractMenu getInstance()
    {
        return collectionMenuInstance;
    }

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
    public void selectOptionByCommand(String command) throws InvalidDeck, DeckNameAlreadyExist, CardNotFoundInCollection, DeckNotFound, DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready {
        if (command.matches("exit"))
        {
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
        }
        else if (command.matches("show"))
        {
            Controller.getInstance().getCurrentPlayer().getCollection().toShowCollection(); // sendto view
        }
        else if (command.matches("search \\w+"))
        {
            String widgetName = command.split(" ")[1];
            Controller.getInstance().getCurrentPlayer().getCollection().toShowSearchResult(widgetName); // sendto view
        }
        else if (command.matches("create deck \\w+"))
        {
            String deckName = command.split(" ")[2];
            if (Controller.getInstance().getCurrentPlayer().findDeck(deckName) == null)
                Controller.getInstance().getCurrentPlayer().addNewDeck(deckName);
            else
                throw new DeckNameAlreadyExist();
        }
        else if (command.matches("delete deck \\w+"))
        {
            String deckName = command.split(" ")[2];
            Controller.getInstance().getCurrentPlayer().removeDeck(deckName);
        }
        else if (command.matches("add \\w+ to deck \\w+"))
        {
            String cardName = command.split(" ")[1];
            String deckName = command.split(" ")[4];
            int cardID = Controller.getInstance().getCurrentPlayer().getCollection().returnIdCardByName(cardName);
            addCard(cardID, deckName);
        }
        else if (command.matches("remove \\w+ from deck \\w+"))
        {
            String cardName = command.split(" ")[1];
            String deckName = command.split(" ")[4];
            int cardID = Controller.getInstance().getCurrentPlayer().getCollection().returnIdCardByName(cardName);
            removeCardFromDeck(cardID,deckName);
        }
        else if (command.matches("validate deck \\w+"))
        {
            String deckName = command.split(" ")[2];
            if (!Controller.getInstance().getCurrentPlayer().findDeck(deckName).isValidDeck())
                throw new InvalidDeck();
        }
        else if (command.matches("select deck \\w+"))
        {
            String deckName = command.split(" ")[2];
            selectDeckAsMainDeck(deckName);
        }
        else if (command.matches("show all decks"))
        {
            Controller.getInstance().getCurrentPlayer().toShowAllDecks(); // send to view
        }
        else if (command.matches("show deck \\w+"))
        {
            String deckName = command.split(" ")[2];
            Player.Deck intendedDeck = Controller.getInstance().getCurrentPlayer().findDeck(deckName);
            intendedDeck.toShowDeck(); // send to view
        }
        else if (command.matches("help"))
        {
            collectionMenuInstance.help(); // send to view
        }
        else if (command.matches("save"))
            collectionMenuInstance.save();
    }

    private void selectDeckAsMainDeck(String deckName) throws DeckNameAlreadyExist, InvalidDeck
    {
        Player.Deck intendedDeck = Controller.getInstance().getCurrentPlayer().findDeck(deckName);
        if (intendedDeck == null)
            throw new DeckNameAlreadyExist();
        else if (!intendedDeck.isValidDeck())
            throw new InvalidDeck();
        else
            Controller.getInstance().getCurrentPlayer().setMainDeck(intendedDeck);
    }

    private void addCard(int cardID, String deckName) throws CardNotFoundInCollection, DeckNotFound, DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready
    {
        Card intendedCard =
                Controller.getInstance().getCurrentPlayer().getCollection().findCardInCollection(cardID);
        Player.Deck intendedDeck =
                Controller.getInstance().getCurrentPlayer().findDeck(deckName);
        if (intendedCard == null)
            throw new CardNotFoundInCollection();
        else if (intendedDeck == null)
            throw new DeckNotFound();
        else
            intendedDeck.addCardToDeck(intendedCard);
    }

    public void removeCardFromDeck(int cardID ,String deckName)
    {
        Player.Deck intendedDeck = Controller.getInstance().getCurrentPlayer().findDeck(deckName);

        Card intendedCard = Controller.getInstance().getCurrentPlayer().getCollection().findCardInCollection(cardID);
        intendedDeck.removeCardFromDeck(intendedCard);

    }

    @Override
    public String help()
    {
        return "1.exit\n2.show\n3.search\n4.save\n5.create deck\n6.delete deck\n7.add card to deck\n" +
                "8.remove card from deck\n9.validate deck\n10.select deck\n11.show all decks\n12.help\n";
    }

    @Override
    public String toShowMenu()
    {
        return null;
    }

    public void save()
    {

    }

    public void createDeck()
    {

    }

    public void addCardToDeck()
    {

    }
}

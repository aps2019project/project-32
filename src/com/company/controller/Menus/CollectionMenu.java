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
    public void selectOptionByCommand(String command) throws InvalidDeck, DeckNameAlreadyExist, CardNotFound, DeckNotFound, DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready
    {
        if (command.matches("exit"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());

        else if (command.matches("show"))
            Controller.getInstance().getCurrentPlayer().getCollection().toShowCollection(); // sendto view

        else if (command.matches("search \\w+"))
            Controller.getInstance().getCurrentPlayer().getCollection().toShowSearchResult(command.split(" ")[1]);
            //send to view
        else if (command.matches("create deck \\w+"))
            createDeck(command);

        else if (command.matches("delete deck \\w+"))
            Controller.getInstance().getCurrentPlayer().removeDeck(command.split(" ")[2]);

        else if (command.matches("add \\d+ to deck \\w+"))
            addCard(command);

        else if (command.matches("remove \\d+ from deck \\w+"))
            removeCard(command);

        else if (command.matches("validate deck \\w+"))
            isValidDeck(command);

        else if (command.matches("select deck \\w+"))
            selectDeckAsMainDeck(command.split(" ")[2]);

        else if (command.matches("show all decks"))
            Controller.getInstance().getCurrentPlayer().toShowAllDecks(); // send to view

        else if (command.matches("show deck \\w+"))
        {
            Player.Deck intendedDeck = Controller.getInstance().getCurrentPlayer().findDeck(command.split(" ")[2]);
            intendedDeck.toShowDeck(); // send to view
        }
        else if (command.matches("help"))
            collectionMenuInstance.help(); // send to view

        else if (command.matches("save"))
            collectionMenuInstance.save();
    }

    private void createDeck(String command) throws DeckNameAlreadyExist
    {
        String deckName = command.split(" ")[2];
        if (Controller.getInstance().getCurrentPlayer().findDeck(deckName) == null)
            Controller.getInstance().getCurrentPlayer().addNewDeck(deckName);
        else
            throw new DeckNameAlreadyExist();
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

    private void addCard(String command) throws CardNotFound, DeckNotFound, DeckIsFull, DeckHasHeroAlready, CardExistInDeckAlready, DeckHasPassiveAlready
    {
        int cardID = Integer.parseInt(command.split(" ")[1]);
        String deckName = command.split(" ")[4];
        Card intendedCard =
                Controller.getInstance().getCurrentPlayer().getCollection().findCardInCollection(cardID);
        Player.Deck intendedDeck =
                Controller.getInstance().getCurrentPlayer().findDeck(deckName);
        if (intendedCard == null)
            throw new CardNotFound();
        else if (intendedDeck == null)
            throw new DeckNotFound();
        else
            intendedDeck.addCardToDeck(intendedCard);
    }

    private void removeCard(String command) throws CardNotFound
    {
        int cardID = Integer.parseInt(command.split(" ")[1]);
        String deckName = command.split(" ")[4];
        Player.Deck intendedDeck = Controller.getInstance().getCurrentPlayer().findDeck(deckName);
        intendedDeck.removeCardFromDeck(cardID);
    }

    private void isValidDeck(String command) throws InvalidDeck
    {
        String deckName = command.split(" ")[2];
        if (!Controller.getInstance().getCurrentPlayer().findDeck(deckName).isValidDeck())
            throw new InvalidDeck();
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
}

package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.*;
import com.company.models.Player;
import com.company.models.widget.cards.Card;
import com.company.view.View;

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
        if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());

        else if (command.matches("Show"))
            View.getInstance().show(Controller.getInstance().getCurrentPlayer()
                    .getCollection().toShowCollection());

        else if (command.matches("Search \\w+"))
            View.getInstance().show(Controller.getInstance().getCurrentPlayer()
                    .getCollection().toShowSearchResult(command.split(" ")[1]));

        else if (command.matches("Create Deck \\w+"))
            createDeck(command);

        else if (command.matches("Delete Deck \\w+"))
            Controller.getInstance().getCurrentPlayer().removeDeck(command.split(" ")[2]);

        else if (command.matches("Add \\d+ To Deck \\w+"))
            addCard(command);

        else if (command.matches("Remove \\d+ From Deck \\w+"))
            removeCard(command);

        else if (command.matches("Validate Deck \\w+"))
            isValidDeck(command);

        else if (command.matches("Select Deck \\w+"))
            selectDeckAsMainDeck(command.split(" ")[2]);

        else if (command.matches("Show All Decks"))
            View.getInstance().show(Controller.getInstance().getCurrentPlayer().toShowAllDecks()); // send to view

        else if (command.matches("Show Deck \\w+"))
        {
            Player.Deck intendedDeck = Controller.getInstance().getCurrentPlayer().findDeck(command.split(" ")[2]);
            View.getInstance().show(intendedDeck.toShowDeck());
        }
        else if (command.matches("Help"))
            View.getInstance().show(collectionMenuInstance.toShowMenu());

        else if (command.matches("Save"))
            collectionMenuInstance.save();
    }

    private void createDeck(String command) throws DeckNameAlreadyExist
    {
        String deckName = command.split(" ")[2];
        if (Controller.getInstance().getCurrentPlayer().findDeck(deckName) == null)
            Controller.getInstance().getCurrentPlayer().addNewDeck(deckName);

        throw new DeckNameAlreadyExist();
    }

    private void selectDeckAsMainDeck(String deckName) throws DeckNameAlreadyExist, InvalidDeck
    {
        Player.Deck intendedDeck = Controller.getInstance().getCurrentPlayer().findDeck(deckName);
        if (intendedDeck == null)
            throw new DeckNameAlreadyExist();
        if (!intendedDeck.isValidDeck())
            throw new InvalidDeck();

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
        if (intendedDeck == null)
            throw new DeckNotFound();

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
    public String toShowMenu()
    {
        return "1.Exit\n2.Show\n3.Search\n4.Save\n5.Create deck\n6.Delete Deck\n7.Add Card To Deck\n" +
                "8.Remove Card From Deck\n9.Validate Deck\n10.Select Deck\n11.Show All Decks\n12.Help\n";
    }

    public void save()
    {
    }
}

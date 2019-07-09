package client.models.widget.cards;


import client.Client;
import client.controller.AnimationGIFSPack;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import client.models.Player;
import client.models.widget.Widget;
import javafx.util.Duration;

import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;

public abstract class Card extends Widget implements Serializable
{
    protected AnimationGIFSPack gifsPack;

    protected int price;
    protected transient ImageView battleImage;
    protected transient Pane battlePane = new Pane();
    protected String battleImageAddress;
    protected int cardsNumber;

    private static long serialVersionUID = 6614624112449760567L;


    public Card(String name, int price, AnimationGIFSPack GIFSPack)
    {
        super(name);

        this.gifsPack = GIFSPack;

        this.battleImage = new ImageView(new Image(GIFSPack.getBreathingPhotoAddress()));

        this.battleImage.setPreserveRatio(true);
        this.battleImage.setFitHeight(100);

        this.battleImageAddress = GIFSPack.getBreathingPhotoAddress();
        battlePane.getChildren().add(battleImage);

        this.price = price;

        this.cardsNumber = 10;
    }

    public Card()
    {
    }

    public Player getOwnerPlayer()
    {
        return ownerPlayer;
    }

    public void setOwnerPlayer(Player ownerPlayer)
    {
        this.ownerPlayer = ownerPlayer;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPrice()
    {
        return price;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public Pane getBattlePane()
    {
        return battlePane;
    }

    public void setBattlePane(Pane battlePane)
    {
        this.battlePane = battlePane;
    }

    public void setCardsNumber(int cardsNumber)
    {
        this.cardsNumber = cardsNumber;
    }

    public int getCardsNumber()
    {
        return cardsNumber;
    }

    public void syncCardNumber()
    {
        try
        {
            Client.getOutputStream().writeObject("number " + this.name);
            Client.getOutputStream().flush();
            Client.getOutputStream().reset();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof Card && this.getName().equals(((Card) obj).getName());
    }

    public AnimationGIFSPack getGifsPack()
    {
        return gifsPack;
    }

    public void setGifsPack(AnimationGIFSPack gifsPack)
    {
        this.gifsPack = gifsPack;
    }

    @Override
    public int hashCode()
    {
        return 1;
    }
}

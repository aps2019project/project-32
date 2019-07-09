package client.models.widget;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import client.models.Player;

import java.io.Serializable;

public abstract class Widget implements Serializable
{
    protected Player ownerPlayer;
    protected String name;
    protected int ID;
    protected static int idCounter;

    static final long serialVersionUID = -51414124;

    public Widget(String name)
    {
        this.name = name;
        this.ID = idCounter;
        idCounter++;
    }

    public Widget() {
    }

    public abstract Pane toShow();

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

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }
    protected ImageView getBackGround(String address){
        Image image = new Image(address);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        return imageView;
    }
}

package client.models.widget.cards.spells;

import client.controller.AnimationGIFSPack;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import client.models.widget.cards.Card;

import java.io.Serializable;
import java.util.ArrayList;

public class Passive extends Card implements Serializable
{
    private Spell spell;
    private FOE foe;
    private TargetType targetType;

    public Passive(String name, int price, Spell spell, FOE foe, TargetType targetType, AnimationGIFSPack gifsPack)
    {
        super(name, price,gifsPack);
        this.spell = spell;
        this.foe = foe;
        this.targetType = targetType;
    }

    @Override
    public Pane toShow() {
        Pane pane = new Pane();
        ImageView imageView = getBackGround(getImageAddress());
        pane.getChildren().add(imageView);

        ImageView imageView1 = new ImageView(new Image(getCharacterImage()));
        imageView1.setPreserveRatio(true);
        imageView1.setX(22);
        imageView1.setY(10);
        imageView1.setFitHeight(110);
        pane.getChildren().add(imageView1);

        ArrayList<Label> labels = new ArrayList<>();

        labels.add(getLabel("buy", 13, 15, 153, Color.WHITE));

        labels.add(getLabel("sell", 13, 90, 153, Color.WHITE));

        labels.add(getLabel("name", 15, 50, 137, Color.WHITE));


        pane.getChildren().addAll(labels);
        pane.setStyle("-fx-background-color: transparent");

        return pane;

    }

    private String getCharacterImage() {
            return "file:Css/pictures/New folder (3)/booster_pack_opening/booster_orb_set_3.png";
    }

    private String getImageAddress() {
            return "file:Css/pictures/New folder (3)/card_backgrounds/reveal_glow_spell.png";
    }

    private Label getLabel(String name, int fontSize, int x, int y, Color color) {
        String labelText = "";
        switch (name) {
            case "name": {
                labelText = this.getName();
                break;
            }
        }
        Label label = new Label(labelText);
        label.setFont(Font.font(fontSize));
        label.setTextFill(color);
        label.relocate(x, y);
        return label;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Passive passive = null;
        passive = new Passive(name, price, ((Spell) spell.clone()), foe, targetType,gifsPack);

        passive.ownerPlayer = this.ownerPlayer;

        return passive;
    }

    public Spell getSpell()
    {
        return spell;
    }

    public FOE getFoe()
    {
        return foe;
    }

    public TargetType getTargetType()
    {
        return targetType;
    }
}

package client.controller.menus;

import client.controller.BattleMenu;
import client.controller.Controller;
import client.models.Player;
import client.models.widget.cards.Card;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import sun.reflect.generics.scope.Scope;

import java.io.IOException;
import java.util.ArrayList;

public class GraveYardMenu extends AbstractMenu
{
    private static GraveYardMenu instance = new GraveYardMenu();

    public static GraveYardMenu getInstance()
    {
        return instance;
    }

    private GraveYardMenu()
    {
    }

    private BattleMenu battleMenu;

    ScrollPane cardsScroll;
    BorderPane borderPane;

    @Override
    public void initializeMenuGUI() throws IOException, InterruptedException
    {
        borderPane = new BorderPane();
        borderPane.setStyle("");
        borderPane.getChildren().add(getBackGround("bg12.jpg"));

        borderPane.setCenter(getCenter());

        this.scene = new Scene(borderPane, Controller.getInstance().getWeight(), Controller.getInstance().getHeight());
        changeCursorImage("file:Css/pictures/New Folder (3)/ui/mouse_auto.png");
        scene.setOnKeyPressed(event ->
        {
            if (event.getCode() == KeyCode.ESCAPE)
                Controller.getInstance().getStage().setScene(battleMenu.getScene());
        });
    }

    private Node getCenter()
    {
        cardsScroll = new ScrollPane();
        cardsScroll.resize(470,208);
        cardsScroll.setMaxSize(470,208);
        cardsScroll.setPrefViewportHeight(208);
        cardsScroll.setPrefViewportWidth(208);
        VBox vBox = getCardsVBox();
        cardsScroll.setContent(vBox);

        return cardsScroll;
    }

    private VBox getCardsVBox()
    {
        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);
        Player player = Controller.getInstance().getCurrentPlayer();
        ArrayList<Card> cards = player.getGraveYard().getGraveYardList();

        for (Card card : cards)
            vBox.getChildren().add(card.toShow());

        return vBox;
    }

    public void setBattleMenu(BattleMenu battleMenu)
    {
        this.battleMenu = battleMenu;
    }
}

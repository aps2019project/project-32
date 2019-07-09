package server;

import client.models.Player;
import client.models.widget.cards.Card;
import com.jniwrapper.Str;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class SocketListener extends Application
{
    static ArrayList<ConnectedSocketHandler> handlers = new ArrayList<>();

    static List<Player> onlinePlayers = Collections.synchronizedList(new ArrayList<>());
    static List<Player> players = Collections.synchronizedList(new ArrayList<>());
    static List<Card> cards = Collections.synchronizedList(new ArrayList<>());

    static boolean auctionIsActive = false;
    static Card auctionCard;
    static String playerStartedAuction;
    static int maxSuggest;
    static HashMap<String, Integer> playersToSuggestValue = new HashMap<>();

    static TextArea textArea = new TextArea();
    static Button button = new Button("Save & Close");

    public static void runTimer()
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            int i = 30;

            @Override
            public void run()
            {
                for (ConnectedSocketHandler handler : SocketListener.handlers)
                {
                    try
                    {
                        if (i == 0)
                        {
                            cancel();

                            if (auctionIsActive)
                            {
                                for (String playerName : playersToSuggestValue.keySet())
                                    if (playersToSuggestValue.get(playerName) == (maxSuggest))
                                    {
                                        for (ConnectedSocketHandler socketHandler : handlers)
                                        {
                                            socketHandler.outputStream.writeObject(playerName + " Won With Suggest " + maxSuggest + " " + auctionCard.getName());
                                            socketHandler.outputStream.flush();
                                        }
                                        break;
                                    }

                                for (ConnectedSocketHandler socketHandler : handlers)
                                {
                                    socketHandler.outputStream.writeObject("increase auction " + playerStartedAuction + " " + maxSuggest + " " + auctionCard.getName());
                                    socketHandler.outputStream.flush();
                                }

                            }
                            auctionIsActive = false;
                        }
                        handler.outputStream.writeObject("time " + i);
                        handler.outputStream.flush();
                        handler.outputStream.writeObject("sync max");
                        handler.outputStream.flush();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                i--;
            }
        };

        timer.schedule(task, 0, 1000);
    }

    @Override
    public void start(Stage primaryStage)
    {
        HBox hBox = new HBox(textArea, button);

        button.setOnMouseClicked(event ->
        {
            try
            {
                writePlayerList();
                writeShopCardList();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(hBox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Properties getProperties() throws IOException
    {
        Properties properties = new Properties();
        properties.load(new FileInputStream("src/config.properties"));
        return properties;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        ServerSocket serverSocket;
        try
        {
            Properties properties = getProperties();
            int port = Integer.parseInt(properties.getProperty("port"));
            serverSocket = new ServerSocket(8000);
        }
        catch (IOException ex)
        {
            serverSocket = new ServerSocket(8000);
        }

        new Thread(() -> launch(args)).start();

        readPlayerList();
        readShopCardList();

        while (true)
        {
            Socket acceptedSocket = serverSocket.accept();
            ConnectedSocketHandler socketHandler = new ConnectedSocketHandler(serverSocket, acceptedSocket);
            handlers.add(socketHandler);
            new Thread(() -> socketHandler.handle()).start();
        }
    }

    private static void readShopCardList() throws IOException, ClassNotFoundException
    {
        InputStream cardsIS = new FileInputStream("src/server/cards.txt");
        ObjectInputStream cardsListOIS = new ObjectInputStream(cardsIS);
        Object readCards = cardsListOIS.readObject();
        cards.addAll(((ArrayList) readCards));

        cardsIS.close();
        cardsListOIS.close();
    }

    private static void readPlayerList() throws IOException, ClassNotFoundException
    {
        InputStream playerListIS = new FileInputStream("C:\\Users\\hp\\Desktop\\my own project\\src\\server\\accounts.txt");
        ObjectInputStream playerListOIS = new ObjectInputStream(playerListIS);
        Object playersList = playerListOIS.readObject();
        players = ((ArrayList<Player>) playersList);
        playerListOIS.close();
        playerListIS.close();
    }

    private void writeShopCardList() throws IOException
    {
        ObjectOutputStream fileOutputStream = new ObjectOutputStream(new FileOutputStream("src/server/cards.txt"));
        fileOutputStream.writeObject(cards);
        fileOutputStream.flush();
        fileOutputStream.close();
        System.exit(13);
    }

    private void writePlayerList() throws IOException
    {
        ObjectOutputStream fileOutputStream = new ObjectOutputStream(new FileOutputStream("src/server/accounts.txt"));
        fileOutputStream.writeObject(players);
        fileOutputStream.flush();
        fileOutputStream.close();
        System.exit(13);
    }
}

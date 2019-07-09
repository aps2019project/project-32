package server;

import client.models.Player;
import client.models.widget.cards.Card;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import static server.SocketListener.*;

public class ConnectedSocketHandler
{
    Socket connectedSocket;
    ServerSocket serverSocket;
    ObjectInputStream inputStream;
    ObjectOutputStream outputStream;
    ObjectInputStream fileInputStream;
    ObjectOutputStream fileOutputStream;
    boolean inCustomCardMenu;
    boolean battleRequested;

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Socket)
            return this.connectedSocket.equals(o);

        return false;
    }

    ConnectedSocketHandler(ServerSocket serverSocket, Socket connectedSocket) throws IOException
    {
        this.serverSocket = serverSocket;
        this.connectedSocket = connectedSocket;
        inputStream = new ObjectInputStream(connectedSocket.getInputStream());
        outputStream = new ObjectOutputStream(connectedSocket.getOutputStream());
    }

    public void handle()
    {
        new Thread(() ->
        {
            try
            {
                outputStream.writeObject(players);
                outputStream.flush();
                outputStream.reset();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            try
            {
                while (true)
                {
                    Object inputObject = inputStream.readObject();
                    System.out.println(inputObject);

                    if (inputObject instanceof Card)
                    {
                        if (!auctionIsActive && !inCustomCardMenu)
                        {
                            auctionCard = ((Card) inputObject);
                            auctionIsActive = true;
                            Thread.sleep(500);
                            runTimer();
                        }
                        if (inCustomCardMenu)
                        {
                            Card customCard = ((Card) inputObject);
                            cards.add(customCard);
                            for (ConnectedSocketHandler handler : handlers)
                            {
                                handler.outputStream.writeObject(customCard);
                                handler.outputStream.flush();
                                handler.outputStream.reset();
                            }
                        }
                    }
                    else if (inputObject instanceof Player)
                    {
                        Player onlinePlayer = ((Player) inputObject);
                        onlinePlayers.add(onlinePlayer);
                        notifyAllOnlineUser(onlinePlayer);
                    }
                    else if (inputObject instanceof ArrayList)
                    {
                        ArrayList<Player> players = ((ArrayList<Player>) inputObject);
                        SocketListener.players = players;
                        for (ConnectedSocketHandler handler : handlers)
                        {
                            handler.outputStream.writeObject(players);
                            handler.outputStream.flush();
                            handler.outputStream.reset();
                        }
                    }
                    else if (inputObject instanceof String)
                    {
                        String inputMessage = ((String) inputObject);
                        System.out.println("Server Input Message : " + inputMessage);

                        if (inputMessage.matches("incustomcardmenu"))
                        {
                            inCustomCardMenu = true;
                        }
                        else if (inputMessage.matches("outcustomcardmenu"))
                        {
                            inCustomCardMenu = false;
                        }
                        else if (inputMessage.matches("sync players"))
                        {
                            this.outputStream.writeObject(players);
                            this.outputStream.flush();
                            this.outputStream.reset();
                        }
                        else if (inputMessage.matches("player start auction .*"))
                        {
                            String playerName = inputMessage.split(" ")[3];
                            playerStartedAuction = playerName;
                        }
                        else if (inputMessage.matches("suggest .*"))
                        {
                            int inputSuggest = Integer.parseInt(inputMessage.split(" ")[2]);
                            if (maxSuggest < inputSuggest)
                            {
                                String playerSuggestedUserName = inputMessage.split(" ")[1];
                                playersToSuggestValue.put(playerSuggestedUserName, inputSuggest);
                                maxSuggest = inputSuggest;

                                for (ConnectedSocketHandler handler : handlers)
                                {
                                    handler.outputStream.writeObject("sync max " + maxSuggest);
                                    handler.outputStream.flush();
                                    handler.outputStream.reset();
                                }
                            }
                            else
                            {
                                this.outputStream.writeObject("another");
                                this.outputStream.flush();
                                this.outputStream.reset();
                            }

                        }
                        else if (inputMessage.matches("number .*"))
                        {
                            textArea.appendText(inputMessage + "\n");

                            String[] messageParts = inputMessage.split(" ");
                            String cardName = "";
                            for (int i = 1; i < messageParts.length; i++)
                                cardName = cardName.concat(messageParts[i] + " ");

                            cardName = cardName.trim();
                            int cardNumber = 0;
                            for (Card card : cards)
                                if (card.getName().equalsIgnoreCase(cardName))
                                    cardNumber = card.getCardsNumber();

                            System.out.println(cardName + " isss  " + cardNumber);

                            this.outputStream.writeObject("number " + cardNumber);
                            this.outputStream.flush();

                        }
                        else if (inputMessage.matches("change .*"))
                        {
                            textArea.appendText(inputMessage + "\n");

                            String[] messageParts = inputMessage.split(" ");
                            String cardName = "";
                            int changedNumber = Integer.parseInt(messageParts[1]);

                            for (int i = 2; i < messageParts.length; i++)
                                cardName = cardName.concat(messageParts[i] + " ");

                            cardName = cardName.trim();
                            for (Card card : cards)
                                if (card.getName().equalsIgnoreCase(cardName))
                                    card.setCardsNumber(changedNumber);

                        }
                        else if (inputMessage.matches("logout .+"))
                        {
                            String userName = inputMessage.split(" ")[1];
                            for (ConnectedSocketHandler handler : handlers)
                                if (!handler.equals(this))
                                {
                                    handler.outputStream.writeObject(userName + " is Offline now ...");
                                    handler.outputStream.flush();
                                }

                            onlinePlayers.removeIf(player -> player.getName().equals(userName));
                            handlers.remove(this);

                        }
                        else if (inputMessage.matches("message .+"))
                        {
                            for (ConnectedSocketHandler handler : handlers)
                                if (!handler.equals(this))
                                {
                                    handler.outputStream.writeObject(inputMessage);
                                    handler.outputStream.flush();
                                }

                        }
                        else if (inputMessage.matches("onlineusers"))
                        {
                            String onlinePlayersList = "onlineusers ";
                            for (Player onlinePlayer : onlinePlayers)
                                onlinePlayersList = onlinePlayersList.concat(onlinePlayer.getName() + " ");

                            outputStream.writeObject(onlinePlayersList);
                            outputStream.flush();

                        }
                        else if (inputMessage.matches("auction isactive"))
                        {
                            outputStream.writeObject("auction " + auctionIsActive);
                            outputStream.flush();

                            if (auctionIsActive)
                                for (ConnectedSocketHandler handler : handlers)
                                {
                                    handler.outputStream.writeObject(auctionCard);
                                    handler.outputStream.flush();
                                }

                        }
                    }
                }

            }
            catch (IOException | ClassNotFoundException e)
            {
                if (e instanceof SocketException)
                    System.out.println(e.getMessage());
                else
                    e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }).start();
    }

    private void notifyAllOnlineUser(Player onlinePlayer) throws IOException
    {
        for (ConnectedSocketHandler handler : SocketListener.handlers)
            if (!handler.equals(this))
            {
                handler.outputStream.writeObject(onlinePlayer.getName() + " is Online now ...");
                handler.outputStream.flush();
            }
    }
}

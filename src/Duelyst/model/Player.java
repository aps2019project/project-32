package Duelyst.model;


import Duelyst.model.Buff.Buff;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player {
    private Image avatar;
    private static Player loginAccount;
    private static ArrayList<Player> allUser = new ArrayList<>();
    private String userName, passWord;
    private int money, wins = 0, mana;
    private Deck mainDeck;
    private ArrayList<Record> matches = new ArrayList<>();
    private Collection myCollection = new Collection();
    private ArrayList<Buff> ownBuffs = new ArrayList<>();

    public Player(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        this.money = 15000;
        this.avatar = new Image("Duelyst/css/avatar1.png");
        allUser.add(this);
    }

    public Player(int x) {
        this.userName = "AI_Player";
        this.money = 15000;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public void addNewRecordedMatch(Record recordedMatch) {
        this.matches.add(recordedMatch);
    }

    public static boolean existThisUser(String userName) {
        for (Player account : Player.getAllUser()) {
            if (account.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean authorize(String userName, String passWord) {
        for (Player account : Player.getAllUser()) {
            if (account.getUserName().equals(userName)) {
                if (account.getPassWord().equals(passWord)) {
                    Player.setLoginAccount(account);
                    return true;
                }
                return false;
            }
        }
        return false;
    }


    public ArrayList<Record> getMatches() {
        return matches;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void incrementMoney(int money) {
        this.money += money;
    }

    public void incrementMana(int unit) {
        this.mana += unit;
    }

    public void decrementMana(int unit) {
        this.mana -= unit;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public Collection getCollection() {
        return myCollection;
    }


    public static void showLeaderboard() {
        for (int i = 0; i < allUser.size() - 1; i++) {
            Player pl = allUser.get(i);
            int maxW = pl.wins;
            int maxi = i;
            for (int j = i + 1; j < allUser.size(); j++) {
                Player tmp = allUser.get(j);
                if (maxW < tmp.wins) {
                    maxi = j;
                    maxW = tmp.wins;
                } else if (maxW == tmp.wins && allUser.get(maxi).userName.compareTo(allUser.get(j).userName) > 0) {
                    maxi = j;
                    maxW = tmp.wins;

                }
            }
            pl = allUser.get(maxi);
            allUser.remove(maxi);
            allUser.add(maxi, allUser.get(i));
            allUser.remove(i);
            allUser.add(i, pl);
        }

    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public static Player getLoginAccount() {
        return loginAccount;
    }

    public static void setLoginAccount(Player loginAccount) {
        Player.loginAccount = loginAccount;
    }

    public static ArrayList<Player> getAllUser() {
        return allUser;
    }

    public Player getAccount(String userName) {
        for (Player account : allUser) {
            if (account.getUserName().equals(userName))
                return account;
        }
        return null;
    }

    public String getPassWord() {
        return passWord;
    }

    public String getUserName() {
        return userName;
    }

    public static void addUser(Player account) {
        allUser.add(account);
    }

    public ArrayList<Buff> getOwnBuffs() {
        return ownBuffs;
    }

    public void setOwnBuffs(ArrayList<Buff> ownBuffs) {
        this.ownBuffs = ownBuffs;
    }
}

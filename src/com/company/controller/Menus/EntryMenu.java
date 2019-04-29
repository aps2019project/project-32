package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.UserNameAlreadyExist;
import com.company.controller.Exceptions.UserNameDidntExist;
import com.company.controller.Exceptions.WeekPassword;
import com.company.controller.Exceptions.WrongPassword;
import com.company.models.Player;
import com.company.view.Request;

public class EntryMenu implements AbstractMenu
{
    private EntryMenu()
    {
    }

    private static EntryMenu entryMenuInstance = new EntryMenu();

    public static AbstractMenu getInstance()
    {
        return entryMenuInstance;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @Override
    public void selectOptionByCommand(String command) throws WrongPassword, UserNameDidntExist, WeekPassword, UserNameAlreadyExist
    {
        if (command.matches("create account \\w+"))
            entryMenuInstance.register(command);

        else if (command.matches("login \\w+"))
            entryMenuInstance.login(command);

        else if (command.matches("help"))
            entryMenuInstance.help(); //sendToView

        else if (command.matches("exit"))
            System.exit(0);
    }

    @Override
    public String help()
    {
        return "1.Register\n2.Login\n";
    }

    @Override
    public String toShowMenu()
    {
        return null;
    }

    private void register(String command) throws UserNameAlreadyExist, WeekPassword
    {
        String userName = command.split(" ")[2];
        boolean userNameHasUsed = false;
        for (Player player : Player.getPlayers())
            if (player.getName().equals(userName))
                throw new UserNameAlreadyExist();

        String passWord = Request.getInstance().getNewCommand();
        if (checkPassWordSafety(passWord))
            Player.getPlayers().add(new Player(userName, passWord));
        else
            throw new WeekPassword();
    }

    private boolean checkPassWordSafety(String passWord)
    {
        boolean passWordHas8Character = false;
        boolean passWordHasDigit = false;
        boolean passWordHasLowerCase = false;
        boolean passWordHasUpperCase = false;

        if (passWord.length() >= 8)
            passWordHas8Character = true;

        for (int i = 0; i < passWord.length(); i++)
        {
            char ch = passWord.charAt(i);
            if (Character.isDigit(ch))
                passWordHasDigit = true;
            else if (Character.isLowerCase(ch))
                passWordHasLowerCase = true;
            else if (Character.isUpperCase(ch))
                passWordHasUpperCase = true;
        }

        return passWordHas8Character && passWordHasDigit && passWordHasLowerCase && passWordHasUpperCase;
    }


    private void login(String command) throws WrongPassword, UserNameDidntExist
    {
        String userName = command.split(" ")[1];
        boolean playerFoundWithThisName = false;
        for (Player player : Player.getPlayers())
            if (player.getName().equals(userName))
            {
                playerFoundWithThisName = true;
                String passWord = Request.getInstance().getNewCommand();
                if (player.getPassWord().equals(passWord))
                {
                    Controller.getInstance().changeCurrentMenuTo(MainMenu.getInstance());
                    Controller.getInstance().setCurrentPlayer(player);
                    break;
                }
                else
                    throw new WrongPassword();
            }
        if (!playerFoundWithThisName)
            throw new UserNameDidntExist();
    }
}



package com.company.controller.Menus;

import com.company.controller.Controller;
import com.company.controller.Exceptions.UserNameAlreadyExist;
import com.company.controller.Exceptions.UserNameDidntExist;
import com.company.controller.Exceptions.WeekPassword;
import com.company.controller.Exceptions.WrongPassword;
import com.company.models.Player;
import com.company.view.Request;
import com.company.view.View;

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
    public void selectOptionByCommand(String command) throws WrongPassword, UserNameDidntExist, WeekPassword, UserNameAlreadyExist
    {
        if (command.matches("Create Account \\w+"))
            entryMenuInstance.register(command);

        else if (command.matches("login \\w+"))
            entryMenuInstance.login(command);

        else if (command.matches("help"))
            View.getInstance().show(entryMenuInstance.toShowMenu());

        else if (command.matches("exit"))
            System.exit(0);
    }

    @Override
    public String toShowMenu()
    {
        return "1.Create Account <UserName>\n2.Login <UserName>\n3.Help\n4.Exit";
    }

    private void register(String command) throws UserNameAlreadyExist, WeekPassword
    {
        String userName = command.split(" ")[2];
        for (Player player : Player.getPlayers())
            if (player.getName().equals(userName))
                throw new UserNameAlreadyExist();

        while (true)
        {
            View.getInstance().show("Enter PassWord!:");
            String passWord = Request.getInstance().getNewCommand();
            if (checkPassWordSafety(passWord))
            {
                Player.getPlayers().add(new Player(userName, passWord));
                break;
            }
            else
                throw new WeekPassword();
        }
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
                View.getInstance().show("Enter PassWord :");
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



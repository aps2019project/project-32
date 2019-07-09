package client.controller.battlemenus;

import client.controller.Controller;
import client.controller.menus.AbstractMenu;
import client.controller.menus.BattleMenuGUI;
import client.view.View;

public class GraveYardMenu extends AbstractMenu
{
    private GraveYardMenu()
    {
    }
    private static GraveYardMenu graveYardMenuInstance = new GraveYardMenu();
    public static AbstractMenu getInstance()
    {
        return graveYardMenuInstance;
    }

    @Override
    public int hashCode()
    {
        return super.hashCode();
    }

    public void selectOptionByCommand(String command)
    {
        if (command.matches("Show Information \\d+"))
            Controller.getInstance().getCurrentPlayer().getGraveYard().toShowCard
                    (Integer.valueOf(command.split(" ")[2]));

        else if (command.matches("Show Cards"))
            Controller.getInstance().getCurrentPlayer().getGraveYard().toShowGraveYardCards();

        else if (command.matches("Help"))
            View.getInstance().show(toShowMenu());

        else if (command.matches("Exit"))
            Controller.getInstance().changeCurrentMenuTo(BattleMenuGUI.getInstance() );
    }

    public String toShowMenu()
    {
        return "1.Show Information <CardID>\n2.Show Cards\n3.Help\n4.Exit";
    }

    @Override
    public void initializeMenuGUI() {

    }
}

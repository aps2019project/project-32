package client.view;
import java.io.Serializable;
import java.util.Scanner;

public class Request implements Serializable
{

    private Request()
    {
    }

    private static Request requestInstance = new Request();

    public static Request getInstance()
    {
        return requestInstance;
    }

    private Scanner input = new Scanner(System.in);

    public String getNewCommand()
    {
       return input.nextLine();
    }
}

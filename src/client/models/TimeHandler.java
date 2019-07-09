package client.models;

import java.io.Serializable;
import java.util.Date;

public class TimeHandler implements Serializable
{
    private static TimeHandler ourInstance = new TimeHandler();

    public static TimeHandler getInstance()
    {
        return ourInstance;
    }

    private Date currentDate;

    private TimeHandler()
    {
        currentDate = new Date();
    }

    public long getTime()
    {
        return currentDate.getTime();
    }

}

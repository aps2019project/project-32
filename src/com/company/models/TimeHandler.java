package com.company.models;

import java.util.Date;

public class TimeHandler
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

    public Date getCurrentDate()
    {
        return currentDate;
    }
}

package com.blackpoint.stan_konta;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class representing an operation on bank account. Holds it's title, amount and day, when it was performed.
 * Allows to create new operation, get it's title, amount and date in several formats
 */
public class Operation {
    private String title;
    private double amount;
    Calendar cal;
    private String time;

    /**
     * Construct operation with given ammount and title. Time of operation is generated automatically
     * @param ammount Ammount of operation
     * @param title Title of operation
     */
    public Operation(double ammount, String title) {
        this.amount = ammount;
        this.title = title;

        cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy | HH:mm");
        this.time = sdf.format(cal.getTime());
    }

    /**
     * Construct operation with given ammount, title and date.
     * @see #Operation(double, String) Operation(double ammount, String title)
     * @param ammount Ammount of operation
     * @param title Title of operationn
     * @param date Date in format dd.mm.yyyy | HH:MM
     */
    public Operation(double ammount, String title, String date) {
        this.amount = ammount;
        this.title = title;

        this.time = date;
    }

    /**
     * Getter for operation's title
     * @return Operation's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter for operation's amount
     * @return Operation's amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Getter for operation's date
     * @see #getDate(boolean)
     * @return Operation's date
     */
    public String getDate() {
        return time;
    }

    /**
     * Getter for formatted operation's date, without space
     * @param space Determines if space should be removed or not
     * @see #getDate()
     * @return Formatted date
     */
    public String getDate(boolean space) {
        if (!space) {
            return this.time.replace(" ", "");
        } else {
            return this.time;
        }
    }

    /**
     * Getter for formatted operation's date - dd.mm.yyyy
     * @return Formatted date
     */
    public String getDay() {
        return this.time.substring(0, 10);
    }
}

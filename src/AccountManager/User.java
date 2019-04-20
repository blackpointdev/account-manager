package AccountManager;

import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class representing user's bank account. Holds it's name, balance and list of all operations performed on user's
 * account. Has users counter, incremented with every new user created.
 */
public class User {
    private static int static_id = 0;
    private int id;
    private String name;
    private boolean isLoadedCorrectly = false;
    private ArrayList<Operation> Operations = new ArrayList<Operation>(); // Operation template: date | ammount | title

    /**
     * Constructs user account with given name and balance. Increments users counter
     * @param name Name of new user
     * @param balance Balance of new user
     */
    public User(String name, double balance) {
        this.name = name;
        this.id = static_id++;
        NewOperation(balance, "Inicjalizacja konta użytkownika " + name);
    }

    /**
     * Adds a new operation
     *
     * @param ammount Ammount of operation
     * @param title   Title of new operation
     */
    public void NewOperation(double ammount, String title) {
        Operations.add(new Operation(ammount, title));
    }

    /**
     * Overloaded method, with default title.
     * @see #NewOperation(double, String) NewOperation
     *
     * @param ammount Amount of operation
     */
    public void NewOperation(double ammount) {
        Operations.add(new Operation(ammount, "undefined"));
    }

    /**
     * Lists all user's operations
     */
    public void ListOperations() {
        for (Operation i : Operations)
            System.out.println(i.getDate() + " | " + i.getAmount() + " | " + i.getTitle());
    }

    /**
     * Lists given ammount of last operations on account, from latest to older.
     *
     * @param lastOperations number of last operations to list
     */
    public void ListOperations(int lastOperations) {
        for (int i = Operations.size() - 1; lastOperations > 0 && i > 0; i--, lastOperations--)
            System.out.println(Operations.get(i).getDate() + " | " + Operations.get(i).getTitle() + " | " + Operations.get(i).getAmount());
    }

    /**
     * Prints all operation records from given date.
     *
     * @param value date in format "dd.MM.yyyy"
     */
    public void ListOperations(String value) {
        for (Operation i : Operations) {
            if (value.equals(i.getDay()))
                System.out.println(i.getDate() + " | " + i.getTitle() + " | " + i.getAmount());
        }
    }

    /**
     * Calculate balance on user's account from ammounts of all operations
     *
     * @return User's account balance
     */
    public double getBalance() {
        double balance = 0;

        for (Operation tmp : Operations) {
            balance += tmp.getAmount();
        }
//        Stuff for rounding balance, to avoid strange outputs like 24.49000000003
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
//        For some reason "format()" method returns string with "," instead of coma lolz
        return Double.parseDouble(df.format(balance).replace(',', '.'));
    }

    /**
     * Loads data of user's operations from file called "username.usr"
     *
     * @throw IllegalArgumentException If data from file is incorrect
     */
    public void LoadData() {
        File f = new File(this.name + ".usr");
        ArrayList<String> records = new ArrayList<String>();

        try {
            Scanner in = new Scanner(f);
            while (in.hasNextLine()) {
                records.add(in.nextLine());
            }

            int counter = 0;
            for (String line : records) {
                if (!line.matches("(([0-2])[0-9]|3[0-1])[.](0\\d|1[0-2])[.]\\d\\d\\d\\d[|]([0-1]\\d|2[0-4])[:]([0-5]\\d)[|](.*)[|](.*)")) {
                    in.close();
                    throw new IllegalArgumentException(Colour.ANSI_RED + "Data from file are incorrect in line "
                            + counter + ": " + line + Colour.ANSI_RESET);
                }
                String[] parts = line.split("[|]");
                Operations.add(new Operation(Double.parseDouble(parts[2]), parts[3], parts[0] + " | " + parts[1]));
            }
            this.isLoadedCorrectly = true;
            in.close();
        } catch (FileNotFoundException e) {
            System.out.print(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    /**
     * Saves user's operations to file called "username.usr"
     */
    public void SaveData() {
        if (!isLoadedCorrectly) {
            System.out.println("Can't save data to file - read from file failed!");
            return;
        }
        File f = new File(name + ".usr");
        if (f.exists()) {
            f.delete();
        }
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(name + ".usr"));
            for (int i = 1; i < Operations.size(); i++) {
                writer.write(Operations.get(i).getDate(false) + "|" + Operations.get(i).getAmount() + "|" + Operations.get(i).getTitle());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Getter for user's name
     *
     * @return User's name
     */
    public String getName() {
        return name;
    }
}

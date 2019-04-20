package com.blackpoint.stan_konta;

import AccountManager.Colour;
import AccountManager.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static void clearScreen() {
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }

    private static void WelcomeList(ArrayList<User> Users) {

        double sum = 0;
        for (User u : Users) {
            System.out.println("------------------------------------------------");
            System.out.println("Użytkownik: " + Colour.ANSI_GREEN + u.getName() + Colour.ANSI_RESET);
            System.out.println("Aktualny stan konta: " + Colour.ANSI_GREEN + u.getBalance() + Colour.ANSI_RESET + "\n");
            System.out.println("Ostatnie operacje:");
            u.ListOperations(5);
            sum += u.getBalance();
        }
        System.out.println("Łącznie : " + Colour.ANSI_GREEN + sum + Colour.ANSI_RESET);
    }

    private static void LoadUsersData(ArrayList<User> Users) {
        for (User u : Users) {
            u.LoadData();
        }
    }

    private static void SaveUsersData(ArrayList<User> Users) {
        for (User u : Users) {
            u.SaveData();
        }
    }


    public static void main(String[] args) {
        ArrayList<User> Users = new ArrayList<User>();

        Users.add(new User("marcin", 0));
        Users.add(new User("rodzice", 0));

        LoadUsersData(Users);

        WelcomeList(Users);

        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.print("Login as: ");
            String log = reader.nextLine();

            // Stream API
            User u = Users.stream().filter(user -> log.equals(user.getName())).findAny().orElse(null);
            try {
                if (log.equals("welcome")) {
                    WelcomeList(Users);
                    continue;
                }
                if (log.equals("exit")) {
                    System.out.println("Zapisywanie...");
                    SaveUsersData(Users);
                    System.out.println("Zamykanie...");
                    return;
                }
                System.out.println("Zalogowano: " + u.getName());
                boolean isCorrect = false;

                while (!isCorrect) {
                    System.out.print("Polecenie: ");
                    String command = reader.nextLine();

                    if (command.equals("balance")) {
                        System.out.println("Aktualny stan konta: " + Colour.ANSI_GREEN + u.getBalance() + Colour.ANSI_RESET);
                    }
                    if (command.equals("list")) {
                        u.ListOperations(5);
                    }
                    if (command.matches("list [0-9]+")) {
                        String[] parts = command.split(" ");
                        int tmp = Integer.parseInt(parts[1]);
                        u.ListOperations(tmp);
                    }
                    if (command.equals("save")) {
                        SaveUsersData(Users);
                    }
                    if (command.equals("logout")) {
                        System.out.println("Wylogowanie...");
                        clearScreen();
                        break;
                    }
                    if (command.equals("exit")) {
                        System.out.println("Zapisywanie...");
                        SaveUsersData(Users);
                        System.out.println("Zamykanie...");
                        return;
                    }
                    if (command.matches("-?[0-9]+[.]?[0-9]?[0-9]?")) {
                        u.NewOperation(Double.parseDouble(command));
                    }
                    if (command.matches("-?[0-9]+[.]?[0-9]?[0-9]?[' '](.)*")) {
                        String[] command_splitted = command.split(" ");
                        u.NewOperation(Double.parseDouble(command));
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("Nie znaleziono użytkownika o nazwie: " + log);
            }
        }
//        System.out.println(Colour.ANSI_RED + "This text is red!" + Colour.ANSI_RESET);
    }
}
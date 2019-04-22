package AccountManager;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class of AccountManager class. Allows user to interact with whole manager.
 */
public class AccountManager {
    private ArrayList<User> Users = new ArrayList<User>();

    public void ListUsersDetails() {
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

    public void AddUser(String name, double balance) {
        Users.add(new User(name, balance));
    }

    public void LoadUsersData() {
        for (User u : Users) {
            u.LoadData();
        }
    }

    public void SaveUsersData() {
        for (User u : Users) {
            u.SaveData();
        }
    }

    public void InitializeLogin() {

        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.print("Login as: ");
            String log = reader.nextLine();

            // Stream API
            User u = Users.stream().filter(user -> log.equals(user.getName())).findAny().orElse(null);
            try {
                if (log.equals("welcome")) {
                    ListUsersDetails();
                    continue;
                }
                if (log.equals("exit")) {
                    System.out.println("Zapisywanie...");
                    SaveUsersData();
                    System.out.println("Zamykanie...");
                    return;
                }

                System.out.println("Zalogowano: " + u.getName());

                while (true) {
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
                        System.out.println("Zapisywanie...");
                        SaveUsersData();
                    }
                    if (command.equals("logout")) {
                        System.out.println("Wylogowanie...");
                        ClearScreen();
                        break;
                    }
                    if (command.equals("exit")) {
                        System.out.println("Zapisywanie...");
                        SaveUsersData();
                        System.out.println("Zamykanie...");
                        return;
                    }
                    if (command.matches("-?[0-9]+[.]?[0-9]?[0-9]?")) {
                        u.NewOperation(Double.parseDouble(command));
                    }
                    if (command.matches("-?[0-9]+[.]?[0-9]?[0-9]?[' '](.)*")) {
                        String[] command_splitted = command.split(" ", 2);
                        u.NewOperation(Double.parseDouble(command_splitted[0]), command_splitted[1]);
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("Nie znaleziono użytkownika o nazwie: " + log);
            }
        }
    }

    private void ClearScreen() {
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }
}

package Client;

// My modules
import AccountManager.AccountManager;

public class Main {

    public static void main(String[] args) {
        AccountManager am = new AccountManager();

        am.AddUser("marcin", 0);
        am.AddUser("adam", 0);

        am.LoadUsersData();
        am.ListUsersDetails();

        am.InitializeLogin();
    }
}
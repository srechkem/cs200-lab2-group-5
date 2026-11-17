// Author: Isaac Kim
// MainMenu is the first menu the user sees.
// It lets an operator, provider, or manager log in, then opens
// the menu (terminal) for that role.

public class MainMenu {

    private ChocAnDatabase database;
    private VerificationController verifier;
    private String currentUserNumber;
    private boolean loggedIn;

    public MainMenu(ChocAnDatabase database) {
        this.database = database;
        this.verifier = new VerificationController(database);
        this.currentUserNumber = "";
        this.loggedIn = false;
    }

    // Shows the main menu until the user chooses to exit
    public void showMainMenu() {
        boolean running = true;
        while (running) {
            String menu = "\n===== ChocAn Main Menu =====\n"
                    + "1) Operator login\n"
                    + "2) Provider login\n"
                    + "3) Manager login\n"
                    + "0) Exit program";
            String choice = Terminal.showMenu(menu);

            if (choice.equals("1")) {
                tryLogin("operator");
            } else if (choice.equals("2")) {
                tryLogin("provider");
            } else if (choice.equals("3")) {
                tryLogin("manager");
            } else if (choice.equals("0")) {
                running = false;
            } else {
                Terminal.displayMessage("Invalid choice. Please enter 0, 1, 2, or 3.");
            }
        }
    }

    // Asks for a number, checks it, and opens the right menu
    private void tryLogin(String role) {
        String number = Terminal.readInput("Enter your 9-digit " + role + " number:");
        if (login(role, number)) {
            Terminal.displayMessage("Login successful. Welcome!");
            launchTerminal(role);
            logout();
        } else {
            Terminal.displayMessage("Login failed: " + role + " number not found.");
        }
    }

    // Returns true if the number is valid for the role
    public boolean login(String role, String number) {
        boolean valid = false;
        if (role.equals("operator")) {
            valid = verifier.verifyOperator(number);
        } else if (role.equals("provider")) {
            valid = verifier.verifyProvider(number);
        } else if (role.equals("manager")) {
            valid = verifier.verifyManager(number);
        }

        if (valid) {
            currentUserNumber = number;
            loggedIn = true;
        }
        return valid;
    }

    public void logout() {
        currentUserNumber = "";
        loggedIn = false;
        Terminal.displayMessage("You have been logged out.");
    }

    // Opens the menu for the role that just logged in
    private void launchTerminal(String role) {
        if (role.equals("operator")) {
            OperatorTerminal terminal = new OperatorTerminal(database, currentUserNumber);
            terminal.run();
        } else if (role.equals("provider")) {
            ProviderTerminal terminal = new ProviderTerminal(database, currentUserNumber);
            terminal.run();
        } else if (role.equals("manager")) {
            ManagerTerminal terminal = new ManagerTerminal(database, currentUserNumber);
            terminal.run();
        }
    }
}

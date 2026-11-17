// Author: Isaac Kim
// Main is where the ChocAn program starts.
// It loads the database and then shows the main menu.

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to Chocoholics Anonymous (ChocAn)");

        ChocAnDatabase database = new ChocAnDatabase();
        MainMenu menu = new MainMenu(database);
        menu.showMainMenu();

        Terminal.displayMessage("Goodbye!");
    }
}

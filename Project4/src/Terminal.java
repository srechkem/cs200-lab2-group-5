// Author: Isaac Kim
// Terminal handles all input and output for the program.
// Every other class uses Terminal.readInput() and Terminal.displayMessage()
// instead of using System.out and Scanner directly.

import java.util.Scanner;

public class Terminal {

    private static Scanner scanner = new Scanner(System.in);

    // Shows a message to the user
    public static void displayMessage(String text) {
        System.out.println(text);
    }

    // Asks the user a question and returns what they typed
    public static String readInput(String prompt) {
        System.out.print(prompt + " ");
        if (!scanner.hasNextLine()) {
            // No more input (for example, the input file ran out)
            System.out.println();
            System.out.println("No more input. Exiting the program.");
            System.exit(0);
        }
        return scanner.nextLine().trim();
    }

    // Shows a menu and returns the user's choice
    public static String showMenu(String menuText) {
        displayMessage(menuText);
        return readInput("Enter your choice:");
    }

    // Asks a yes/no question and returns true for yes
    public static boolean askYesNo(String prompt) {
        String answer = readInput(prompt + " (y/n):");
        return answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes");
    }
}

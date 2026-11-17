// Author: Isaac Kim
// PLACEHOLDER: this is a temporary version of OperatorTerminal so the program
// compiles and the Operator menu can be shown. Sarah Rechkemmer will replace
// this whole file with the finished version.

public class OperatorTerminal {

    private String operatorNumber;

    public OperatorTerminal(ChocAnDatabase database, String operatorNumber) {
        this.operatorNumber = operatorNumber;
    }

    // Shows the Operator menu until the user logs out
    public void run() {
        boolean running = true;
        while (running) {
            String menu = "\n===== Operator Menu =====\n"
                    + "1) Manage members\n"
                    + "2) Manage providers\n"
                    + "0) Log out";
            String choice = Terminal.showMenu(menu);

            if (choice.equals("0")) {
                running = false;
            } else {
                Terminal.displayMessage("This feature is coming soon.");
            }
        }
    }
}

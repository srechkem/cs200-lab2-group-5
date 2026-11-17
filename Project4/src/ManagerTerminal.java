// Author: Isaac Kim
// PLACEHOLDER: this is a temporary version of ManagerTerminal so the program
// compiles and the Manager menu can be shown. Ross Craig will replace
// this whole file with the finished version.

public class ManagerTerminal {

    private String managerNumber;

    public ManagerTerminal(ChocAnDatabase database, String managerNumber) {
        this.managerNumber = managerNumber;
    }

    // Shows the Manager menu until the user logs out
    public void run() {
        boolean running = true;
        while (running) {
            String menu = "\n===== Manager Menu =====\n"
                    + "1) Run main accounting procedure\n"
                    + "2) Summary report\n"
                    + "3) Member report\n"
                    + "4) Provider report\n"
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

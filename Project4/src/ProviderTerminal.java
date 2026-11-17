// Author: Isaac Kim
// PLACEHOLDER: this is a temporary version of ProviderTerminal so the program
// compiles and the Provider menu can be shown. Jackson Turner will replace
// this whole file with the finished version.

public class ProviderTerminal {

    private String providerNumber;

    public ProviderTerminal(ChocAnDatabase database, String providerNumber) {
        this.providerNumber = providerNumber;
    }

    // Shows the Provider menu until the user logs out
    public void run() {
        boolean running = true;
        while (running) {
            String menu = "\n===== Provider Menu =====\n"
                    + "1) Validate member card\n"
                    + "2) Bill ChocAn for a service\n"
                    + "3) Request provider directory\n"
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

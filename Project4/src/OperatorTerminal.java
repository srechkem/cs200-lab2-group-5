// Author: Sarah Rechkemmer
// OperatorTerminal is the menu an operator sees after logging in.
// The operator can add, edit, and delete members and providers.

public class OperatorTerminal {

    private String operatorNumber;
    private MemberController memberController;
    private ProviderController providerController;

    public OperatorTerminal(ChocAnDatabase database, String operatorNumber) {
        this.operatorNumber = operatorNumber;
        this.memberController = new MemberController(database);
        this.providerController = new ProviderController(database);
    }

    // Shows the operator menu until the operator logs out
    public void run() {
        boolean running = true;
        while (running) {
            String menu = "\n===== Operator Menu =====\n"
                    + "1) Manage members\n"
                    + "2) Manage providers\n"
                    + "0) Log out";
            String choice = Terminal.showMenu(menu);

            if (choice.equals("1")) {
                manageMembers();
            } 
            else if (choice.equals("2")) {
                manageProviders();
            } 
            else if (choice.equals("0")) {
                running = false;
            } 
            else {
                Terminal.displayMessage("Invalid choice.");
            }
        }
    }

    // ===================== MEMBERS =====================

    private void manageMembers() {
        boolean running = true;
        while (running) {
            String menu = "\n----- Manage Members -----\n"
                    + "1) Add member\n"
                    + "2) Edit member\n"
                    + "3) Delete member\n"
                    + "4) Suspend or reinstate member\n"
                    + "5) List all members\n"
                    + "0) Back";
            String choice = Terminal.showMenu(menu);

            if (choice.equals("1")) {
                addMember();
            } 
            else if (choice.equals("2")) {
                editMember();
            } 
            else if (choice.equals("3")) {
                deleteMember();
            } 
            else if (choice.equals("4")) {
                changeMemberStatus();
            } 
            else if (choice.equals("5")) {
                Terminal.displayMessage(memberController.listMembers());
            } 
            else if (choice.equals("0")) {
                running = false;
            } 
            else {
                Terminal.displayMessage("Invalid choice.");
            }
        }
    }

    private void addMember() {
        String name = askForText("Member name (max 25 characters):", 25);
        String street = askForText("Street address (max 25 characters):", 25);
        String city = askForText("City (max 14 characters):", 14);
        String state = askForState("");
        String zip = askForZip("");

        String number = memberController.getNextMemberNumber();
        Member m = new Member(name, number, street, city, state, zip, "Active");

        if (memberController.addMember(m)) {
            Terminal.displayMessage("Member added. Their member number is " + number + ".");
        } 
        else {
            Terminal.displayMessage("Could not add member: that number is already used.");
        }
    }

    private void editMember() {
        String number = Terminal.readInput("Enter the member number to edit:");
        Member m = memberController.findMember(number);
        if (m == null) {
            Terminal.displayMessage("No member has that number.");
            return;
        }

        Terminal.displayMessage("Current information:\n" + m.getInfo()
                + "\n\nFor each question, press Enter to keep the current value.");

        String name = askToKeepOrChange("Name", m.getName(), 25);
        String street = askToKeepOrChange("Street address", m.getStreetAddress(), 25);
        String city = askToKeepOrChange("City", m.getCity(), 14);
        String state = askForState(m.getState());
        String zip = askForZip(m.getZip());

        memberController.updateMember(number, name, street, city, state, zip);
        Terminal.displayMessage("Member updated:\n" + m.getInfo());
    }

    private void deleteMember() {
        String number = Terminal.readInput("Enter the member number to delete:");
        Member m = memberController.findMember(number);
        if (m == null) {
            Terminal.displayMessage("No member has that number.");
            return;
        }

        if (Terminal.askYesNo("Delete " + m.getName() + " (" + number + ")?")) {
            memberController.deleteMember(number);
            Terminal.displayMessage("Member deleted.");
        } 
        else {
            Terminal.displayMessage("Delete cancelled.");
        }
    }

    private void changeMemberStatus() {
        String number = Terminal.readInput("Enter the member number:");
        Member m = memberController.findMember(number);
        if (m == null) {
            Terminal.displayMessage("No member has that number.");
            return;
        }

        if (m.isActive()) {
            if (Terminal.askYesNo(m.getName() + " is Active. Suspend this member?")) {
                memberController.updateStatus(number, "Suspended");
                Terminal.displayMessage("Member suspended.");
            }
        } 
        else {
            if (Terminal.askYesNo(m.getName() + " is Suspended. Reinstate this member?")) {
                memberController.updateStatus(number, "Active");
                Terminal.displayMessage("Member reinstated.");
            }
        }
    }

    // ===================== PROVIDERS =====================

    private void manageProviders() {
        boolean running = true;
        while (running) {
            String menu = "\n----- Manage Providers -----\n"
                    + "1) Add provider\n"
                    + "2) Edit provider\n"
                    + "3) Delete provider\n"
                    + "4) List all providers\n"
                    + "0) Back";
            String choice = Terminal.showMenu(menu);

            if (choice.equals("1")) {
                addProvider();
            } 
            else if (choice.equals("2")) {
                editProvider();
            } 
            else if (choice.equals("3")) {
                deleteProvider();
            } 
            else if (choice.equals("4")) {
                Terminal.displayMessage(providerController.listProviders());
            } 
            else if (choice.equals("0")) {
                running = false;
            } 
            else {
                Terminal.displayMessage("Invalid choice.");
            }
        }
    }

    private void addProvider() {
        String name = askForText("Provider name (max 25 characters):", 25);
        String street = askForText("Street address (max 25 characters):", 25);
        String city = askForText("City (max 14 characters):", 14);
        String state = askForState("");
        String zip = askForZip("");

        String number = providerController.getNextProviderNumber();
        Provider p = new Provider(name, number, street, city, state, zip);

        if (providerController.addProvider(p)) {
            Terminal.displayMessage("Provider added. Their provider number is " + number + ".");
        } 
        else {
            Terminal.displayMessage("Could not add provider: that number is already used.");
        }
    }

    private void editProvider() {
        String number = Terminal.readInput("Enter the provider number to edit:");
        Provider p = providerController.findProvider(number);
        if (p == null) {
            Terminal.displayMessage("No provider has that number.");
            return;
        }

        Terminal.displayMessage("Current information:\n" + p.getInfo()
                + "\n\nFor each question, press Enter to keep the current value.");

        String name = askToKeepOrChange("Name", p.getName(), 25);
        String street = askToKeepOrChange("Street address", p.getStreetAddress(), 25);
        String city = askToKeepOrChange("City", p.getCity(), 14);
        String state = askForState(p.getState());
        String zip = askForZip(p.getZip());

        providerController.updateProvider(number, name, street, city, state, zip);
        Terminal.displayMessage("Provider updated:\n" + p.getInfo());
    }

    private void deleteProvider() {
        String number = Terminal.readInput("Enter the provider number to delete:");
        Provider p = providerController.findProvider(number);
        if (p == null) {
            Terminal.displayMessage("No provider has that number.");
            return;
        }

        if (Terminal.askYesNo("Delete " + p.getName() + " (" + number + ")?")) {
            providerController.deleteProvider(number);
            Terminal.displayMessage("Provider deleted.");
        } 
        else {
            Terminal.displayMessage("Delete cancelled.");
        }
    }

    // ===================== INPUT HELPERS =====================

    // Keeps asking until the user types something that is not too long
    private String askForText(String prompt, int maxLength) {
        while (true) {
            String text = Terminal.readInput(prompt);
            if (text.length() == 0) {
                Terminal.displayMessage("This cannot be empty.");
            } 
            else if (text.length() > maxLength) {
                Terminal.displayMessage("Too long. The limit is " + maxLength + " characters.");
            } 
            else {
                return text;
            }
        }
    }

    // Used when editing: pressing Enter keeps the old value
    private String askToKeepOrChange(String fieldName, String currentValue, int maxLength) {
        while (true) {
            String text = Terminal.readInput(fieldName + " [" + currentValue + "]:");
            if (text.length() == 0) {
                return currentValue;
            } 
            else if (text.length() > maxLength) {
                Terminal.displayMessage("Too long. The limit is " + maxLength + " characters.");
            } 
            else {
                return text;
            }
        }
    }

    // State must be 2 letters. If currentValue is not empty, Enter keeps it.
    private String askForState(String currentValue) {
        while (true) {
            String prompt = "State (2 letters):";
            if (currentValue.length() > 0) {
                prompt = "State [" + currentValue + "]:";
            }
            String state = Terminal.readInput(prompt).toUpperCase();
            if (state.length() == 0 && currentValue.length() > 0) {
                return currentValue;
            }
            if (state.length() == 2 && Character.isLetter(state.charAt(0)) && Character.isLetter(state.charAt(1))) {
                return state;
            }
            Terminal.displayMessage("Please enter exactly 2 letters, like AL.");
        }
    }

    // ZIP code must be 5 digits. If currentValue is not empty, Enter keeps it.
    private String askForZip(String currentValue) {
        while (true) {
            String prompt = "ZIP code (5 digits):";
            if (currentValue.length() > 0) {
                prompt = "ZIP code [" + currentValue + "]:";
            }
            String zip = Terminal.readInput(prompt);
            if (zip.length() == 0 && currentValue.length() > 0) {
                return currentValue;
            }
            if (zip.length() == 5 && isAllDigits(zip)) {
                return zip;
            }
            Terminal.displayMessage("Please enter exactly 5 digits.");
        }
    }

    private boolean isAllDigits(String text) {
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}

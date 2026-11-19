// Author: Jackson Turner
// ProviderTerminal is the menu a provider sees after logging in.
// The provider can scan/check a member's card, bill ChocAnon for a service,
// and/or request the provider directory.

public class ProviderTerminal {

    private String providerNumber;
    private BillServiceController billController;
    private ProviderDirectoryController directoryController;

    public ProviderTerminal(ChocAnDatabase database, String providerNumber) {

        this.providerNumber = providerNumber;
        this.billController = new BillServiceController(database);
        this.directoryController = new ProviderDirectoryController(database);

    }

    // Shows the provider menu until the provider logs out
    public void run() {

        boolean running = true;
        while (running) {

            String menu = "\n===== Provider Menu =====\n"
                    + "1) Validate member card\n"
                    + "2) Bill ChocAn for a service\n"
                    + "3) Request provider directory\n"
                    + "0) Log out";
            String choice = Terminal.showMenu(menu);

            if (choice.equals("1")) {
                validateMember();
            } else if (choice.equals("2")) {
                billService();
            } else if (choice.equals("3")) {
                requestDirectory();
            } else if (choice.equals("0")) {
                running = false;
            } else {
                Terminal.displayMessage("Invalid choice.");
            }
        }
    }

    // Option 1: the provider swipes (types) a member's card number into the system
    private void validateMember() {

        String memberNumber = Terminal.readInput("Enter the 9-digit member number:");
        String result = billController.validateMember(memberNumber);
        Terminal.displayMessage(result);

    }

    // Option 2: record a service the provider provided to a member
    private void billService() {

        // Step 1: the member number must be valid and in good standing
        String memberNumber = Terminal.readInput("Enter the 9-digit member number:");

        String result = billController.validateMember(memberNumber);

        Terminal.displayMessage(result);

        if (!result.equals("Validated")) {
            Terminal.displayMessage("Cannot bill for this member.");
            return;
        }

        // Step 2: date of service
        String date = Terminal.readInput("Enter the date of service (MM-DD-YYYY):");
        while (!billController.isValidDate(date)) {

            Terminal.displayMessage("That is not a valid date. Use MM-DD-YYYY and not a future date.");
            date = Terminal.readInput("Enter the date of service (MM-DD-YYYY):");

        }

        // Step 3: provider types in the service code, and then the provider confirms the service name
        Service service = null;
        boolean confirmed = false;
        while (!confirmed) {

            String code = Terminal.readInput("Enter the 6-digit service code (or 0 to cancel):");

            if (code.equals("0")) {
                Terminal.displayMessage("Billing cancelled.");
                return;
            }

            service = billController.getService(code);

            if (service == null) {
                Terminal.displayMessage("No service has that code. Try again, or look at the provider directory.");
            } else {
                confirmed = Terminal.askYesNo("The service is \"" + service.getServiceName() + "\". Is this correct?");
            }

        }

        // Step 4: optional comments about the service that the provider can enter
        String comments = Terminal.readInput("Comments (optional, max 100 characters, press Enter to skip):");

        while (comments.length() > 100) {

            Terminal.displayMessage("Too long. The limit is 100 characters.");
            comments = Terminal.readInput("Comments (optional, max 100 characters):");

        }

        // Step 5: save the record and display the fee
        ServiceRecord record = billController.billChocAn(memberNumber, providerNumber, date,
                service.getServiceCode(), comments);

        String message = "Service recorded.\n"
                + "Member:  " + memberNumber + "\n"
                + "Service: " + service.getServiceName() + " (" + service.getServiceCode() + ")\n"
                + "Date:    " + record.getDateOfService() + "\n"
                + "Fee to be paid: $" + String.format("%.2f", record.getFee());

        Terminal.displayMessage(message);
    }

    // Option 3: display the provider directory and save it as a text file
    private void requestDirectory() {
        ProviderDirectory directory = directoryController.requestDirectory(providerNumber);
        Terminal.displayMessage(directory.toFileText());
        Terminal.displayMessage("The directory was also saved to " + directoryController.getFileName(providerNumber));
    }
}

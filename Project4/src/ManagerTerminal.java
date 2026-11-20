// Author: Ross Craig
// ManagerTerminal is the menu a manager sees after logging in.
// The manager can run the weekly accounting procedure or ask
// for a summary, member, or provider report at any time.

public class ManagerTerminal {

    private String managerNumber;
    private ReportController reportController;
    private AccountingController accountingController;

    public ManagerTerminal(ChocAnDatabase database, String managerNumber) {
        this.managerNumber = managerNumber;
        this.reportController = new ReportController(database);
        this.accountingController = new AccountingController(database, reportController);
    }

    // Shows the manager menu until the manager logs out
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

            if (choice.equals("1")) {
                String result = accountingController.runMainAccountingProcedure();
                Terminal.displayMessage(result);
            } else if (choice.equals("2")) {
                showSummaryReport();
            } else if (choice.equals("3")) {
                showMemberReport();
            } else if (choice.equals("4")) {
                showProviderReport();
            } else if (choice.equals("0")) {
                running = false;
            } else {
                Terminal.displayMessage("Invalid choice.");
            }
        }
    }

    private void showSummaryReport() {
        SummaryReport report = reportController.generateSummaryReport();
        displayReport(report);
    }

    private void showMemberReport() {
        String number = Terminal.readInput("Enter the member number:");
        MemberReport report = reportController.generateMemberReport(number);
        if (report == null) {
            Terminal.displayMessage("No member has that number.");
        } else {
            displayReport(report);
        }
    }

    private void showProviderReport() {
        String number = Terminal.readInput("Enter the provider number:");
        ProviderReport report = reportController.generateProviderReport(number);
        if (report == null) {
            Terminal.displayMessage("No provider has that number.");
        } else {
            displayReport(report);
        }
    }

    // Shows a report on the screen and saves a copy to a file
    public void displayReport(Report r) {
        Terminal.displayMessage(r.getReportText());
        String fileName = reportController.writeReportFile(r);
        Terminal.displayMessage("Report saved to " + fileName);
    }
}

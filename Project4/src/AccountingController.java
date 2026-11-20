// Author: Ross Craig
// AccountingController runs the main accounting procedure:
// it makes all of the weekly reports and writes the EFT file
// that tells the bank how much to pay each provider.

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class AccountingController {

    private ChocAnDatabase database;
    private ReportController reportController;
    private String eftFolder = "eft";

    public AccountingController(ChocAnDatabase database, ReportController reportController) {
        this.database = database;
        this.reportController = reportController;
    }

    // Runs the whole weekly accounting procedure and returns a message
    public String runMainAccountingProcedure() {
        String reportMessage = reportController.runWeeklyReports();

        ArrayList<EFTRecord> eftRecords = createEFTRecords();
        String eftFile = writeEFTFile(eftRecords);

        double total = 0;
        for (int i = 0; i < eftRecords.size(); i++) {
            total = total + eftRecords.get(i).getAmount();
        }

        return "Main accounting procedure finished.\n\n"
                + reportMessage + "\n\n"
                + "EFT records created: " + eftRecords.size() + "\n"
                + "Total to pay providers: $" + String.format("%.2f", total) + "\n"
                + "EFT file saved: " + eftFile;
    }

    // Makes one EFT record for each provider who gave services this week
    public ArrayList<EFTRecord> createEFTRecords() {
        ArrayList<EFTRecord> eftRecords = new ArrayList<EFTRecord>();
        ArrayList<Provider> providers = database.getAllProviders();
        for (int i = 0; i < providers.size(); i++) {
            Provider p = providers.get(i);
            double total = calculateProviderTotal(p.getNumber());
            if (total > 0) {
                eftRecords.add(new EFTRecord(p.getName(), p.getNumber(), total));
            }
        }
        return eftRecords;
    }

    // Adds up what one provider earned this week
    private double calculateProviderTotal(String providerNumber) {
        ProviderReport report = reportController.generateProviderReport(providerNumber);
        return report.getTotalFee();
    }

    // Saves the EFT records to a file. Returns the file name, or "" if it failed.
    private String writeEFTFile(ArrayList<EFTRecord> records) {
        try {
            File folder = new File(eftFolder);
            folder.mkdirs();
            String fileName = eftFolder + "/EFT_" + DateHelper.today() + ".txt";
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            writer.println("ChocAn EFT Data - " + DateHelper.today());
            writer.println(String.format("%-28s %-10s %10s", "Provider", "Number", "Amount"));
            for (int i = 0; i < records.size(); i++) {
                writer.println(records.get(i).toFileLine());
            }
            writer.close();
            return fileName;
        } catch (Exception e) {
            return "";
        }
    }
}

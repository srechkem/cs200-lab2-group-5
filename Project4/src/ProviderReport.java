// Author: Ross Craig
// ProviderReport lists every service one provider gave this week,
// plus the number of consultations and the total fee.

public class ProviderReport extends Report {

    private Provider provider;

    public ProviderReport(Provider provider) {
        super();
        this.provider = provider;
    }

    public Provider getProvider() {
        return provider;
    }

    // The number of consultations is the number of services in the report
    public int getConsultationCount() {
        return details.size();
    }

    // Adds up the fees of every service in the report
    public double getTotalFee() {
        double total = 0;
        for (int i = 0; i < details.size(); i++) {
            total = total + details.get(i).getFee();
        }
        return total;
    }

    public String buildFileName() {
        return makeSafeName(provider.getName()) + "_" + reportDate + ".txt";
    }

    public String getReportText() {
        String text = "========== PROVIDER REPORT ==========\n";
        text = text + "Report date: " + reportDate + "\n\n";
        text = text + "Provider name:   " + provider.getName() + "\n";
        text = text + "Provider number: " + provider.getNumber() + "\n";
        text = text + "Address:         " + provider.getStreetAddress() + "\n";
        text = text + "                 " + provider.getCity() + ", " + provider.getState() + " " + provider.getZip() + "\n\n";

        if (details.size() == 0) {
            text = text + "No services this week.\n";
        } else {
            text = text + "Services this week:\n";
            text = text + String.format("%-12s %-20s %-18s %-10s %-7s %8s\n",
                    "Date", "Received", "Member", "Member #", "Code", "Fee");
            for (int i = 0; i < details.size(); i++) {
                ReportDetails d = details.get(i);
                text = text + String.format("%-12s %-20s %-18s %-10s %-7s %8.2f\n",
                        d.getDateOfService(), d.getDateTimeReceived(), d.getMemberName(),
                        d.getMemberNumber(), d.getServiceCode(), d.getFee());
            }
        }

        text = text + "\nTotal consultations: " + getConsultationCount() + "\n";
        text = text + "Total fee for the week: $" + String.format("%.2f", getTotalFee()) + "\n";
        return text;
    }
}

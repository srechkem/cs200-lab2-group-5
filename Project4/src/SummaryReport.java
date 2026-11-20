// Author: Ross Craig
// SummaryReport is for the manager. It lists every provider who should be
// paid this week, how many consultations they had, and their total fee.

import java.util.ArrayList;

public class SummaryReport extends Report {

    // These three lists line up: index 0 in each list is the same provider
    private ArrayList<String> providerNames = new ArrayList<String>();
    private ArrayList<Integer> consultationCounts = new ArrayList<Integer>();
    private ArrayList<Double> providerFees = new ArrayList<Double>();

    public SummaryReport() {
        super();
    }

    public void addProviderTotal(String providerName, int count, double fee) {
        providerNames.add(providerName);
        consultationCounts.add(count);
        providerFees.add(fee);
    }

    public int getProviderCount() {
        return providerNames.size();
    }

    public int getConsultationCount() {
        int total = 0;
        for (int i = 0; i < consultationCounts.size(); i++) {
            total = total + consultationCounts.get(i);
        }
        return total;
    }

    public double getTotalFee() {
        double total = 0;
        for (int i = 0; i < providerFees.size(); i++) {
            total = total + providerFees.get(i);
        }
        return total;
    }

    public String buildFileName() {
        return "SummaryReport_" + reportDate + ".txt";
    }

    public String getReportText() {
        String text = "========== SUMMARY REPORT ==========\n";
        text = text + "Report date: " + reportDate + "\n\n";

        if (providerNames.size() == 0) {
            text = text + "No providers gave services this week.\n";
        } else {
            text = text + String.format("%-28s %14s %12s\n", "Provider", "Consultations", "Fee");
            for (int i = 0; i < providerNames.size(); i++) {
                text = text + String.format("%-28s %14d %12.2f\n",
                        providerNames.get(i), consultationCounts.get(i), providerFees.get(i));
            }
        }

        text = text + "\nProviders to be paid: " + getProviderCount() + "\n";
        text = text + "Total consultations:  " + getConsultationCount() + "\n";
        text = text + "Total fee:            $" + String.format("%.2f", getTotalFee()) + "\n";
        return text;
    }
}

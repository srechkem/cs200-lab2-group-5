// Author: Ross Craig
// Report is the parent class for MemberReport, ProviderReport, and SummaryReport.
// It is abstract, which means we never make a plain "Report" object.
// Each child class decides its own file name and how its text looks.

import java.util.ArrayList;

public abstract class Report {

    protected String reportDate;
    protected ArrayList<ReportDetails> details;

    public Report() {
        this.reportDate = DateHelper.today();
        this.details = new ArrayList<ReportDetails>();
    }

    public void addDetail(ReportDetails d) {
        details.add(d);
    }

    public ArrayList<ReportDetails> getDetails() {
        return details;
    }

    // Child classes must write these two methods
    public abstract String buildFileName();

    public abstract String getReportText();

    // Turns a name like "Dr. Sam Patel" into "Dr_Sam_Patel" for a file name
    protected String makeSafeName(String name) {
        return name.replace(" ", "_").replace(".", "").replace(",", "");
    }
}

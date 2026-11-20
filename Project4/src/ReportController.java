// Author: Ross Craig
// ReportController builds member, provider, and summary reports
// from this week's service records, and saves them to the "reports" folder.

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ReportController {

    private ChocAnDatabase database;
    private String reportFolder = "reports";

    public ReportController(ChocAnDatabase database) {
        this.database = database;
    }

    // Returns every service record from the last 7 days
    public ArrayList<ServiceRecord> getRecordsForWeek() {
        ArrayList<ServiceRecord> weekRecords = new ArrayList<ServiceRecord>();
        ArrayList<ServiceRecord> allRecords = database.getAllServiceRecords();
        for (int i = 0; i < allRecords.size(); i++) {
            ServiceRecord r = allRecords.get(i);
            if (DateHelper.isWithinLastWeek(r.getDateOfService())) {
                weekRecords.add(r);
            }
        }
        return weekRecords;
    }

    // Looks up the names for a record and makes a ReportDetails object
    private ReportDetails makeDetails(ServiceRecord r) {
        String memberName = "(deleted member)";
        Member m = database.findMember(r.getMemberNumber());
        if (m != null) {
            memberName = m.getName();
        }

        String providerName = "(deleted provider)";
        Provider p = database.findProvider(r.getProviderNumber());
        if (p != null) {
            providerName = p.getName();
        }

        String serviceName = "(unknown service)";
        Service s = database.findService(r.getServiceCode());
        if (s != null) {
            serviceName = s.getServiceName();
        }

        return new ReportDetails(r.getDateOfService(), r.getDateTimeReceived(), memberName,
                r.getMemberNumber(), providerName, r.getProviderNumber(), r.getServiceCode(),
                serviceName, r.getFee());
    }

    // Builds the report for one member. Returns null if the member does not exist.
    public MemberReport generateMemberReport(String memberNumber) {
        Member m = database.findMember(memberNumber);
        if (m == null) {
            return null;
        }
        MemberReport report = new MemberReport(m);
        ArrayList<ServiceRecord> records = getRecordsForWeek();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getMemberNumber().equals(memberNumber)) {
                report.addDetail(makeDetails(records.get(i)));
            }
        }
        return report;
    }

    // Builds the report for one provider. Returns null if the provider does not exist.
    public ProviderReport generateProviderReport(String providerNumber) {
        Provider p = database.findProvider(providerNumber);
        if (p == null) {
            return null;
        }
        ProviderReport report = new ProviderReport(p);
        ArrayList<ServiceRecord> records = getRecordsForWeek();
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getProviderNumber().equals(providerNumber)) {
                report.addDetail(makeDetails(records.get(i)));
            }
        }
        return report;
    }

    // Builds the summary report: every provider who gave a service this week
    public SummaryReport generateSummaryReport() {
        SummaryReport summary = new SummaryReport();
        ArrayList<Provider> providers = database.getAllProviders();
        for (int i = 0; i < providers.size(); i++) {
            ProviderReport pr = generateProviderReport(providers.get(i).getNumber());
            if (pr.getConsultationCount() > 0) {
                summary.addProviderTotal(providers.get(i).getName(), pr.getConsultationCount(), pr.getTotalFee());
            }
        }
        return summary;
    }

    // Saves a report to a text file. Returns the file name, or "" if it failed.
    public String writeReportFile(Report r) {
        try {
            File folder = new File(reportFolder);
            folder.mkdirs();
            String fileName = reportFolder + "/" + r.buildFileName();
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            writer.print(r.getReportText());
            writer.close();
            return fileName;
        } catch (Exception e) {
            return "";
        }
    }

    // Makes all of the weekly reports and saves them.
    // Returns a short message saying what was made.
    public String runWeeklyReports() {
        int memberReports = 0;
        int providerReports = 0;

        // A report for each member who got at least one service
        ArrayList<Member> members = database.getAllMembers();
        for (int i = 0; i < members.size(); i++) {
            MemberReport mr = generateMemberReport(members.get(i).getNumber());
            if (mr.getDetails().size() > 0) {
                writeReportFile(mr);
                memberReports++;
            }
        }

        // A report for each provider who gave at least one service
        ArrayList<Provider> providers = database.getAllProviders();
        for (int i = 0; i < providers.size(); i++) {
            ProviderReport pr = generateProviderReport(providers.get(i).getNumber());
            if (pr.getConsultationCount() > 0) {
                writeReportFile(pr);
                providerReports++;
            }
        }

        // One summary report for the manager
        SummaryReport summary = generateSummaryReport();
        String summaryFile = writeReportFile(summary);

        return "Member reports saved:   " + memberReports + "\n"
                + "Provider reports saved: " + providerReports + "\n"
                + "Summary report saved:   " + summaryFile + "\n"
                + "(All reports are in the \"" + reportFolder + "\" folder.)";
    }
}

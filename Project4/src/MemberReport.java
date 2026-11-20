// Author: Ross Craig
// MemberReport lists every service one member received this week.

public class MemberReport extends Report {

    private Member member;

    public MemberReport(Member member) {
        super();
        this.member = member;
    }

    public String buildFileName() {
        return makeSafeName(member.getName()) + "_" + reportDate + ".txt";
    }

    public String getReportText() {
        String text = "========== MEMBER REPORT ==========\n";
        text = text + "Report date: " + reportDate + "\n\n";
        text = text + "Member name:   " + member.getName() + "\n";
        text = text + "Member number: " + member.getNumber() + "\n";
        text = text + "Address:       " + member.getStreetAddress() + "\n";
        text = text + "               " + member.getCity() + ", " + member.getState() + " " + member.getZip() + "\n\n";

        if (details.size() == 0) {
            text = text + "No services this week.\n";
        } else {
            text = text + "Services this week:\n";
            text = text + String.format("%-12s %-25s %-32s\n", "Date", "Provider", "Service");
            for (int i = 0; i < details.size(); i++) {
                ReportDetails d = details.get(i);
                text = text + String.format("%-12s %-25s %-32s\n",
                        d.getDateOfService(), d.getProviderName(), d.getServiceName());
            }
        }
        return text;
    }
}

// Author: Ross Craig
// ReportDetails holds the information about one service for a report:
// who got it, who gave it, what it was, when, and how much it cost.

public class ReportDetails {

    private String dateOfService;
    private String dateTimeReceived;
    private String memberName;
    private String memberNumber;
    private String providerName;
    private String providerNumber;
    private String serviceCode;
    private String serviceName;
    private double fee;

    public ReportDetails(String dateOfService, String dateTimeReceived, String memberName, String memberNumber,
                         String providerName, String providerNumber, String serviceCode,
                         String serviceName, double fee) {
        this.dateOfService = dateOfService;
        this.dateTimeReceived = dateTimeReceived;
        this.memberName = memberName;
        this.memberNumber = memberNumber;
        this.providerName = providerName;
        this.providerNumber = providerNumber;
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.fee = fee;
    }

    public String getDateOfService() {
        return dateOfService;
    }

    public String getDateTimeReceived() {
        return dateTimeReceived;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public String getProviderName() {
        return providerName;
    }

    public String getProviderNumber() {
        return providerNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getFee() {
        return fee;
    }
}

// Author: Isaac Kim
// A ServiceRecord is saved every time a provider bills ChocAn
// for a service they gave to a member.

public class ServiceRecord {

    private String dateTimeReceived;   // MM-DD-YYYY HH:MM:SS (when it was entered)
    private String dateOfService;      // MM-DD-YYYY (when the service happened)
    private String providerNumber;
    private String memberNumber;
    private String serviceCode;
    private double fee;
    private String comments;

    public ServiceRecord(String dateTimeReceived, String dateOfService, String providerNumber,
                         String memberNumber, String serviceCode, double fee, String comments) {
        this.dateTimeReceived = dateTimeReceived;
        this.dateOfService = dateOfService;
        this.providerNumber = providerNumber;
        this.memberNumber = memberNumber;
        this.serviceCode = serviceCode;
        this.fee = fee;
        this.comments = comments;
    }

    public String getDateTimeReceived() {
        return dateTimeReceived;
    }

    public String getDateOfService() {
        return dateOfService;
    }

    public String getProviderNumber() {
        return providerNumber;
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public double getFee() {
        return fee;
    }

    public String getComments() {
        return comments;
    }
}

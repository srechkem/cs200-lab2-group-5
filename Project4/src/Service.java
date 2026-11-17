// Author: Isaac Kim
// A Service is something a provider can do for a member,
// like a dietitian session. Each service has a 6-digit code and a fee.

public class Service {

    private String serviceCode;
    private String serviceName;
    private double fee;

    public Service(String serviceCode, String serviceName, double fee) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.fee = fee;
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

    public void setFee(double fee) {
        this.fee = fee;
    }
}

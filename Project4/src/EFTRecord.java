// Author: Ross Craig
// An EFTRecord (Electronic Funds Transfer) says how much money
// ChocAn should send to one provider's bank account this week.

public class EFTRecord {

    private String providerName;
    private String providerNumber;
    private double amount;

    public EFTRecord(String providerName, String providerNumber, double amount) {
        this.providerName = providerName;
        this.providerNumber = providerNumber;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    // One line in the EFT file
    public String toFileLine() {
        return String.format("%-28s %-10s %10.2f", providerName, providerNumber, amount);
    }
}

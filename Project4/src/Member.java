// Author: Isaac Kim
// A Member is a person who pays ChocAn and receives services.
// A member's status is either "Active" or "Suspended".

public class Member extends Person {

    private String status;

    public Member(String name, String number, String streetAddress, String city, String state, String zip, String status) {
        super(name, number, streetAddress, city, state, zip);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public boolean isActive() {
        return status.equals("Active");
    }

    public void suspend() {
        status = "Suspended";
    }

    public void reinstate() {
        status = "Active";
    }

    // Adds the status to the normal Person info
    public String getInfo() {
        return super.getInfo() + "\nStatus:  " + status;
    }
}

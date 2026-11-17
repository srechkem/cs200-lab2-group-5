// Author: Isaac Kim
// Person is the parent class for everyone in the ChocAn system:
// members, providers, operators, and managers.

public class Person {

    private String name;
    private String number;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;

    public Person(String name, String number, String streetAddress, String city, String state, String zip) {
        this.name = name;
        this.number = number;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    // Returns the person's information as a few lines of text
    public String getInfo() {
        String info = "Name:    " + name + "\n";
        info = info + "Number:  " + number + "\n";
        info = info + "Address: " + streetAddress + "\n";
        info = info + "         " + city + ", " + state + " " + zip;
        return info;
    }
}

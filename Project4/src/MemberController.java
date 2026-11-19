// Author: Sarah Rechkemmer
// MemberController adds, edits, and deletes ChocAn members.
// The OperatorTerminal asks the user for information and then
// calls these methods to change the database.

import java.util.ArrayList;

public class MemberController {
    private ChocAnDatabase database;

    public MemberController(ChocAnDatabase database) {
        this.database = database;
    }

    // Adds a new member. Returns false if the number is already used.
    public boolean addMember(Member m) {
        if (isDuplicate(m.getNumber())) {
            return false;
        }
        database.saveMember(m);
        return true;
    }

    // Deletes a member. Returns false if the member was not found.
    public boolean deleteMember(String memberNumber) {
        return database.removeMember(memberNumber);
    }

    // Changes a member's name and address. Returns false if not found.
    public boolean updateMember(String memberNumber, String name, String streetAddress,
                                String city, String state, String zip) {
        Member m = database.findMember(memberNumber);
        if (m == null) {
            return false;
        }
        m.setName(name);
        m.setStreetAddress(streetAddress);
        m.setCity(city);
        m.setState(state);
        m.setZip(zip);
        database.saveAll();
        return true;
    }

    // Changes a member's status to "Active" or "Suspended"
    public boolean updateStatus(String memberNumber, String status) {
        Member m = database.findMember(memberNumber);
        if (m == null) {
            return false;
        }
        if (status.equals("Suspended")) {
            m.suspend();
        } 
        else {
            m.reinstate();
        }
        database.saveAll();
        return true;
    }

    public Member findMember(String memberNumber) {
        return database.findMember(memberNumber);
    }

    // Makes a new member number that is 1 more than the biggest one
    public String getNextMemberNumber() {
        int biggest = 100000000;
        ArrayList<Member> members = database.getAllMembers();
        for (int i = 0; i < members.size(); i++) {
            int number = Integer.parseInt(members.get(i).getNumber());
            if (number > biggest) {
                biggest = number;
            }
        }
        return "" + (biggest + 1);
    }

    // Returns a list of every member, one per line
    public String listMembers() {
        ArrayList<Member> members = database.getAllMembers();
        if (members.size() == 0) {
            return "There are no members.";
        }
        String text = "Members:\n";
        for (int i = 0; i < members.size(); i++) {
            Member m = members.get(i);
            text = text + "  " + m.getNumber() + "  " + m.getName() + " (" + m.getStatus() + ")\n";
        }
        return text;
    }

    private boolean isDuplicate(String memberNumber) {
        return database.findMember(memberNumber) != null;
    }
}

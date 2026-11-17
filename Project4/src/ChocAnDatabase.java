// Author: Isaac Kim
// ChocAnDatabase stores all members, providers, services, and service records.
// It saves everything to text files in the "data" folder so the information
// is still there the next time the program runs.
// Each line in a file is one item, with the fields separated by "|".

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ChocAnDatabase {

    private static final String DATA_FOLDER = "data";
    private static final String MEMBERS_FILE = "data/members.txt";
    private static final String PROVIDERS_FILE = "data/providers.txt";
    private static final String SERVICES_FILE = "data/services.txt";
    private static final String RECORDS_FILE = "data/records.txt";

    private ArrayList<Member> members = new ArrayList<Member>();
    private ArrayList<Provider> providers = new ArrayList<Provider>();
    private ArrayList<Operator> operators = new ArrayList<Operator>();
    private ArrayList<Manager> managers = new ArrayList<Manager>();
    private ArrayList<Service> services = new ArrayList<Service>();
    private ArrayList<ServiceRecord> serviceRecords = new ArrayList<ServiceRecord>();

    public ChocAnDatabase() {
        // Operators and managers are ChocAn staff, so they are set up here
        operators.add(new Operator("Olivia Operator", "900000001"));
        managers.add(new Manager("Mark Manager", "800000001"));

        File membersFile = new File(MEMBERS_FILE);
        if (membersFile.exists()) {
            loadFromFiles();
        } else {
            // First time running: add sample data and save it
            addSampleData();
            saveAll();
        }
    }

    // ---------- Finding things ----------

    public Member findMember(String number) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getNumber().equals(number)) {
                return members.get(i);
            }
        }
        return null;
    }

    public Provider findProvider(String number) {
        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i).getNumber().equals(number)) {
                return providers.get(i);
            }
        }
        return null;
    }

    public Operator findOperator(String number) {
        for (int i = 0; i < operators.size(); i++) {
            if (operators.get(i).getNumber().equals(number)) {
                return operators.get(i);
            }
        }
        return null;
    }

    public Manager findManager(String number) {
        for (int i = 0; i < managers.size(); i++) {
            if (managers.get(i).getNumber().equals(number)) {
                return managers.get(i);
            }
        }
        return null;
    }

    public Service findService(String code) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).getServiceCode().equals(code)) {
                return services.get(i);
            }
        }
        return null;
    }

    // ---------- Getting the lists ----------

    public ArrayList<Member> getAllMembers() {
        return members;
    }

    public ArrayList<Provider> getAllProviders() {
        return providers;
    }

    public ArrayList<Service> getAllServices() {
        return services;
    }

    public ArrayList<ServiceRecord> getAllServiceRecords() {
        return serviceRecords;
    }

    // ---------- Adding and removing (each change is saved right away) ----------

    public void saveMember(Member m) {
        members.add(m);
        saveAll();
    }

    public boolean removeMember(String number) {
        Member m = findMember(number);
        if (m == null) {
            return false;
        }
        members.remove(m);
        saveAll();
        return true;
    }

    public void saveProvider(Provider p) {
        providers.add(p);
        saveAll();
    }

    public boolean removeProvider(String number) {
        Provider p = findProvider(number);
        if (p == null) {
            return false;
        }
        providers.remove(p);
        saveAll();
        return true;
    }

    public void saveServiceRecord(ServiceRecord r) {
        serviceRecords.add(r);
        saveAll();
    }

    // ---------- Saving to files ----------

    // Writes every list to its file
    public void saveAll() {
        try {
            File folder = new File(DATA_FOLDER);
            folder.mkdirs();

            PrintWriter writer = new PrintWriter(new FileWriter(MEMBERS_FILE));
            for (int i = 0; i < members.size(); i++) {
                Member m = members.get(i);
                writer.println(m.getNumber() + "|" + m.getName() + "|" + m.getStreetAddress() + "|"
                        + m.getCity() + "|" + m.getState() + "|" + m.getZip() + "|" + m.getStatus());
            }
            writer.close();

            writer = new PrintWriter(new FileWriter(PROVIDERS_FILE));
            for (int i = 0; i < providers.size(); i++) {
                Provider p = providers.get(i);
                writer.println(p.getNumber() + "|" + p.getName() + "|" + p.getStreetAddress() + "|"
                        + p.getCity() + "|" + p.getState() + "|" + p.getZip());
            }
            writer.close();

            writer = new PrintWriter(new FileWriter(SERVICES_FILE));
            for (int i = 0; i < services.size(); i++) {
                Service s = services.get(i);
                writer.println(s.getServiceCode() + "|" + s.getServiceName() + "|" + s.getFee());
            }
            writer.close();

            writer = new PrintWriter(new FileWriter(RECORDS_FILE));
            for (int i = 0; i < serviceRecords.size(); i++) {
                ServiceRecord r = serviceRecords.get(i);
                // A "|" in the comments would break the file, so change it to "/"
                String comments = r.getComments().replace("|", "/");
                writer.println(r.getDateTimeReceived() + "|" + r.getDateOfService() + "|"
                        + r.getProviderNumber() + "|" + r.getMemberNumber() + "|"
                        + r.getServiceCode() + "|" + r.getFee() + "|" + comments);
            }
            writer.close();
        } catch (Exception e) {
            Terminal.displayMessage("Error: could not save data. " + e.getMessage());
        }
    }

    // ---------- Loading from files ----------

    private void loadFromFiles() {
        // split("\\|") splits a line at each "|" character.
        // The -1 keeps empty fields at the end (like empty comments).
        try {
            Scanner reader = new Scanner(new File(MEMBERS_FILE));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split("\\|", -1);
                if (parts.length == 7) {
                    members.add(new Member(parts[1], parts[0], parts[2], parts[3], parts[4], parts[5], parts[6]));
                }
            }
            reader.close();

            reader = new Scanner(new File(PROVIDERS_FILE));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split("\\|", -1);
                if (parts.length == 6) {
                    providers.add(new Provider(parts[1], parts[0], parts[2], parts[3], parts[4], parts[5]));
                }
            }
            reader.close();

            reader = new Scanner(new File(SERVICES_FILE));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split("\\|", -1);
                if (parts.length == 3) {
                    services.add(new Service(parts[0], parts[1], Double.parseDouble(parts[2])));
                }
            }
            reader.close();

            reader = new Scanner(new File(RECORDS_FILE));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split("\\|", -1);
                if (parts.length == 7) {
                    serviceRecords.add(new ServiceRecord(parts[0], parts[1], parts[2], parts[3],
                            parts[4], Double.parseDouble(parts[5]), parts[6]));
                }
            }
            reader.close();
        } catch (Exception e) {
            Terminal.displayMessage("Error: could not load data. " + e.getMessage());
        }
    }

    // ---------- Sample data for the first run ----------

    private void addSampleData() {
        members.add(new Member("Alice Johnson", "100000001", "12 Oak Street", "Tuscaloosa", "AL", "35401", "Active"));
        members.add(new Member("Brian Smith", "100000002", "45 Pine Avenue", "Tuscaloosa", "AL", "35404", "Active"));
        members.add(new Member("Carla Diaz", "100000003", "78 Elm Road", "Northport", "AL", "35473", "Active"));
        members.add(new Member("David Lee", "100000004", "9 Maple Drive", "Birmingham", "AL", "35203", "Active"));
        members.add(new Member("Emma Brown", "100000005", "33 Cedar Lane", "Tuscaloosa", "AL", "35405", "Suspended"));

        providers.add(new Provider("Healthy Habits Clinic", "200000001", "100 University Blvd", "Tuscaloosa", "AL", "35401"));
        providers.add(new Provider("Dr. Sam Patel", "200000002", "250 McFarland Blvd", "Northport", "AL", "35476"));
        providers.add(new Provider("FitLife Gym", "200000003", "500 Lurleen Wallace", "Tuscaloosa", "AL", "35401"));

        services.add(new Service("598470", "Dietitian Session", 75.00));
        services.add(new Service("883948", "Aerobics Exercise Session", 50.00));
        services.add(new Service("123456", "Chocolate Addiction Counseling", 120.00));
        services.add(new Service("234567", "Group Therapy Session", 60.00));
        services.add(new Service("345678", "Nutrition Workshop", 40.00));
        services.add(new Service("456789", "Wellness Check-up", 90.00));

        // A few services from this week so the reports have something to show
        serviceRecords.add(new ServiceRecord(DateHelper.daysAgo(3) + " 10:15:00", DateHelper.daysAgo(3),
                "200000001", "100000001", "598470", 75.00, "First visit"));
        serviceRecords.add(new ServiceRecord(DateHelper.daysAgo(2) + " 14:30:00", DateHelper.daysAgo(2),
                "200000002", "100000001", "123456", 120.00, ""));
        serviceRecords.add(new ServiceRecord(DateHelper.daysAgo(2) + " 16:00:00", DateHelper.daysAgo(2),
                "200000003", "100000002", "883948", 50.00, "Morning class"));
        serviceRecords.add(new ServiceRecord(DateHelper.daysAgo(1) + " 09:45:00", DateHelper.daysAgo(1),
                "200000001", "100000003", "345678", 40.00, ""));
    }
}

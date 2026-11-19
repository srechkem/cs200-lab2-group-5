// Author: Jackson Turner
// The ProviderDirectory is the list of every service a provider can bill for,
// sorted by service name (A to Z), with its code and associated fee.

import java.util.ArrayList;

public class ProviderDirectory {

    private String generatedDate;
    private ArrayList<Service> services;

    public ProviderDirectory(ArrayList<Service> allServices) {

        this.generatedDate = DateHelper.today();

        // Copy the list so sorting does not change the database's files
        this.services = new ArrayList<Service>();

        for (int i = 0; i < allServices.size(); i++) {

            services.add(allServices.get(i));

        }

        sortAlphabetically();
    }

    // Sorts the services by name using selection sort
    public void sortAlphabetically() {

        for (int i = 0; i < services.size() - 1; i++) {

            int smallest = i;

            for (int j = i + 1; j < services.size(); j++) {

                String nameJ = services.get(j).getServiceName();
                String nameSmallest = services.get(smallest).getServiceName();
                if (nameJ.compareToIgnoreCase(nameSmallest) < 0) {

                    smallest = j;

                }
            }

            // Swap the smallest one into ith position
            Service temp = services.get(i);
            services.set(i, services.get(smallest));
            services.set(smallest, temp);
        }
    }

    public ArrayList<Service> getServices() {

        return services;

    }

    // Builds the directory as a table
    public String toFileText() {

        String text = "ChocAn Provider Directory (" + generatedDate + ")\n";
        text = text + "---------------------------------------------------\n";
        text = text + String.format("%-32s %-8s %8s\n", "Service Name", "Code", "Fee");
        text = text + "---------------------------------------------------\n";

        for (int i = 0; i < services.size(); i++) {

            Service s = services.get(i);
            text = text + String.format("%-32s %-8s %8.2f\n", s.getServiceName(), s.getServiceCode(), s.getFee());
            
        }
        return text;
    }
}

// Author: Sarah Rechkemmer
// ProviderController adds, edits, and deletes ChocAn providers.
// It works the same way as MemberController.

import java.util.ArrayList;

public class ProviderController {
    private ChocAnDatabase database;

    public ProviderController(ChocAnDatabase database) {
        this.database = database;
    }

    // Adds a new provider. Returns false if the number is already used.
    public boolean addProvider(Provider p) {
        if (isDuplicate(p.getNumber())) {
            return false;
        }
        database.saveProvider(p);
        return true;
    }

    // Deletes a provider. Returns false if the provider was not found.
    public boolean deleteProvider(String providerNumber) {
        return database.removeProvider(providerNumber);
    }

    // Changes a provider's name and address. Returns false if not found.
    public boolean updateProvider(String providerNumber, String name, String streetAddress,
                                  String city, String state, String zip) {
        Provider p = database.findProvider(providerNumber);
        if (p == null) {
            return false;
        }
        p.setName(name);
        p.setStreetAddress(streetAddress);
        p.setCity(city);
        p.setState(state);
        p.setZip(zip);
        database.saveAll();
        return true;
    }

    public Provider findProvider(String providerNumber) {
        return database.findProvider(providerNumber);
    }

    // Makes a new provider number that is 1 more than the biggest one
    public String getNextProviderNumber() {
        int biggest = 200000000;
        ArrayList<Provider> providers = database.getAllProviders();
        for (int i = 0; i < providers.size(); i++) {
            int number = Integer.parseInt(providers.get(i).getNumber());
            if (number > biggest) {
                biggest = number;
            }
        }
        return "" + (biggest + 1);
    }

    // Returns a list of every provider, one per line
    public String listProviders() {
        ArrayList<Provider> providers = database.getAllProviders();
        if (providers.size() == 0) {
            return "There are no providers.";
        }
        String text = "Providers:\n";
        for (int i = 0; i < providers.size(); i++) {
            Provider p = providers.get(i);
            text = text + "  " + p.getNumber() + "  " + p.getName() + "\n";
        }
        return text;
    }

    private boolean isDuplicate(String providerNumber) {
        return database.findProvider(providerNumber) != null;
    }
}

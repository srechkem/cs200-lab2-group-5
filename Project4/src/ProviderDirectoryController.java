// Author: Jackson Turner
// ProviderDirectoryController builds the provider directory of services and saves it
// to a text file in the "reports" folder when a provider requests it.

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class ProviderDirectoryController {

    private ChocAnDatabase database;
    private String directoryFolder = "reports";

    public ProviderDirectoryController(ChocAnDatabase database) {

        this.database = database;

    }

    // Builds the directory, saves it to a text file, and returns the directory to the provider
    public ProviderDirectory requestDirectory(String providerNumber) {

        ProviderDirectory directory = new ProviderDirectory(database.getAllServices());

        boolean saved = writeDirectoryFile(directory, providerNumber);

        if (!saved) {

            Terminal.displayMessage("Warning: the directory file could not be saved.");

        }
        return directory;
    }

    // Returns the name of the file the directory was written to
    public String getFileName(String providerNumber) {

        return directoryFolder + "/ProviderDirectory_" + providerNumber + ".txt";

    }

    private boolean writeDirectoryFile(ProviderDirectory directory, String providerNumber) {

        try {

            File folder = new File(directoryFolder);
            folder.mkdirs();
            PrintWriter writer = new PrintWriter(new FileWriter(getFileName(providerNumber)));
            writer.print(directory.toFileText());
            writer.close();
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
}

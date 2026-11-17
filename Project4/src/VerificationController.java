// Author: Isaac Kim
// VerificationController checks that the number someone types
// at login belongs to a real operator, provider, or manager.

public class VerificationController {

    private ChocAnDatabase database;

    public VerificationController(ChocAnDatabase database) {
        this.database = database;
    }

    public boolean verifyOperator(String operatorNumber) {
        return database.findOperator(operatorNumber) != null;
    }

    public boolean verifyProvider(String providerNumber) {
        return database.findProvider(providerNumber) != null;
    }

    public boolean verifyManager(String managerNumber) {
        return database.findManager(managerNumber) != null;
    }
}

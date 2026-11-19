// Author: Jackson Turner
// BillServiceController checks member cards and records the services
// that providers provide to members. By recording services, ChockAnon can then pay the providers

public class BillServiceController {

    private ChocAnDatabase database;

    public BillServiceController(ChocAnDatabase database) {

        this.database = database;

    }

    // Checks a member number and returns one of the following:
    // "Validated", "Invalid Number", or "Member Suspended"
    public String validateMember(String memberNumber) {

        Member m = database.findMember(memberNumber);

        if (m == null) {

            return "Invalid Number";

        }
        if (!m.isActive()) {

            return "Member Suspended";

        }
        return "Validated";
    }

    // Returns the service with associated with this code, or null if the code is invalid
    public Service getService(String serviceCode) {

        return database.findService(serviceCode);

    }

    // Checks the date of service in MM-DD-YYYY format, date can be in the past but no in the future
    public boolean isValidDate(String date) {

        if (!DateHelper.isValidDate(date)) {

            return false;

        }

        return DateHelper.isNotInFuture(date);

    }

    // Creates a service record and saves it. Returns the new record.
    public ServiceRecord billChocAn(String memberNumber, String providerNumber, String dateOfService,
                                    String serviceCode, String comments) {

        Service service = database.findService(serviceCode);

        String received = DateHelper.now();

        ServiceRecord record = new ServiceRecord(received, dateOfService, providerNumber,
                memberNumber, serviceCode, service.getFee(), comments);

        database.saveServiceRecord(record);
        
        return record;
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Smart_fleet {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Vehicle> vehicles = new ArrayList<>();
    static ArrayList<Driver> drivers = new ArrayList<>();
    static ArrayList<Trip> trips = new ArrayList<>();
    static ArrayList<Fuel> fuels = new ArrayList<>();
    static ArrayList<Maintenance> maintenanceList = new ArrayList<>();

    static abstract class Vehicle {

        String id, number, model, type, status;

        Vehicle(String id, String number, String model,
                String type, String status) {
            this.id = id;
            this.number = number;
            this.model = model;
            this.type = type;
            this.status = status;
        }

        abstract void displayType();

        void display() {
            System.out.println("----------------------------------------");
            System.out.println("Vehicle ID     : " + id);
            System.out.println("Vehicle Number : " + number);
            System.out.println("Model          : " + model);
            System.out.println("Type           : " + type);
            System.out.println("Status         : " + status);
            displayType();
        }
    }

    static class Car extends Vehicle {

        Car(String id, String number, String model, String status) {
            super(id, number, model, "Car", status);
        }

        @Override
        void displayType() {
            System.out.println("Category       : Passenger Car");
        }
    }

    static class Truck extends Vehicle {

        Truck(String id, String number, String model, String status) {
            super(id, number, model, "Truck", status);
        }

        @Override
        void displayType() {
            System.out.println("Category       : Heavy Transport Truck");
        }
    }

    static class Van extends Vehicle {

        Van(String id, String number, String model, String status) {
            super(id, number, model, "Van", status);
        }

        @Override
        void displayType() {
            System.out.println("Category       : Transport Van");
        }
    }

    static class Driver {

        String id, name, phone, license, vehicleNumber;

        Driver(String id, String name, String phone,
               String license, String vehicleNumber) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.license = license;
            this.vehicleNumber = vehicleNumber;
        }

        void display() {
            System.out.println("----------------------------------------");
            System.out.println("Driver ID      : " + id);
            System.out.println("Name           : " + name);
            System.out.println("Phone          : " + phone);
            System.out.println("License        : " + license);
            System.out.println("Vehicle Number : " + vehicleNumber);
        }
    }

    static class Trip {

        String id, vehicleNumber, driverName;
        String source, destination, status;
        double distance;

        Trip(String id, String vehicleNumber, String driverName,
             String source, String destination,
             double distance, String status) {
            this.id = id;
            this.vehicleNumber = vehicleNumber;
            this.driverName = driverName;
            this.source = source;
            this.destination = destination;
            this.distance = distance;
            this.status = status;
        }

        void display() {
            System.out.println("----------------------------------------");
            System.out.println("Trip ID       : " + id);
            System.out.println("Vehicle       : " + vehicleNumber);
            System.out.println("Driver        : " + driverName);
            System.out.println("Source        : " + source);
            System.out.println("Destination   : " + destination);
            System.out.println("Distance      : " + distance + " km");
            System.out.println("Status        : " + status);
        }
    }

    static class Fuel {

        String id, vehicleNumber, fuelType, date;
        double quantity, cost;

        Fuel(String id, String vehicleNumber, String fuelType,
             double quantity, double cost, String date) {
            this.id = id;
            this.vehicleNumber = vehicleNumber;
            this.fuelType = fuelType;
            this.quantity = quantity;
            this.cost = cost;
            this.date = date;
        }

        void display() {
            System.out.println("----------------------------------------");
            System.out.println("Fuel ID       : " + id);
            System.out.println("Vehicle       : " + vehicleNumber);
            System.out.println("Fuel Type     : " + fuelType);
            System.out.println("Quantity      : " + quantity + " Litres");
            System.out.println("Cost          : Rs." + cost);
            System.out.println("Date          : " + date);
        }
    }

    static class Maintenance {

        String id, vehicleNumber, serviceType, date, remarks;
        double cost;

        Maintenance(String id, String vehicleNumber,
                    String serviceType, String date,
                    double cost, String remarks) {
            this.id = id;
            this.vehicleNumber = vehicleNumber;
            this.serviceType = serviceType;
            this.date = date;
            this.cost = cost;
            this.remarks = remarks;
        }

        void display() {
            System.out.println("----------------------------------------");
            System.out.println("Maintenance ID : " + id);
            System.out.println("Vehicle        : " + vehicleNumber);
            System.out.println("Service Type   : " + serviceType);
            System.out.println("Date           : " + date);
            System.out.println("Cost           : Rs." + cost);
            System.out.println("Remarks        : " + remarks);
        }
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n==========================================");
            System.out.println("       SMART FLEET MONITORING SYSTEM");
            System.out.println("==========================================");
            System.out.println("1. Vehicle Monitoring");
            System.out.println("2. Driver Monitoring");
            System.out.println("3. Trip Monitoring");
            System.out.println("4. Fuel Monitoring");
            System.out.println("5. Maintenance Monitoring");
            System.out.println("6. Reports");
            System.out.println("7. Exit");
            System.out.println("==========================================");

            int choice = readInt("Enter your choice: ");

            switch (choice) {

                case 1:
                    vehicleMenu();
                    break;

                case 2:
                    driverMenu();
                    break;

                case 3:
                    tripMenu();
                    break;

                case 4:
                    fuelMenu();
                    break;

                case 5:
                    maintenanceMenu();
                    break;

                case 6:
                    reports();
                    break;

                case 7:
                    System.out.println(
                        "\nThank you for using Smart Fleet Monitoring System!"
                    );
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void vehicleMenu() {

        while (true) {

            System.out.println("\n========== VEHICLE MONITORING ==========");
            System.out.println("1. Add Vehicle");
            System.out.println("2. View Vehicles");
            System.out.println("3. Search Vehicle");
            System.out.println("4. Update Vehicle Status");
            System.out.println("5. Delete Vehicle");
            System.out.println("6. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addVehicle();
                    break;

                case 2:
                    viewVehicles();
                    break;

                case 3:
                    searchVehicle();
                    break;

                case 4:
                    updateVehicle();
                    break;

                case 5:
                    deleteVehicle();
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addVehicle() {

        System.out.print("Enter Vehicle ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Vehicle Number: ");
        String number = sc.nextLine();

        System.out.print("Enter Model: ");
        String model = sc.nextLine();

        System.out.print("Enter Type (Car/Truck/Van): ");
        String type = sc.nextLine();

        System.out.print("Enter Status: ");
        String status = sc.nextLine();

        if (type.equalsIgnoreCase("Car")) {

            vehicles.add(
                new Car(id, number, model, status)
            );

        } else if (type.equalsIgnoreCase("Truck")) {

            vehicles.add(
                new Truck(id, number, model, status)
            );

        } else if (type.equalsIgnoreCase("Van")) {

            vehicles.add(
                new Van(id, number, model, status)
            );

        } else {

            System.out.println("Invalid vehicle type!");
            return;
        }

        System.out.println("Vehicle added successfully!");
    }

    static void viewVehicles() {

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found!");
            return;
        }

        for (Vehicle v : vehicles) {
            v.display();
        }
    }

    static void searchVehicle() {

        System.out.print("Enter Vehicle Number: ");
        String number = sc.nextLine();

        for (Vehicle v : vehicles) {

            if (v.number.equalsIgnoreCase(number)) {

                System.out.println("Vehicle Found!");
                v.display();
                return;
            }
        }

        System.out.println("Vehicle not found!");
    }

    static void updateVehicle() {

        System.out.print("Enter Vehicle Number: ");
        String number = sc.nextLine();

        for (Vehicle v : vehicles) {

            if (v.number.equalsIgnoreCase(number)) {

                System.out.print("Enter New Status: ");
                v.status = sc.nextLine();

                System.out.println("Vehicle status updated!");
                return;
            }
        }

        System.out.println("Vehicle not found!");
    }

    static void deleteVehicle() {

        System.out.print("Enter Vehicle Number: ");
        String number = sc.nextLine();

        for (int i = 0; i < vehicles.size(); i++) {

            if (vehicles.get(i).number.equalsIgnoreCase(number)) {

                vehicles.remove(i);

                System.out.println("Vehicle deleted!");
                return;
            }
        }

        System.out.println("Vehicle not found!");
    }

    static void driverMenu() {

        while (true) {

            System.out.println("\n========== DRIVER MONITORING ==========");
            System.out.println("1. Add Driver");
            System.out.println("2. View Drivers");
            System.out.println("3. Search Driver");
            System.out.println("4. Delete Driver");
            System.out.println("5. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addDriver();
                    break;

                case 2:
                    viewDrivers();
                    break;

                case 3:
                    searchDriver();
                    break;

                case 4:
                    deleteDriver();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addDriver() {

        System.out.print("Enter Driver ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Driver Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        System.out.print("Enter License Number: ");
        String license = sc.nextLine();

        System.out.print("Enter Vehicle Number: ");
        String vehicleNumber = sc.nextLine();

        drivers.add(
            new Driver(
                id,
                name,
                phone,
                license,
                vehicleNumber
            )
        );

        System.out.println("Driver added successfully!");
    }

    static void viewDrivers() {

        if (drivers.isEmpty()) {
            System.out.println("No drivers found!");
            return;
        }

        for (Driver d : drivers) {
            d.display();
        }
    }

    static void searchDriver() {

        System.out.print("Enter Driver ID: ");
        String id = sc.nextLine();

        for (Driver d : drivers) {

            if (d.id.equalsIgnoreCase(id)) {

                d.display();
                return;
            }
        }

        System.out.println("Driver not found!");
    }

    static void deleteDriver() {

        System.out.print("Enter Driver ID: ");
        String id = sc.nextLine();

        for (int i = 0; i < drivers.size(); i++) {

            if (drivers.get(i).id.equalsIgnoreCase(id)) {

                drivers.remove(i);

                System.out.println("Driver deleted!");
                return;
            }
        }

        System.out.println("Driver not found!");
    }

    static void tripMenu() {

        while (true) {

            System.out.println("\n========== TRIP MONITORING ==========");
            System.out.println("1. Add Trip");
            System.out.println("2. View Trips");
            System.out.println("3. Search Trip");
            System.out.println("4. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addTrip();
                    break;

                case 2:
                    viewTrips();
                    break;

                case 3:
                    searchTrip();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addTrip() {

        System.out.print("Enter Trip ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Vehicle Number: ");
        String vehicle = sc.nextLine();

        System.out.print("Enter Driver Name: ");
        String driver = sc.nextLine();

        System.out.print("Enter Source: ");
        String source = sc.nextLine();

        System.out.print("Enter Destination: ");
        String destination = sc.nextLine();

        double distance =
            readDouble("Enter Distance (km): ");

        System.out.print("Enter Trip Status: ");
        String status = sc.nextLine();

        trips.add(
            new Trip(
                id,
                vehicle,
                driver,
                source,
                destination,
                distance,
                status
            )
        );

        System.out.println("Trip added successfully!");
    }

    static void viewTrips() {

        if (trips.isEmpty()) {
            System.out.println("No trips found!");
            return;
        }

        for (Trip t : trips) {
            t.display();
        }
    }

    static void searchTrip() {

        System.out.print("Enter Trip ID: ");
        String id = sc.nextLine();

        for (Trip t : trips) {

            if (t.id.equalsIgnoreCase(id)) {

                t.display();
                return;
            }
        }

        System.out.println("Trip not found!");
    }

    static void fuelMenu() {

        while (true) {

            System.out.println("\n========== FUEL MONITORING ==========");
            System.out.println("1. Add Fuel Record");
            System.out.println("2. View Fuel Records");
            System.out.println("3. Search Fuel Record");
            System.out.println("4. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addFuel();
                    break;

                case 2:
                    viewFuel();
                    break;

                case 3:
                    searchFuel();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addFuel() {

        System.out.print("Enter Fuel ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Vehicle Number: ");
        String vehicle = sc.nextLine();

        System.out.print("Enter Fuel Type: ");
        String type = sc.nextLine();

        double quantity =
            readDouble("Enter Quantity (Litres): ");

        double cost =
            readDouble("Enter Cost: ");

        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        fuels.add(
            new Fuel(
                id,
                vehicle,
                type,
                quantity,
                cost,
                date
            )
        );

        System.out.println("Fuel record added successfully!");
    }

    static void viewFuel() {

        if (fuels.isEmpty()) {
            System.out.println("No fuel records found!");
            return;
        }

        for (Fuel f : fuels) {
            f.display();
        }
    }

    static void searchFuel() {

        System.out.print("Enter Fuel ID: ");
        String id = sc.nextLine();

        for (Fuel f : fuels) {

            if (f.id.equalsIgnoreCase(id)) {

                f.display();
                return;
            }
        }

        System.out.println("Fuel record not found!");
    }

    static void maintenanceMenu() {

        while (true) {

            System.out.println(
                "\n======= MAINTENANCE MONITORING ======="
            );

            System.out.println("1. Add Maintenance");
            System.out.println("2. View Maintenance");
            System.out.println("3. Search Maintenance");
            System.out.println("4. Back");

            int choice = readInt("Enter choice: ");

            switch (choice) {

                case 1:
                    addMaintenance();
                    break;

                case 2:
                    viewMaintenance();
                    break;

                case 3:
                    searchMaintenance();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addMaintenance() {

        System.out.print("Enter Maintenance ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Vehicle Number: ");
        String vehicle = sc.nextLine();

        System.out.print("Enter Service Type: ");
        String service = sc.nextLine();

        System.out.print("Enter Date: ");
        String date = sc.nextLine();

        double cost =
            readDouble("Enter Cost: ");

        System.out.print("Enter Remarks: ");
        String remarks = sc.nextLine();

        maintenanceList.add(
            new Maintenance(
                id,
                vehicle,
                service,
                date,
                cost,
                remarks
            )
        );

        System.out.println(
            "Maintenance record added successfully!"
        );
    }

    static void viewMaintenance() {

        if (maintenanceList.isEmpty()) {
            System.out.println(
                "No maintenance records found!"
            );
            return;
        }

        for (Maintenance m : maintenanceList) {
            m.display();
        }
    }

    static void searchMaintenance() {

        System.out.print("Enter Maintenance ID: ");
        String id = sc.nextLine();

        for (Maintenance m : maintenanceList) {

            if (m.id.equalsIgnoreCase(id)) {

                m.display();
                return;
            }
        }

        System.out.println(
            "Maintenance record not found!"
        );
    }

    static void reports() {

        System.out.println(
            "\n========================================"
        );

        System.out.println(
            "             FLEET REPORT"
        );

        System.out.println(
            "========================================"
        );

        System.out.println(
            "Total Vehicles      : " + vehicles.size()
        );

        System.out.println(
            "Total Drivers       : " + drivers.size()
        );

        System.out.println(
            "Total Trips         : " + trips.size()
        );

        System.out.println(
            "Fuel Records        : " + fuels.size()
        );

        System.out.println(
            "Maintenance Records : " +
            maintenanceList.size()
        );

        System.out.println(
            "========================================"
        );
    }

    static int readInt(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Integer.parseInt(
                    sc.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "Please enter a valid number!"
                );
            }
        }
    }

    static double readDouble(String message) {

        while (true) {

            try {

                System.out.print(message);

                return Double.parseDouble(
                    sc.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                    "Please enter a valid value!"
                );
            }
        }
    }
}
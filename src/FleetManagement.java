import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class FleetManagement {

    // Declare variables!

    // scanner for reading from the keyboard
    private static final Scanner keyboard = new Scanner(System.in);

    // file the fleet gets saved to and loaded from
    private static final String DB_FILE_NAME = "FleetData.db";

    // making sure all the colons line up
    private static final String PROMPT_FORMAT = "%-44s: ";





    /**
     * @param args command line arguments
     */
    // start of main method
    // loads the fleet, runs menu, saves the fleet
    public static void main(String[] args) {

        // variables for the fleet and the menu choice
        Fleet fleet;
        String menuOption;

        // first run uses the csv file, later runs use the .db file
        if (args.length > 0) {
            fleet = readCSVFile(args[0]);
        } else {
            fleet = readDBFile();
        } //end of if else

        System.out.println();
        System.out.println("Welcome to the Fleet Management System");
        System.out.println("--------------------------------------");

        // do while loop with a switch statement to keep showing the menu
        // until they pick X
        do {
            System.out.println();
            System.out.printf(PROMPT_FORMAT,
                    "(P)rint, (A)dd, (R)emove, (E)xpense, e(X)it");

            // make it upper case so lower case letters work too
            menuOption = keyboard.nextLine().trim().toUpperCase();
            switch (menuOption) {
                case "P":
                    fleet.printReport();
                    break;
                case "A":
                    addBoat(fleet);
                    break;
                case "R":
                    removeBoat(fleet);
                    break;
                case "E":
                    requestExpense(fleet);
                    break;
                case "X":
                    break;
                default:
                    System.out.println("Invalid menu option, try again");
                    break;
            } //end of switch
        } while (!menuOption.equals("X")); //end of do while loop

        // save everything before ending
        writeDBFile(fleet);
        System.out.println();
        System.out.println("Exiting the Fleet Management System");
    } //end of main method





    /**
     * @param csvLine one line of csv data
     * @return the new boat
     */
    // start of createBoatFromCSV method
    // splits the line at the commas and makes a boat out of the parts
    private static Boat createBoatFromCSV(String csvLine) {

        String[] parts;
        BoatType type;
        String name;
        int year;
        String make;
        int length;
        double price;

        // order is type, name, year, make, length, price
        parts = csvLine.split(",");
        type = BoatType.valueOf(parts[0].trim().toUpperCase());
        name = parts[1].trim();
        year = Integer.parseInt(parts[2].trim());
        make = parts[3].trim();
        length = Integer.parseInt(parts[4].trim());
        price = Double.parseDouble(parts[5].trim());

        return new Boat(type, name, year, make, length, price);
    } //end of createBoatFromCSV method





    /**
     * @param fileName the csv file name
     * @return the fleet from the file
     */
    // start of readCSVFile method
    // reads the csv file one line at a time, each line is one boat
    private static Fleet readCSVFile(String fileName) {

        Fleet fleet;
        BufferedReader inputFile;
        String line;

        fleet = new Fleet();
        try {
            inputFile = new BufferedReader(new FileReader(fileName));
            line = inputFile.readLine();

            // null means we hit the end of the file
            while (line != null) {

                // skips blank lines
                if (line.trim().length() > 0) {
                    fleet.addBoat(createBoatFromCSV(line));
                } //end of if
                line = inputFile.readLine();
            } //end of while loop
            inputFile.close();
        } catch (IOException exception) {
            System.out.println("Error reading " + fileName + ": " +
                    exception.getMessage());
        } //end of try catch

        return fleet;
    } //end of readCSVFile method





    /**
     * @return the saved fleet
     */
    // start of readDBFile method
    // loads the fleet that was saved last time
    private static Fleet readDBFile() {

        Fleet fleet;
        ObjectInputStream inputFile;

        try {
            inputFile = new ObjectInputStream(
                    new FileInputStream(DB_FILE_NAME));
            fleet = (Fleet) inputFile.readObject();
            inputFile.close();
        } catch (IOException | ClassNotFoundException exception) {

            // if it can't be read, start with an empty fleet
            System.out.println("Error reading " + DB_FILE_NAME + ": " +
                    exception.getMessage());
            fleet = new Fleet();
        } //end of try catch

        return fleet;
    } //end of readDBFile method





    /**
     * @param fleet the fleet to save
     */
    // start of writeDBFile method
    // saves the fleet in serialized form
    private static void writeDBFile(Fleet fleet) {

        ObjectOutputStream outputFile;

        try {
            outputFile = new ObjectOutputStream(
                    new FileOutputStream(DB_FILE_NAME));
            outputFile.writeObject(fleet);
            outputFile.close();
        } catch (IOException exception) {
            System.out.println("Error writing " + DB_FILE_NAME + ": " +
                    exception.getMessage());
        } //end of try catch
    } //end of writeDBFile method





    /**
     * @param fleet the fleet to add to
     */
    // start of addBoat method
    // asks for one line of csv data and adds that boat
    private static void addBoat(Fleet fleet) {

        String csvLine;

        System.out.printf(PROMPT_FORMAT, "Please enter the new boat CSV data");
        csvLine = keyboard.nextLine();
        fleet.addBoat(createBoatFromCSV(csvLine));
    } //end of addBoat method





    /**
     * @param fleet the fleet to remove from
     */
    // start of removeBoat method
    private static void removeBoat(Fleet fleet) {

        String boatName;

        System.out.printf(PROMPT_FORMAT, "Which boat do you want to remove?");
        boatName = keyboard.nextLine().trim();

        // removeBoat gives back false if the boat isn't there
        if (!fleet.removeBoat(boatName)) {
            System.out.println("Cannot find boat " + boatName);
        } //end of if
    } //end of removeBoat method





    /**
     * @param fleet the fleet with the boat
     */
    // start of requestExpense method
    // asks which boat and how much, then says yes or no
    private static void requestExpense(Fleet fleet) {

        String boatName;
        Boat boat;
        double amount;

        System.out.printf(PROMPT_FORMAT,
                "Which boat do you want to spend on?");
        boatName = keyboard.nextLine().trim();
        boat = fleet.findBoat(boatName);
        if (boat == null) {
            System.out.println("Cannot find boat " + boatName);
        } else {
            System.out.printf(PROMPT_FORMAT, "How much do you want to spend?");
            amount = keyboard.nextDouble();

            // clears the rest of the line after the number
            keyboard.nextLine();

            // spend gives back true if it stays under the purchase price
            if (boat.spend(amount)) {
                System.out.printf("Expense authorized, $%.2f spent.\n",
                        boat.getExpenses());
            } else {
                System.out.printf(
                        "Expense not permitted, only $%.2f left to spend.\n",
                        boat.getAmountLeft());
            } //end of inner if else
        } //end of if else
    } //end of requestExpense method

} //end of FleetManagement class

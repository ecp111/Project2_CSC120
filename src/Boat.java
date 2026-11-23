import java.io.Serializable;

public class Boat implements Serializable {

    // Declare variables!

    // sailing or power
    private BoatType type;

    // name, year it was made, and make/model of the boat
    private String name;
    private int year;
    private String make;

    // length in feet, up to 100
    private int length;

    // how much the boat cost and how much has been spent on it so far
    private double purchasePrice;
    private double expenses;





    /**
     * @param type SAILING or POWER
     * @param name the boat's name
     * @param year the year it was made
     * @param make the make or model
     * @param length the length in feet
     * @param purchasePrice the price paid for the boat
     */
    // start of Boat constructor
    // sets up a new boat, new boats start with nothing spent
    public Boat(BoatType type, String name, int year, String make,
                int length, double purchasePrice) {

        this.type = type;
        this.name = name;
        this.year = year;
        this.make = make;
        this.length = length;
        this.purchasePrice = purchasePrice;
        this.expenses = 0.0;
    } //end of Boat constructor





    /**
     * @return the boat's name
     */
    // start of getName method
    public String getName() {

        return name;
    } //end of getName method





    /**
     * @return the purchase price
     */
    // start of getPurchasePrice method
    public double getPurchasePrice() {

        return purchasePrice;
    } //end of getPurchasePrice method





    /**
     * @return the expenses so far
     */
    // start of getExpenses method
    public double getExpenses() {

        return expenses;
    } //end of getExpenses method





    /**
     * @return how much money is left to spend
     */
    // start of getAmountLeft method
    // price minus what's already been spent
    public double getAmountLeft() {

        return purchasePrice - expenses;
    } //end of getAmountLeft method





    /**
     * @param amount how much they want to spend
     * @return true if the expense is allowed
     */
    // start of spend method
    // only lets them spend if the total stays at or under the price
    public boolean spend(double amount) {

        if (expenses + amount <= purchasePrice) {
            expenses = expenses + amount;
            return true;
        } else {
            return false;
        } //end of if else
    } //end of spend method





    /**
     * @return one line of the fleet report
     */
    // start of toString method
    // lines up type, name, year, make, length, paid and spent
    public String toString() {

        return String.format(
                "    %-8s%-20s %4d %-12s%2d' : Paid $ %8.2f : Spent $ %8.2f",
                type, name, year, make, length, purchasePrice, expenses);
    } //end of toString method

} //end of Boat class

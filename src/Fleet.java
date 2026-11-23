import java.io.Serializable;
import java.util.ArrayList;

public class Fleet implements Serializable {

    // Declare variables!

    // list that holds all the boats in the fleet
    private ArrayList<Boat> boats;





    // start of Fleet constructor
    // starts with an empty list of boats
    public Fleet() {

        boats = new ArrayList<Boat>();
    } //end of Fleet constructor





    /**
     * @param newBoat the boat to add
     */
    // start of addBoat method
    public void addBoat(Boat newBoat) {

        boats.add(newBoat);
    } //end of addBoat method





    /**
     * @param boatName the name to look for
     * @return the boat, or null if it isn't there
     */
    // start of findBoat method
    // goes through the list until it finds a matching name
    public Boat findBoat(String boatName) {

        int index;

        // equalsIgnoreCase so upper or lower case both work
        for (index = 0; index < boats.size(); index++) {
            if (boats.get(index).getName().equalsIgnoreCase(boatName)) {
                return boats.get(index);
            } //end of if
        } //end of for loop

        // null means we didn't find it
        return null;
    } //end of findBoat method





    /**
     * @param boatName the name of the boat to remove
     * @return true if it was removed
     */
    // start of removeBoat method
    public boolean removeBoat(String boatName) {

        Boat boatToRemove;

        // find the boat first, then take it out of the list
        boatToRemove = findBoat(boatName);
        if (boatToRemove == null) {
            return false;
        } else {
            boats.remove(boatToRemove);
            return true;
        } //end of if else
    } //end of removeBoat method





    // start of printReport method
    // prints every boat, then the totals paid and spent
    public void printReport() {

        int index;
        double totalPaid;
        double totalSpent;

        totalPaid = 0.0;
        totalSpent = 0.0;

        System.out.println();
        System.out.println("Fleet report:");

        // print each boat and add its money to the totals
        for (index = 0; index < boats.size(); index++) {
            System.out.println(boats.get(index));
            totalPaid = totalPaid + boats.get(index).getPurchasePrice();
            totalSpent = totalSpent + boats.get(index).getExpenses();
        } //end of for loop

        // making sure the total colons line up with the boat lines
        System.out.printf("    %-49s : Paid $ %8.2f : Spent $ %8.2f\n",
                "Total", totalPaid, totalSpent);
    } //end of printReport method

} //end of Fleet class


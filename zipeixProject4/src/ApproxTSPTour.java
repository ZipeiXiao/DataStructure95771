import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class ApproxTSPTour {

    /**
     * Part One
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // prompt the user for two dates
        System.out.println("Enter start date:");
        String startString = scanner.nextLine();
        System.out.println("Enter end date:");
        String endString = scanner.nextLine();

        try {
            System.out.println("Crime records between " + startString + " and " + endString + ":");
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
            Date startDate = format.parse(startString);
            Date endDate = format.parse(endString);

            CrimeGraph graph = new CrimeGraph(startDate, endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}

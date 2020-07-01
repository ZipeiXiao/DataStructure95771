import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class KML {

    /**
     * KML File name
     */
    public static final String filename = "PGHCrimes.kml";

    /**
     * Part Three
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileWriter myWriter = null;

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

            // calculate the optimal one
            graph.minPath();

            // create a new file
            File myObj = new File(filename);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

            // write the KML File
            myWriter = new FileWriter(filename);
            myWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<kml xmlns=\"http://earth.google.com/kml/2.2\">\n" +
                    "<Document>\n" +
                    "<name>Pittsburgh TSP</name><description>TSP on Crime</description><Style id=\"style6\">\n" +
                    "<LineStyle>\n" +
                    "<color>73FF0000</color>\n" +
                    "<width>5</width>\n" +
                    "</LineStyle>\n" +
                    "</Style>\n" +
                    "<Style id=\"style5\">\n" +
                    "<LineStyle>\n" +
                    "<color>507800F0</color>\n" +
                    "<width>5</width>\n" +
                    "</LineStyle>\n" +
                    "</Style>\n" +
                    "<Placemark>\n" +
                    "<name>TSP Path</name>\n" +
                    "<description>TSP Path</description>\n" +
                    "<styleUrl>#style6</styleUrl>\n" +
                    "<LineString>\n" +
                    "<tessellate>1</tessellate>\n" +
                    "<coordinates>\n");

            // write the Cycle path created by MST
            for (int i : graph.tsp) {
                CrimeRecord record = graph.crimeRecords.get(i);
                myWriter.write(record.getLongitude() + "," + record.getLatitude() + ",0.000000\n");
            }
            myWriter.write("</coordinates>\n" +
                    "</LineString>\n" +
                    "</Placemark>\n" +
                    "<Placemark>\n" +
                    "<name>Optimal Path</name>\n" +
                    "<description>Optimal Path</description>\n" +
                    "<styleUrl>#style5</styleUrl>\n" +
                    "<LineString>\n" +
                    "<tessellate>1</tessellate>\n" +
                    "<coordinates>\n");

            // write the optimal Cycle points
            for (int i : graph.optimum) {
                CrimeRecord record = graph.crimeRecords.get(i);
                myWriter.write(record.getLongitude() + "," + record.getLatitude() + ",0.000000\n");
            }
            myWriter.write("</coordinates>\n" +
                    "</LineString>\n" +
                    "</Placemark>\n" +
                    "</Document>\n" +
                    "</kml>\n");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } finally {
            scanner.close();
            if (myWriter != null) {
                myWriter.close();
            }
        }
    }
}

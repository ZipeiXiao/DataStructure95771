import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Read file and build the 2D Array and the MST
 */
public class CrimeGraph {
    /**
     * File name
     */
    public static final String fileName = "CrimeLatLonXY1990.csv";

    /**
     * Rows of records in files
     */
    public List<CrimeRecord> crimeRecords;

    /**
     * The distance between 2 point in the graph
     */
    public double[][] edges;

    /**
     * Distances in the MST
     */
    private double[] distance;

    /**
     * Records the parent index of the nodes in MST
     */
    private int[] parents;

    /**
     * Root of MST
     */
    private Node root;

    /**
     * Hamiltonian cycle by MST
     */
    public List<Integer> tsp = new ArrayList<>();

    /**
     * Optimal Hamiltonian cycle length
     */
    public double min = Double.MAX_VALUE;

    /**
     * Optimal Hamiltonian cycle
     */
    public List<Integer> optimum = new ArrayList<>();

    /**
     * Default Constructor
     * @param startDate input start date
     * @param endDate input end date
     */
    public CrimeGraph(Date startDate, Date endDate) {
        crimeRecords = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));

            // skip the column name row
            String line = scanner.nextLine();

            // read file line by line
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                // csv file splited by comments
                String[] columns = line.split(",");

                // the date column of that row
                Date curDate = format.parse(columns[5]);

                // those crimes that occurred between start and end inclusive
                if (curDate.compareTo(startDate) >= 0 && curDate.compareTo(endDate) <= 0) {

                    // show the row
                    System.out.println(line);

                    // add the row into array list
                    CrimeRecord curRecord = new CrimeRecord(Double.parseDouble(columns[0]),
                                                            Double.parseDouble(columns[1]),
                                                            Integer.parseInt(columns[2]),
                                                            columns[3], columns[4],
                                                            columns[5], columns[6],
                                                            Double.parseDouble(columns[7]),
                                                            Double.parseDouble(columns[8]));
                    crimeRecords.add(curRecord);
                } else if (curDate.after(endDate)) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } catch (ParseException e2) {
            System.err.println("Date Format incorrect");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        // calculate the distances between any two nodes
        buildMatrix();
        buildMinimumSpanningTree();

        // add the start root to form a cycle
        tsp.add(0);
        System.out.println("Hamiltonian Cycle (not necessarily optimum):");
        for (int i : tsp) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("Length Of cycle:  ");

        // calculate the length of Hamiltonian Cycle
        double sum = 0;
        for (int i = 1; i < tsp.size(); i++) {
            int prev = tsp.get(i - 1);
            int cur = tsp.get(i);
            sum += edges[prev][cur];
        }

        // To convert from feet to miles, simply multiply the computed distances by 0.00018939
        System.out.print(sum * 0.00018939 + " miles");
    }

    /**
     * Build edges weight Matrix
     */
    private void buildMatrix() {

        // initialize the the matrix
        int len = crimeRecords.size();
        edges = new double[len][len];

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                double width = crimeRecords.get(j).getX() - crimeRecords.get(i).getX();
                double height = crimeRecords.get(j).getY() - crimeRecords.get(i).getY();

                // compute distances between vertices in South Western PA using the Pythagorean theorem
                edges[i][j] = Math.sqrt(Math.abs(Math.pow(width, 2) + Math.pow(height, 2)));
            }
        }
    }

    /**
     * Compute a minimum spanning tree T for G from root r using MST-Prim(G,c,r)
     */
    public void buildMinimumSpanningTree() {
        parents = new int[crimeRecords.size()];
        distance = new double[crimeRecords.size()];

        // Q = V[G]
        MinHeap priorityQueue = new MinHeap(parents.length);

        distance[0] = 0;

        // pi[r] = null
        // while Q is not empty
        while (priorityQueue.getCurrentSize() > 0) {

            // do: u = ExtractMin(Q)
            Node min = priorityQueue.deleteMin();

            // find light edge
            // u = r first time through
            // foreach v in Adj[u]
            for (int i = 0; i < priorityQueue.getCurrentSize(); i++) {

                Node v = priorityQueue.getHeapArray()[i];
                int vIndex = v.getIndex();
                double wuv = edges[vIndex][min.getIndex()];
                double keyv = priorityQueue.getHeapArray()[i].getKey();

                // do: if v in Q && w(u,v) < key[v]
                if (wuv < keyv) {

                    // update adjacent nodes
                    // then pi[v] = u
                    parents[vIndex] = min.getIndex();

                    // key[v] = w(u,v)
                    v.setKey(wuv);
                    distance[vIndex] = wuv;
                }
            }
        }

        // The root of the minimum spanning tree will always be the first index.
        root = new Node(0, 0);
        buildMST(root);
        preOrder(root);
    }

    /**
     * Find the parent to children relationship
     * @param parent parent node
     */
    private void buildMST(Node parent) {

        // traverse besides the root (0) to find children
        for (int i = 1; i < parents.length; i++) {

            // if find the child
            if (parents[i] == parent.getIndex()) {

                // add to children list
                parent.getChildren().add(new Node(distance[i], i));
            }
        }

        // if no child, break the loop
        if (!parent.getChildren().isEmpty()) {
            for (Node child : parent.getChildren()) {
                buildMST(child);
            }
        }
    }

    /**
     * DFS Traverse
     * @param node MST Node
     */
    private void preOrder(Node node) {
        tsp.add(node.getIndex());

        // base case
        if (node.getChildren().isEmpty()) {
            return;
        }
        for (Node child : node.getChildren()) {
            preOrder(child);
        }
    }

    /**
     * Calculate the optimal Hamiltonian Cycle length
     * @return optimal Hamiltonian Cycle length
     */
    public double minPath() {

        // turn the ArrayList to array
        int[] records = new int[crimeRecords.size()];
        for (int i = 0; i < crimeRecords.size(); i++) {
            records[i] = i;
        }

        // DFS
        minPathHelper(records, 0);
        return min;
    }

    /**
     * Back Tracking helper
     * @param records crime records
     * @param index current processing index
     */
    private void minPathHelper(int[] records, int index) {

        // base case
        if (index == records.length) {

            // update the minimum path
            if (pathSum(records) < min) {
                min = pathSum(records);
                optimum = new ArrayList<>();
                for (int i = 0; i < records.length; i++) {
                    optimum.add(records[i]);
                }
                optimum.add(records[0]);
            }
            return;
        }

        // recursively call itself
        for (int i = index; i < records.length; i++) {
            swap(index, i, records);
            minPathHelper(records, index + 1);
            swap(index, i, records);
        }
    }

    /**
     * Swap 2 item in the array
     * @param a first
     * @param b second
     * @param records array
     */
    private void swap(int a, int b, int[] records) {
        int tmp = records[a];
        records[a] = records[b];
        records[b] = tmp;
    }

    /**
     * Calculate the Cycle length
     * @param records crime array
     * @return Cycle length
     */
    private double pathSum(int[] records) {
        double sum = 0;

        for (int i = 1; i < records.length; i++) {

            // compute distances between vertices in South Western PA using the Pythagorean theorem
            sum += edges[records[i - 1]][records[i]];
        }

        // add the last point to the first point length
        sum += edges[records[0]][records[records.length - 1]];
        return sum;
    }
}

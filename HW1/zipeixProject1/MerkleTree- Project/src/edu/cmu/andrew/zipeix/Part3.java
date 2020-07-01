package edu.cmu.andrew.zipeix;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Use the classes from Part 1 to build a Merkle tree from a text file
 * and compute the Merkle root of the Merkle tree.
 */
public class Part3 {
    /**
     * CrimeLatLonXY.csv file has the Merkle root of:
     * A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58.
     * @param args unused
     */
    public static void main(String[] args) {
        Scanner type = new Scanner(System.in);
        // prompt the user for a file name.
        System.out.println("Enter a file name.");
        String nextLine = type.nextLine();

        Scanner scanner = null;
        try {
            // read a UTF-8 file of text lines – delimited by line breaks.
            scanner = new Scanner(new File(nextLine), "UTF-8");

            // Each line will be stored in a node on a list.
            SinglyLinkedList firstList = new SinglyLinkedList();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                firstList.addAtEndNode(line);
            }

            /*
             *  Once each line is stored in a node – forming a list of lines,
             *   a second list will be created containing the cryptographic hashes
             *   of these nodes.
             */
            SinglyLinkedList secondList = new SinglyLinkedList();
            for (int i = 0; i < firstList.countNodes(); i++) {
                String data = (String) firstList.getObjectAt(i);
                String hash = h(data);
                secondList.addAtEndNode(hash);
            }

            /*
             * If, after completing a list, the list has an odd number of nodes,
             * copy the last node and then add it to the end of the list – forcing all lists
             * to be even in size – except, of course, for the Merkle root.
             */
            if (firstList.countNodes() % 2 != 0) {
                firstList.addAtEndNode(firstList.getLast());
            }
            if (secondList.countNodes() % 2 != 0) {
                secondList.addAtEndNode(secondList.getLast());
            }

            // create a list of lists for each level in the Merkle tree.
            SinglyLinkedList tree = new SinglyLinkedList();
            tree.addAtEndNode(firstList);
            tree.addAtEndNode(secondList);

            SinglyLinkedList pre = (SinglyLinkedList) tree.getLast();

            // implement this tree within a list of lists.
            while (pre.countNodes() > 2) {
                if (pre.countNodes() % 2 != 0) {
                    pre.addAtEndNode(pre.getLast());
                }
                SinglyLinkedList newLevel = new SinglyLinkedList();
                for (int i = 0; i < pre.countNodes(); i += 2) {
                    String hash1 = (String) pre.getObjectAt(i);
                    String hash2 = (String) pre.getObjectAt(i + 1);

                    // concatenate these two hashes and hash the concatenation to compute a new hash.
                    String newHash = h(hash1 + hash2);
                    newLevel.addAtEndNode(newHash);
                }
                tree.addAtEndNode(newLevel);
                pre = (SinglyLinkedList) tree.getLast();
            }
            String hashOne = (String) pre.getObjectAt(0);
            String hashTwo = (String) pre.getLast();

            // concatenate these two hashes and hash the concatenation to compute a new hash.
            String rootValue = h(hashOne + hashTwo);
            SinglyLinkedList root = new SinglyLinkedList();
            root.addAtEndNode(rootValue);

            // display the Merkle root.
            System.out.println(root);

        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } catch (NoSuchAlgorithmException e1) {
            System.err.println("No such hash method");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * SHA-256 Hash Method
     * @param text one line of the file
     * @return hash value of that line
     * @throws NoSuchAlgorithmException
     */
    public static String h(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}

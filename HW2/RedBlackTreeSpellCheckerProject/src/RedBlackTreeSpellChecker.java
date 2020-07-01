import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class RedBlackTreeSpellChecker {

    /**
     * Input the file name by command line.
     * @param args txt file name
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String inputString;
        //= in.nextLine();
        // System.out.println(inputString);
        String[] commandLine;
        //= inputString.split(" ");
        System.out.println("Loading a tree of English words from " + args[0] + ".");
        RedBlackTree redBlackTree = new RedBlackTree();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(args[0]));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                redBlackTree.insert(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        System.out.println("Red Black Tree is loaded with " + redBlackTree.getSize() + " words.");
        System.out.println("Initial tree height is " + redBlackTree.height() + ".");
        System.out.println("Never worse than 2 * Lg(n + 1) = " + 2 * (Math.log(redBlackTree.getSize() + 1) / Math.log(2)) + ".");
        displayMenu();

        while (true) {
            inputString = in.nextLine();
            if (inputString.equals(">d")) {
                System.out.println("Level order traversal");
                redBlackTree.levelOrderTraversal();
            } else if (inputString.equals(">s")) {
                System.out.println("Inorder traversal");
                redBlackTree.inOrderTraversal();
            } else if (inputString.equals(">r")) {
                System.out.println("Reverse inorder traversal");
                redBlackTree.reverseOrderTraversal();
            } else if (inputString.equals(">i")) {
                System.out.println("Tree diameter is " + redBlackTree.diameter(redBlackTree.root) + ".");
            } else if (inputString.equals(">m")) {
                displayMenu();
            } else if (inputString.equals(">!")) {
                System.out.println("Bye.");
                break;
            } else {
                commandLine = inputString.split(" ");
                if (commandLine.length == 2) {
                    if (commandLine[0].equals(">c")) {
                        if (redBlackTree.contains(commandLine[1])) {
                            System.out.println("Found "
                                    + commandLine[1]
                                    + " after "
                                    + redBlackTree.getRecentCompares()
                                    + " comparisons");
                        } else {
                            System.out.println(commandLine[1] + " Not in dictionary. Perhaps you mean");
                            System.out.println(redBlackTree.closeBy(commandLine[1]));
                        }
                    } else if (commandLine[0].equals(">a")) {
                        if (redBlackTree.contains(commandLine[1])) {
                            System.out.println("The word '" + commandLine[1] + "'  is already in the dictionary.");
                        } else {
                            redBlackTree.insert(commandLine[1]);
                            System.out.println(commandLine[1] + " was added to dictionary.");
                        }
                    } else if (commandLine[0].equals(">f")) {
                        try {
                            scanner = new Scanner(new File(commandLine[1]));
                            boolean allFound = true;
                            while (scanner.hasNextLine()) {
                                String line = scanner.nextLine();
                                String[] wordsFromText = line.split("\\W");
                                if (wordsFromText.length > 1) {
                                    for (String word : wordsFromText) {
                                        if (!word.equals("") && !redBlackTree.contains(word)) {
                                            allFound = false;
                                            System.out.println("'" + word + "' was not found in dictionary.");
                                        }
                                    }
                                } else {
                                    if (!redBlackTree.contains(line)) {
                                        allFound = false;
                                        System.out.println("'" + line + "' was not found in dictionary.");
                                    }
                                }
                            }
                            if (allFound) {
                                System.out.println("No spelling errors found.");
                            }
                        } catch (FileNotFoundException e) {
                            System.err.println("Cannot find the file");
                        } finally {
                            if (scanner != null) {
                                scanner.close();
                            }
                        }
                    } else {
                        System.out.println("Wrong Command");
                    }
                } else {
                    System.out.println("Wrong Command");
                }
            }
        }
    }

    /**
     * @worst-time O(1)
     * @best-time O(1)
     */
    private static void displayMenu() {
        System.out.println("Legal commands are:");
        System.out.println("d    display the entire word tree with a level order traversal.");
        System.out.println("s    print the words of the tree in sorted order (using an inorder traversal).");
        System.out.println("r    print the words of the tree in reverse sorted order (reverse inorder traversal).");
        System.out.println("c    <word> to spell check this word.");
        System.out.println("a    <word> add this word to tree.");
        System.out.println("f    <fileName> to check this text file for spelling errors.");
        System.out.println("i    display the diameter of the tree.");
        System.out.println("m    view this menu.");
        System.out.println("!    to quit.");
    }
}

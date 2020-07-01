package edu.cmu.andrew.zipeix;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Part2 {
    /**
     * Hold the super-increasing sequence of integers that make
     * up part of the private key and used for decryption.
     */
    private static SinglyLinkedList w;
    /**
     * Hold the public key material used for encryption.
     */
    private static SinglyLinkedList b;

    private static BigInteger sum = BigInteger.ZERO;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            do {
                // user will enter a string of less than 80 characters in length.
                System.out.println("Enter a string and I will encrypt it as single large integer.");
                String nextLine = scan.nextLine();
                if (nextLine.length() > 80) {
                    System.out.println("Input String is too long. Please reenter it.");
                    continue;
                }
                System.out.println("Clear text:");
                System.out.println(nextLine);
                System.out.print("Number of clear text bytes = ");
                System.out.println(nextLine.length());

                // First, a super-increasing sequence w is created
                w = initializeW(nextLine);

                // Then, choose a number q that is greater than the sum.
                BigInteger q = sum.add(randomIncrement());

                // Also, choose a number r that is in the range [1,q) and is co-prime to q.
                BigInteger r = pickR(q);
                // The private key consists of q, w and r.

                // To calculate a public key, generate the sequence β by multiplying each element in w by r mod q
                b = calculateB(r, q);
                // The sequence β makes up the public key.

                // First, translate input string to binary (in this case, using ASCII or UTF-8)
                String inputBinary = stringToBit(nextLine);

                // multiplies each respective bit by the corresponding number in β
                BigInteger encrypt = encode(inputBinary);

                System.out.print(nextLine);
                System.out.println(" is encrypted as ");
                System.out.println(encrypt);

                // To decrypt, multiplies encrypted string by r −1 mod q (Modular inverse)
                BigInteger decryptNumber = encrypt.multiply(r.modInverse(q)).mod(q);

                StringBuilder decryptBinary = new StringBuilder();
                for (int i = 0; i < (nextLine.length() * 8); i++) {
                    decryptBinary.append(0);
                }

                // Then selecting the next largest element less than or equal to the difference, until the difference is 0}:
                int countNode = w.countNodes() - 1;
                while (decryptNumber.compareTo(BigInteger.ZERO) != 0) {
                    BigInteger current = (BigInteger) w.getObjectAt(countNode);

                    // Decomposes decryptNumber by selecting the largest element in w which is less than or equal to decryptNumber.
                    if (decryptNumber.compareTo(current) >= 0) {
                        decryptNumber = decryptNumber.subtract(current);
                        decryptBinary.setCharAt(countNode, '1');
                    }
                    countNode--;
                }

                StringBuilder decrypt = new StringBuilder();
                for (int i = 0; i < decryptBinary.length(); i += 8) {
                    int cur = Integer.valueOf(decryptBinary.substring(i, i + 8), 2);
                    decrypt.append((char) cur);
                }

                System.out.print("Result of decryption: ");
                System.out.println(decrypt);
            } while (true);
        } catch (NumberFormatException e1) {
            System.out.println("Number Format Exception:" + e1.getMessage());
        } finally {
            scan.close();
        }
    }

    /**
     * This is the basis for a private key.
     * @param s input string
     * @return w
     */
    public static SinglyLinkedList initializeW(String s) {
        SinglyLinkedList result = new SinglyLinkedList();
        for(int i = 0;i < (s.length() * 8); i++) {
            BigInteger cur = randomIncrement();
            // add it to the current sum
            cur = cur.add(sum);
            // add it as the node of the super increasing sequence
            result.addAtEndNode(cur);
            // add it to sum
            sum = sum.add(cur);
        }
        return result;
    }

    public static BigInteger randomIncrement() {
        BigInteger increment = new BigInteger(4, new Random()).abs();
        return increment;
    }

    public static BigInteger pickR(BigInteger q) {
        BigInteger r = q.subtract(BigInteger.ONE);
        while (!(r.gcd(q)).equals(BigInteger.ONE)) {
            r = r.subtract(BigInteger.ONE);
        }
        return r;
    }

    public static SinglyLinkedList calculateB(BigInteger r, BigInteger q) {
        SinglyLinkedList result = new SinglyLinkedList();
        for (int i = 0; i < w.countNodes(); i++) {
            BigInteger wi = (BigInteger) (w.getObjectAt(i));
            result.addAtEndNode(wi.multiply(r).mod(q));
        }
        return result;
    }

    public static String stringToBit(String input) {
        StringBuilder sb = new StringBuilder();
        char[] array = input.toCharArray();
        for (char c : array) {
            sb.append(Integer.toBinaryString(0x100 | c).substring(1));
        }
        return sb.toString();
    }

    public static BigInteger encode(String inputBinary) {
        BigInteger encrypt = BigInteger.ZERO;
        for (int i = 0; i < inputBinary.length(); i++) {
            char inputI = inputBinary.charAt(i);
            if (inputI == '1') {
                encrypt = encrypt.add((BigInteger) b.getObjectAt(i));
            }
        }
        return encrypt;
    }
}

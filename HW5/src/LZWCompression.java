import java.io.*;

class LZWCompression {

    /**
     * Storing 12 bit integers.
     */
    private byte[] compressionBuffer = new byte[3];

    private String[] decompressionBuffer = new String[3];

    private byte byteIn;
    private char ch;
    private String s = "";

    private boolean isEmptyBuffer = true;

    /**
     * Hashmap used for compress.
     */
    private MyHashMap<String, Integer> codeword;

    /**
     * Hashmap used for decompress.
     */
    private MyHashMap<Integer, String> wordCode;

    private int index = 256;

    //List that stores integer which comes from the compressed file
    private SinglyLinkedList<String, Integer> codeList = new SinglyLinkedList<>();

    private DataInputStream in;

    private DataOutputStream out;

    /**
     * LZW 12-Bit compression.
     * @param inputFileName file to be compressed
     * @param outputFileName compressed file
     * @throws IOException Read/Write Exception
     */
    public void LZWCompress(String inputFileName, String outputFileName) throws IOException {
        in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(inputFileName)));
        out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(outputFileName)));

        // enter all symbols in the table.
        initializeCodeWordMap();

        try {

            // read(first character from w into string s);
            read();
            s = s + ch;

            // while(any input left){
            while(true) {

                // read(character c);
                read();

                /**
                 * To handle large files, those that overflow the 12-bit table size, your program will
                 * detect overflow and generate a brand new table and begin processing anew.
                 */
                if (index == (int) Math.pow(2, 12)) {
                    initializeCodeWordMap();
                }

                // if(s + c is in the table)
                if (codeword.containsKey(s + ch)) {

                    // s = s + c;
                    s = s + ch;
                } else {

                    // output codeword(s);
                    writeChunk(false);

                    // Enter s + c into the table;
                    codeword.put(s + ch, index++);

                    // s = c;
                    s = Character.toString(ch);
                }
            }
        } catch(EOFException e) {
            in.close();
        }

        // output codeword(s);
        if (s.equals("")) {
            writeChunk(true);
        }
        out.close();
    }

    /**
     * Decompress compressed file using LZW 12-Bit compression.
     * @param inputFileName file to be decompressed
     * @param outputFileName decompressed file
     * @throws IOException read/write exception
     */
    public void LZWDecompress(String inputFileName, String outputFileName) throws IOException {
        in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(inputFileName)));
        out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(outputFileName)));

        // enter all symbols into the table;
        initializeWordCodeMap();
        isEmptyBuffer = true;

        // read(priorcodeword) and output its corresponding character;
        // readChunk();
        // codeList.reset();
        // int priorCodeWord = (Integer) codeList.next().getValue();
        int priorCodeWord = readDecompression();
        write(wordCode.get(priorCodeWord));

        Integer codeWord;
        String value;

        // while(codewords are still left to be input){
        try {
            while (true) {

                /**
                 * To handle large files, those that overflow the 12-bit table size, your program will
                 * detect overflow and generate a brand new table and begin processing anew.
                 */
                if (index == (int) Math.pow(2, 12)) {
                    initializeWordCodeMap();
                }

                // read(codeword);
                // codeWord = (Integer) codeList.next().getValue();
                codeWord = readDecompression();
                if (codeWord == null) {
                    continue;
                }

                // if(codeword not in the table)  {
                if (!wordCode.containsKey(codeWord)) {
                    value = wordCode.get(priorCodeWord) + wordCode.get(priorCodeWord).charAt(0);

                    // enter string(priorcodeword) + firstChar(string(priorcodeword)) into the table;
                    wordCode.put(index, value);

                    // output string(priorcodeword) + firstChar(string(priorcodeword));
                    write(value);
                } else {
                    value = wordCode.get(priorCodeWord) + wordCode.get(codeWord).charAt(0);

                    // enter string(priorcodeword) + firstChar(string(codeword)) into the table;
                    wordCode.put(index, value);

                    // output codeword;
                    write(wordCode.get(codeWord));
                }
                index++;

                // priorcodeword = codeword;
                priorCodeWord = codeWord;
            }
        } catch (EOFException e) {
            if (in != null) {
                in.close();
            }
        }
        out.close();
    }

    /**
     * The input file to the compression algorithm will be read in 8-bit bytes.
     * @throws IOException
     */
    private void read() throws IOException {
        byteIn = in.readByte();
        ch = (char) (byteIn & 0xFF);
    }

    /**
     * Using LZW 12-Bit compression, the output file will be written in 12-bit chunks.
     * @param endOfFile
     * @throws IOException
     */
    private void writeChunk(boolean endOfFile) throws IOException {
        String binary = Integer.toBinaryString(codeword.get(s));
        for (int i = binary.length(); i < 12; i++) {
            binary = "0" + binary;
        }

        if (isEmptyBuffer) {
            compressionBuffer[0] = (byte) Integer.parseInt(binary.substring(0, 8), 2);
            compressionBuffer[1] = (byte) Integer.parseInt(binary.substring(8, 12) + "0000", 2);
            if (endOfFile) {
                out.writeByte(compressionBuffer[0]);
                out.writeByte(compressionBuffer[1]);
            }
        } else {
            compressionBuffer[1] += (byte) Integer.parseInt(binary.substring(0, 4), 2);
            compressionBuffer[2] = (byte) Integer.parseInt(binary.substring(4, 12), 2);
            for(int i = 0; i < 3; i++) {
                out.writeByte(compressionBuffer[i]);
                compressionBuffer[i] = 0;
            }
        }
        isEmptyBuffer = !isEmptyBuffer;
    }

    /**
     * Decompression read file as whole
     * @throws IOException
     */
    private void readChunk() throws IOException {
        try {
            while (true) {
                String s1 = Integer.toBinaryString(in.readByte() & 0xFF);
                String s2 = Integer.toBinaryString(in.readByte() & 0xFF);
                for (int i = s1.length(); i < 8; i++) {
                    s1 = "0" + s1;
                }

                for (int i = s2.length(); i < 8; i++) {
                    s2 = "0" + s2;
                }
                // Add integer into arraylist
                codeList.addAtEndNode(s1 + s2.substring(0, 4), Integer.parseInt(s1 + s2.substring(0, 4), 2));
                String s3 = Integer.toBinaryString(in.readByte() & 0xFF);

                for (int i = s3.length(); i < 8; i++) {
                    s3 = "0" + s3;
                }

                codeList.addAtEndNode(s2.substring(4, 8) + s3, Integer.parseInt(s2.substring(4, 8) + s3, 2));
            }
        } catch (EOFException e) {
            in.close();
        }
    }

    private Integer readDecompression() throws IOException {
        Integer ans = null;
        if (isEmptyBuffer) {
            String s1 = Integer.toBinaryString(in.readByte() & 0xFF);
            String s2 = Integer.toBinaryString(in.readByte() & 0xFF);
            for (int i = s1.length(); i < 8; i++) {
                s1 = "0" + s1;
            }
            decompressionBuffer[0] = s1;

            for (int i = s2.length(); i < 8; i++) {
                s2 = "0" + s2;
            }
            decompressionBuffer[1] = s2;
            isEmptyBuffer = !isEmptyBuffer;
            ans = Integer.parseInt(s1 + s2.substring(0, 4), 2);
        } else {
            String s3 = Integer.toBinaryString(in.readByte() & 0xFF);
            for (int i = s3.length(); i < 8; i++) {
                s3 = "0" + s3;
            }
            decompressionBuffer[2] = s3;
            isEmptyBuffer = !isEmptyBuffer;
            ans = Integer.parseInt(decompressionBuffer[1].substring(4, 8) + s3, 2);
        }
        return ans;
    }

    /**
     * Decompression result writes to file
     * @throws IOException write exception
     */
    private void write(String s) throws IOException {
        for (char c : s.toCharArray()) {
            out.writeByte(c);
        }
    }

    /**
     * enter all symbols in the table.
     */
    private void initializeCodeWordMap() {
        codeword = new MyHashMap<>(256);
        for (int i = 0; i < 256; i++) {
            codeword.put(Character.toString((char) i), i);
        }
        index = 256;
    }

    /**
     * Key: Integer
     * Value: String
     * Put all 8 bits code into map.
     */
    private void initializeWordCodeMap() {
        wordCode = new MyHashMap<>(256);
        for (int i = 0; i < 256; i++) {
            wordCode.put(i, Character.toString((char) i));
        }
        index = 256;
    }

    /**
     * This program works both on ASCII files and binary files.
     * The compression degree on words.html is 42.9%
     * The compression degree on CrimeLatLonXY1990.csv is 49.2%
     * The compression degree on 01_Overview.mp4 is 135%
     * @param args command line
     */
    public static void main(String[] args) {
        LZWCompression lzw = new LZWCompression();
        if (args.length == 3 || args.length == 4) {
            try {
                if (args[0].equals("-c")) {
                    if(args.length == 3) {
                        lzw.LZWCompress(args[1], args[2]);
                    } else if(args.length == 4 && args[1].equals("-v")) {
                        lzw.LZWCompress(args[2], args[3]);
                        File infile = new File(args[2]);
                        File outfile = new File(args[3]);
                        System.out.printf("bytes read = %d , bytes write = %d", infile.length(), outfile.length());
                    }
                } else if (args[0].equals("-d")) {
                    if(args.length == 3) {
                        lzw.LZWDecompress(args[1], args[2]);
                    } else if(args.length == 4 && args[1].equals("-v")) {
                        lzw.LZWDecompress(args[2], args[3]);
                        File infile = new File(args[2]);
                        File outfile = new File(args[3]);
                        System.out.printf("bytes read = %d , bytes write = %d", infile.length(), outfile.length());
                    }
                } else {
                    System.out.println("Wrong arguments!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Wrong number of arguments!");
        }
    }
}

package first;

import java.io.*;
import java.util.*;
import java.nio.file.DirectoryStream;

public class Main {
    static String extension;
    static final String PATH = "D:\\1";

    public static void main(String[] args) throws Exception {
        String searchPhrase;
        ArrayList<File> files;
        ArrayList<Integer> filesWithPhrase;

        files = new ArrayList<File>();
        filesWithPhrase = new ArrayList<Integer>();
        extension = "txt";
        searchPhrase = "lol";
        listf(PATH, files);

        for (int i = 0; i < files.size(); i++) {
            boolean isIn;
            String message;
            isIn = isExist(files.get(i), searchPhrase);
            if (isIn) message =  "("+ files.get(i).getAbsolutePath() + ") YES ";
            else message = "("+ files.get(i).getAbsolutePath() + ") NO ";
            System.out.println(message);
        }
    }

    static Scanner input = new Scanner(System.in);

    static boolean isExist(File file, String phrase) throws IOException {
        String str;
        FileInputStream fin;
        fin = new FileInputStream(file.getAbsolutePath());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fin, 1024 * 1024);
        str = convertFromByteToString(bufferedInputStream);

        /*ArrayList<Integer> enteries;
        if (str.contains(phrase)) {
            enteries = new ArrayList<>();
            scoreEnteriesIndexes(str, enteries, phrase);
            for (int i = 0; i < enteries.size(); i++) {
                System.out.print(enteries.get(i)+ " ");
            }
            System.out.println();
        }*/
        return str.contains(phrase);
    }

    static void scoreEnteriesIndexes(String str, ArrayList<Integer> ents, String phrase) {
        int firstIndex;
        int lastIndex;
        firstIndex = str.indexOf(phrase);
        lastIndex = str.lastIndexOf(phrase);
        ents.add(firstIndex);
        while ((firstIndex != lastIndex) & (firstIndex < lastIndex)) {
            firstIndex = str.indexOf(phrase, (firstIndex + 1));
            ents.add(firstIndex);
        }
    }

    static String convertFromByteToString(BufferedInputStream bis) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            buf.write((byte) result);
            result = bis.read();
        }
        return buf.toString();
    }

    static String getMatchingFileAdress(FilenameFilter filter, File file) {

        return "adress";
    }

    public static void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);
        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    if (file.toString().toLowerCase().contains(extension))
                        files.add(file);
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
    }
}

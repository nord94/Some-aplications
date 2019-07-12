package first;

import java.io.*;
import java.util.*;

public class Main {
    static final String PATH = "D:\\1";
    static String extension = "txt";
    static String phrase = "lol";
    static ArrayList<SuitableFile> files = new ArrayList<>();
    static FastFileSearch[] threads;
    static final int NUM_OF_THREADS = 11;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        listf(PATH, files);
        threads = new FastFileSearch[NUM_OF_THREADS];
        FastFileSearch.codePhrase = phrase;
        while (FastFileSearch.counter != files.size()) {
            int i;
            int border = (files.size() - FastFileSearch.counter) % NUM_OF_THREADS == 0 ?
                    NUM_OF_THREADS : (files.size() - FastFileSearch.counter) % NUM_OF_THREADS;
            for (i = 0; i < border; i++) {
                threads[i] = new FastFileSearch("Thread #" + i, files.get(FastFileSearch.counter), phrase);
                threads[i].thrd.join();
                FastFileSearch.counter++;
            }
            System.out.printf("%d/%d files done\n", FastFileSearch.counter, files.size());
        }
        for (int i = 0; i < files.size(); i++) {
            System.out.printf("File (%s) contains (%s): %b\n", files.get(i).getFile().getAbsolutePath(), phrase
                    , files.get(i).isSuitable());
        }
    }

    static void listf(String directoryName, ArrayList<SuitableFile> files) {
        File directory = new File(directoryName);
        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    if (file.toString().toLowerCase().endsWith(extension))
                        files.add(new SuitableFile(file));
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
    }
}

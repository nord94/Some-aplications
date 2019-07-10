package first;

import java.io.*;
import java.util.*;
import java.nio.file.DirectoryStream;

class SuitableFile extends File {
    private boolean isSuitable;

    SuitableFile(String path, boolean suitable) {
        super(path);
        isSuitable = suitable;
    }

    SuitableFile(String path) {
        super(path);
    }

    public boolean isSuitable() {
        return isSuitable;
    }

    public void setSuitable(boolean suitable) {
        isSuitable = suitable;
    }
}

public class Main extends Thread {
    static String extension;
    static final String PATH = "D:\\1";
    static final int NUMOFTHREADS = 12;
    static ArrayList<SuitableFile> files = new ArrayList<>();
    int counter;
    static String searchPhrase;
    Thread thrd;

    public static void main(String[] args) throws Exception {
        Main[] threads;

        threads = new Main[12];
        extension = "txt";
        searchPhrase = "lol";
        listf(PATH, files);
        for (int i = 0; i < NUMOFTHREADS; i++) {
            threads[i] = new Main("Thread #" + i, i);
        }

        for (int i = 0; i < NUMOFTHREADS; i++) {
            try {
                threads[i].thrd.join();
                System.out.printf("Поток %d присоединен\n", i);
            }catch (InterruptedException exc){
                System.out.println("прерывание основного потока");
            }
        }
        for (int i = 0; i < files.size(); i++) {
            System.out.printf("File (%s) contains (%s): %b\n", files.get(i).getAbsolutePath(),
                    searchPhrase, files.get(i).isSuitable());
        }

        /*for (int i = 0; i < files.size(); i++) {
            files.get(i).setSuitable(isExist(files.get(i), searchPhrase));
            System.out.printf("File (%s) contains (%s): %b\n", files.get(i).getAbsolutePath(),
                    searchPhrase, files.get(i).isSuitable());
        }*/
    }

    Main(String name, int count) {
        thrd = new Thread(this, name);
        counter = count;
        thrd.start();
    }

    @Override
    public void run(){
        try {
            files.get(counter).setSuitable(isExist(files.get(counter), searchPhrase));
        }catch (IOException exc){
            System.out.println("Ошибка при чтении файла");
        }

    }

    boolean isExist(File file, String phrase) throws IOException {
        String str;
        FileInputStream fin;
        fin = new FileInputStream(file.getAbsolutePath());
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fin, 1024 * 1024);
        str = convertFromByteToString(bufferedInputStream);
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

    static void listf(String directoryName, ArrayList<SuitableFile> files) {
        File directory = new File(directoryName);
        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    if (file.toString().toLowerCase().contains(extension))
                        files.add(new SuitableFile(file.getAbsolutePath()));
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
    }
}

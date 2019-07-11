package first;

import java.io.*;
import java.util.ArrayList;

class SuitableFile {
    private boolean isSuitable;
    private final File file;
    String phrase;
    static final int MAX_BUF_SIZE = 1024 * 1024 * 200;

    SuitableFile(File inputFile) {
        file = inputFile;
    }

    public File getFile() {
        return file;
    }

    boolean isExist(File file, String phrase) {
        String str;
        FileInputStream fin;
        BufferedInputStream bufferedInputStream;
        str = "";
        try {
            fin = new FileInputStream(file);
            bufferedInputStream = new BufferedInputStream(fin, MAX_BUF_SIZE);
            str = convertFromByteToString(bufferedInputStream);
        } catch (IOException exc) {
            System.out.println(exc.getClass());
        }
        return str.contains(phrase);
    }

    private String convertFromByteToString(BufferedInputStream bis) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();
        while (result != -1) {
            buf.write((byte) result);
            result = bis.read();
        }
        return buf.toString();
    }

    public boolean isSuitable() {
        return isSuitable;
    }

    public void setSuitable(String searchPhrase) {
        isSuitable = isExist(file, searchPhrase);
    }

    static void scoreEnteriesIndexes(String str, ArrayList<Integer> ents, String phrase) {
        int firstIndex;
        int lastIndex;
        firstIndex = str.indexOf(phrase);
        lastIndex = str.lastIndexOf(phrase);
        ents.add(firstIndex);
        while (firstIndex < lastIndex) {
            firstIndex = str.indexOf(phrase, (firstIndex + 1));
            ents.add(firstIndex);
        }
    }
}

package first;

import java.io.*;
import java.util.ArrayList;

public class FastFileSearch implements Runnable {
    Thread thrd;
    SuitableFile file;
    static String phrase;

    @Override
    public void run() {
        file.setSuitable(phrase);
        System.out.printf("(%s) launched\n", thrd.getName());
    }

    FastFileSearch(String name, SuitableFile suitableFile, String searchPhrase) {
        thrd = new Thread(this, name);
        file = suitableFile;
        phrase = searchPhrase;
        thrd.start();
    }
}

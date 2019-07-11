package first;

import java.io.*;
import java.util.ArrayList;

public class FastFileSearch implements Runnable {
    Thread thrd;
    SuitableFile file;
    int counter;

    @Override
    public void run() {
        file.setSuitable();
        System.out.printf("(%s) launched\n", thrd.getName());
    }

    FastFileSearch(String name, SuitableFile suitableFile) {
        thrd = new Thread(this, name);
        file = suitableFile;
        thrd.start();
    }

}

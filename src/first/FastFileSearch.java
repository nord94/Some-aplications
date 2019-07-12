package first;

public class FastFileSearch implements Runnable {
    Thread thrd;
    SuitableFile file;
    static String codePhrase;
    static int counter = 0;

    @Override
    public void run() {
        file.setSuitable(codePhrase);
        System.out.printf("(%s) launched\n", thrd.getName());
    }

    FastFileSearch(String name, SuitableFile suitableFile, String searchPhrase) {
        thrd = new Thread(this, name);
        file = suitableFile;
        codePhrase = searchPhrase;
        thrd.start();
    }
}

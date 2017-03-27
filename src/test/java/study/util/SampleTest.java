package study.util;


/**
 * Created by Administrator on 2016/12/27.
 */
public class SampleTest {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(1000l);
            System.out.println("     "+i);
            javax.print.PrintServiceLookup.lookupDefaultPrintService();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("  **********");
                }
            }).run();
        }
    }
}

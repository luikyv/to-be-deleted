import metrics.TimeMeter;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {

        TimeMeter timer = new TimeMeter();
        timer.start();
        timer.finish();
        timer.start();
        timer.finish();
        timer.start();
        timer.finish();
        timer.start();
        timer.finish();
        timer.start();
        timer.finish();
        timer.start();
        timer.finish();
        System.out.println(timer.getMeanDurationMilliseconds());

    }
}

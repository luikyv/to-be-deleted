package metrics;

import java.util.ArrayList;

public class TimeMeter {

    private Long startTimeMilliseconds;
    public static Integer LAST_N_SAMPLES = 2;
    private ArrayList<Long> lastNSamples;

    public TimeMeter() {
        this.startTimeMilliseconds = 0L;
        this.lastNSamples = new ArrayList<>();
    }

    public void start() {
        this.startTimeMilliseconds = System.currentTimeMillis();
    }

    public void finish() {
        Long timeElapsed = System.currentTimeMillis() - this.startTimeMilliseconds;

        if(lastNSamples.size() == LAST_N_SAMPLES) {
            lastNSamples.remove(LAST_N_SAMPLES -1);
        }
        lastNSamples.add(0, timeElapsed);
        this.startTimeMilliseconds = 0L;
    }

    public Double getMeanDurationMilliseconds() {
        return lastNSamples.stream().mapToLong(s -> s).average().orElse(0);
    }
}

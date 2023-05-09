package observability;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;

import java.time.Duration;
import java.util.HashMap;

public class MeterManager {

    private static final MeterRegistry registry = new JmxMeterRegistry(new JmxConfig() {
        @Override
        public String get(String s) {
            return null;
        }

        @Override
        public String domain() {
            return "amc";
        }
    }, Clock.SYSTEM);

    // Maps to hold the meters created in runtime
    private static HashMap<String, Counter> counterMapper = new HashMap<>();
    private static HashMap<String, Timer> timeMapper = new HashMap<>();

    // Constants
    private static final String METER_TEMPLATE = "%s_%s_%s";
    private static final Integer TIME_TO_EXPIRATION_MINUTES = 30;

    public static Counter getCounter(String meterName, String business, String brand) {
        String counterKey = String.format(METER_TEMPLATE, meterName, business, brand);

        if(!counterMapper.containsKey(counterKey)) {
            Counter counter = Counter
                    .builder(meterName)
                    .tag("business", business)
                    .tag("brand", brand)
                    .register(registry);
            counterMapper.put(counterKey, counter);
            return counter;
        }

        return counterMapper.get(counterKey);
    }

    public static Timer getTimer(String meterName, String business, String brand) {
        String timerKey = String.format(METER_TEMPLATE, meterName, business, brand);

        if(!timeMapper.containsKey(timerKey)) {
            Timer timer = Timer
                    .builder(meterName)
                    .distributionStatisticExpiry(Duration.ofMinutes(TIME_TO_EXPIRATION_MINUTES))
                    .tag("business", business)
                    .tag("brand", brand)
                    .register(registry);
            timeMapper.put(timerKey, timer);
            return timer;
        }

        return timeMapper.get(timerKey);
    }
}

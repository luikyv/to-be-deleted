package observability;

import io.micrometer.core.instrument.*;
import io.micrometer.jmx.JmxConfig;
import io.micrometer.jmx.JmxMeterRegistry;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MeterManager implements MeterManagerMBean {

    static {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            server.registerMBean(new MeterManager(), new ObjectName("amc:name=meter_manager"));
        } catch (
                MalformedObjectNameException |
                InstanceAlreadyExistsException |
                MBeanRegistrationException |
                NotCompliantMBeanException e
        ) {
            // TODO: Log the exception
        }
    }

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
    private static final String METER_TEMPLATE = "%s.%s.%s";
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

    @Override
    public Map<String, Double> getCustomMetrics() {
        Map<String, Double> map = new HashMap<>();

        Map<String, Double> countMap = counterMapper.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().count())
        );
        map.putAll(countMap);

        Map<String, Double> timeMap = timeMapper.entrySet().stream().collect(
                Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().mean(TimeUnit.MILLISECONDS))
        );
        map.putAll(timeMap);

        return map;
    }
}

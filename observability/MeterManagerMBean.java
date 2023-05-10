package observability;

import java.util.Map;

public interface MeterManagerMBean {

    public Map<String, Double> getCustomMetrics();
}
